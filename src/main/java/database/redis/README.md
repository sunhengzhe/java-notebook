# Redis

使用 C 语言开发的开源的高性能键值对（key-value）内存数据库，是一种 NoSQL 数据库。

## 数据类型

提供五种数据类型来存储值：字符串类型、散列类型、列表类型、集合类型、有序集合类型。

Ø String（字符类型）

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
