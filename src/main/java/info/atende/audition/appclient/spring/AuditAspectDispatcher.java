package info.atende.audition.appclient.spring;

import info.atende.audition.appclient.spring.providers.UserDetailsProvider;
import info.atende.audition.model.AuditEvent;
import info.atende.audition.model.Resource;
import info.atende.audition.model.SecurityLevel;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * Dispatch {@link AuditEvent} to the audit system queue
 * @author Giovanni Silva.
 */
@Component
class AuditAspectDispatcher {
    private RabbitTemplate rabbit;
    private HttpServletRequest request;
    private UserDetailsProvider userDetailsProvider;

    private Logger logger = Logger.getLogger(AuditAspectDispatcher.class);
    @Autowired
    public AuditAspectDispatcher(RabbitTemplate rabbit,
                                 HttpServletRequest request,
                                 UserDetailsProvider userDetailsProvider) {
        this.rabbit = rabbit;
        this.request = request;
        this.userDetailsProvider = userDetailsProvider;
    }

    void dispatchEvent(String action, Resource resource, SecurityLevel level, LocalDateTime timeOfEvent,
                       String description){
        logger.trace("Dispatch Audit Event Started");
        AuditEvent event = new AuditEvent();
        event.setAction(action);
        event.setResource(resource);
        event.setSecurityLevel(level);
        event.setDescription(description);
        event.setDateTime(timeOfEvent);
        // Get Ip and get UserName
        event.setIp(request.getRemoteAddr());
        event.setUserName(userDetailsProvider.getUserName());
        // TODO Think how to inject the application name
        event.setApplicationName("mock");
        rabbit.convertAndSend(event);
    }

    public void setRabbit(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
}
