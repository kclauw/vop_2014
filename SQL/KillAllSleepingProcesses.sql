drop procedure if exists `kill_all_sleeping_processes`;

DELIMITER $$
create procedure `kill_all_sleeping_processes`()
BEGIN
declare i INT default 0;
declare n INT;
set n = (select count(1) from INFORMATION_SCHEMA.PROCESSLIST where COMMAND='Sleep');
while i<n do
	set @id = (select ID from INFORMATION_SCHEMA.PROCESSLIST where COMMAND='Sleep' LIMIT 0,1);
	kill @id;
	set i = i + 1;
end while;
end $$
DELIMITER ;

call kill_all_sleeping_processes();
show processlist;