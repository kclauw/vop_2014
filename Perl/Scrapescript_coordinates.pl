
# +-+-+-+-+-+-+-+
# |I|M|P|O|R|T|S|
# +-+-+-+-+-+-+-+ 

use DBI;
use Encode qw(decode encode);
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

my $db_release = 0;
my $db_port_ownpc = 1;





# +-+-+-+-+
# |I|N|I|T|
# +-+-+-+-+

#Sql-statements
my $sql_lock = "LOCK TABLES Place WRITE, Coordinates WRITE";
my $sql_unlock = "UNLOCK TABLES";

my $sql_get_places = "select p.placeId, p.zipcode, n.name, c.name from Place p join Country c on p.countryID = c.countryID join Placename n on p.placenameID = n.placenameID where p.coordinatesID is null";
my $sql_insert_coordinate = "insert into Coordinates (latitude, longitude) values(?,?) on duplicate key update latitude=?, longitude=?";
my $sql_get_coordinate = "\tset ? = ( select coordinatesID from Coordinates where latitude=? and longitude=? )";
my $sql_update_place = "\tupdate Place set coordinatesID = ? where placeId = ?";

my @sql;
my @sql_parameters;
my $numberofsqlvariables = 0;

my $timer_starttime = time();
my $numberofprints = 0;
my $timer_prevtime = 0;

my @places;

my $lastprogressprint = "";
my $lastprintwasprogress = 0;



# +-+-+-+-+-+-+-+-+-+-+-+
# |P|R|E|P|A|R|A|T|I|O|N|
# +-+-+-+-+-+-+-+-+-+-+-+

printlines("PREPARATION");

#DB info
my $db_name ="team12_" . (($db_release)?"release":"staging");
my $db_user = "team12";
my $db_pass = "RKAxujnJ";
my $db_host="db.vop.tiwi.be:" . (($db_port_ownpc)?"443":"3306");

#Connecteren
my $dbh = DBI->connect("DBI:mysql:$db_name:$db_host", $db_user, $db_pass, { PrintError => 0, mysql_enable_utf8 => 1 } ) or die DBI->errstr;
printlines("Fetching places");
printlines("\tConnected to $db_host");

#Get id's from two files
eval {
	my $sth = $dbh->prepare( $sql_get_places ) or die $dbh->errstr;
	$sth->execute() or die $dbh->errstr;

	while ( my @place = $sth->fetchrow_array() ) {
		$place[0] = encode("utf-8", $place[0]);
		$place[1] = encode("utf-8", $place[1]);
		$place[2] = encode("utf-8", $place[2]);
		$place[3] = encode("utf-8", $place[3]);
	    push(@places, \@place);
	}
	1;
} or do {
	printlines("\tERROR: " . ($@ =~ s/^\s+|\s+$//rg)) if ($@);
};

$dbh->disconnect() or die $dbh->errstr;
printlines("\tDisconnected from $db_host");

#Fetching Coordinates from Google API
printlines("Fetching coordinates");

my $google_api ="https://maps.googleapis.com/maps/api/geocode/xml?address={0}+{1},+{2}&sensor=false&key=AIzaSyAvtR35KQlMs6IuWdR7Bx3bvnOEIpl1C3w";

addtoSQL($sql_lock);

my $progressprecision = 1000;
$progressprecision *= 10 while (@places > $progressprecision * 1000);
my $lastprogress = -1;
printlinesbis("\tProgress: ");
my $numberofstatements_total = 0+@places;
my $numberofstatements_done = 0;

my $fetchcount = 0;
foreach my $place (@places) {
	++$numberofstatements_done;
	my $progress = int(($numberofstatements_done / $numberofstatements_total) * $progressprecision);
	if ($lastprogress != $progress && $progress % 1 == 0)
	{
		$lastprogress = $progress;
		printprogress("\tProgress: " . ($progress/($progressprecision/100)) . "%");
	}

	my ($placeId, $zipcode, $placename, $country) = @{$place};
	my $url = $google_api;
	$url =~ s/\{0\}/$zipcode/g;
	$url =~ s/\{1\}/$placename/g;
	$url =~ s/\{2\}/$country/g;
	$url =~ s/\s+/+/g;

	my $result = get $url;
	if ($result) {
		my ($latitude, $longitude) = $result =~ m/<location>\s*?\n\s*?<lat>(.*?)<\/lat>\s*?\n\s*?<lng>(.*?)<\/lng>\s*?\n\s*?<\/location>/g;
		if($latitude && $longitude){
			addtoSQL($sql_insert_coordinate, $latitude, $longitude, $latitude, $longitude);
			my $var_coordid = getsqlvariablename();
			addtoSQL($sql_get_coordinate, $var_coordid, $latitude, $longitude);
			addtoSQL($sql_update_place, $var_coordid, $placeId);
			++$fetchcount;
		}
	}
}
addtoSQL("\n" . $sql_unlock);
printlines("Fetched $fetchcount/" . @places . " coordinates");
fillvarsinSQL();
printlines("Query fully generated");





# +-+-+-+-+-+-+-+-+-+-+
# |D|E|P|L|O|Y|M|E|N|T|
# +-+-+-+-+-+-+-+-+-+-+

printlines("DEPLOYEMENT");

#Bevestiging vragen
printlines("Are you sure you want to fetch the coordinates of incomplete places? [y|n]");
my $continue = <STDIN>;
$continue = "n" if ( !defined $continue || $continue ne "y\n" );
printlines( "Answer: " . ( ($continue eq "y\n") ? "Positive - Proceeding script" : "Negative - Aborting script" ) );
goto END if ( $continue ne "y\n" );


#Connecteren
$dbh = DBI->connect("DBI:mysql:$db_name:$db_host", $db_user, $db_pass, { PrintError => 0, mysql_enable_utf8 => 1 } ) or die DBI->errstr;
printlines("Connected to $db_host");

$dbh->begin_work or die $dbh->errstr;
printlines("Started transaction");

$progressprecision = 100;
$progressprecision *= 10 while (@sql > $progressprecision * 1000);
$lastprogress = -1;
printlinesbis("\tProgress: ");

eval {
	my $numberofstatements_total = 0+@sql;
	my $numberofstatements_done = 0;

	foreach my $statements (@sql) {
		++$numberofstatements_done;
		my $progress = int(($numberofstatements_done / $numberofstatements_total) * $progressprecision);
		if ($lastprogress != $progress && $progress % 1 == 0)
		{
			$lastprogress = $progress;
			printprogress("\tProgress: " . ($progress/($progressprecision/100)) . "%");
		}

		foreach my $statement (split(m/\n/x, $statements )) {
			next if ($statement =~ m/^\s*#.*$/g || $statement =~ m/^\s*$/g);

			my $numberofparameters = $statement =~ tr/?//;
			my @parameters = splice(@sql_parameters, 0, $numberofparameters);

			my $sth = $dbh->prepare( $statement ) or die $dbh->errstr;
			$sth->execute(@parameters) or die $dbh->errstr;

		}
	}

	$dbh->commit() or $dbh->errstr;
	printlines("Commited changes");
	1;
} or do {
	printlines("ERROR: " . ($@ =~ s/^\s+|\s+$//rg)) if ($@);
    $dbh->rollback();
	printlines("Rolledback changes");
};

$dbh->disconnect() or die $dbh->errstr;
printlines("Disconnected from $db_host");





# +-+-+-+-+-+-+-+
# |G|E|N|E|R|A|L|
# +-+-+-+-+-+-+-+

sub addtoSQL {
	my $first = 1;
	foreach (@_) {
		if ($first)
		{
			$first = 0;
			push(@sql, $_ . ";");
		} else {
			push(@sql_parameters, $_);
		}
	}
}
sub getsqlvariablename {
	++$numberofsqlvariables;
	return "\@var" . $numberofsqlvariables;
}

sub fillvarsinSQL {
	printlines("Filling in variables in query");

	my @sql_parameters_copy = @sql_parameters;
	@sql_parameters = ();

	my $lastprogress = -1;
	printlinesbis("\tProgress: ");

	my $j = 0;
	for (my $i = 0; $i < @sql; $i++) {
		my $progress = int(($i / @sql) * 100);
		if ($lastprogress != $progress && $progress % 10 == 0)
		{
			$lastprogress = $progress;
			printprogress("\tProgress: $progress%");
		}
		my @statementelements = split(m/\?/x, $sql[$i] );
		if (@statementelements > 1)
		{
			$sql[$i] = $statementelements[0];
			for (my $k = 1; $k < @statementelements; $k++) {

				my $el = "?";
				if ($sql_parameters_copy[$j] =~ /^\@var.*/)
				{
					$el = $sql_parameters_copy[$j];
				} else {
					push(@sql_parameters, $sql_parameters_copy[$j]);
				}
				$sql[$i] = $sql[$i] . $el . $statementelements[$k];

				++$j;
			}
		}
	}
	printprogress("\tProgress: 100%");
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