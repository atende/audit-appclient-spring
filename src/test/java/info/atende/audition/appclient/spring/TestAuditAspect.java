package info.atende.audition.appclient.spring;

import info.atende.audition.appclient.spring.test.TestTarget;
import info.atende.audition.appclient.spring.test.TestTargetNoAuditClass;
import info.atende.audition.appclient.spring.test.TestTargetNoAuditMethod;
import info.atende.audition.model.SecurityLevel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.aop.aspectj.annotation.AspectJProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.PostConstruct;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Unit Test for AuditAspect
 * @author Giovanni Silva.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-config.xml")
public class TestAuditAspect {
    @Autowired
    TestTargetNoAuditClass testTargetNoAuditClass;
    @Autowired
    TestTargetNoAuditMethod testTargetNoAuditMethod;
    @Autowired
    TestTarget testTarget;
    @Autowired
    AuditAspect aspect;

    @Test
    public void ignore_class_with_annotation() throws Throwable {
        AuditAspectDispatcher mockDispatcher = createNewDispatcherMock();
        testTargetNoAuditClass.printSomething();
        testTargetNoAuditClass.printSomething2();
        verify(mockDispatcher, times(0)).dispatchEvent(anyString(),anyObject(),anyObject(),anyObject(),anyString());
    }

    @Test
    public void ignore_method_with_annotation() throws Throwable {
        AuditAspectDispatcher mockDispatcher = createNewDispatcherMock();
        testTargetNoAuditMethod.printSomething();
        testTargetNoAuditMethod.doPrintSomething();
        verify(mockDispatcher, times(1)).dispatchEvent(anyString(),anyObject(),anyObject(),anyObject(),anyString());
    }

    @Test
    public void dispatch_event_method_without_annotation(){
        AuditAspectDispatcher mockDispatcher = createNewDispatcherMock();
        testTarget.noAnnotation();
        verify(mockDispatcher, times(1)).dispatchEvent(eq("noAnnotation"), anyObject(), eq(SecurityLevel.NORMAL),
                anyObject(), eq(""));
    }

    @Test
    public void dispatch_event_method_with_annotation(){
        AuditAspectDispatcher mockDispatcher = createNewDispatcherMock();
        testTarget.withAnnocation();
        verify(mockDispatcher, times(1)).dispatchEvent(eq("changeAction"), anyObject(), eq(SecurityLevel.HIGHT),
                anyObject(), eq(""));
    }

    private AuditAspectDispatcher createNewDispatcherMock(){
        AuditAspectDispatcher mockDispatcher = mock(AuditAspectDispatcher.class);
        aspect.setDispatcher(mockDispatcher);
        return mockDispatcher;
    }


}
