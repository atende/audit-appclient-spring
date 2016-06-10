package info.atende.audition.appclient.spring;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author Giovanni Silva.
 */
@Component
@ConditionalOnMissingBean
public class AuthenticationFacadeImpl implements AuthenticationFacade {
    @Override
    public Authentication getAuthentication(){
       return SecurityContextHolder.getContext().getAuthentication();
    }


}
