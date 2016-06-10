package info.atende.audition.appclient.spring;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * Get details for the current user
 * @author Giovanni Silva.
 */
@Component
@ConditionalOnMissingBean
public class UserDetailsProviderImpl implements UserDetailsProvider {

    @Override
    public String getUserName(Authentication user){
        return user.getName();
    }
}
