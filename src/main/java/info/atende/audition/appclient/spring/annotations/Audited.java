package info.atende.audition.appclient.spring.annotations;

import info.atende.audition.model.SecurityLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Giovanni Silva.
 */
@Retention(RetentionPolicy.RUNTIME)
// TODO target ElementType.Type
@Target({ElementType.METHOD})
public @interface Audited {
    public String action() default "";
    public String resourceType() default "";
    public String resourceId() default "";
    public SecurityLevel securityLevel() default SecurityLevel.NORMAL;
    public String description() default "";
}
