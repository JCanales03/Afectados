package cl.conaf.afectados;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MS-06 Afectados y Alertas Municipales")
                        .version("1.0.0")
                        .description("Microservicio para registrar alertas municipales, " +
                                "infraestructura comprometida y personas evacuadas " +
                                "durante emergencias por incendios forestales - CONAF")
                        .contact(new Contact()
                                .name("Elizabeth Cabrera")
                                .email("el.cabrera@duocuc.cl")));
    }
}