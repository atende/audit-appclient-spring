package info.atende.audition.appclient.spring;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Just to test
 * TODO Remove this class
 * @author Giovanni Silva.
 */
public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring.xml");
        CustomerService customerService = ctx.getBean("costumer", CustomerService.class);

        customerService.printName();

        customerService.printURL();
        customerService.printThrowException();

        ctx.close();
    }
}
