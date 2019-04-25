# Redis

使用 C 语言开发的开源的高性能键值对（key-value）内存数据库，是一种 NoSQL 数据库。

## 通用操作

`DEL` 删除 key。删除成功（key 存在）返回 1，否则（key 不存在）返回 0。

`EXISTS` 返回 key 是否存在，如果存在返回 1，否则返回 0。

`TYPE` 返回指定 key 存放的 value 的类型

## Redis 的 key

redis key 是二进制安全的（binary safe）。这意味着可以使用任意二进制序列作为一个 key，甚至一个 JPEG 文件的内容，空字符串也是一个合法的 key。

### key 的长度

太长的 key 和太短的 key 都是不推荐的。太长的 key 内容占用会更多，另外也会影响查询的时间；太短的 key 可读性会比较差。

推荐使用 `user:1000:followers` 或 `comment:1234:reply-to` 这种的命名规范，并在系统中保持一致。多个单词之间可以使用点或者中划线。

最长的 key 大小为 512 MB。

## Redis 的 value

提供五种数据类型来存储值：字符串类型、散列类型、列表类型、集合类型、有序集合类型。

### Ø String（字符类型）

String 类型是最简单的一种类型，使用 `SET` 和 `GET` 来设置和获取 value。value 可以是任意类型的字符串，包括二进制数据。甚至可以将 jpeg 图片的内容存到 value 里。

value 最大不能超过 512 MB。

#### Options

`NX` 和 `XX` 分别代表只有 key 不存在时才设置和只有 key 存在时才设置。

```
> get mykey
(nil)
> set mykey myvalue nx
OK
> set mykey othervalue xx
OK
```

#### 数字操作

使用 `INCR`、`INCRBY`、`DECR` 和 `DECRBY` 会让 redis 将字符串转为数字，并进行加 1、加自定义数量、减 1、减自定义数量。

它们都是原子类（atomic）操作，这意味着即使多个客户端同时对同一个 key 进行 `INCR`，它们也不会进入一个竞态条件。

#### GETSET

`GETSET` 命令设置一个新值并返回旧值。比如有个系统在每个用户访问时会让浏览次数加 1，如果要每小时统计一次，可以每小时使用 GETSET 将该值设置为 0 并返回旧值。

#### 多值

`MSET` 和 `MGET` 可以进行批量的 `SET` 和 `GET`。`MGET` 会返回一个数组。

```jshelllanguage
> mset a 1 b 2 c 3
OK
> mget a b c
1) "1"
2) "2"
3) "3"
```

### Ø Hash（散列类型）

设置单值 `HSET key field valud`

设置多值 `HMSET key field value [field value ...]`

字段不存在时赋值 `HSETNX key field value`

获取单值 `HGET key field`

获取多值 `HMGET key field [field ...]`

获取所有值 `HGETALL key`

删除值 `HDEL key field [field ...]`

增加数字 `HINCRBY key field increment`

### Ø List（列表类型）

redis 中的 list 是使用链表实现的。

`LPUSH` 和 `RPUSH` 添加元素到 list 的头部和尾部。`LRANGE` 获取一个范围内的元素，可以接受负数，代表倒数第几个。

```jshelllanguage
> lpush mylist a
(integer) 1
> rpush mylist b
(integer) 2
> lrange mylist 0 -1
1) "a"
2) "b"
```

list 还支持弹出元素，可以使用 `LPOP`、`RPOP` 检索并删除头或尾的元素。

```jshelllanguage
> rpop mylist
"b"
> lpop mylist
"a"
> lrange mylist 0 -1
(empty list or set)
```

#### Capped lists

可以使用 `LTRIM` 来实现一个固定长度的列表，`LTRIM` 和 `LRANGE` 很像，但 `LTRIM` 会只保留范围内的数据，而丢弃其他数据。

```jshelllanguage
> rpush mylist a b c d e f g
(integer) 7
> ltrim mylist 0 2
OK
> lrange mylist 0 -1
1) "a"
2) "b"
3) "c"
```

所以使用 `LPUSH` + `LTRIM` 添加一个元素并删除超过限度的元素。

```jshelllanguage
LPUSH mylist <some element>
LTRIM mylist 0 999
```

注：虽然 `LRANGE` 理论上是一个 O(n) 的操作，但如果是访问头或者尾附近小段的数据的时间复杂度还是常数级的。

#### Blocking operations on lists

使用 list 可以实现队列，比如生产进程 `LPUSH`，消费进程 `RPOP`，但队列为空时，因为轮询，消费者可能会有很多无用的请求。

`BLPOP` 和 `BRPOP` 是 `LPOP` 和 `RPOP` 的阻塞版，调用它们后，只有在队列不为空或者超时后才返回值。

```jshelllanguage
> brpop tasks 5
1) "tasks"
2) "do_something"
```

第二个参数代表超时的时间，即等待 tasks 队列有元素，但如果超过 5 秒则返回。另外如果指定 0 的话即无限等待。

可以同时指定多个队列，当某一个队列有值时即返回。

- 客户端有先后顺序：第一个阻塞等待列表的客户端，在有值后会被第一个返回。
- `BRPOP` 的返回值与 `RPOP` 不同，它返回两个元素的数组，因为 `BRPOP` 可以对多个 list 进行阻塞。所以它会返回 list 的 key。
- 如果超时，返回 NULL

#### key 的自动创建和删除

在 redis 里面无需手动创建 key 或者删除一个空的 key，redis 会在我们 push 但是 key 不存在时创建 list，会在 list 为空时删除它。

这不仅仅针对 list，它适用于由多个元素组成的所有 redis 的数据类型：Streams, Sets, Sorted Sets 和 Hashes。

1. 当向聚合数据类型添加元素时，如果 key 不存在，在元素添加前会自动创建。
2. 当向聚合数据类型删除元素后，如果 value 为空，key 会被自动删除（Stream 是唯一例外的类型）。
3. 调用只读命令如 `LLEN`（获取 list 的长度）或对一个空 key 执行删除操作时，就如同对一个内容为空的聚合数据类型操作一样。

但当 key 存在时，不能执行非该类型的操作，否则会报 `WRONGTYPE` 错误。

#### Ø Set（集合类型）

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

Ø SortedSet（有序集合类型，简称 zset）

## 过期设置

- 精度可以设置为秒或者毫秒
- 过期时间是以毫秒计算的
- 过期信息会存在磁盘上，这意味着即使 redis 停掉了，时间还是会继续。

```jshelllanguage
> set mykey one
OK
> get mykey
"one"
> expire mykey 5
(integer) 1
> get mykey
"one"
> get mykey
(nil)
```

使用 `EXPIRE` 设置过期时间，使用 `PERSIST` 移除过期时间使其持久保存。

使用 `TTL` 查看剩余时间（秒），使用 `PTTL` 以毫秒级查看。

可以在 `SET` 时就设置过期时间。

```jshelllanguage
set mykey haha ex 10
```
