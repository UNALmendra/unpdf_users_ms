package unpdf.users_ms.Config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.support.BaseLdapPathContextSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.ldap.DefaultSpringSecurityContextSource;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.authentication.LdapAuthenticator;
import org.springframework.security.ldap.server.UnboundIdContainer;
import org.springframework.security.ldap.userdetails.PersonContextMapper;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    public SecurityConfiguration() {
        super(true); // Disable defaults
    }

    @Override
    protected void configure(HttpSecurity http) {
        // Do nothing, this is just overriding the default behavior in WebSecurityConfigurerAdapter

    }

    @Bean
    UnboundIdContainer ldapContainer() {
        UnboundIdContainer container = new UnboundIdContainer("dc=unpdf-users,dc=com", "classpath:ldap-server.ldif");
        container.setPort(0);
        return container;
    }

    @Bean
    ContextSource contextSource(UnboundIdContainer container) {
        int port = container.getPort();
        return new DefaultSpringSecurityContextSource("ldap://localhost:" + port + "/dc=unpdf-users,dc=com");
    }

    @Bean
    BindAuthenticator authenticator(BaseLdapPathContextSource contextSource) {
        BindAuthenticator authenticator = new BindAuthenticator(contextSource);
        authenticator.setUserDnPatterns(new String[] { "uid={0},ou=people" });
        return authenticator;
    }

    @Bean
    LdapAuthenticationProvider authenticationProvider(LdapAuthenticator authenticator) {
        LdapAuthenticationProvider provider = new LdapAuthenticationProvider(authenticator);
        provider.setUserDetailsContextMapper(new PersonContextMapper());
        return provider;
    }
}
