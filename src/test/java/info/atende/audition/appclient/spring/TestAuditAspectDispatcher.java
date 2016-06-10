package info.atende.audition.appclient.spring;

import info.atende.audition.model.AuditEvent;
import info.atende.audition.model.Resource;
import info.atende.audition.model.SecurityLevel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

/**
 * @author Giovanni Silva.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-config.xml")
public class TestAuditAspectDispatcher {
    @Autowired
    AuditAspectDispatcher dispatcher;

    @Test
    public void dispatch_event(){
        RabbitTemplate mockTemplate = mock(RabbitTemplate.class);

        dispatcher.setRabbit(mockTemplate);

        Resource r = new Resource("type", "id");
        LocalDateTime time = LocalDateTime.of(2015,3,3,3,33);
        AuditEvent event = new AuditEvent("mock","user","action",r,time,"10.10.2.1",SecurityLevel.NORMAL);

        dispatcher.dispatchEvent("action", r, SecurityLevel.NORMAL,time,"description");
        verify(mockTemplate, VerificationModeFactory.times(1)).convertAndSend(eq(event));
    }
}
