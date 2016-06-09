package info.atende.audition.appclient.spring;

import info.atende.audition.appclient.spring.test.TestTargetNoAuditClass;
import info.atende.audition.appclient.spring.test.TestTargetNoAuditMethod;
import org.junit.Test;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.*;

/**
 * Unit Test for AuditAspect
 * @author Giovanni Silva.
 */
public class TestAuditAspect {

    @Test
    public void testNoAuditClass() throws Throwable {
        TestTargetNoAuditClass mockTarget = new TestTargetNoAuditClass();
        AspectJProxyFactory factory = new AspectJProxyFactory(mockTarget);
        AuditAspect aspect = spy(AuditAspect.class);
        factory.addAspect(aspect);
        TestTargetNoAuditClass proxy = factory.getProxy();

        proxy.printSomething();
        proxy.printSomething2();

        verify(aspect, times(0)).dispatchEvent(anyObject());
    }

    @Test
    public void testNoAuditMethod() throws Throwable {
        TestTargetNoAuditMethod mockTarget = new TestTargetNoAuditMethod();
        AspectJProxyFactory factory = new AspectJProxyFactory(mockTarget);
        AuditAspect aspect = spy(AuditAspect.class);
        factory.addAspect(aspect);
        TestTargetNoAuditMethod proxy = factory.getProxy();

        proxy.printSomething();
        proxy.doPrintSomething();

        verify(aspect, times(1)).dispatchEvent(anyObject());
    }

    @Test
    public void testOfTestAhahaha(){
        TestTargetNoAuditMethod target = new TestTargetNoAuditMethod();
        AspectJProxyFactory factory = new AspectJProxyFactory(target);
        AuditAspect aspect = new AuditAspect();
        factory.addAspect(aspect);
        TestTargetNoAuditMethod proxy = factory.getProxy();

        proxy.printSomething();
        proxy.doPrintSomething();
    }


}
