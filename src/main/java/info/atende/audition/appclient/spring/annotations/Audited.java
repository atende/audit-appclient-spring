package info.atende.audition.appclient.spring.annotations;

import info.atende.audition.model.SecurityLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Configure information about the audited method
 * @author Giovanni Silva.
 */
@Retention(RetentionPolicy.RUNTIME)
// TODO target ElementType.Type
@Target({ElementType.METHOD})
public @interface Audited {
    String action() default "";
    String resourceType() default "";
    String resourceId() default "";
    SecurityLevel securityLevel() default SecurityLevel.NORMAL;
    String description() default "";
}
