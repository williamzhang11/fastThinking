# 为什么使用消息队列

该问题与

消息队列有什么优点?

消息队列有什么作用?

消息队列的使用场景?

几乎是等价的。只不过为什么使用消息队列要结合业务回答。

消息队列的作用主要有三个：解耦，异步，削峰

## 1.解耦

B,C,D系统需要使用A系统产生的关键数据

无消息队列时：

	（1）系统A为系统B,C,D等提供各自的接口，导致系统A与它们紧密耦合

	（2）添加系统E又需要接口，删除B系统原接口又没用了
	
有消息队列时：

	（1）系统A作为生产者，将消息发送到消息队列
	
	（2）系统B,C,D作为消费者订阅消息
	
	（3）新增消费者只需要订阅消息，对原系统和业务没有影响
	
![image](https://github.com/williamzhang11/fastThinking/blob/master/src/main/java/com/xiu/fastThinking/whymessagequeue/image/mqdecouple.JPG)
## 2.异步

用户请求数据时，系统的响应时间是保证用户体验很重要的一部分

无消息队列时：

	用户请求A系统，A系统需要等待BCD执行完成后响应，用户收到响应用时近1秒

用消息队列时：

	用户请求A系统，A系统将请求推到消息队列中，B，C，D异步执行。用户收到响应用时200毫秒
![image](https://github.com/williamzhang11/fastThinking/blob/master/src/main/java/com/xiu/fastThinking/whymessagequeue/image/mqasync.JPG)

## 3.削峰

秒杀场景下，假如每秒有5000个请求，而mysql每秒最大处理1000条sql

无消息队列时：

	1.用户请求数据直接写入数据库，高并发时数据库压力剧增，甚至奔溃，
	
	mysql宕机，整个系统都不能用了
	
有消息队列：

	1.用户请求数据先存入mq中，系统A每秒读取1000条数据进行处理，每秒多出4000条未处理数据按
	
	场景稍后处理
	
![image](https://github.com/williamzhang11/fastThinking/blob/master/src/main/java/com/xiu/fastThinking/whymessagequeue/image/peakclipping.JPG)



















