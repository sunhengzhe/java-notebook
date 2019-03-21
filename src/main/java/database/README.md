# 数据库

## 内存数据库

内存数据库会在应用启动的时候创建，并在应用停止时销毁。为什么会有内存数据库呢？它有下列好处：

- 几乎不用搭建基础设施
- 几乎不用配置
- 几乎不用维护
- 非常方便学习、POC 和单元测试

## NoSQL

NoSQL 即 Not Only SQL，泛指非关系型数据库。是为了解决高并发、高可用、高可扩展、大数据存储问题而产生的数据库解决方案。主要分为键值对型（key-value）、文档型（document）、列式存储（columnar）和图表型（graph）。

NoSQL 不像传统的关系型数据库一样把数据存放在表中，而且在搭建数据库之前需要仔细设计 schema。它尤其适用于处理大量的、分布式数据。

![](https://cdn.ttgtmedia.com/rms/onlineImages/data_management-nosql.png)

### 键值对型

key-value 模型十分简单，所以在会话管理和 web 应用缓存中有着高性能和高可扩展性，基于不同的实现，可以存储在内存、固态硬盘或磁盘驱动器中。

例如 Aerospike, Berkeley DB, MemchacheDB, Redis 和 Riak 等。

### 文档型

文档型数据库主要用来存储半结构化数据（semi-structured data）和描述文档格式的数据。

例如 Couchbase Server, CouchDB, DocumentDB, MarkLogic 和 MongoDB 等。

### 列式存储

列式存储相对于传统的行式存储而言的，列式存储按列存储数据：

![](https://img-blog.csdn.net/20141115094556515?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZGNfNzI2/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

对比优缺点如下：

行式存储优点：

- 数据被保存在一起
- INSERT/UPDATE 容易

列式存储优点：

- 查询时只有涉及到的列会被读取
- 投影(projection)很高效
- 任何列都能作为索引

行式存储缺点:

- 选择(Selection)时即使只涉及某几列，所有数据也都会被读取

列式存储缺点：

- 选择完成时，被选择的列要重新组装
- INSERT/UPDATE 比较麻烦

例如 Google BigTable, Cassandra 和 HBase 等。
        
#### 参考
- [几张图看懂列式存储](https://blog.csdn.net/dc_726/article/details/41143175)

### 图表型

图表型存储使用节点（node）表示数据，使用边（edge）表示节点之间的连接。
因为图形系统存储节点之间的关系，它可以支持更丰富的数据关系的表示。

例如 AllegroGraph, IBM Graph, Neo4j 和 Titan 等。

## 事务

事务的四大特性 ACID，分别是 Atomicity（原子性）、Consistency（一致性）、Isolation（隔离性）、Durability（持久性）。

### Isolation 隔离性与隔离级别

#### 隔离性问题

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

#### 脏读

| 事务 A | 事务 B |
| --- | --- |
| 启动事务 | 启动事务 |
| insert into user values(4, "zhaoliu") | |
| | select * from user |
| 提交事务 | 提交事务 |

如果事务 B 查询时能读取到 (4, zhaoliu) 的记录，就称为 **脏读**，即读到了未提交事务操作的信息。

#### 不可重复读

| 事务 A | 事务 B |
| --- | --- |
| 启动事务 | 启动事务 |
| select name from user where id = 1 | |
| | update user set name = "xxx" where id = 1 |
| | 提交事务 |
| select name from user where id = 1 | |
| 提交事务 | |

如果事务 A 第二次查询结果为 xxx，与第一次查询信息不一致，这称为 **不可重复读**，即已提交事务对事务 A 产生了影响。

#### 幻读

| 事务 A | 事务 B |
| --- | --- |
| 启动事务 | 启动事务 |
| select * from user where id = 4 | |
| | insert into user values(4, "zhaoliu") |
| | 提交事务 |
| insert into user values(4, "zhaoliu") | |

事务 A 查询时没有 id 为 4 的数据，但是插入时抛出了主键重复的错误，这称为 **幻读**，也是已提交事务对事务 A 产生的影响。

不可重复读和幻读都是已提交事务对事务造成的影响，但是不可重复读偏向于 读-读，幻读偏向于 读-写。

### 隔离级别

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


