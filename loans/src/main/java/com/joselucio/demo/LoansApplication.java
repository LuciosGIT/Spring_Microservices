package com.joselucio.demo;

import com.joselucio.demo.dto.LoansContactInfoDto;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableDiscoveryClient
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableConfigurationProperties(value = {LoansContactInfoDto.class})
@OpenAPIDefinition(
		info = @Info(
				title = "Loans microservice REST API Documentation",
				description = "Project Loans microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "José Lúcio",
						email = "jlucioosjunior@gmail.com",
						url = "https://www.github.com/LuciosGIT"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.apache.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Project Loans microservice REST API Documentation",
				url = "https://www.joselucio.com/swagger-ui.html"
		))
public class LoansApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoansApplication.class, args);
	}

}
