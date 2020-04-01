package com.example.encdec.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfig.class);

//    private static final String SQL_LOGIN =
//            "select u.username, u.password, u.status as active from s_user u " +
//                    "where u.status='t' and u.username = ?";
//
//    private static final String SQL_PRIVILEGE =
//            "select u.username, p.kode as authority from s_user u " +
//                    "inner join s_role r on (u.id_role=r.id) " +
//                    "inner join s_roles_privileges rp on (rp.id_role=r.id) " +
//                    "inner join s_privilege p on (rp.id_privilege=p.id) " +
//                    "where u.username = ?";

    private static final String SQL_LOGIN
            = "select username, password, active as enabled "
            + "from s_users where username= ?";
    private static final String SQL_PERMISSION
            = "select username, 'ROLE_TES' as authority "
            + "from s_users where username = ?";

    @Autowired
    private DataSource ds;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncrypter();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        LOGGER.info("Sql login : {} ", SQL_LOGIN);
//        LOGGER.info("Sql privilege : {} ", SQL_PRIVILEGE);
        auth
                .jdbcAuthentication()
                .dataSource(ds)
                .usersByUsernameQuery(SQL_LOGIN)
                .authoritiesByUsernameQuery(SQL_PERMISSION)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.ALWAYS);
        //http.csrf().disable();
        http // authorize request
                .authorizeRequests()
                .anyRequest().authenticated()
                .and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/keluar"))
                .logoutSuccessUrl("/login").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true).permitAll()
                .and().formLogin().defaultSuccessUrl("/home", true)
                //.successHandler(loginHandler)
                .loginPage("/login")
                .permitAll();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/assets/**")
                .antMatchers("/api-v1/**")
        //.antMatchers("/zul/footer.zul")
        //        .antMatchers("/theme")
        ;
    }
}
