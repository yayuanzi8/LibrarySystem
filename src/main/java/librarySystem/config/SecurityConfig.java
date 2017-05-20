package librarySystem.config;

import librarySystem.security.ReaderDetailsService;
import librarySystem.security.ValidateCodeUsernamePasswordAuthenticationFilter;
import librarySystem.util.MD5Util;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(readerDetailsService()).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.encode((String) rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(MD5Util.encode((String) rawPassword));
            }
        });
    }

    @Bean
    public ReaderDetailsService readerDetailsService() {
        return new ReaderDetailsService();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //添加字符过滤器
        CharacterEncodingFilter characterEncodingFilter =
                new CharacterEncodingFilter();
        characterEncodingFilter.setEncoding("UTF-8");
        characterEncodingFilter.setForceRequestEncoding(true);
        characterEncodingFilter.setForceResponseEncoding(true);
        http.addFilterBefore(characterEncodingFilter, CsrfFilter.class);
        //允许任何人访问登录界面
        http.authorizeRequests().antMatchers("/user/login").access("!isAuthenticated()");
        //关闭跨站请求攻击防护
        http.csrf().disable();
        //启用rememberMe功能
//        http.rememberMe().rememberMeServices(rememberMeServices());
        http.rememberMe().rememberMeParameter("remember-me").
                tokenValiditySeconds(1000000).key("libraryReader").
                userDetailsService(readerDetailsService()).
                rememberMeServices(rememberMeServices());
        http.logout().logoutUrl("/user/logout").logoutSuccessUrl("/");
        http.addFilterAt(validateCodeUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.debug(true);
    }

    @Bean
    public ValidateCodeUsernamePasswordAuthenticationFilter validateCodeUsernamePasswordAuthenticationFilter() throws Exception {
        ValidateCodeUsernamePasswordAuthenticationFilter filter =
                new ValidateCodeUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager());
        filter.setFilterProcessesUrl("/user/logon");
        filter.setUsernameParameter("cred_num");
        filter.setPasswordParameter("password");
        filter.setPostOnly(true);
        filter.setAuthenticationSuccessHandler(successHandler());
        filter.setAuthenticationFailureHandler(failureHandler());
        filter.setRememberMeServices(rememberMeServices());
        return filter;
    }

    @Bean
    public LoginUrlAuthenticationEntryPoint entryPoint() {
        return new LoginUrlAuthenticationEntryPoint("/user/login");
    }

    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler successHandler() {
        SavedRequestAwareAuthenticationSuccessHandler handler =
                new SavedRequestAwareAuthenticationSuccessHandler();
        handler.setDefaultTargetUrl("/user/loginSuccess");
        return handler;
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler failureHandler() {
        return new SimpleUrlAuthenticationFailureHandler("/user/loginFail");
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        TokenBasedRememberMeServices services = new TokenBasedRememberMeServices("libraryReader", readerDetailsService());
        System.out.println(services.getKey());
        System.out.println(services.getParameter());
        return services;
    }

}
