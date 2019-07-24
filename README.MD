 本项目主要是整理 工作中的技术思考总结。 目录结构为：com.xiu.fastThinking下的每个包中都有一种， 以及详细的解释。
 
 本项目为maven项目，直接使用maven方式导入即可。

## 目前已整理的技术以及对应的包名如下：
|技术|包路径|
| :------ | :------ |
| 数据库三范式与反模式是什么							|				[com.xiu.fastTech.threenormalform](src/main/java/com/xiu/fastThinking/threenormalform/README.MD)|
| mysql数据库为什么不推荐使用物理外键					|				[com.xiu.fastTech.mysqlforeignkey](src/main/java/com/xiu/fastThinking/mysqlforeignkey/README.MD)|
| Innodb的主键怎么选择							    |				[com.xiu.fastTech.primarykeychoose](src/main/java/com/xiu/fastThinking/primarykeychoose/README.MD)|
| 使用覆盖索引为什么更快								|				[com.xiu.fastTech.coverindex](src/main/java/com/xiu/fastThinking/coverindex/README.MD)|
| 数据库索引使用总结									|				[com.xiu.fastTech.indexuse](src/main/java/com/xiu/fastThinking/indexuse/README.MD)|
| innodb在什么情况下会产生死锁								|				[com.xiu.fastTech.innodbdeadlock](src/main/java/com/xiu/fastThinking/innodbdeadlock/README.MD)|
| 为什么使用消息队列								|				[com.xiu.fastTech.whymessagequeue](src/main/java/com/xiu/fastThinking/whymessagequeue/README.MD)|
| 消息队列有哪些缺点								|				[com.xiu.fastTech.mqweakness](src/main/java/com/xiu/fastThinking/mqweakness/README.MD)|
| Kafka、ActiveMQ、RabbitMQ、RocketMQ 有什么优缺点？								|				[com.xiu.fastTech.mqcompare](src/main/java/com/xiu/fastThinking/mqcompare/README.MD)|
| 如何保证消息队列的高可用性？								|				[com.xiu.fastTech.mqha](src/main/java/com/xiu/fastThinking/mqha/README.MD)|
| 如何保证消息不被重复消费？								|				[com.xiu.fastTech.mqdoublespend](src/main/java/com/xiu/fastThinking/mqdoublespend/README.MD)|


	