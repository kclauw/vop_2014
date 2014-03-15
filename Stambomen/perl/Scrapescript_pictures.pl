
# +-+-+-+-+-+-+-+
# |I|M|P|O|R|T|S|
# +-+-+-+-+-+-+-+ 

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





# +-+-+-+-+
# |I|N|I|T|
# +-+-+-+-+

my $url_pictures_links_men= "http://www.imdb.com/search/name?gender=male&sort=starmeter,asc&start=";
my $url_pictures_links_women= "http://www.imdb.com/search/name?gender=female&sort=starmeter,asc&start=";

my $timer_starttime = time();
my $numberofprints = 0;
my $timer_prevtime = 0;

my @pictures_men;
my @pictures_women;
my @personIDs_men;
my @personIDs_women;



# +-+-+-+-+-+-+-+-+-+-+-+
# |P|R|E|P|A|R|A|T|I|O|N|
# +-+-+-+-+-+-+-+-+-+-+-+

printlines("PREPARATION");

if (@ARGV != 2) {
	printlines("ERROR: Please include the csv-files with the men and women personIDs (in that order)");
	goto END;
}

#Get id's from two files
for (my $i = 0; $i <= 1; $i++) {
	my $personIDs = ($i)?\@personIDs_men:\@personIDs_women;

	open FILE, ("<" . $ARGV[$i]) or die $!;
	while (<FILE>) {
		push(@{$personIDs}, split(';', $_));
	}
	close(FILE);
	printlines((($i)?"Men":"Women") . " - Picked up personIDs from '" . $ARGV[$i] . "'");
}

for (my $i = 1; $i >= 0; $i--) {
	my @pictures_links;
	my $pictures = ($i)?\@pictures_men:\@pictures_women;
	my $personIDs = ($i)?\@personIDs_men:\@personIDs_women;

	while (@{$personIDs} > @pictures_links) {
		my $html_pictures_links = get($url_pictures_links_men . (@pictures_links + 1));
		if (!$html_pictures_links) {
			printlines("ERROR: Could not fetch all data");
			goto END;
		}
		$html_pictures_links = encode_utf8($html_pictures_links);
		push( @pictures_links, $html_pictures_links =~ m/<a href="(\/name\/nm.*?\/)".*?<img src=".*?".*?title=.*<\/a>/g );
	}
	printlines((($i)?"Men":"Women") . " - Picked up picture-urls");
	printlines((($i)?"Men":"Women") . " - Fetching pictures");
	my $lastprogress = -1;
	while (@{$personIDs} > @{$pictures}) {
		my $progress = int((@{$pictures} / @{$personIDs}) * 100);
		if ($lastprogress != $progress && $progress % 5 == 0)
		{
			$lastprogress = $progress;
			printlines("\tProgress: $progress%");
		}

		my $picture_link = $pictures_links[0];
		shift @pictures_links;

		my $html_picture = get("http://www.imdb.com" . $picture_link);
		if (!$html_picture) {
			printlines("ERROR: Could not fetch all data");
			goto END;
		}
		$html_picture = encode_utf8($html_picture);
		my @picture = $html_picture =~ m/<img id="name-poster".*?src="(.*?)".*?\/>/gs;

		my $ff = File::Fetch->new(uri => $picture[0]);
		my $file = $ff->fetch( to => "persons/" . @{$personIDs}[(@{$pictures}+0)] . ".jpg" );

		push( @{$pictures}, $file );
	}
	printlines("\tProgress: 100%");
	printlines((($i)?"Men":"Women") . " - Picked up " . @{$pictures} . " pictures");
}





# +-+-+-+-+-+-+-+-+-+-+
# |D|E|P|L|O|Y|M|E|N|T|
# +-+-+-+-+-+-+-+-+-+-+

printlines("DEPLOYEMENT");

#Assets info
my $assets_location = (($assets_release)?"release":"staging") . "/images/persons/";
my $assets_user = "team12";
my $assets_pass = "RKAxujnJ";
my $assets_name ="dav.assets.vop.tiwi.be/team12/";

#Bevestiging vragen
printlines("Are you sure you want to upload the pictures to http://$assets_name$assets_location? [y|n]");
my $continue = <STDIN>;
$continue = "n" if ( !defined $continue || $continue ne "y\n" );
printlines( "Answer: " . ( ($continue eq "y\n") ? "Positive - Proceeding script" : "Negative - Aborting script" ) );
goto END if ( $continue ne "y\n" );

my $dav = HTTP::DAV::Nginx -> new("http://$assets_user:$assets_pass\@$assets_name", die_on_errors => 1);

for (my $i = 1; $i >= 0; $i--) {
	my $pictures = ($i)?\@pictures_men:\@pictures_women;
	my $personIDs = ($i)?\@personIDs_men:\@personIDs_women;

	printlines((($i)?"Men":"Women") . " - Uploading pictures to http://$assets_name$assets_location");

	my $lastprogress = -1;
	my $filesuploaded = 0;
	foreach my $personID (@{$personIDs}) {
		my $progress = int(($filesuploaded / @{$personIDs}) * 100);
		if ($lastprogress != $progress && $progress % 5 == 0)
		{
			$lastprogress = $progress;
			printlines("\tProgress: $progress%");
		}

		$dav -> put($assets_location . $personID . ".jpg", file => @{$pictures}[0]);
		shift @{$pictures};
		++$filesuploaded;
	}
	printlines("\tProgress: 100%");
}
sleep 1;
rmtree "persons";




# +-+-+-+-+-+-+-+
# |G|E|N|E|R|A|L|
# +-+-+-+-+-+-+-+

sub printlines {
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

	my @myarray = @_;
	print join "\n", map { "[${timer_time}s [+${timer_diffprevtime}s]] $numberofprintsstr -> " . ( ($_ =~ m/^[A-Z]*$/g)?" ":"\t\t" ) . (($_ =~ s/\n/\n\t\t\t\t\t\t\t/g && defined $1)?$1:$_) } @myarray;
	print "\n";
}

END:
printlines("FINISHED");
print "\n";