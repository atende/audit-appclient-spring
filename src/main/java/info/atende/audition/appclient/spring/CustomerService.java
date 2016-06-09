package info.atende.audition.appclient.spring;

import info.atende.audition.model.SecurityLevel;

/**
 * @author Giovanni Silva.
 */
public class CustomerService {
    private String name;
    private String url;

    public void setName(String name) {
        this.name = name;
    }

    public void setUrl(String url) {
        this.url = url;
    }

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
