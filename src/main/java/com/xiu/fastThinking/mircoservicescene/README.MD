# 微服务组件使用的一些场景

## 1. 客户端如何访问这些服务？（API Gateway）

传统的开发方式，所有的服务都是本地的，UI可以直接调用，现在按功能拆分成独立的服务，
跑在独立的一般都在独立的虚拟机上的 Java进程了。客户端UI如何访问他的？后台有N个服务，
前台就需要记住管理N个服务，一个服务下线/更新/升级，前台就要重新部署，这明显不服务我们 拆分
的理念，特别当前台是移动应用的时候，通常业务变化的节奏更快。另外，N个小服务的调用也是一个
不小的网络开销。还有一般微服务在系统内部，通常是无状态的，用户登录信息和权限管理最好有一
个统一的地方维护管理（OAuth）。

所以，一般在后台N个服务和UI之间一般会一个代理或者叫API Gateway，他的作用包括


（1）提供统一服务入口，让微服务对前台透明

（2）聚合后台的服务，节省流量，提升性能

（3）提供安全，过滤，流控等API管理功能

最重要的作用是为前台（通常是移动应用）提供后台服务的聚合，提供一个统一的服务出口，
解除他们之间的耦合，不过API Gateway也有可能成为单点故障点或者性能的瓶颈。

![image](https://github.com/williamzhang11/fastThinking/blob/master/src/main/java/com/xiu/fastThinking/whymessagequeue/image/mgateway.JPG)

## 2 服务之间如何通信？（服务调用）

因为所有的微服务都是独立的Java进程跑在独立的虚拟机上，所以服务间的通行就是
IPC（inter process communication），已经有很多成熟的方案。现在基本最通用的有两种方式

(1) REST（JAX-RS，Spring Boot）
(2) RPC（Thrift, Dubbo）
(3) 异步消息调用(Kafka, Notify)

![image](https://github.com/williamzhang11/fastThinking/blob/master/src/main/java/com/xiu/fastThinking/whymessagequeue/image/servicecall.JPG)

一般同步调用比较简单，一致性强，但是容易出调用问题，性能体验上也会差些，特别是调用层次多的时候

(1)一般REST基于HTTP，更容易实现，更容易被接受，服务端实现技术也更灵活些，各个语言都能支持，同时
能跨客户端，对客户端没有特殊的要 求，只要封装了HTTP的SDK就能调用，所以相对使用的广一些
(2)RPC也有自己的优点，传输协议更高效，安全更可控.

(3)异步消息的方式在分布式系统中有特别广泛的应用，他既能减低调用服务之间的耦合，又能成为调用之间的缓冲，
确保消息积压不会冲垮被调用方，同时能 保证调用方的服务体验，继续干自己该干的活，不至于被后台性能拖慢。
不过需要付出的代价是一致性的减弱，需要接受数据最终一致性；还有就是后台服务一般要 实现幂等性，因为消息
发送出于性能的考虑一般会有重复（保证消息的被收到且仅收到一次对性能是很大的考验）；最后就是必须引入一
个独立的broker

## 3. 这么多服务怎么查找？（服务发现）

​ 在微服务架构中，一般每一个服务都是有多个拷贝，来做负载均衡。一个服务随时可能下线，
也可能应对临时访问压力增加新的服务节点。服务之间如何相互 感知？服务如何管理？这就是服务发
现的问题了。一般有两类做法，也各有优缺点。

原理：
基本都是通过zookeeper等类似技术做服务注册信息的分布式管理。当 服务上线时，服务提供者将自
己的服务信息注册到ZK（或类似框架），并通过心跳维持长链接，实时更新链接信息。服务调用者通过
ZK寻址，根据可定制算法，找到一个服务，还可以将服务信息缓存在本地以提高性能。当服务下线时，
ZK会发通知给服务客户端

客户端做：优点是架构简单，扩展灵活，只对服务注册器依赖。缺点是客户端要维护所有调用服务的地址，有技术难度，一般大公司都有成熟的内部框架支持，比如Dubbo。

服务端做：优点是简单，所有服务对于前台调用方透明，一般在小公司在云服务上部署的应用采用的比较多。

![image](https://github.com/williamzhang11/fastThinking/blob/master/src/main/java/com/xiu/fastThinking/whymessagequeue/image/servicefind.JPG)

## 4. 服务挂了怎么办

当我们的系统是由一系列的服务调用链组成的时候，我们必须确保任一环节出问题都不
至于影响整体链路。相应的手段有很多：

重试机制
限流
熔断机制
负载均衡
降级（本地缓存）

![image](https://github.com/williamzhang11/fastThinking/blob/master/src/main/java/com/xiu/fastThinking/whymessagequeue/image/serviceretry.JPG)




微服务架构需要考虑的问题，包括

API Gateway
服务间调用
服务发现
服务容错
服务部署
数据调用










