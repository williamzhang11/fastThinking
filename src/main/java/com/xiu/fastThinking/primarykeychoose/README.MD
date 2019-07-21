# 主键选择
	从数据库索引优化的角度看，Innodb引擎非常适合使用自增主键。
	因为InnoDB使用聚集索引，数据记录存储与主索引的叶子节点上，如果表使用自增主键，每次插入新的记录，会顺序添加到当前索引节点的后续位置，
	当一页写满，会自动开辟新的页。
![image](https://github.com/williamzhang11/fastThinking/blob/master/src/main/java/com/xiu/fastThinking/primarykeychoose/image/p1.jpg)

	这样会形成一个紧凑的索引结构，由于插入时，不需要移动已有数据，因此效率很高，也不会增加很多开销在维护索引上
	如果使用非自增主键（如身份证号），每次插入主键的值近似于随机，因此每次新记录都被插入到现有索引页的中间位置
![image](https://github.com/williamzhang11/fastThinking/blob/master/src/main/java/com/xiu/fastThinking/primarykeychoose/image/p2.jpg)

	mysql不得不为新记录插到合适位置而移动数据。甚至目标页可能已经被会写到磁盘上而从缓存中清掉，此时又要从磁盘读回来。
	这增加了很多开销，同时频繁的移动、分页操作造成了大量的碎片，得到了不够紧凑的索引结构，后续不得不通过OPTIMIZE TABLE来重建表并优化填充页面。
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	