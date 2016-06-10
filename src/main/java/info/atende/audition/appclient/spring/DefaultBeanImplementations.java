package info.atende.audition.appclient.spring;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provider for default implementations of configuration details
 * @author Giovanni Silva.
 */
@Configuration
public class DefaultBeanImplementations {
    @Bean
    @ConditionalOnMissingBean(UserDetailsProvider.class)
    public  UserDetailsProvider userDetailsProvider(){
        return new UserDetailsProviderImpl();
    }
    @Bean
    @ConditionalOnMissingBean(AuthenticationFacade.class)
    public AuthenticationFacade authenticationFacade(){
        return new AuthenticationFacadeImpl();
    }
}
