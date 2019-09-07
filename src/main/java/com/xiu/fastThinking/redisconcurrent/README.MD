# redis并发竞争问题

redis的并发竞争问题，主要是发生在并发写竞争

场景：

考虑到redis没有像db中的sql语句，update val=val+10 where ...无法使用这种方式进行数据的更新。

假如某个key="price",value 为10，现在想把value值进行+10操作。正常逻辑是先把数据key为price的值读回来，加上10，再把值设置回去。
如果只有一个连接的情况，没什么问题，但是如果有2个连接同时想对price+10操作，可能出现问题。

T1时刻，连接1将price读出，目标设置的数据为10+10 = 20。

T2时刻，连接2也将数据读出，也是为10，目标设置为20。

T3时刻，连接1将price设置为20。

T4时刻，连接2也将price设置为20，则最终结果是一个错误值20。


## 解决方案

方案1
利用redis自带的incr命令，具体用法看这里http://doc.redisfans.com/string/incr.html。

方案2
可以使用独占锁的方式，类似操作系统的mutex机制。（网上有例子，http://blog.csdn.net/black_ox/article/details/48972085 不过实现相对复杂，成本较高）

方案3
使用乐观锁的方式进行解决（成本较低，非阻塞，性能较高）

如何用乐观锁方式进行解决？

本质上是假设不会进行冲突，使用redis的命令watch进行构造条件。伪代码如下：

```
watch price

get price $price

$price = $price + 10

multi

set price $price

exec

```

解释一下：

watch这里表示监控该key值，后面的事务是有条件的执行，如果从watch的exec语句执行时，watch的key对应的value值被修改了，则事务不会执行。

https://www.cnblogs.com/shamo89/p/8376907.html

方案4
这个是针对客户端来的，在代码里要对redis操作的时候，针对同一key的资源，就先进行加锁（java里的synchronized或lock）。

方案5
利用redis的setnx实现内置的锁。


方案6
利用分布式锁
https://www.cnblogs.com/xubingqi/p/6744397.html























