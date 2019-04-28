# SortedSet

SortedSet 是一种数据类型，类似于 Set 和 Hash 的混合。如 Set 一样只包含不重复的字符串，不过 set 的元素是无序的，每个元素和一个浮点数关联，称为 score。（这也是为什么 SortedSet 像 Hash，因为每个元素都映射到一个值）

依据以下规则排序：

1. 如果 A、B 分数不一样，若 A.score > B.score，那么 A > B。
2. 如果 A、B 分数一样，A、B 按 string 的字典顺序排序，而它们一定是不同的，因为 SortedSet 里不能有相同的元素。

使用 `ZADD` 来添加一个元素，相较于 `SADD`，它多一个参数，即 score。

```jshelllanguage
zadd myzset 1000 apple
```

使用 `ZRANGE` 获取集合中的一段元素。

使用 `ZREVRANGE` 逆序输出。

在命令后面添加 `WITHSCORES` 连带输出 score。

## 范围操作

使用 `ZRANGEBYSCORE` 获取 score 在一个范围内的数据。`-inf` 代表负无穷。

```jshelllanguage
zrangebyscore myzset -inf 1950
```

使用 `ZREMRANGEBYSCORE` 删除 score 在一个范围内的数据，并返回删除的个数。

```jshelllanguage
> zremrangebyscore myzset 10 20
(integer) 1
```

使用 `ZRANK` 获取元素的排名，相应的有 `ZREVRANK` 获取降序排名。

## Lexicographical scores

在 redis 2.8 以上，Sorted Set 可以使用词典获取范围。在同样的 score 下，使用字典顺序来进行操作。

- `ZRANGEBYLEX`, `ZREVRANGEBYLEX` 通过词典获取范围

- `ZREMRANGEBYLEX` 通过词典排序

- `ZLEXCOUNT` 词典在某个范围内的数量

```jshelllanguage
> zrangebylex myzset [a [c
1) "apple"
```

## 更新 score

同样的 key 再次调用 `ZADD` 即可更新 score。
