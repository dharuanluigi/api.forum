package br.com.alura.api.forum.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(getApiInfo());
    }

    private Info getApiInfo() {
        return new Info()
                .title("Forum api")
                .description("Endpoint backend of forum implementation")
                .version("0.0.1-BETA")
                .license(getLicense())
                .contact(getContact());
    }

    private License getLicense() {
        return new License().name("Apache 2.0").url("https://www.apache.org/licenses/LICENSE-2.0");
    }

    private Contact getContact() {
        return new Contact()
                .name("Dharuan Emina")
                .email("dharuanluigi@gmail.com")
                .url("https://github.com/dharuanluigi");
    }
}
