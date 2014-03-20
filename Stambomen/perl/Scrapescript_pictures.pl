
# +-+-+-+-+-+-+-+
# |I|M|P|O|R|T|S|
# +-+-+-+-+-+-+-+ 

use DBI;
use Encode;
use File::Fetch;
use File::Path;
use HTTP::DAV::Nginx;
use IO::Handle;
use List::MoreUtils qw/ uniq /;
use List::Util 'shuffle';
use LWP::Simple;
use strict;
use Time::HiRes qw( time );
use Time::HiRes;
use warnings;
STDOUT->autoflush(1);





# +-+-+-+-+-+-+-+-+-+
# |C|O|N|S|T|A|N|T|S|
# +-+-+-+-+-+-+-+-+-+

my $assets_release = 0;
my $db_port_ownpc = 1;

my @treeIDs = ( 1 ); #IDs van de trees waarvan de personen foto's moeten krijgen





# +-+-+-+-+
# |I|N|I|T|
# +-+-+-+-+

#Sql-statements
my $sql_get_treepersonIDs = "select t.personID, p.gender from PersonTree t join Person p on t.personID=p.personID where t.treeID=?";

my @url_pictures_links = ("http://www.imdb.com/search/name?gender=female&sort=starmeter,asc&start=", "http://www.imdb.com/search/name?gender=male&sort=starmeter,asc&start=");

my $timer_starttime = time();
my $numberofprints = 0;
my $timer_prevtime = 0;

my %pictures;

my %personIDs;

my $lastprogressprint = "";
my $lastprintwasprogress = 0;



# +-+-+-+-+-+-+-+-+-+-+-+
# |P|R|E|P|A|R|A|T|I|O|N|
# +-+-+-+-+-+-+-+-+-+-+-+

printlines("PREPARATION");

#DB info
my $db_name ="team12_" . (($assets_release)?"release":"staging");
my $db_user = "team12";
my $db_pass = "RKAxujnJ";
my $db_host="db.vop.tiwi.be:" . (($db_port_ownpc)?"443":"3306");

#Connecteren
my $dbh = DBI->connect("DBI:mysql:$db_name:$db_host", $db_user, $db_pass, { PrintError => 0, mysql_enable_utf8 => 1 } ) or die DBI->errstr;
printlines("Fetching PersonIDs");
printlines("\tConnected to $db_host");

#Get id's from two files
foreach my $treeid (@treeIDs) {
	eval {
		my $sth = $dbh->prepare( $sql_get_treepersonIDs ) or die $dbh->errstr;
		$sth->execute($treeid) or die $dbh->errstr;

		while ( my ($personID, $gender) = $sth->fetchrow_array() ) {
		    push(@{$personIDs{$treeid}[$gender]}, $personID);
		}
		printlines("\tFetched PersonIDs for tree with ID: $treeid");
		1;
	} or do {
		printlines("\tERROR: " . ($@ =~ s/^\s+|\s+$//rg)) if ($@);
	};
}

$dbh->disconnect() or die $dbh->errstr;
printlines("\tDisconnected from $db_host");

#Get picture links
for (my $gender = 1; $gender >= 0; $gender--) {
	my $numberofpersons = 0;
	foreach my $treeid (keys %personIDs) {
		$numberofpersons = 0+@{$personIDs{$treeid}[$gender]} if (@{$personIDs{$treeid}[$gender]} > $numberofpersons);
	}
	printlines((($gender)?"Men":"Women") . " - Fetching $numberofpersons pictures");
	
	my $progressprecision = 100;
	$progressprecision *= 10 while ($numberofpersons > $progressprecision * 1);
	my $lastprogress = -1;
	printlinesbis("\tProgress: ");

	my $html_pictures_links_startoff = 1;
	while (getpictureslength($gender) < $numberofpersons) {
		#Links ophalen van één bepaalde index-pagina
		my $html_pictures_links = get($url_pictures_links[$gender] . $html_pictures_links_startoff);
		if (!$html_pictures_links) {
			printlines("ERROR: Could not fetch all data");
			goto END;
		}
		$html_pictures_links = encode_utf8($html_pictures_links);

		#Van elke van de acteurs de afbeelding ophalen
		foreach my $picture_link ( $html_pictures_links =~ m/<a href="(\/name\/nm.*?\/)".*?<img src=".*?".*?title=.*<\/a>/g ) {
			my $progress = int((getpictureslength($gender) / $numberofpersons) * $progressprecision);
			if ($lastprogress != $progress && $progress % 1 == 0)
			{
				$lastprogress = $progress;
				printprogress("\tProgress: " . ($progress/($progressprecision/100)) . "%");
			}

			++$html_pictures_links_startoff;

			my $html_picture = get("http://www.imdb.com" . $picture_link);
			if (!$html_picture) {
				printlines("ERROR: Could not fetch all data");
				goto END;
			}
			$html_picture = encode_utf8($html_picture);
			my @picture = $html_picture =~ m/<img id="name-poster".*?src="(.*?)"/gs;
			next if (!@picture);

			my $ff = File::Fetch->new(uri => $picture[0]);
			my $file = $ff->fetch( to => "tmp/persons/$gender/" . (getpictureslength($gender)+1) . ".jpg" );

			push( @{$pictures{$gender}}, $file );

			last if(getpictureslength($gender) == $numberofpersons);
		}
	}

	printprogress("\tProgress: 100%");
	print "\n";
}





# +-+-+-+-+-+-+-+-+-+-+
# |D|E|P|L|O|Y|M|E|N|T|
# +-+-+-+-+-+-+-+-+-+-+

printlines("DEPLOYEMENT");

#Assets info
my $assets_location = (($assets_release)?"release":"staging") . "/images/persons";
my $assets_name ="http://team12:RKAxujnJ\@dav.assets.vop.tiwi.be/team12/";

#Bevestiging vragen
printlines("Are you sure you want to upload the pictures to http://$assets_name$assets_location? [y|n]");
my $continue = <STDIN>;
$continue = "n" if ( !defined $continue || $continue ne "y\n" );
printlines( "Answer: " . ( ($continue eq "y\n") ? "Positive - Proceeding script" : "Negative - Aborting script" ) );
goto END if ( $continue ne "y\n" );

my $dav = HTTP::DAV::Nginx -> new($assets_name, die_on_errors => 1);

for (my $gender = 1; $gender >= 0; $gender--) {

	my $numberofpersons = 0;
	foreach my $treeid (keys %personIDs) {
		$numberofpersons += @{$personIDs{$treeid}[$gender]};
	}
	printlines((($gender)?"Men":"Women") . " - Uploading $numberofpersons pictures");

	my $progressprecision = 100;
	$progressprecision *= 10 while ($numberofpersons > $progressprecision * 10);
	my $lastprogress = -1;
	printlinesbis("\tProgress: ");

	my $filesuploaded = 0;
	foreach my $treeid (keys %personIDs) {
		eval
		{
			$dav -> mkcol("$assets_location/$treeid");
		};

		my @pictures_copy = shuffle @{$pictures{$gender}};

		foreach my $personID (@{$personIDs{$treeid}[$gender]}) {
			my $progress = int(($filesuploaded / $numberofpersons) * $progressprecision);
			if ($lastprogress != $progress && $progress % 1 == 0)
			{
				$lastprogress = $progress;
				printprogress("\tProgress: " . ($progress/($progressprecision/100)) . "%");
			}

			$dav -> put("$assets_location/$treeid/$personID.jpg", file => $pictures_copy[0]);
			shift @pictures_copy;

			++$filesuploaded;
		}
	}
	printprogress("\tProgress: 100%");
	print "\n";
}
sleep 1;
rmtree "tmp";




# +-+-+-+-+-+-+-+
# |G|E|N|E|R|A|L|
# +-+-+-+-+-+-+-+

sub getpictureslength {
	my ($gender) = @_;

	return 0 if (!defined $pictures{$gender});
	return @{$pictures{$gender}};
}

sub printprogress {
	my $temp = $timer_prevtime;
	print "\r";
	--$numberofprints;
	printlinesbis(($lastprogressprint));
	$timer_prevtime = $temp;

	$temp = $timer_prevtime;
	print "\r";
	--$numberofprints;
	printlinesbis(@_);
	$lastprogressprint = $_[0];
	$lastprogressprint =~ s/[^\t]/ /g;
	$timer_prevtime = $temp;

	$lastprintwasprogress = 1;
}

sub printlinesbis {
	++$numberofprints;
	my $timer_time = time() - $timer_starttime;
	my $timer_diffprevtime = $timer_time - $timer_prevtime;
	$timer_prevtime = $timer_time;

	$timer_time = sprintf("%.2f", $timer_time);
	$timer_time = (" " x (6 - length($timer_time))) . $timer_time;
	$timer_diffprevtime = sprintf("%.2f", $timer_diffprevtime);
	$timer_diffprevtime = (" " x (6 - length($timer_diffprevtime))) . $timer_diffprevtime;

	my $numberofprintsstr = "" . $numberofprints;
	$numberofprintsstr = (" " x (3 - length($numberofprintsstr))) . $numberofprintsstr;

	my @myarray = $_[0];
	print join "\n", map { "[${timer_time}s [+${timer_diffprevtime}s]] $numberofprintsstr -> " . ( ($_ =~ m/^[A-Z]*$/g)?" ":"\t\t" ) . (($_ =~ s/\n/\n\t\t\t\t\t\t\t/g && defined $1)?$1:$_) } @myarray;
}
sub printlines {
	if ($lastprintwasprogress)
	{
		$lastprintwasprogress = 0;
		print "\n";
	}
	printlinesbis(@_);
	print "\n";
}

END:
printlines("FINISHED");
print "\n";