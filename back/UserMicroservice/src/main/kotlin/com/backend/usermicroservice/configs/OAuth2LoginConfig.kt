import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.core.AuthorizationGrantType

@Configuration
class OAuth2LoginConfig {

    @Bean
    fun clientRegistrationRepository(): ClientRegistrationRepository {
        val clientRegistration = ClientRegistration.withRegistrationId("your-registration-id")
            .clientId("your-client-id")
            .clientSecret("your-client-secret")
            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
            .authorizationUri("your-authorization-uri")
            .tokenUri("your-token-uri")
            .userInfoUri("your-user-info-uri")
            .userNameAttributeName("your-user-name-attribute-name")
            .clientName("your-client-name")
            .build()

        return InMemoryClientRegistrationRepository(clientRegistration)
    }
}