# Hash

设置单值 `HSET key field valud`

设置多值 `HMSET key field value [field value ...]`

字段不存在时赋值 `HSETNX key field value`

获取单值 `HGET key field`

获取多值 `HMGET key field [field ...]`

获取所有值 `HGETALL key`

删除值 `HDEL key field [field ...]`

增加数字 `HINCRBY key field increment`
