# mysql为什么不推荐使用物理外键
	mysql中innodb支持外键。外键约束是为了是保证数据一致性，完整性，保证数据主从关系。
	但外键存在性能问题：
	1.数据库需要维护外键的内部管理
	2.外键等于把数据的一致性事务实现，全部交给数据库服务器完成
	3.有了外键，当做涉及外键字段的增，删，改操作后，需要触发相关操作去检查，而不得不消耗资源
	4.外键还会因为需要请求对其他表内部加锁，容易出现死锁。
	
	如果不使用物理外键，也可以通过业务逻辑实现逻辑外键。
	1.大多数情况下只更新不删除，进行逻辑删除
	2.有些操作可以使用异步操作提高性能
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	