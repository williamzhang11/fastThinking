# innodb产生死锁的4种情况
	
## innodb死锁的检测
	有2种方法
	1.如果存在2个事务相互等待，当一个等待时间超过设置某一个阈值时，对其中一个事务回滚，
	另一个事务继续执行。参数innodb_lock_wait_timeout设置超时
	
	
	2.wait-for graph算法来主动进行死锁检测。innodb将各个事务看成一个个节点，资源就
	是各个事务占用的锁，当事务1需要等待事务2时，就生成一条有向边，同理事务2需要等待事务1，
	也形成一条有向边，最后形成一个有向图。就是死锁。
	
![image](https://github.com/williamzhang11/fastThinking/blob/master/src/main/java/com/xiu/fastThinking/innodbdeadlock/image/waitforgraph.png)

## 死锁的4种情况

### 不同表相同记录行锁冲突
	事务A和事务B操作2张表，但出现循环等待锁情况
	
![image](https://github.com/williamzhang11/fastThinking/blob/master/src/main/java/com/xiu/fastThinking/innodbdeadlock/image/twotable.png)

### 相同表记录行锁冲突

	如2个job在执行数据批量更新时，jobA处理id为[1,2,3,4],jobB处理的id列表为[8,9,4,2]
	
![image](https://github.com/williamzhang11/fastThinking/blob/master/src/main/java/com/xiu/fastThinking/innodbdeadlock/image/onetable.png)

### 不同索引锁冲突

	这种情况比较隐晦，事务A执行时，除了在二级索引加锁，还会在主键索引加锁，在主键索引加锁顺序[1,4,2,3,5],而事务B执行时，
	只在主键索引上加锁，加锁顺序[1,2,3,4,5],这样就造成死锁的可能
	
![image](https://github.com/williamzhang11/fastThinking/blob/master/src/main/java/com/xiu/fastThinking/innodbdeadlock/image/indexlock.png)

### gap锁冲突
	innodb在RR级别下，范围条件会对gap区间加锁

![image](https://github.com/williamzhang11/fastThinking/blob/master/src/main/java/com/xiu/fastThinking/innodbdeadlock/image/gaplock.png)


## gap锁
 	为了解决幻读问题，innodb引入了gap锁。

      在事务A执行：update msg set message=‘订单’ where token=‘asd’;

   innodb首先会和RC级别一样，给索引上的记录添加上X锁，此外，还在非唯一索引’asd’与相邻两个索引的区间加上锁。

       这样，当事务B在执行insert into msg values (null,‘asd',’hello’); commit;时，
       会首先检查这个区间是否被锁上，如果被锁上，则不能立即执行，需要等待该gap锁被释放。这样就能避免幻读问题。
	
![image](https://github.com/williamzhang11/fastThinking/blob/master/src/main/java/com/xiu/fastThinking/innodbdeadlock/image/gaplock1.png)


## 如何避免死锁

	1）以固定的顺序访问表和行。比如对第2节两个job批量更新的情形，简单方法是对id列表先排序，后执行，
	这样就避免了交叉等待锁的情形；又比如对于3.1节的情形，将两个事务的sql顺序调整为一致，也能避免死锁。
	
	2）大事务拆小。大事务更倾向于死锁，如果业务允许，将大事务拆小。
	
	3）在同一个事务中，尽可能做到一次锁定所需要的所有资源，减少死锁概率。
	
	4）降低隔离级别。如果业务允许，将隔离级别调低也是较好的选择，比如将隔离级别从RR调整为RC，可以避免掉很多因为gap锁造成的死锁。
	
	5）为表添加合理的索引。可以看到如果不走索引将会为表的每一行记录添加上锁，死锁的概率大大增大。

## 死锁排查

	1）通过应用业务日志定位到问题代码，找到相应的事务对应的sql；
	2）确定数据库隔离级别。    执行select @@global.tx_isolation，可以确定数据库的隔离级别，我们数据库的隔离级别是RC，这样可以很大概率排除gap锁造成死锁的嫌疑;
	3）执行下show InnoDB STATUS看看最近死锁的日志
























源地址：http://www.cnblogs.com/LBSer
	
	


































