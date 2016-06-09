package info.atende.audition.appclient.spring.test;

import info.atende.audition.appclient.spring.Auditable;
import info.atende.audition.appclient.spring.NoAudit;

/**
 * @author Giovanni Silva.
 */
@Auditable
public class TestTargetNoAuditMethod {

    @NoAudit
    public void printSomething(){
        System.out.println("The Aspect Advice should not execute after this method");
    }

    public void doPrintSomething(){
        System.out.println("The Aspect Advice should execute after this method");
    }
}
