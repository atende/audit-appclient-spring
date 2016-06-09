package info.atende.audition.appclient.spring.test;

/**
 * @author Giovanni Silva.
 */

import info.atende.audition.appclient.spring.Auditable;
import info.atende.audition.appclient.spring.Audited;
import org.springframework.stereotype.Component;

@Auditable
@Component
public class TestTarget {

    public void noAnnotation(){
        System.out.println("No annotation");
    }
    @Audited(action = "changeAction")
    public void withAnnocation(){
        System.out.println("With annotation");
    }
}
