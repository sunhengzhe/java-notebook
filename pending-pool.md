# 知识池

等待沉淀为笔记。

问题很重要，一定要有问题。

## 2019/04/12

Spring 中的 `Scheduled`，有三种

- fixedDelay
- cron
- fixedRate

fixedRate 与 javascript 中的 interval 类似。

需要使用一下，并看一下 Scheduled 的实现原理

- [\[肥朝\]原理暂且不谈，定时器你当真会用？](https://juejin.im/post/5caf8419e51d456e3b701863?utm_source=gold_browser_extension)

## 2019/04/10

时间复杂度并不是表示一个程序解决问题需要花多少时间，而是当问题规模扩大后，程序需要的时间长度增长得有多快。

- [什么是P问题、NP问题和NPC问题](http://www.matrix67.com/blog/archives/105)

## 2019/04/08

平衡二叉树、红黑树、B 树、B+树

- [AVL Tree 可视化](https://www.cs.usfca.edu/~galles/visualization/AVLtree.html)

- [往 mysql 中插入一亿条数据](https://www.jianshu.com/p/3e18feb65b26)

什么是存储过程？

存储过程（Stored Procedure）是一种在数据库中存储复杂程序，以便外部程序调用的一种数据库对象。

存储过程是为了完成特定功能的SQL语句集，经编译创建并保存在数据库中，用户可通过指定存储过程的名字并给定参数(需要时)来调用执行。

存储过程思想上很简单，就是数据库 SQL 语言层面的代码封装与重用。

## 2019/04/06

为什么分布式的节点一般是奇数个，而且从 3 开始？

选举算法选出奇数节点的条件是可用节点数量 > 总节点数量/2。

1. 防止由脑裂造成的集群不可用。

如果是偶数台，脑裂出的两个集群数量相等的可能性更大。

2. 在容错能力相同的情况下，奇数台更节省资源。

## 2019/04/04

### etcd 与服务发现

[etcd](https://github.com/etcd-io/etcd) is a distributed reliable key-value store for the most critical data of a distributed system

The name "etcd" originated from two ideas, the unix "/etc" folder and "d"istibuted systems. The "/etc" folder is a place to store configuration data for a single system whereas etcd stores configuration information for large scale distributed systems. Hence, a "d"istributed "/etc" is "etcd".

- [etcd versus other key-value stores](https://coreos.com/etcd/docs/latest/learning/why.html)

etcd 是受到 ZooKeeper 与 doozer 启发而催生的项目，有在 CoreOS 和 Kubernetes 中使用到。

etcd 的应用场景之一是服务发现（Service Discovery）

- [服务发现的基本原理](https://zhuanlan.zhihu.com/p/34332329)

同样是 kv 数据库，和 redis 有什么不同？

redis 侧重于存储，是一个数据库，但 etcd 侧重于分布式一致性的保证，所以可以直接用来做服务注册，而 redis 需要额外的开发

### SQL：查询分组内最大/最小值

表结构 `user`：

| name | class | score |
| --- | --- | --- |
| zhangsan | A | 90 |
| lisi | A | 89 |
| wangwu | A | 93 |
| maliu | B | 91 |
| zhaosi | B | 97 |
| guoba | C | 88 |

查询每个班级中成绩最好的学生名。

首先查询每个班级中最高分：`select class, max(score) score from user group by class`

然后在表中再查这些最高分的对应的学生：`select a.* from user a inner join (..) b on a.class = b.class and a.score = b.score`。

综合，即

```sql
select a.* from user a inner join (
  select class, max(score) score from user group by class
) b on a.class = b.class and a.score = b.score
```

## 2019/04/03

### 硬链接和软链接

- [理解 Linux 的硬链接与软链接](https://www.ibm.com/developerworks/cn/linux/l-cn-hardandsymb-links/index.html)

linux 中，文件分为文件名和数据。数据又分为用户数据（user data）和元数据（metadata），用户数据存放在文件数据块（data block），是记录文件真实内容的地方。
inode 是元数据的一部分，但不包含文件名，inode 号是文件的唯一标识。

通过文件名获取文件内容：

![](https://www.ibm.com/developerworks/cn/linux/l-cn-hardandsymb-links/image001.jpg)

查看 inode 的两种方式：

1. ls -i
2. stat <file>

硬链接（hard link) / 软链接（又称符号链接，即 soft link 或 symbolic link）

![](https://www.ibm.com/developerworks/cn/linux/l-cn-hardandsymb-links/image002.jpg)

使用方式：

```bash
# 硬链
link origin-file hard-link-file
# 软链
link -s origin-file soft-link-file
```

有什么不同？

硬链接和会新建一个文件名，但与源文件使用同一个 inode 号。这也是为什么

1) 创建硬链接必须要源文件存在（inode 存在且链接计数器大于 0）
2）新建硬链接后，即使删除源文件，硬链接也能访问文件的内容。

软链接使用新的 inode，只是数据块内的内容指向的是源文件名，所以这也是为什么

1）新建软链接后，删除源文件，软链接就变成了死链接。
2）创建软连接无需源文件存在。当新建一个与原文件名一样的文件时，软链就又可用了。
