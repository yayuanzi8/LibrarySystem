package librarySystem.config;

import librarySystem.security.ReaderDetailsService;
import librarySystem.security.ValidateCodeUsernamePasswordAuthenticationFilter;
import librarySystem.util.MD5Util;
import librarySystem.util.ReaderUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.*;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.security.web.authentication.session.*;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.session.ConcurrentSessionFilter;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SimpleRedirectInvalidSessionStrategy;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;

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

    public boolean isSelf(Integer credNum, HttpSession session) {
        return ReaderUtil.getUserFromSecurityContext(session).getCredNum().equals(credNum);
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
        //关闭跨站请求攻击防护
        http.csrf().disable();
        http.userDetailsService(readerDetailsService());
        //启用rememberMe功能
        http.rememberMe().rememberMeParameter("remember-me").
                tokenValiditySeconds(1000000).key("libraryReader").
                userDetailsService(readerDetailsService()).
                rememberMeServices(rememberMeServices());
        http.logout().logoutUrl("/user/logout").logoutSuccessUrl("/").invalidateHttpSession(true).deleteCookies("remember-me");
        http.addFilterAt(concurrentSessionFilter(), ConcurrentSessionFilter.class);
        http.addFilterAt(validateCodeUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.sessionManagement().sessionAuthenticationStrategy(compositeSessionAuthenticationStrategy()).invalidSessionStrategy(invalidSessionStrategy());
        http.headers().frameOptions().sameOrigin();//配置允许同域名使用X-Frame-Options访问
        http.exceptionHandling().accessDeniedPage("/403.jsp");
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
        filter.setSessionAuthenticationStrategy(compositeSessionAuthenticationStrategy());
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
        return new TokenBasedRememberMeServices("libraryReader", readerDetailsService());
    }

    //会话管理部分
    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public InvalidSessionStrategy invalidSessionStrategy() {
        return new SimpleRedirectInvalidSessionStrategy("/");
    }

    @Bean
    public ConcurrentSessionControlAuthenticationStrategy concurrentSessionControlAuthenticationStrategy() {
        ConcurrentSessionControlAuthenticationStrategy strategy =
                new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
        strategy.setMaximumSessions(1);
        strategy.setExceptionIfMaximumExceeded(false);
        return strategy;
    }

    @Bean
    public SessionFixationProtectionStrategy sessionFixationProtectionStrategy() {
        return new SessionFixationProtectionStrategy();
    }

    @Bean
    public RegisterSessionAuthenticationStrategy registerSessionAuthenticationStrategy() {
        return new RegisterSessionAuthenticationStrategy(sessionRegistry());
    }

    @Bean
    public CompositeSessionAuthenticationStrategy compositeSessionAuthenticationStrategy() {
        ArrayList<SessionAuthenticationStrategy> strategyArrayList = new ArrayList<>();
        strategyArrayList.add(concurrentSessionControlAuthenticationStrategy());
        strategyArrayList.add(sessionFixationProtectionStrategy());
        strategyArrayList.add(registerSessionAuthenticationStrategy());
        return new CompositeSessionAuthenticationStrategy(strategyArrayList);
    }

    @Bean
    public ConcurrentSessionFilter concurrentSessionFilter() {
        ConcurrentSessionFilter filter = new ConcurrentSessionFilter(sessionRegistry(), "/expired.jsp");
        filter.setRedirectStrategy(new DefaultRedirectStrategy());
        //配置删除remember-me cookie的处理器，这样就不会再被自动退出登陆之后再次自动登录
        filter.setLogoutHandlers(new LogoutHandler[]{new SecurityContextLogoutHandler(), new CookieClearingLogoutHandler("remember-me")});
        return filter;
    }


}
