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

- [String](./data_type/String.md)
- [Hash](./data_type/Hash.md)
- [Set](./data_type/Set.md)
- [List](./data_type/List.md)
- [SortedSet](./data_type/SortedSet.md)

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
