package info.atende.audition.appclient.spring;

import org.springframework.security.core.Authentication;

/**
 * @author Giovanni Silva.
 */
public interface UserDetailsProvider {
    String getUserName(Authentication auth);
}
