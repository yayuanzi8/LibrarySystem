package librarySystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return MD5Util.encode((String) rawPassword);
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return encodedPassword.equals(MD5Util.encode((String) rawPassword));
            }
        });
    }*/

    /*@Bean
    protected StudentDetailsService userDetailsService() {
        return new StudentDetailsService();
    }

    @Bean
    public TeacherDetailsService teacherDetailsService(){
        return new TeacherDetailsService();
    }

    @Bean(name = "studentAuthenticationProvider")
    public AuthenticationProvider studentAuthenticationProvider(StudentDetailsService studentDetailsService) {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                UserDetails userDetails = studentDetailsService.loadUserByUsername((String) authentication.getPrincipal());
                return new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(),userDetails.getAuthorities());
            }

            @Override
            public boolean supports(Class<?> aClass) {
                return aClass.isInstance(UserDetails.class);
            }
        };
    }

    @Bean(name = "teacherAuthenticationProvider")
    public AuthenticationProvider teacherAuthenticationProvider(TeacherDetailsService teacherDetailsService) {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                UserDetails userDetails = teacherDetailsService.loadUserByUsername((String) authentication.getPrincipal());
                return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
            }

            @Override
            public boolean supports(Class<?> aClass) {
                return aClass.isInstance(UserDetails.class);
            }
        };
    }*/

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
        http.authorizeRequests().antMatchers("/user/login").permitAll();
        //关闭跨站请求攻击防护
        http.csrf().disable();
        //启用rememberMe功能
        http.rememberMe().rememberMeParameter("remember-me").tokenValiditySeconds(604800).key("librarySystemUser");
    }
}
