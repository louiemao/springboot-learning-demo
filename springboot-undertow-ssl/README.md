# http转https
http转https需要安全协议ssl证书，协议证书基本上都需要花钱买的，也有免费试用期限的，不过可以自己生成ssl证书（又为自签证书），自己生成证书的方法有多种，在这里就简单的介绍一种由jdk自带的keytool工具生成自签证书，生成证书的前提条件是已经安装了jdk，其次找到安装jdk的bin目录下，可以看到有个keytool

### keytool生成ssl证书
```
keytool -genkey -alias undertow -storetype PKCS12 -keyalg RSA  -keypass 123456 -keysize 1024 -validity 365 -keystore D:\temp\ldkeystore.p12 -storepass 123456

您的名字与姓氏是什么?
  [Unknown]:
您的组织单位名称是什么?
  [Unknown]:
您的组织名称是什么?
  [Unknown]:
您所在的城市或区域名称是什么?
  [Unknown]:
您所在的省/市/自治区名称是什么?
  [Unknown]:
该单位的双字母国家/地区代码是什么?
  [Unknown]:
CN=Unknown, OU=Unknown, O=Unknown, L=Unknown, ST=Unknown, C=Unknown是否正确?
  [否]:  y
```

### 配置证书
在application.properties中配置自签证书
```
server.ssl.key-store=classpath:ldkeystore.p12   #证书
server.ssl.key-store-password=123456           #别名密码
server.ssl.key-store-type=PKCS12             #证书类型
server.ssl.key-alias=undertow   #别名
```
配置好之后就可以启动服务，通过https://localhost:8080访问
这里没有修改端口号，可以配置server.port=8443，把端口改为8443


# https+http2
```

/**
 * 这个配置类是可选配置，用于配置http请求重定向到https和对http2的支持
 */
@Configuration
public class HttpsConfig {
    /**
     * undertow服务器下http重定向到https
     */
    @Bean
    UndertowEmbeddedServletContainerFactory embeddedServletContainerFactory() {

        UndertowEmbeddedServletContainerFactory factory = new UndertowEmbeddedServletContainerFactory();

        // 这段就可以可以转换为http2
        factory.addBuilderCustomizers(builder -> builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true));
        //这段可以增加http重定向，如果只需要http2的话下面的代码可以去掉
        factory.addBuilderCustomizers(new UndertowBuilderCustomizer() {
            @Override
            public void customize(Undertow.Builder builder) {
                builder.addHttpListener(8080, "0.0.0.0");
            }
        });
        //下面这段是将http的8080端口重定向到https的8443端口上
        factory.addDeploymentInfoCustomizers(deploymentInfo -> {
            deploymentInfo.addSecurityConstraint(new SecurityConstraint()
                    .addWebResourceCollection(new WebResourceCollection()
                            .addUrlPattern("/*")).setTransportGuaranteeType(TransportGuaranteeType.CONFIDENTIAL)
                    .setEmptyRoleSemantic(SecurityInfo.EmptyRoleSemantic.PERMIT))
                    .setConfidentialPortManager(exchange -> 8443);
        });
        return factory;
    }
}
```
重启服务，https://localhost:8443可以访问网页，也可以通过http://localhost:8080访问会自动跳转到https://localhost:8443的端口上 ；

验证http2:使用Chrome的网络工具，在地址栏中输入chrome://net-internals/#http2进行查看