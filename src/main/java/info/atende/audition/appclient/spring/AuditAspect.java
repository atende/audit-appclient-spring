package info.atende.audition.appclient.spring;

import info.atende.audition.model.AuditEvent;
import info.atende.audition.model.Resource;
import info.atende.audition.model.SecurityLevel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @author Giovanni Silva.
 */
@Aspect
public class AuditAspect {
    Logger logger = LoggerFactory.getLogger(AuditAspect.class);
    // TODO target ElementType.Type
    @Around("execution(* info.atende.audition.appclient.spring.CustomerService.print*(..))")
    public void dispatchAuditEvent(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            joinPoint.proceed();
            dispatchEvent(joinPoint);
        }catch (Exception ex){
            throw ex;
        }

    }

    void dispatchEvent(ProceedingJoinPoint joinPoint) throws Throwable {

        final String methodName = joinPoint.getSignature().getName();
        final MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        /**
         * Do nothing if class is annotated with NoAudit
         */
        NoAudit classNoAudit = (NoAudit) joinPoint.getSignature().getDeclaringType().getAnnotation(NoAudit.class);
        if(classNoAudit != null){
            return;
        }
        /**
         * Do nothing if method is annotated with NoAudit
         */
        Method method = signature.getMethod();
        NoAudit noAuditMethod  = method.getAnnotation(NoAudit.class);
        if(noAuditMethod != null){
            return;
        }

        /**
         * Get the method implementation, in cases where the target is a interface
         */
        if(method.getDeclaringClass().isInterface()){
            try {
                method = joinPoint.getTarget().getClass().getDeclaredMethod(methodName, method.getParameterTypes());
            } catch (NoSuchMethodException e) {
                logger.error("Can't get method implementation for annotation audit event");
            }
        }

        Audited annotation = method.getAnnotation(Audited.class);

        AuditEvent event = new AuditEvent();
        if(annotation == null){
            event.setAction(methodName);
            event.setSecurityLevel(SecurityLevel.NORMAL);
        }else{
            if(!annotation.action().trim().equals("")){
                event.setAction(annotation.action());
            }else {
                event.setAction(methodName);
            }
            if(!annotation.description().trim().equals("")){
                event.setDescription(annotation.description());
            }
            Resource r = new Resource();
            r.setResourceType(annotation.resourceType());
            r.setResourceId(annotation.resourceId());
            event.setResource(r);
            event.setSecurityLevel(annotation.securityLevel());

        }

        event.setDateTime(LocalDateTime.now());

        // Get Ip and get UserName
        logger.trace("Intercepted Audit Method");
        System.out.println("=======");
        System.out.println("hijacked : " + joinPoint.getSignature().getName());
        System.out.println(event);
        System.out.println("*******");
    }
}
