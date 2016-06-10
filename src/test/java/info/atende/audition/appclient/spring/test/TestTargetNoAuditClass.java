package info.atende.audition.appclient.spring.test;

import info.atende.audition.appclient.spring.annotations.Auditable;
import info.atende.audition.appclient.spring.annotations.NoAudit;
import org.springframework.stereotype.Component;

/**
 * A target for test NoAudit annotation
 * @author Giovanni Silva.
 */
@Auditable
@NoAudit
@Component
public class TestTargetNoAuditClass {
    public void printSomething(){
        System.out.println("The Aspect Advice should not execute after this method");
    }

    public void printSomething2(){
        System.out.println("The Aspect Advice should not execute after this method");
    }
}
