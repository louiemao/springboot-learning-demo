package com.louie.learning.springboot;

import io.undertow.Undertow;
import io.undertow.UndertowOptions;
import io.undertow.servlet.api.SecurityConstraint;
import io.undertow.servlet.api.SecurityInfo;
import io.undertow.servlet.api.TransportGuaranteeType;
import io.undertow.servlet.api.WebResourceCollection;
import org.springframework.boot.context.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
