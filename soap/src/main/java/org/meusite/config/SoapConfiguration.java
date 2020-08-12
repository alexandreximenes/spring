package org.meusite.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;

@Configuration
@EnableWs
public class SoapConfiguration extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean servlet(ApplicationContext context){

        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/soapWS/*");
    }
    @Bean
    public SimpleXsdSchema getSchema(){
        return new SimpleXsdSchema(new ClassPathResource("user.xsd"));
    }

    @Bean
    public DefaultWsdl11Definition definition(){
        DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
        definition.setSchema(getSchema());
        definition.setTargetNamespace("http://www.meusite.org/user");
        definition.setLocationUri("/soapWS");
        definition.setPortTypeName("UserService");

        return definition;
    }
}
