import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SeguridadNavegacion {

    @Autowired
    private AutenticacionUsuario miProvider ;  // tu AuthenticationProvider

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
          .csrf(csrf -> csrf.disable())
          .authorizeHttpRequests(auth -> auth
            .requestMatchers("/login", "/registro", "/verificarCodigo").permitAll()
            .anyRequest().authenticated()
          )
          .formLogin(form -> form
            .loginPage("/login")
            .defaultSuccessUrl("/", true)
            .usernameParameter("email")
            .permitAll()
          )
          .logout(logout -> logout
            .logoutUrl("/logout")
            .logoutSuccessUrl("/login?logout")
            .permitAll()
          );

        http.authenticationProvider(miProvider);
        return http.build();
    }
}