# Isolation 隔离性与隔离级别

## 隔离性问题

当数据库有多个事务同时执行时，可能出现以下问题：

- 脏读（dirty read）
- 不可重复读（non-repeatable read）
- 幻读（phantom read）

假设有 user 表：

| id | name |
| --- | --- |
| 1   | zhangsan   |
| 2   | lisi       |
| 3   | wangwu     |

### 脏读

| 事务 A | 事务 B |
| --- | --- |
| 启动事务 | 启动事务 |
| insert into user values(4, "zhaoliu") | |
| | select * from user |
| 提交事务 | 提交事务 |

如果事务 B 查询时能读取到 (4, zhaoliu) 的记录，就称为 **脏读**，即读到了未提交事务操作的信息。

### 不可重复读

| 事务 A | 事务 B |
| --- | --- |
| 启动事务 | 启动事务 |
| select name from user where id = 1 | |
| | update user set name = "xxx" where id = 1 |
| | 提交事务 |
| select name from user where id = 1 | |
| 提交事务 | |

如果事务 A 第二次查询结果为 xxx，与第一次查询信息不一致，这称为 **不可重复读**，即已提交事务对事务 A 产生了影响。

### 幻读

| 事务 A | 事务 B |
| --- | --- |
| 启动事务 | 启动事务 |
| select * from user where id = 4 | |
| | insert into user values(4, "zhaoliu") |
| | 提交事务 |
| insert into user values(4, "zhaoliu") | |

事务 A 查询时没有 id 为 4 的数据，但是插入时抛出了主键重复的错误，这称为 **幻读**，也是已提交事务对事务 A 产生的影响。

不可重复读和幻读都是已提交事务对事务造成的影响，但是不可重复读偏向于 读-读，幻读偏向于 读-写。

## 隔离级别

SQL 标准的事务隔离级别包括：

- 读未提交（read uncommitted）：一个事务还没提交时，它的变更就能被别的事务看到。
- 读提交（read committed）：一个事务提交后，它的变更才会被别的事务看到。
- 可重复读（repeatable read）：一个事务执行过程中看到的数据，总是跟这个事务在启动时看到的数据是一致的。在可重复读的隔离级别下，未提交变更对其它事务是不可见的。
- 串行化（serializable）：对于同一行记录，读和写都会加锁，当出现读写锁冲突时，后一个事务必须要等到前一个事务执行完才能执行。

解决的问题：

| | 脏读 | 不可重复读 | 幻读 |
| --- | --- | --- | --- |
| 读未提交 | ❌ | ❌ | ❌ |
| 读提交 | ✅ | ❌ | ❌ |
| 可重复读 | ✅ | ✅ | ❌ |
| 串行化 |  ✅ | ✅ | ✅ | 

