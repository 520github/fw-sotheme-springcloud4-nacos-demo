
http://localhost:6061/actuator/health

http://localhost:6061/monitor/up


http://localhost:6061/monitor/beans

http://localhost:6061/monitor/metrics    


http://localhost:6061/monitor/env


http://localhost:6061/monitor/configprops


http://localhost:6061/monitor/metrics/process.uptime


-- 无法方法
http://localhost:6061/monitor/httptrace


http://localhost:6061/monitor/mappings

--直接关闭进程
curl -X POST http://localhost:6061/monitor/shutdown



http://localhost:6061/monitor/metrics/order.get.timer

http://localhost:6061/monitor/metrics/order.get.counter

http://localhost:6061/monitor/metrics/test.timeout.gauge


-- 日志级别
http://localhost:6061/monitor/loggers

--修改日志级别
curl -X "POST" "http://localhost:6061/monitor/loggers/org.sunso.sotheme.springcloud4.order.controller"  -H "Content-Type: application/json;charset=UTF-8"  -d '{"configuredLevel": "DEBUG"}'





---

http://localhost:6061/monitor/threaddump


--- 自定义
http://localhost:6061/monitor/monitor?data=mum