package configuraciones;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
* Clase de configuración para habilitar y personalizar CORS en la aplicación.
* CORS (Cross-Origin Resource Sharing) permite que una aplicación web
* acceda a recursos en un dominio diferente del que la aloja.
* 
* msm - 060325
*/
@Configuration
public class CorsConfiguracion {
   
	 /**
     * Define un bean de configuración de CORS.
     * Spring invocará este método para aplicar las reglas de CORS en la aplicación.
     *
     * msm - 060325
     * @return Un objeto WebMvcConfigurer con las reglas de CORS definidas.
     */
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            
            /**
             * Método para configurar las reglas de CORS en la aplicación.
             * 
             * @param registry Registro donde se definen las reglas de CORS.
             */
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                
                // Definimos las reglas de CORS para todas las rutas de la aplicación
                registry.addMapping("/**") // Permite el acceso a todas las rutas
                        .allowedOrigins("http://localhost:4200") // Permite peticiones desde Angular (localhost:4200)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Métodos HTTP permitidos
                        .allowedHeaders("*") // Permite todos los headers en la petición
                        .allowCredentials(true); // Permite el uso de credenciales (cookies, autenticación)
            }
        };
    }
}