
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

--之间关闭进程
curl -X POST http://localhost:6061/monitor/shutdown