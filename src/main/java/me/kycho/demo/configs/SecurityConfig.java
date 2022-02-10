package me.kycho.demo.configs;

import me.kycho.demo.accounts.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

//@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public TokenStore tokenStore() {
        return new InMemoryTokenStore();
    }

    // AuthenticationManager를 다른곳에서도 참조할수 있도록 Bean으로 노출 시켜준다.
    // WebSecurityConfigurerAdapter.class 에 설명 젹혀있음.
    // 왜??? 노출 시키지 ???
    // 백기선 답변 - AutherizationServer랑 ResourceServer에서
    //              해당 AuthenticationManager를 참조할 수 있도록 bean으로 노출시키는 겁니다.
    //              모든 경우에 그럴 필요는 없구요. 필요한 경우에만요.
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    // AuthenticationManager를 어떻게 만들지 재정의한다.
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService)
                .passwordEncoder(passwordEncoder);
    }

    // Security Filter를 적용할지 말지 설정해준다. (지정한 요청이 security 쪽으로 아예 안간다.)
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().mvcMatchers("/docs/index.html");
        // 스프링부트가 제공하는 static resources 기본위치에 spring security filter가 적용되지 않도록 한다.
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
