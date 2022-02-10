package me.kycho.demo.accounts;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
// https://docs.spring.io/spring-security/reference/servlet/integrations/mvc.html#mvc-authentication-principal
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : account") // SpEL
public @interface CurrentUser {
}
