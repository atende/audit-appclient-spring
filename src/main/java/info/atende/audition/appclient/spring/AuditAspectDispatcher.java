package info.atende.audition.appclient.spring;

import info.atende.audition.model.AuditEvent;
import info.atende.audition.model.Resource;
import info.atende.audition.model.SecurityLevel;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author Giovanni Silva.
 */
@Component
public class AuditAspectDispatcher {

    Logger logger = Logger.getLogger(AuditAspectDispatcher.class);

    public void dispatchEvent(String action, Resource resource, SecurityLevel level, LocalDateTime timeOfEvent,
                              String description){
        AuditEvent event = new AuditEvent();

        event.setAction(action);
        event.setResource(resource);
        event.setSecurityLevel(level);
        event.setDescription(description);
        event.setDateTime(timeOfEvent);

        // Get Ip and get UserName
        logger.trace("Intercepted Audit Method");
        System.out.println("=======");
        System.out.println("hijacked : " + action);
        System.out.println(event);
        System.out.println("*******");
    }
}
