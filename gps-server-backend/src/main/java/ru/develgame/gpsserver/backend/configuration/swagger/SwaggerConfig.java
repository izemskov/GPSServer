package ru.develgame.gpsserver.backend.configuration.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.develgame.gpsserver.backend.configuration.security.SecurityConfiguration;

import static ru.develgame.gpsserver.backend.security.jwt.util.JwtHttpConverterService.AUTH_HEADER;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public OpenAPI api() {
        return new OpenAPI()
                .info(new Info()
                        .title("GPSServer API")
                )
                .schemaRequirement(SecurityConfiguration.SECURITY_SCHEME_NAME,
                        new SecurityScheme()
                                .type(Type.APIKEY)
                                .scheme("bearer")
                                .in(In.HEADER)
                                .name(AUTH_HEADER)
                                .description("Authorization token")
                );
    }
}
