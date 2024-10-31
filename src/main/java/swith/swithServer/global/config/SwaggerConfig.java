package swith.swithServer.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(getInfo())
                .servers(getServers());
    }

    private List<Server> getServers() {
        return List.of(new Server()
                .url("/api")
                .description("swith server API"));
    }



    private Info getInfo() {
        return new Info()
                .title("swith API")
                .version("dev")
                .description("swith api 명세서");
    }

}
