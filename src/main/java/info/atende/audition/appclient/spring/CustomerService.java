package info.atende.audition.appclient.spring;

import info.atende.audition.appclient.spring.annotations.Auditable;
import info.atende.audition.appclient.spring.annotations.Audited;
import info.atende.audition.model.SecurityLevel;

/**
 * Just to test
 * TODO Remove this class
 * @author Giovanni Silva.
 */
@Auditable
public class CustomerService {
    private String name = "Name";
    private String url = "URL";


    public void printName() {
        System.out.println("Customer name : " + this.name);
    }
    @Audited(action = "print_url", resourceType = "url", resourceId = "1", securityLevel = SecurityLevel.HIGHT, description = "Descriptiohn")
    public void printURL() {
        System.out.println("Customer website : " + this.url);
    }

    public void printThrowException() {
        throw new IllegalArgumentException();
    }
}
