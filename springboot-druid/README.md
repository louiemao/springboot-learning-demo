# Springboot集成druid

### 添加依赖
```
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-jdbc</artifactId>
		</dependency>
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>druid-spring-boot-starter</artifactId>
			<version>1.1.5</version>
		</dependency>
		<!--h2数据库驱动-->
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<version>1.4.194</version>
		</dependency>
```

### application.properties
```
spring.datasource.type=com.alibaba.druid.pool.DruidDataSource
#druid连接池配置
spring.datasource.druid.initial-size=5
spring.datasource.druid.max-active=20
spring.datasource.druid.min-idle=5
spring.datasource.druid.max-wait=60000
spring.datasource.druid.pool-prepared-statements=true
spring.datasource.druid.max-pool-prepared-statement-per-connection-size=20
spring.datasource.druid.validation-query=SELECT 1 FROM DUAL
#spring.datasource.druid.validation-query-timeout=
spring.datasource.druid.test-on-borrow=false
spring.datasource.druid.test-on-return=false
spring.datasource.druid.test-while-idle=true
spring.datasource.druid.time-between-eviction-runs-millis=60000
spring.datasource.druid.min-evictable-idle-time-millis=300000
#spring.datasource.druid.max-evictable-idle-time-millis=
spring.datasource.druid.filters=stat,wall
#监控配置
# WebStatFilter配置，说明请参考Druid Wiki，配置_配置WebStatFilter
#spring.datasource.druid.web-stat-filter.enabled= #是否启用StatFilter默认值true
#spring.datasource.druid.web-stat-filter.url-pattern=
spring.datasource.druid.web-stat-filter.exclusions=*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*
#spring.datasource.druid.web-stat-filter.session-stat-enable=
#spring.datasource.druid.web-stat-filter.session-stat-max-count=
#spring.datasource.druid.web-stat-filter.principal-session-name=
#spring.datasource.druid.web-stat-filter.principal-cookie-name=
#spring.datasource.druid.web-stat-filter.profile-enable=
# StatViewServlet配置，说明请参考Druid Wiki，配置_StatViewServlet配置
#spring.datasource.druid.stat-view-servlet.enabled= #是否启用StatViewServlet默认值true
#spring.datasource.druid.stat-view-servlet.url-pattern=
#spring.datasource.druid.stat-view-servlet.reset-enable=
spring.datasource.druid.stat-view-servlet.login-username=admin
spring.datasource.druid.stat-view-servlet.login-password=admin
spring.datasource.druid.stat-view-servlet.allow=127.0.0.1,169.24.49.36
#spring.datasource.druid.stat-view-servlet.deny=
#配置StatFilter
spring.datasource.druid.filter.stat.db-type=h2
spring.datasource.druid.filter.stat.log-slow-sql=true
spring.datasource.druid.filter.stat.slow-sql-millis=2000
#配置WallFilter
spring.datasource.druid.filter.wall.enabled=true
spring.datasource.druid.filter.wall.db-type=h2
spring.datasource.druid.filter.wall.config.delete-allow=false
spring.datasource.druid.filter.wall.config.drop-table-allow=false
```