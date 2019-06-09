redis:106.14.13.61:6379
mysql:106.14.13.61:3306/soul_user
mysql:106.14.13.61:3306/soul_dynamics_01
mysql:106.14.13.61:3306/soul_dynamics_02
zookeeper106.14.13.61:2181
mq:  106.14.13.61:61616

1:user thrift 140.143.79.106:7911

2:message mq 106.14.13.61:61616

3:user edge port:8082

4:dynamics dubbo provider port:20880

5:dynamics dubbo edge port:8081

6:ws netty chat port : 8089

7:zuul port:8080




##
http://localhost:8081//dynamics/list
http://localhost:8082/user/sendVerifyCode?email=1203269511@qq.com


单机部署：
netty-server 8089 8090