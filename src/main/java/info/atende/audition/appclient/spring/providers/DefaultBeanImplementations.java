package info.atende.audition.appclient.spring.providers;

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
}
