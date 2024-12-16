package com.joselucio.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverterAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import reactor.core.publisher.Mono;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.authorizeExchange(exchanges -> exchanges.pathMatchers(HttpMethod.GET)
                .permitAll().pathMatchers("/joselucio/accounts/**").hasRole("ACCOUNTS")
                .pathMatchers("/joselucio/cards/**").hasRole("CARDS")
                .pathMatchers("/joselucio/loans/**").hasRole("LOANS"))
                .oauth2ResourceServer(oAuth2ResourceServerSpec -> oAuth2ResourceServerSpec
                        .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(grantedAuthoritiesExtractor())));

        serverHttpSecurity.csrf(csrfSpec -> csrfSpec.disable());

        return serverHttpSecurity.build();
    }


    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    WebFilter writeableHeaders() {
        return (exchange, chain) -> {
            HttpHeaders writeableHeaders = HttpHeaders.writableHttpHeaders(
                    exchange.getRequest().getHeaders());
            ServerHttpRequestDecorator writeableRequest = new ServerHttpRequestDecorator(
                    exchange.getRequest()) {
                @Override
                public HttpHeaders getHeaders() {
                    return writeableHeaders;
                }
            };
            ServerWebExchange writeableExchange = exchange.mutate()
                    .request(writeableRequest)
                    .build();
            return chain.filter(writeableExchange);
        };
    }

    private Converter<Jwt, Mono<AbstractAuthenticationToken>> grantedAuthoritiesExtractor() {
        JwtAuthenticationConverter jwtAuthenticationConverter =
                new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter
                (new KeycloakRoleConverter());
        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
    }


}
