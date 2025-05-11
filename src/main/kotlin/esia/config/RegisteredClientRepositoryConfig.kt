package esia.config

import java.util.UUID
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings

@Configuration
@EnableConfigurationProperties(RegisteredClientProperties::class)
class RegisteredClientRepositoryConfig {

  @Bean
  fun registeredClientRepository(
      registeredClientProperties: RegisteredClientProperties,
      passwordEncoder: PasswordEncoder
  ): RegisteredClientRepository {
    val registeredClient =
        RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId(registeredClientProperties.clientId)
            .clientSecret(passwordEncoder.encode(registeredClientProperties.clientSecret))
            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
            .redirectUri(registeredClientProperties.redirectUri)
            .scopes { it.addAll(registeredClientProperties.scopes) }
            .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
            .build()

    return InMemoryRegisteredClientRepository(registeredClient)
  }
}

@ConfigurationProperties("spring.security.oauth2.client.registration.esia-client")
data class RegisteredClientProperties(
    var clientId: String = "",
    var clientSecret: String = "",
    var redirectUri: String = "",
    var scopes: List<String> = emptyList()
)
