# 布隆过滤器 Bloom Filter

布隆过滤器可以用于检索一个元素是否在一个集合中，但布隆过滤器不存储元素的值，所以空间效率和查询时间都比较高。

基本的布隆过滤器支持两个操作：

- add：添加元素到布隆过滤器中。
- test：用来判断元素是否在过滤器中，如果返回 false，代表 **一定** 不在过滤器中；如果返回 true，代表 **可能存在**。

也就是说，布隆过滤器是有一定的误识别率的，这个错误的概率与过滤器的大小、元素，以及使用率有关。

布隆过滤器有两个属性，一个是 bitmap 的长度 m，一个是哈希函数的数量 k。

add 一个元素时，对元素做 k 次哈希，求得 k 个位置，将这些位置置为 1。test 一个元素时，同样地对其做 k 次哈希，如果这 k 个位置都是 1，则说明这个元素可能在布隆过滤器中，任一个位置不为 0 则一定不在过滤器里面。

![](https://camo.githubusercontent.com/0f929fdbdb42685ecf5b2c25b68d46d0fb679c33/687474703a2f2f696d61676573323031352e636e626c6f67732e636f6d2f626c6f672f313033303737362f3230313730312f313033303737362d32303137303130363134333134313738342d313437353033313030332e706e67)

为什么有误识别率也很容易理解，因为求得的这 k 个位置很可能是别的元素置过的。

## 计数过滤器（Counting bloom filter）

从上面可知，简单的布隆过滤器无法支持删除操作。因为如果两个元素用到了同一位，那么删除 A 元素会导致 B 元素 test 失败。

使用计数过滤器可以解决这个问题，计数过滤器阵列位置（桶）从单个位扩展为 n 位计数器。简单来讲，就是每一位不再存比特，而是存整数，如果要删除，将对应的位减一。

它的缺点也是显而易见的：需要占用更多的空间。

## 决定布隆过滤器的参数

布隆过滤器是一定会有一个错误率的，设错误率为 p。它与布隆过滤器的大小 m、存放元素个数以及哈希函数个数 k 的关系为：

![](https://cdn-images-1.medium.com/max/1600/1*4QvZrOV7d9XgQXqVaIOGPg.png)

当我们确定要存放的元素个数 n、自己需要的错误率 p 后，可以分别求得 m 与 k：

![](https://cdn-images-1.medium.com/max/1600/1*eDTlEUQCLRB8wL96GileXA.png)

另外需要注意的是，因为布隆过滤器用在查询的场景，所以哈希函数不能选择比较慢的，比如 Sha-1、MD5。应该选择如 [murmur](https://sites.google.com/site/murmurhash/)
、[fnv](http://isthe.com/chongo/tech/comp/fnv/) 系列哈希和 [HashMix](http://www.google.com/codesearch/url?ct=ext&url=http://www.concentric.net/~Ttwang/tech/inthash.htm&usg=AFQjCNEBOwEAd_jb5vYSckmG7OxrkeQhLA) 等。

## 参考

- [Probabilistic Data structures: Bloom filter](https://hackernoon.com/probabilistic-data-structures-bloom-filter-5374112a7832)
- [Bloom Filters](https://www.jasondavies.com/bloomfilter/)
