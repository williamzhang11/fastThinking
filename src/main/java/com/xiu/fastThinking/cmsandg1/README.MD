# CMS与G1 

1.绝大部分新生成的对象都放在Eden区，当Eden区将满，JVM会因申请不到内存，而触发Young GC ,进行Eden区+有对象的Survivor区(设为S0区)垃圾回收，把存活的对象用复制算法拷贝到一个空的Survivor(S1)中，此时Eden区被清空，另外一个Survivor S0也为空。下次触发Young GC回收Eden+S0，将存活对象拷贝到S1中。新生代垃圾回收简单、粗暴、高效。


2.若发现Survivor区满了，则将这些对象拷贝到old区或者Survivor没满但某些对象足够Old,也拷贝到Old区(每次Young GC都会使Survivor区存活对象值+1，直到阈值)。 3.Old区也会进行垃圾收集(Young GC),发生一次 Major GC 至少伴随一次Young GC，一般比Young GC慢十倍以上。

3.JVM在Old区申请不到内存，会进行Full GC。Old区使用一般采用Concurrent-Mark–Sweep策略回收内存。

缺点：

GC过程中会出现STW(Stop-The-World)，若Old区对象太多，STW耗费大量时间。
CMS收集器对CPU资源很敏感。
CMS收集器无法处理浮动垃圾，可能出现“Concurrent Mode Failure”失败而导致另一次Full GC的产生。
CMS导致内存碎片问题。

最主要的区别是：
cms只适用于老年代，标记-清除算法，存在碎片问题

G1，老年代(标记-整理算法)和新生代（复制算法）都适用











