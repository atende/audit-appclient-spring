package info.atende.audition.appclient.spring.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation mark a class to be Audited
 * All public methods will be intercepted by default
 * To ignore a method or class (if you define your on PointCut) use {@link NoAudit}
 * @author Giovanni Silva.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Auditable {
}
