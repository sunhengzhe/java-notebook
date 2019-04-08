# MySQL

## 事务

mysql 默认采用 `autocommit`，即执行 SQL 语句后就会马上执行 COMMIT 操作。因此要显式地开启一个事务务须使用命令 BEGIN 或 START TRANSACTION，或者执行命令 SET AUTOCOMMIT=0，用来禁止使用当前会话的自动提交。

查看当前 `autocommit` 状态：`show variables like 'autocommit'`。

在 MySQL 中只有使用了 Innodb 数据库引擎的数据库或表才支持事务。

使用 `show create table <table_name>` 查看表使用的引擎。

使用 `alter table <table_name> set engine=innodb` 来更改表的引擎。

要避免自动提交，需要使用 `begin` 或 `start transaction` 来显式开启一个事务，或者使用 `set autocommit=0` 在此次会话关闭 autocommit。

使用 `show variables like 'transaction_isolation'` 查看事务隔离级别。Mysql 8 以前使用 `show variables like 'tx_isolation'`。

mysql 隔离性默认为可重复读，即在一个事务内，不会读到其他事务提交的数据。
