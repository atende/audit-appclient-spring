package info.atende.audition.appclient.spring;

import info.atende.audition.model.AuditEvent;
import info.atende.audition.model.Resource;
import info.atende.audition.model.SecurityLevel;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @author Giovanni Silva.
 */
@Aspect
public class AuditAspect {

    private Logger logger = LoggerFactory.getLogger(AuditAspect.class);

    private AuditAspectDispatcher dispatcher;

    @Pointcut("within(@info.atende.audition.appclient.spring.Auditable *)")
    public void beanAnnotatedWithAuditable() {}

    @Pointcut("execution(public * *(..))")
    public void publicMethod() {}

    @Pointcut("publicMethod() && beanAnnotatedWithAuditable()")
    public void publicMethodInsideAClassMarkedWithAuditable() {}

    @Autowired
    public AuditAspect(AuditAspectDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Around("publicMethodInsideAClassMarkedWithAuditable()")
    public void dispatchAuditEvent(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            joinPoint.proceed();
            dispatchEvent(joinPoint);
        }catch (Exception ex){
            throw ex;
        }

    }

    private void dispatchEvent(ProceedingJoinPoint joinPoint) throws Throwable {
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
        String action;
        SecurityLevel level;
        String description = "";
        Resource resource = new Resource();
        if(annotation == null){
            action = methodName;
            level = SecurityLevel.NORMAL;
            resource.setResourceType("");
            resource.setResourceId("");
        }else{
            if(!annotation.action().trim().equals("")){
                action = annotation.action();
            }else {
                action = methodName;
            }
            if(!annotation.description().trim().equals("")){
                description = annotation.description();
            }
            resource.setResourceType(annotation.resourceType());
            resource.setResourceId(annotation.resourceId());
            level = annotation.securityLevel();

        }

        dispatcher.dispatchEvent(action, resource, level,LocalDateTime.now(), description);
    }

    public void setDispatcher(AuditAspectDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
}
