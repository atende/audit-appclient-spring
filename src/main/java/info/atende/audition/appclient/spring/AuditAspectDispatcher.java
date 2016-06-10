package info.atende.audition.appclient.spring;

import info.atende.audition.model.AuditEvent;
import info.atende.audition.model.Resource;
import info.atende.audition.model.SecurityLevel;
import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Giovanni Silva.
 */
@Component
public class AuditAspectDispatcher {
    private RabbitTemplate rabbit;
    private Logger logger = Logger.getLogger(AuditAspectDispatcher.class);

    @Autowired
    public AuditAspectDispatcher(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    public void dispatchEvent(String action, Resource resource, SecurityLevel level, LocalDateTime timeOfEvent,
                              String description){
        AuditEvent event = new AuditEvent();
        logger.trace("Dispatch Audit Event Started");
        event.setAction(action);
        event.setResource(resource);
        event.setSecurityLevel(level);
        event.setDescription(description);
        event.setDateTime(timeOfEvent);

        // Get Ip and get UserName
//        Resource r = new Resource("type", "id");
//        LocalDateTime time = LocalDateTime.of(2015,3,3,3,33);
//        AuditEvent event = new AuditEvent("mock","user","action",r,time,"10.10.2.1",SecurityLevel.NORMAL);

        rabbit.convertAndSend(event);
    }

    public void setRabbit(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }
}
