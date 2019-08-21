# 如何迁移到分库分表？ 

方法1：停机部署法

(1)出一个公告，比如“今晚00:00～6:00进行停机维护，暂停服务”

(2)写一个迁移程序，读 db-old 数据库，通过中间件写入新库 db-new1 和 db-new2 ，具体如下图所示

![image](https://github.com/williamzhang11/fastThinking/blob/master/src/main/java/com/xiu/fastThinking/image/sharding1.JPG)



(3)校验迁移前后一致性，没问题就切该部分业务到新库。

方法2：双写部署法，基于业务层
(1)先计算你要迁移的那张表的 max(主键) 。在迁移过程中，只迁移 db-old 中 test_tb 表里，主键小等于该 max(主键) 的值，也就是所谓的历史数据。

(2)在代码中，与 test_tb 有关的业务，多加一条往消息队列中发消息的代码，将操作的sql发送到消息队列中，至于消息体如何组装，大家自行考虑。 需要注意的是， 只发写请求的sql，只发写请求的sql，只发写请求的sql。重要的事情说三遍！

![image](https://github.com/williamzhang11/fastThinking/blob/master/src/main/java/com/xiu/fastThinking/image/sharding2.JPG)
(3)系统上线。另外，写一段迁移程序，迁移 db-old 中 test_tb 表里，主键小于该 max(主键)的数据，也就是所谓的历史数据。

(4)将迁移程序下线，写一段订阅程序订阅消息队列中的数据

(5)订阅程序将订阅到到数据，通过中间件写入新库

(6)新老库一致性验证，去除代码中的双写代码，将涉及到 test_tb 表的读写操作，指向新库。


方法3：双写部署法，基于 binlog


(1)打开binlog日志，系统正常上线就好

(2)还是写一个迁移程序，迁移历史数据

![image](https://github.com/williamzhang11/fastThinking/blob/master/src/main/java/com/xiu/fastThinking/image/sharding3.JPG)
(3)写一个订阅程序，订阅binlog(mysql中有 canal 。至于oracle中，大家就随缘自己写吧)。然后将订阅到
到数据通过中间件，写入新库。
![image](https://github.com/williamzhang11/fastThinking/blob/master/src/main/java/com/xiu/fastThinking/image/sharding4.JPG)

(4)检验一致性，没问题就切库。
