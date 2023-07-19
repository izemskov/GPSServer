package ru.develgame.gpsviewer.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    private UserDetailsService gpsServerUserDetailsService;

    @Autowired
    private DaoAuthenticationProvider gpsServerDaoAuthenticationProvider;

    private static final String ZUL_FILES = "/zkau/web/**/*.zul";
    private static final String[] ZK_RESOURCES = {
            "/zkau/web/**/js/**",
            "/zkau/web/**/zul/css/**",
            "/zkau/web/**/font/**",
            "/zkau/web/**/img/**"
    };
    // allow desktop cleanup after logout or when reloading login page
    private static final String REMOVE_DESKTOP_REGEX = "/zkau\\?dtid=.*&cmd_0=rmDesktop&.*";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // ZK already sends a AJAX request with a built-in CSRF token,
        // please refer to https://www.zkoss.org/wiki/ZK%20Developer's%20Reference/Security%20Tips/Cross-site%20Request%20Forgery
        http.csrf().disable();

        http.authorizeRequests()
                // block direct access to zul files
                .antMatchers(ZUL_FILES).denyAll()
                // allow zk resources
                .antMatchers(HttpMethod.GET, ZK_RESOURCES).permitAll()
                // allow desktop cleanup
                .regexMatchers(HttpMethod.GET, REMOVE_DESKTOP_REGEX).permitAll()
                // allow desktop cleanup from ZATS
                .requestMatchers(req -> "rmDesktop".equals(req.getParameter("cmd_0"))).permitAll()
                // accept any requests to login and logout pages
                .mvcMatchers("/login","/logout").permitAll()
                // any requests to other pages have to have authorization
                .anyRequest().authenticated()
                // login page
                .and().formLogin().loginPage("/login").defaultSuccessUrl("/")
                // logout page
                .and().logout().logoutUrl("/logout").logoutSuccessUrl("/");

        http.headers().frameOptions().sameOrigin();

        return http.build();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(gpsServerUserDetailsService);
        auth.authenticationProvider(gpsServerDaoAuthenticationProvider);
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }
}
