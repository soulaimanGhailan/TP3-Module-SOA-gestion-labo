package soul.dev.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Bean
    RouteLocator gatewayRoutes(RouteLocatorBuilder builder){
        return builder.routes()
                .route(r->r.path("/api/v1/researchers/**").uri("http://localhost:8082/"))
                .route(r->r.path("/api/v1/projects").uri("http://localhost:8081/"))
                .route(r->r.path("/api/v1/encadrants/**").uri("http://localhost:8083/"))
                .route(r->r.path("/public/auth/**").uri("http://localhost:8080/"))
                .build();
    }
}
