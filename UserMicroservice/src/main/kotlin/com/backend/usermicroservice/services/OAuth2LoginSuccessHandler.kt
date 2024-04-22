import com.backend.usermicroservice.services.CustomOAuth2UserService
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler

class OAuth2LoginSuccessHandler(
    private val userService: CustomOAuth2UserService,
) : AuthenticationSuccessHandler {

    override fun onAuthenticationSuccess(
        request: jakarta.servlet.http.HttpServletRequest?,
        response: jakarta.servlet.http.HttpServletResponse?,
        authentication: Authentication?
    ) {
        val oAuth2User = authentication?.principal as OAuth2User
        val email = oAuth2User.attributes["email"] as String
        val name = oAuth2User.attributes["given_name"] as String



        val jwtToken = userService.processOAuthPostLogin(name, email)

        response?.addHeader("Authorization", "Bearer $jwtToken")

        response?.sendRedirect("/list")
    }
}