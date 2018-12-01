# Redis 自动填充

自动填充一般在快速查询时用到比较多,
例如书名, 作者, 通讯录等左匹配的快速查询.

redis的自动填充一般有2种做法:

- 数据量小的时候, 使用list存储所需要的数据, 在需要自动填充时对list种的数据进行过滤
- 数据量比较大的时候, 利用zset的特性对自动填充所需要的数据进行过滤

redis使用**utf8**对字符串进行存储.

## utf8

参考文献

- [wiki-utf8](https://zh.wikipedia.org/wiki/UTF-8)
- [字符编码笔记：ASCII，Unicode 和 UTF-8](http://www.ruanyifeng.com/blog/2007/10/ascii_unicode_and_utf-8.html)

根据UTF8最新规范[RFC3629](https://datatracker.ietf.org/doc/rfc3629/),
里面提及了UTF8的范围[U+0000, U+10FFFF], 最多**4个字节**.

```
In UTF-8, characters from the U+0000..U+10FFFF range
(the UTF-16 accessible range) are encoded using sequences of 1 to 4 octets.
```

## zset

zset用于存储键值对, 数据格式: `member score`,
会根据score对数据进行排序.

当zset种member的score相同时, 会根据member按照alpha进行排序.

常用操作:

- zadd
- zrange
- zrangebyscore
- zrem
- zrank

## 大量数据的自动填充

解决方案:

- 计算要左匹配的字符串的**最大前驱和最小后继**
- 将前驱和后继插入到zset中, 来确定要自动填充所需要的内容的范围
- 获取前驱和后继的rank
- 根据rank从zset中获取这个范围的数据
- 移除zset中的前驱和后继

在生产环境里会涉及到并发的场景,
一般会将**UUID**作为前驱和后继的后缀来避免冲突, 提高并发;
并且整个流程会使用redis的**乐观锁**来保证一次提交多个操作.
