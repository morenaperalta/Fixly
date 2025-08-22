package com.femcoders.fixly.shared.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${server.port:8080}")
    private String serverPort;

    @Value("${spring.application.name}")
    private String applicationName;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(apiInfo())
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Local Development Server")
                ))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
    }

    private Info apiInfo() {
        return new Info()
                .title("Fixly API")
                .description("""
                        
                        A comprehensive work order management system that allows organizations to:
                        - **Create and manage work orders** with different priorities and statuses
                        - **User management** with role-based access control (Admin, Client, Technician, Supervisor)
                        - **Real-time status tracking** with detailed history
                        - **Comment system** for internal notes and client communication
                        - **File attachment support** via Cloudinary integration
                        
                        ### Authentication
                        This API uses JWT Bearer tokens for authentication. To access protected endpoints:
                        1. Register a new user or login with existing credentials
                        2. Use the returned JWT token in the Authorization header: `Bearer <your-token>`
                        
                        ### User Roles
                        - **ADMIN**: Full system access
                        - **CLIENT**: Can create work orders and view their own orders
                        - **TECHNICIAN**: Can be assigned to work orders and update progress
                        - **SUPERVISOR**: Can approve/reject completed work orders
                        """)
                .version("1.0.0")
                .contact(new Contact()
                        .name("Morena Peralta")
                        .email("mperalta.dev@gmail.com")
                        .url("https://github.com/morenaperalta"));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer")
                .description("Enter JWT Bearer token in the format: Bearer <token>");
    }
}
