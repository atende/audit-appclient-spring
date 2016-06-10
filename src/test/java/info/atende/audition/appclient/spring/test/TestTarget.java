package info.atende.audition.appclient.spring.test;

/**
 * @author Giovanni Silva.
 */

import info.atende.audition.appclient.spring.annotations.Auditable;
import info.atende.audition.appclient.spring.annotations.Audited;
import info.atende.audition.model.SecurityLevel;
import org.springframework.stereotype.Component;

@Auditable
@Component
public class TestTarget {

    public void noAnnotation(){
        System.out.println("No annotation");
    }
    @Audited(action = "changeAction", securityLevel = SecurityLevel.HIGHT)
    public void withAnnocation(){
        System.out.println("With annotation");
    }
}
