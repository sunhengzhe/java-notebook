# String

String 类型是最简单的一种类型，使用 `SET` 和 `GET` 来设置和获取 value。value 可以是任意类型的字符串，包括二进制数据。甚至可以将 jpeg 图片的内容存到 value 里。

value 最大不能超过 512 MB。

## Options

`NX` 和 `XX` 分别代表只有 key 不存在时才设置和只有 key 存在时才设置。

```
> get mykey
(nil)
> set mykey myvalue nx
OK
> set mykey othervalue xx
OK
```

## 数字操作

使用 `INCR`、`INCRBY`、`DECR` 和 `DECRBY` 会让 redis 将字符串转为数字，并进行加 1、加自定义数量、减 1、减自定义数量。

它们都是原子类（atomic）操作，这意味着即使多个客户端同时对同一个 key 进行 `INCR`，它们也不会进入一个竞态条件。

## GETSET

`GETSET` 命令设置一个新值并返回旧值。比如有个系统在每个用户访问时会让浏览次数加 1，如果要每小时统计一次，可以每小时使用 GETSET 将该值设置为 0 并返回旧值。

## 多值

`MSET` 和 `MGET` 可以进行批量的 `SET` 和 `GET`。`MGET` 会返回一个数组。

```jshelllanguage
> mset a 1 b 2 c 3
OK
> mget a b c
1) "1"
2) "2"
3) "3"
```
