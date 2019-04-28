# List

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

## Capped lists

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

## Blocking operations on lists

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

## key 的自动创建和删除

在 redis 里面无需手动创建 key 或者删除一个空的 key，redis 会在我们 push 但是 key 不存在时创建 list，会在 list 为空时删除它。

这不仅仅针对 list，它适用于由多个元素组成的所有 redis 的数据类型：Streams, Sets, Sorted Sets 和 Hashes。

1. 当向聚合数据类型添加元素时，如果 key 不存在，在元素添加前会自动创建。
2. 当向聚合数据类型删除元素后，如果 value 为空，key 会被自动删除（Stream 是唯一例外的类型）。
3. 调用只读命令如 `LLEN`（获取 list 的长度）或对一个空 key 执行删除操作时，就如同对一个内容为空的聚合数据类型操作一样。

但当 key 存在时，不能执行非该类型的操作，否则会报 `WRONGTYPE` 错误。
