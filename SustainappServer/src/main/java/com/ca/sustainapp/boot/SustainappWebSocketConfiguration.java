package com.ca.sustainapp.boot;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Classe de configuration pour l'utilisation des websockets
 * @author Anas Neumann <anas.neumann.isamm@gmail.com>
 * @since 03/05/2017
 * @version 1.0
 */
@Configuration
@EnableWebSocketMessageBroker
public class SustainappWebSocketConfiguration extends AbstractWebSocketMessageBrokerConfigurer {

	/**
	 * Methode de configuration du chemin pour l'utilisation des websockets
	 * @param config
	 */
	@Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/websocket");
        config.setApplicationDestinationPrefixes("/app");
    }

	/**
	 * Methode de configuration pour l'enregistrement via le protocol STOMP
	 * @param registry
	 */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/sustainapp-websocket").setAllowedOrigins("*").withSockJS();
    }
	
}