package esia.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint

@Configuration
@EnableWebSecurity
class AuthorizationServerConfig {

  @Bean
  @Order(1)
  fun authorizationServerSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
    OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http)

    http
        .getConfigurer(OAuth2AuthorizationServerConfigurer::class.java)
        .oidc(Customizer.withDefaults())

    http
        .exceptionHandling { exceptions ->
          exceptions.authenticationEntryPoint(LoginUrlAuthenticationEntryPoint("/login"))
        }
        .oauth2ResourceServer { resourceServer -> resourceServer.jwt(Customizer.withDefaults()) }

    return http.build()
  }

  @Bean
  @Order(2)
  fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http
        .authorizeHttpRequests { authorize ->
          authorize
              .requestMatchers("/register", "/css/**", "/js/**", "/error")
              .permitAll()
              .anyRequest()
              .authenticated()
        }
        .formLogin { formLogin -> formLogin.loginPage("/login").defaultSuccessUrl("/").permitAll() }

    return http.build()
  }

  @Bean
  fun authorizationServerSettings(): AuthorizationServerSettings {
    return AuthorizationServerSettings.builder().issuer("http://localhost:8080").build()
  }

  @Bean
  fun passwordEncoder(): PasswordEncoder {
    return BCryptPasswordEncoder()
  }
}
