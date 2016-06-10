package info.atende.audition.appclient.spring;

import org.springframework.security.core.Authentication;

/**
 * Get details for the current user
 * @author Giovanni Silva.
 */
public class UserDetailsProviderImpl implements UserDetailsProvider {

    @Override
    public String getUserName(Authentication user){
        return user.getName();
    }
}
