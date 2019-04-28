# Set

set 是一个无序的字符串集合，`SADD` 命令添加一个元素到集合中。

`SMEMBERS` 查看所有元素。

`SISMEMBER` 检查元素是否存在于集合中。

集合擅长表达对象之间的关系。

`SPOP` 返回一个随机的元素。

`SINTER` 取集合的交集。

```jshelllanguage
> sadd myset H1 B2 C7
(integer) 3
> sadd yourset H2 B2 C7
(integer) 3
> sinter myset yourset
1) "C7"
2) "B2"
```

`SUNIONSTORE` 取各个集合的并集，并存放到一个新的集合中去。还可以利用这个命令复制一个集合。

```jshelllanguage
# 复制 myset 到 copyset
sunionstore copyset myset
```

使用 `SCARD` 获取集合的个数，card 代表 cardinality。

如果只想返回元素但不想移除的话，使用 `SRANDMEMBER` 命令。
