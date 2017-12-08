### 参考
[springboot(九)：定时任务](http://www.ityouknow.com/springboot/2016/12/02/springboot(%E4%B9%9D)-%E5%AE%9A%E6%97%B6%E4%BB%BB%E5%8A%A1.html)

### 参数说明
@Scheduled 参数可以接受两种定时的设置，一种是我们常用的cron="*/6 * * * * ?",一种是 fixedRate = 6000，两种都表示每隔六秒打印一下内容。

#####fixedRate 说明
* @Scheduled(fixedRate = 6000) ：上一次开始执行时间点之后6秒再执行
* @Scheduled(fixedDelay = 6000) ：上一次执行完毕时间点之后6秒再执行
* @Scheduled(initialDelay=1000, fixedRate=6000) ：第一次延迟1秒后执行，之后按fixedRate的规则每6秒执行一次
