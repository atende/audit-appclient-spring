package info.atende.audition.appclient.spring.test;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.mock;

/**
 * A test configuration for RabbitMQ to mock in spring beans
 * @author Giovanni Silva.
 */
@Configuration
public class TestBeansFake {

    public RabbitTemplate rabbitTemplateMock;

    @Bean
    public RabbitTemplate rabbitTemplateMock(){
        return mock(RabbitTemplate.class);
    }
    @Bean
    public HttpServletRequest httpServletRequest(){
        return mock(HttpServletRequest.class);
    }

}
