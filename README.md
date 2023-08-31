
## 直接通过orderService访问
~~~
curl http://localhost:6061/order/get/105

curl http://localhost:6061/order/user/get/1692078242128965633

curl http://localhost:6061/order/user/service/get/1692078242128965633

curl http://localhost:6061/order/user/service/save/222
~~~

## 直接通过userService访问
~~~
curl http://localhost:6062/user/get/1692078242128965633

curl http://localhost:6062/user/save/111
~~~

## 通过gateway访问order和user服务
~~~
访问orderService服务：
curl http://localhost:6060/orderService4/order/get/105

访问userService服务：
curl http://localhost:6060/userService4/user/get/1692078242128965633
~~~

## orderService通过feign调用userService
~~~
curl http://localhost:6061/order/user/get/1692078242128965633

curl http://localhost:6060/orderService4/order/user/get/1692078242128965633
~~~