# OPGEPAST! Deze query verwijdert alles wat aan een user gelinkt is in de database.
# De reden waarom er verwijderd wordt op naam en niet op id, is om het risico op fouten te verlagen.
set @username = 'Lowie';



set @userid = (select userID from User where username=@username);
delete from Person where personID in (select personID from PersonTree where treeID in (select treeID from Tree where ownerID=@userid));
delete from Tree where ownerID=@userid;
delete from Request where friend=@userid or receiver=@userid;
delete from RoleUser where userID=@userid;
delete from User where userID=@userid;