# 知识池

等待沉淀为笔记。

问题很重要，一定要有问题。

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

使用方式：

```bash
# 硬链
link origin-file hard-link-file
# 软链
link -s origin-file soft-link-file
```

有什么不同？

硬链接和会新建一个文件名，但与源文件使用同一个 inode 号。这也是为什么

1) 创建硬链接必须要源文件存在（inode 存在且链接计数器大于 0）
2）新建硬链接后，即使删除源文件，硬链接也能访问文件的内容。

软链接使用新的 inode，只是数据块内的内容指向的是源文件名，所以这也是为什么

1）新建软链接后，删除源文件，软链接就变成了死链接。
2）创建软连接无需源文件存在。当新建一个与原文件名一样的文件时，软链就又可用了。
