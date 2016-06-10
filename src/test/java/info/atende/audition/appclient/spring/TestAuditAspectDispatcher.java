package info.atende.audition.appclient.spring;

import info.atende.audition.appclient.spring.providers.UserDetailsProvider;
import info.atende.audition.model.AuditEvent;
import info.atende.audition.model.Resource;
import info.atende.audition.model.SecurityLevel;
import org.junit.Test;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * @author Giovanni Silva.
 */
public class TestAuditAspectDispatcher {

    @Test
    public void dispatch_event(){
        // Mocks
        RabbitTemplate mockTemplate = mock(RabbitTemplate.class);
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        UserDetailsProvider mockDetailsProvider = mock(UserDetailsProvider.class);
        Authentication authentication = mock(Authentication.class);

        // Mock behavior
        when(mockRequest.getRemoteAddr()).thenReturn("10.10.2.1");
        when(mockDetailsProvider.getUserName()).thenReturn("user");

        AuditAspectDispatcher dispatcher = new AuditAspectDispatcher(mockTemplate,
                    mockRequest, mockDetailsProvider);

        dispatcher.setRabbit(mockTemplate);

        Resource r = new Resource("type", "id");
        LocalDateTime time = LocalDateTime.of(2015,3,3,3,33);
        AuditEvent event = new AuditEvent("mock","user","action",r,time,"10.10.2.1",SecurityLevel.NORMAL);

        dispatcher.dispatchEvent("action", r, SecurityLevel.NORMAL,time,null);
        verify(mockTemplate, VerificationModeFactory.times(1)).convertAndSend(eq(event));
    }
}
