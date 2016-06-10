package info.atende.audition.appclient.spring.providers;

/**
 * Provide current user details for the event system
 * You can teach the audit system how to get the user by overriding this information
 * The default implementation use SpringContextHolder from Spring Security
 * @author Giovanni Silva.
 */
public interface UserDetailsProvider {
    String getUserName();
}
