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

Ø List（列表类型）

Ø Set（集合类型）

Ø SortedSet（有序集合类型，简称zset）

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
