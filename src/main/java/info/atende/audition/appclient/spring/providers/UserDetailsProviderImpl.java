package info.atende.audition.appclient.spring.providers;

import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Get details for the current user
 * Uses SecurityContextHolder to the the current user login
 * @author Giovanni Silva.
 */
class UserDetailsProviderImpl implements UserDetailsProvider {

    @Override
    public String getUserName(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
