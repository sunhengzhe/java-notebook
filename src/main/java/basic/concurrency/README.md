# 并发 Concurrency

## 多进程与多线程

每个进程拥有自己的一整套变量，而线程则共享数据。

## 线程状态

线程可以有如下 6 种状态：

- New 新创建
- Runnable 可运行
- Blocked 被阻塞
- Waiting 等待
- Timed waiting 计时等待
- Terminated 被终止

### 新创建

当使用 new 操作符创建新新线程时，如 `new Thread(r)`，该线程还没有开始运行。
这时它的状态为 New，程序还没有开始运行线程中的代码。

### 可运行

调用 start 方法后，线程处于 runnable 状态。一个可运行的线程可能正在运行也可能没有运行，取决于操作系统给线程提供运行的时间。
通常桌面以及服务器操作系统都使用抢占式调度，但手机这样的小型设备可能使用协作式调度。在任何给定时刻，一个 **可运行** 的线程可能正在运行也可能没有运行。

> 抢占式调度系统：给每一个可执行的线程一个时间片来执行任务。时间片用完后，操作系统剥夺该系统的运行权，并考虑线程的优先级来给下一个线程运行机会。

在多个 CPU 的机器上，每个 CPU 运行一个线程，所以可以有多个线程并行运行。

## 阻塞与等待

线程处于阻塞或等待时，暂时不活动，它不运行任何代码且消耗最小的资源。

- 当线程试图获取一个内部的对象锁（非 `java.util.concurrent` 库中的锁），而该锁被其他线程持有，则线程进入阻塞状态。
- 当线程等待另一个线程通知调度器一个条件时，它自己进入等待状态。如 `Object.wait` 方法或 `Thread.join`，或等待 `java.util.concurrent` 库中的 Lock 或 Condition 时。
- 有几个方法有超时参数，调用它们导致进入计时等待状态。如 `Thread.sleep`、`Object.wait`、`Thread.join`、`Lock.tryLock` 和 `Condition.await` 的计时版。

### 被终止

有两种原因会让线程终止：

- 因为 run 方法正常退出而自然死亡
- 因为没有捕获的异常终止了 run 方法而意外死亡

也可以使用线程的 stop 方法杀死线程，该方法抛出 ThreadDeath 错误对象，由此杀死线程。但 stop 方法已经过时，不建议使用。

