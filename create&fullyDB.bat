@echo off
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -uroot -p mysql < ddl.sql
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -uroot -p mysql < dml.sql
pause