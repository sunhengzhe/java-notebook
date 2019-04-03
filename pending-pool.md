# 知识池

等待沉淀为笔记

## 2019/04/03

### 硬链接和软链接

- [理解 Linux 的硬链接与软链接](https://www.ibm.com/developerworks/cn/linux/l-cn-hardandsymb-links/index.html)

linux 中，文件分为文件名和数据。数据又分为用户数据（user data）和元数据（metadata），用户数据存放在文件数据块（data block），是记录文件真实内容的地方。
inode 是元数据的一部分，但不包含文件名，inode 号是文件的唯一标识。

通过文件名获取文件内容：

![](https://www.ibm.com/developerworks/cn/linux/l-cn-hardandsymb-links/image001.jpg)

查看 inode 的两种方式：

1. ls -i
2. stat <file>

硬链接（hard link) / 软链接（又称符号链接，即 soft link 或 symbolic link）

![](https://www.ibm.com/developerworks/cn/linux/l-cn-hardandsymb-links/image002.jpg)
