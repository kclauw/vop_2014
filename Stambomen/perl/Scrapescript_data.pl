
# +-+-+-+-+-+-+-+
# |I|M|P|O|R|T|S|
# +-+-+-+-+-+-+-+ 

use Date::Calc qw(Add_Delta_Days);
use DBI;
use Encode qw(decode encode);
use File::Basename;
use IO::Handle;
use List::MoreUtils qw/ uniq /;
use List::Util 'shuffle';
use LWP::Simple;
use strict;
use Time::HiRes qw( time );
use Time::HiRes;
use utf8;
use warnings;
STDOUT->autoflush(1);





# +-+-+-+-+-+-+-+-+-+
# |C|O|N|S|T|A|N|T|S|
# +-+-+-+-+-+-+-+-+-+

#DB info
my $db_release = 0;
my $db_port_ownpc = 1;
#Wegschrijven van de query (is enkel om te controleren, niet om te gebruiken)
#OPGELET!: Dit kan het script doen vastlopen wanneer er enorm veel statements worden gegenereerd!
my $writequery = 0;
my $openwrittenfile = 0;
#Hoe moet de boom worden ingevuld?
#USERS
my %users = ( "Lowie" => "123456789" ); #Users
# my %users = ( "Axl" => "123456789", "Jelle" => "123456789", "Sander" => "123456789", "Lowie" => "123456789", "Kenzo" => "123456789" ); #Users
#TREES
my @numberoftrees = 4..8; #Variatie in aantal bomen per user
my @maxnumberofpersons = 50..70; #Maximum aantal personen per boom
#PERSONS
my @privacyoptions = 0..2; #Variatie in de privacy van een boom
my @headyearofbirth = 1600..1800; #Variatie in geboortejaar van hoofd van familie
my @childrenperperson = (0..6, 0..5, 0..4, 0..3, 0..2, 0..1, 0); #Variatie in aantal kinderen per persoon
my @personage = (70..110, 72..105, 74..100, 76..95, 78..90, 80..85); #Variatie in leeftijd van een persoon
my @partneragedifference = (-10..10, -5..10, 0..10); #Variatie in verschil in leeftijd tussen partners
my @parentagehavingchild = (20..40, 22..36, 24..32, 26..28); #Variatie in leeftijd waarop ouder kind krijgt
#PLACES
my $oddsotherprovince = 5; #Kans andere provincie (1 op ...)
my $oddsothercountry = 20; #Kans ander land (1 op ...)





# +-+-+-+-+
# |I|N|I|T|
# +-+-+-+-+

#Sql-statements
my $sql_lock = "LOCK TABLES User WRITE, Tree WRITE, Country WRITE, Placename WRITE, Place WRITE, Person WRITE, PersonTree WRITE, ParentRelation WRITE";
my $sql_unlock = "UNLOCK TABLES";
my $sql_set_nullvar = "set \@var0 = null";
my $sql_lastinsertid = "set ? = ( select last_insert_id() )";
	#User
my $sql_add_user = "\n#USER\ninsert into User (username, password) values (?,?)";
	#Tree
my $sql_add_tree = "\t#TREE\n\tinsert into Tree (ownerID, privacy, name) values (?,?,?)";
	#Place
my $sql_add_country = "\t\t\t#PLACE\n\t\t\tinsert into Country (name) values (?) on duplicate key update name=?";
my $sql_get_countryfromname = "\t\t\tset ? = ( select countryID from Country where name = ? )";
my $sql_add_placename = "\t\t\tinsert into Placename (name) values (?) on duplicate key update name=?";
my $sql_get_placenamefromname = "\t\t\tset ? = ( select placenameID from Placename where name = ? )";
my $sql_add_place = "\t\t\tinsert into Place (countryID, zipcode, placenameID) values (?,?,?) on duplicate key update countryID=?, zipcode=?, placenameID=?";
my $sql_get_place = "\t\t\tset ? = ( select placeID from Place where countryID=? and zipcode=? and placenameID=? )";
	#Person
my $sql_add_person = "\t\t#PERSON\n\t\tinsert into Person (firstname,lastname,gender,birthdate,deathdate,birthplace) values (?,?,?,?,?,?)";
my $sql_add_persontree = "\t\tinsert into PersonTree (treeId, personID) values (?,?)";
my $sql_add_parentrelation = "\t\tinsert into ParentRelation (treeID,parent,child) values (?,?,?)";

#Variables
my $url_men= "http://www.naamkunde.net/?page_id=293&vt_list_male=true";
my $url_women= "http://www.naamkunde.net/?page_id=293&vt_list_female=true";
my $url_surnames= "http://home.scarlet.be/~rg588291/surname.htm";
my $url_places= "http://postcodezoeker.be/Postcodes.php";

my $timer_starttime = time();
my $numberofprints = 0;
my $timer_prevtime = 0;

my @sql = ( ($sql_set_nullvar . ";") );
my @sql_parameters;
my $numberofsqlvariables = 0;

my @personstack;

my $lastprogressprint = "";
my $lastprintwasprogress = 0;





# +-+-+-+-+-+-+-+-+-+-+-+
# |P|R|E|P|A|R|A|T|I|O|N|
# +-+-+-+-+-+-+-+-+-+-+-+

printlines("PREPARATION");

#Mannen ophalen
my $html_men = get $url_men;
goto DATACHECK if (!$html_men);
$html_men = encode("utf-8", $html_men);
my @men = $html_men =~ m/<td>([A-Za-z äâ éèëê ïî öô üû ç ]*) \(M\)<\/td>/g;
goto DATACHECK if (@men == 0);
(s/([A-Za-zäâéèëêïîöôüûç]+)/ucfirst(lc($1))/ge) for @men;
printlines("Picked up " . @men . " mennames");
#Vrouwen ophalen
my $html_women = get $url_women;
goto DATACHECK if (!$html_women);
$html_women = encode("utf-8", $html_women);
my @women = $html_women =~ m/<td>([A-Za-z äâ éèëê ïî öô üû ç ]*) \(V\)<\/td>/g;
goto DATACHECK if (@women == 0);
(s/([A-Za-zäâéèëêïîöôüûç]+)/ucfirst(lc($1))/ge) for @women;
printlines("Picked up " . @women . " womennames");
#Familienamen ophalen
my $html_surnames = get $url_surnames;
goto DATACHECK if (!$html_surnames);
$html_surnames = encode("utf-8", $html_surnames);
my @surnames = shuffle ( $html_surnames =~ m/<a href="idx.*?[^.]">([A-Za-z äâ éèëê ïî öô üû ç ]*)<\/a>/g );
goto DATACHECK if (@surnames == 0);
(s/([A-Za-zäâéèëêïîöôüûç]+)/ucfirst(lc($1))/ge) for @surnames;
printlines("Picked up " . @surnames . " surnames");
#Plaatsen ophalen
my $html_places = get $url_places;
goto DATACHECK if (!$html_places);
$html_places = encode("utf-8", $html_places);
my %places;
my @placesrough = $html_places =~ m/<tr.*?><td.*?>(.*?)<\/td>.*?<td.*?><a.*?>(.*?)<\/a>.*?<a.*?>(.*?)<\/a>.*?<\/tr>/g;
(s/([A-Za-zäâéèëêïîöôüûç]+)/ucfirst(lc($1))/ge) for @placesrough;
(s/ ?\(.*?\)//g) for @placesrough;
(s/\/.*//g) for @placesrough;
my $placesaantal = @placesrough / 3;
goto DATACHECK if ($placesaantal == 0);
my $cry = encode("utf-8", "België");
while( my($pc, $pl, $pr) = ( @placesrough ) ) {
	$places{$cry}{$pr}{$pc} = $pl;
	splice(@placesrough,0,3);
}
printlines("Picked up " . $placesaantal . " places");

DATACHECK:
if (!$html_men || !$html_women || !$url_surnames || !$html_places || @men == 0 || @women == 0 || @surnames == 0 || $placesaantal == 0) {
	printlines("ERROR: Could not fetch all data");
	goto END;
}





# +-+-+-+-+-+-+-+-+-+-+
# |G|E|N|E|R|A|T|I|N|G|
# +-+-+-+-+-+-+-+-+-+-+

printlines("GENERATING");

addtoSQL($sql_lock);
foreach my $username (keys %users) {

	my $password = $users{$username};
	#User toevoegen
	addtoSQL($sql_add_user, $username, $password);
	my $var_userid = getsqlvariablename();
	addtoSQL($sql_lastinsertid, $var_userid);

	#Aantal bomen vastleggen
	my $trees = getnumberoftrees();

	printlines("Generating $trees trees for " . $username);
	printlinesbis("\tProgress: ");

	for (my $m = 0; $m < $trees; $m++) { #Loop voor bomen
		printprogress("\tProgress: tree " . ($m+1) . " out of $trees");

		@personstack = ();
		#Familienaam bepalen
		my $data_head_surname = getsurname();
		#Boom toevoegen
		addtoSQL($sql_add_tree, $var_userid, getprivacyoption(), $data_head_surname);
		my $var_treeid = getsqlvariablename();
		addtoSQL("\t" . $sql_lastinsertid, $var_treeid);

		#Hoofd van familie
		my $data_head_firstname = getmanname();
		my $data_head_country = getcountry();
		my $data_head_province = getprovince($data_head_country);
		my $data_head_zipcode = getzipcode($data_head_country, $data_head_province);
		my $data_head_place = getplace($data_head_country, $data_head_province, $data_head_zipcode);
		my $data_head_birthdate = getbirthdatehead();
		my $data_head_deathdate = getdeathdate($data_head_birthdate);

		my $var_head_placeid = addplacetoSQL($data_head_country, $data_head_zipcode, $data_head_place); #Place
		#(firstname,lastname,gender,birthdate,deathdate,birthplace)
		my $var_head_personid = addpersontoSQL($data_head_firstname, $data_head_surname, 1, $data_head_birthdate, $data_head_deathdate, $var_head_placeid);
		addtoSQL($sql_add_persontree, $var_treeid, $var_head_personid);
		my $head_childrennumber = 0;
		while ($head_childrennumber == 0) {
			$head_childrennumber = getchildrennumber();
		}
		push(@personstack, [($var_treeid, $var_head_personid, $data_head_surname, 1, $data_head_birthdate, $data_head_country, $data_head_province, $head_childrennumber)]);

		#Aantal personen vastleggen
		my $n = getmaxnumberofpersons();
		while ($n > 0) { #Loop voor personen
			$n -= 0+$personstack[0][7];

			addchildren($personstack[0]);
			shift @personstack;
		}
	}
}
addtoSQL("\n" . $sql_unlock);

fillvarsinSQL();
printlines("Query fully generated");
testprintSQL() if $writequery;





# +-+-+-+-+-+-+-+-+-+-+
# |D|E|P|L|O|Y|M|E|N|T|
# +-+-+-+-+-+-+-+-+-+-+

printlines("DEPLOYEMENT");

#DB info
my $db_name ="team12_" . (($db_release)?"release":"staging");
my $db_user = "team12";
my $db_pass = "RKAxujnJ";
my $db_host="db.vop.tiwi.be:" . (($db_port_ownpc)?"443":"3306");

#Bevestiging vragen
printlines("Are you sure you want to execute the query in '$db_name' on '$db_host'? [y|n]");
my $continue = <STDIN>;
$continue = "n" if ( !defined $continue || $continue ne "y\n" );
printlines( "Answer: " . ( ($continue eq "y\n") ? "Positive - Proceeding script" : "Negative - Aborting script" ) );
goto END if ( $continue ne "y\n" );

Time::HiRes::sleep(0.1);
#Connecteren
my $dbh = DBI->connect("DBI:mysql:$db_name:$db_host", $db_user, $db_pass, { PrintError => 0, mysql_enable_utf8 => 1 } ) or die DBI->errstr;
printlines("Connected to $db_host");

$dbh->begin_work or die $dbh->errstr;
printlines("Started transaction");

my $progressprecision = 100;
$progressprecision *= 10 while (@sql > $progressprecision * 1000);
my $lastprogress = -1;
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
			printlines("\tDoing: Adding user") if ($statement =~ m/^\s*#USER.*$/g);
			printlines("\tDoing: Adding tree") if ($statement =~ m/^\s*#TREE.*$/g);
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

sub addchildren {
	my ($var_treeid, $var_parent_personid, $data_parent_surname, $data_parent_gender, $data_parent_birthdate, $data_parent_country, $data_parent_province, $childrennumber) = @{$_[0]};

	#PARTNER
	my $data_partner_gender = ($data_parent_gender)?0:1; #Gender
	my $data_partner_firstname = $data_partner_gender?getmanname():getwomanname(); #Firstname
	my $data_partner_surname = getsurname(); #Surname
	my ($data_partner_country, $data_partner_province, $data_partner_zipcode, $data_partner_place) = getplacefull($data_parent_country, $data_parent_province); #Place
	my $data_partner_birthdate = getbirthdatepartner($data_parent_birthdate); #Birtdate
	my $data_partner_deathdate = getdeathdate($data_partner_birthdate); #Birtdate
	my $var_partner_placeid = addplacetoSQL($data_partner_country, $data_partner_zipcode, $data_partner_place); #Place
	#(firstname,lastname,gender,birthdate,deathdate,birthplace)
	my $var_partner_personid = addpersontoSQL($data_partner_firstname, $data_partner_surname, $data_partner_gender, $data_partner_birthdate, $data_partner_deathdate, $var_partner_placeid);
	addtoSQL($sql_add_persontree, $var_treeid, $var_partner_personid);

	for (my $i = 0; $i < $childrennumber; $i++) {
		#CHILD
		my $data_child_gender = random(2); #Gender
		my $data_child_firstname = $data_child_gender?getmanname():getwomanname(); #Firstname
		my $data_child_lastname = $data_parent_gender?$data_parent_surname:$data_partner_surname; #Surname
		my ($data_child_country, $data_child_province, $data_child_zipcode, $data_child_place);
		if (random(2) == 0) {
			($data_child_country, $data_child_province, $data_child_zipcode, $data_child_place) = getplacefull($data_parent_country, $data_parent_province); #Place
		} else {
			($data_child_country, $data_child_province, $data_child_zipcode, $data_child_place) = getplacefull($data_partner_country, $data_partner_province); #Place
		}
		my $data_child_birthdate = getbirthdatechild(($data_parent_birthdate gt $data_partner_birthdate)?$data_parent_birthdate:$data_partner_birthdate); #Birtdate
		my $data_child_deathdate = getdeathdate($data_child_birthdate); #Birtdate
		my $var_child_placeid = addplacetoSQL($data_child_country, $data_child_zipcode, $data_child_place); #Birtdate
		#(firstname,lastname,gender,birthdate,deathdate,birthplace)
		my $var_child_personid = addpersontoSQL($data_child_firstname, $data_child_lastname, $data_child_gender, $data_child_birthdate, $data_child_deathdate, $var_child_placeid);
		addtoSQL($sql_add_persontree, $var_treeid, $var_child_personid);

		#RELATIONS
		addtoSQL($sql_add_parentrelation, $var_treeid, $var_parent_personid, $var_child_personid);
		addtoSQL($sql_add_parentrelation, $var_treeid, $var_partner_personid, $var_child_personid);

		#CHILDREN
		my $child_childrennumber = getchildrennumber();
		while ($child_childrennumber == 0 && @personstack <= 1) {
			$child_childrennumber = getchildrennumber();
		}

		push(@personstack, [($var_treeid, $var_child_personid, $data_child_lastname, $data_child_gender, $data_child_birthdate, $data_child_country, $data_child_province, $child_childrennumber)]) if ($child_childrennumber != 0);
	}

}

sub getplacefull {
	my ($data_country, $data_province, $data_zipcode, $data_place);

	if (@_ == 2)
	{
		my ($data_relative_country, $data_relative_province) = @_;
		$data_country = getcountry($data_relative_country);
		if ($data_country eq $data_relative_country)
		{
			$data_province = getprovince($data_country, $data_relative_province);
		} else {
			$data_province = getprovince($data_country);
		}
	}
	else
	{
		$data_country = getcountry();
		$data_province = getprovince($data_country);
	}
	$data_zipcode = getzipcode($data_country, $data_province);
	$data_place = getplace($data_country, $data_province, $data_zipcode);

	return ($data_country, $data_province, $data_zipcode, $data_place);
}

sub addplacetoSQL {
	my ($data_country, $data_zipcode, $data_placename) = @_;

	addtoSQL($sql_add_country, $data_country, $data_country);
	my $var_countryid = getsqlvariablename();
	addtoSQL($sql_get_countryfromname, $var_countryid, $data_country);

	addtoSQL($sql_add_placename, $data_placename, $data_placename);
	my $var_placenameid = getsqlvariablename();
	addtoSQL($sql_get_placenamefromname, $var_placenameid, $data_placename);

	addtoSQL($sql_add_place, $var_countryid, $data_zipcode, $var_placenameid, $var_countryid, $data_zipcode, $var_placenameid);
	my $var_placeid = getsqlvariablename();
	addtoSQL($sql_get_place, $var_placeid, $var_countryid, $data_zipcode, $var_placenameid);

	return $var_placeid;
}
sub addpersontoSQL {
	my ($data_firstname, $data_lastname, $data_gender, $data_birthdate, $data_deathdate, $data_birthplace) = @_;

	addtoSQL($sql_add_person, $data_firstname, $data_lastname, $data_gender, $data_birthdate, $data_deathdate, $data_birthplace);
	my $var_personid = getsqlvariablename();
	addtoSQL("\t\t" . $sql_lastinsertid, $var_personid);

	return $var_personid;
}
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

sub getsurname {
	return $surnames[random(0+@surnames)];
}
sub getmanname {
	return $men[random(0+@men)];
}
sub getwomanname {
	return $women[random(0+@women)];
}
sub getprivacyoption {
	return $privacyoptions[random(0+@privacyoptions)];
}
sub getnumberoftrees {
	return $numberoftrees[random(0+@numberoftrees)];
}
sub getcountry {
	if (@_ == 1 && random($oddsothercountry) != 0)
	{
		return $_[0];
	}
	return (keys %places)[random(0+(keys %places))];
}
sub getprovince {
	if (@_ == 2 && random($oddsotherprovince) != 0)
	{
		return $_[1];
	}
	my %hash = %{$places{$_[0]}};
	return (keys %hash)[random(0+(keys %hash))];
}
sub getzipcode {
	my %hash = %{$places{$_[0]}{$_[1]}};
	return (keys %hash)[random(0+(keys %hash))];
}
sub getplace {
	return $places{$_[0]}{$_[1]}{$_[2]};
}
sub getchildrennumber {
	return $childrenperperson[random(0+@childrenperperson)];
}
sub getmaxnumberofpersons {
	return $maxnumberofpersons[random(0+@maxnumberofpersons)];
}
sub adddaystodate {
	my ($date, $daystoadd) = @_;
	my ($year, $month, $day) = $date =~ m/([0-9]+)-([0-9]+)-([0-9]+)/g;
	($year, $month, $day) = Add_Delta_Days($year, $month, $day, $daystoadd);
	return "$year-$month-$day";
}
sub getbirthdatechild {
	my $date = $_[0];
	return adddaystodate($date, ($parentagehavingchild[random(0+@parentagehavingchild)] * 365) + random(365));
}
sub getdeathdate {
	my $date = $_[0];
	return adddaystodate($date, ($personage[random(0+@personage)] * 365) + random(365));
}
sub getbirthdatepartner {
	my $date = $_[0];
	return adddaystodate($date, ($partneragedifference[random(0+@partneragedifference)] * 365) + random(365));
}
sub getbirthdatehead {
	return adddaystodate($headyearofbirth[random(0+@headyearofbirth)] . "-01-01", random(365));
}

sub random() {
	return 0 if ($_[0] <= 1);
	return int((rand ($_[0]-1))+ 0.5);
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

	my @myarray = @_;
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
sub testprintSQL {
	my $precomment = 	"#Script generated for Team12 on " . localtime(time) . 
				"\n" .	"#\tConstants:" . 
				"\n" .	"#\t  - Number of trees\t\t\t= [" . $numberoftrees[0] . "," . @numberoftrees[@numberoftrees - 1] . "]" . 
				"\n" .	"#\t  - Max number of persons\t= [" . $maxnumberofpersons[0] . "," . @maxnumberofpersons[@maxnumberofpersons - 1] . "]" . 
				"\n" .	"#\t  - Children per person\t\t= [" . $childrenperperson[0] . "," . @childrenperperson[@childrenperperson - 1] . "]" . 
				"\n" .	"#\t  - Odds other province\t\t= 1 in $oddsotherprovince" . 
				"\n" .	"#\t  - Odds other country\t\t= 1 in $oddsothercountry";

	my $sqlprint = $precomment . "\n";
	my $j = 0;
	for (my $i = 0; $i < @sql; $i++) {

		my @statementelements = split(m/\?/x, $sql[$i] );
		$sqlprint .= "\n" . $statementelements[0];

		if (@statementelements > 1)
		{
			$sql[$i] = $statementelements[0];
			for (my $k = 1; $k < @statementelements; $k++) {
				my $string = ($sql_parameters[$j] !~ m/^\d+$/g)?"'":"";
				$sqlprint .= $string . $sql_parameters[$j] . $string . $statementelements[$k];

				++$j;
			}
		}
	}

	writetofile($sqlprint, "", "sql", "query");
}
sub writetofile {
	my ($print, $filenamesuffix, $extension, $description) = @_;

	my $filename = basename($0) . $filenamesuffix;
	$extension = "." . $extension;

	my $counter = 1;

	while (-e ("$filename.$counter$extension")) {
		++$counter;
	}

	open (MYFILE, ">$filename.$counter$extension");
	print MYFILE $print;
	close (MYFILE);

	printlines("Wrote $description to '$filename.$counter$extension'");

	system("start $filename.$counter$extension") if $openwrittenfile;
}

END:
printlines("FINISHED");
print "\n";