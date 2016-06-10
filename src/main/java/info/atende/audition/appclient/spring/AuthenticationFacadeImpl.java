package info.atende.audition.appclient.spring;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author Giovanni Silva.
 */
public class AuthenticationFacadeImpl implements AuthenticationFacade {

    @Override
    public Authentication getAuthentication(){
        System.out.println("Implementation");
       return SecurityContextHolder.getContext().getAuthentication();
    }


}
