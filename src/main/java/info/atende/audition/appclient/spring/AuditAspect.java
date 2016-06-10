package info.atende.audition.appclient.spring;

import info.atende.audition.appclient.spring.annotations.Auditable;
import info.atende.audition.appclient.spring.annotations.Audited;
import info.atende.audition.appclient.spring.annotations.NoAudit;
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
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * This aspect is used to dispatch AuditEvent for the queue
 * To use you can annotate a bean with {@link Auditable} annotation
 * or you can define your AOP Pointcut and execute the aspect {@link #dispatchAuditEvent(ProceedingJoinPoint)}
 * @see <a href="http://atende.github.io/audit-appclient-spring">Documentation</a>
 * @author Giovanni Silva.
 */
@Aspect
@Component
public class AuditAspect {

    private Logger logger = LoggerFactory.getLogger(AuditAspect.class);

    private AuditAspectDispatcher dispatcher;

    @Pointcut("within(@info.atende.audition.appclient.spring.annotations.Auditable *)")
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
