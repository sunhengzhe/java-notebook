# Elasticsearch

Elasticsearch 是一个开源的搜索引擎，建立在一个全文搜索引擎库 Apache Lucene™ 基础之上。

Elasticsearch 也是使用 Java 编写的，它的内部使用 Lucene 做索引与搜索，但是它的目的是使全文检索变得简单， 通过隐藏 Lucene 的复杂性，取而代之的提供一套简单一致的 RESTful API。

Kibana is an open source analytics and visualization platform designed to work with Elasticsearch. You use Kibana to search, view, and interact with data stored in Elasticsearch indices. You can easily perform advanced data analysis and visualize your data in a variety of charts, tables, and maps.

Elasticsearch 是面向文档的。Elasticsearch 不仅存储文档，而且索引每个文档的内容使之可以被检索。

一个 Elasticsearch 集群可以包含多个索引 ，相应的每个索引可以包含多个类型。这些不同的类型存储着多个文档，每个文档又有多个属性。

索引（名词）：一个索引类似于传统关系数据库中的一个数据库 ，是一个存储关系型文档的地方。 索引(index)的复数词为 indices 或 indexes。

索引（动词）：索引一个文档 就是存储一个文档到一个索引（名词）中以便它可以被检索和查询到。这非常类似于 SQL 语句中的 INSERT 关键词，除了文档已存在时新文档会替换旧文档情况之外。

索引文档：`PUT /<index>/<type>/<id>`，请求体包含数据的 json 文档。
检索文档：`GET /<index>/<type>/<id>`

### 搜索

搜索所有：`GET /<index>/<type>/_search`

查询字符串：`GET /<index>/<type>/_search?q=last_name:Smith`

查询表达式：

```
GET /megacorp/employee/_search
{
    "query" : {
        "match" : {
            "last_name" : "Smith"
        }
    }
}
```

全文搜索 `match`，短语搜索 `match_phrase`。

高亮搜索：

```
GET /megacorp/employee/_search
{
    "query" : {
        "match_phrase" : {
            "about" : "rock climbing"
        }
    },
    "highlight": {
        "fields" : {
            "about" : {}
        }
    }
}
```

返回部分数据：

```
# 从 _source 中筛选
GET /megacorp/employee/1?_source=age,last_name
# 只返回 _source 内容
GET /megacorp/employee/1/_source
```

更新文档：

```
PUT /website/blog/123
```

创建文档：

```
# 让 elasticsearch 自动创建 id
POST /website/blog/

# 然而，如果已经有自己的 _id ，那么我们必须告诉 Elasticsearch ，只有在相同的 _index 、 _type 和 _id 不存在时才接受我们的索引请求。

# 方法一
PUT /website/blog/123?op_type=create
# 方法二
PUT /website/blog/123/_create
# 如果具有相同的 _index 、 _type 和 _id 的文档已经存在，Elasticsearch 将会返回 409 Conflict 响应码
```

删除文档：

```
DELETE /website/blog/123
```

### 分布式

一个运行中的 Elasticsearch 实例称为一个节点，而集群是由一个或者多个拥有相同 cluster.name 配置的节点组成， 它们共同承担数据和负载的压力。当有节点加入集群中或者从集群中移除节点时，集群将会重新平均分布所有的数据。

索引：指向一个或者多个物理分片的逻辑命名空间。
分片：底层的工作单元，一个分片是一个 Lucene 的实例

Elasticsearch 是利用分片将数据分发到集群内各处的。分片是数据的容器，文档保存在分片内，分片又被分配到集群内的各个节点里。 当你的集群规模扩大或者缩小时， Elasticsearch 会自动的在各节点中迁移分片，使得数据仍然均匀分布在集群里。

一个分片可以是主分片或者副本分片。 索引内任意一个文档都归属于一个主分片，所以主分片的数目决定着索引能够保存的最大数据量。

### es 2.4.0 + kibana 4.6.4

```bash
# 启动 es
./elasticsearch-2.4.0/bin/elasticsearch -Des.insecure.allow.root=true -d
# 测试
curl 'http://localhost:9200/?pretty'

# 安装 kibana sense 插件
./kibana-4.6.4-linux-x86_64/bin/kibana plugin --install elastic/sense
# 启动 kibana
./kibana-4.6.4-linux-x86_64/bin/kibana
# 打开 sense
http://localhost:5601/app/sense
```
