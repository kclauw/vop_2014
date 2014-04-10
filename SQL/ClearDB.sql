# EASY THERE COWBOY!
# PAS ENORM GOED OP MET WAT JE DOET MET DIT SCRIPT!

SET foreign_key_checks = 0;

truncate table Coordinates;
truncate table Country;
truncate table ParentRelation;
truncate table Person;
truncate table PersonTree;
truncate table Place;
truncate table Placename;
truncate table Request;
truncate table RoleUser;
truncate table Tree;
truncate table User;

SET foreign_key_checks = 1;

insert into User values (1, "Moderator", "123456789", 1, 0);