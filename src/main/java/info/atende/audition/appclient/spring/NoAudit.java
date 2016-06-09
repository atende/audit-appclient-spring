package info.atende.audition.appclient.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Methods and classes annotated with this annotation are ignored
 * The AuditEvent is not sent
 * @author Giovanni Silva.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface NoAudit {

}
