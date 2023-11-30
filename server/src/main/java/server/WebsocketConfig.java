package server;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebsocketConfig implements WebSocketMessageBrokerConfigurer {

    /**
     * Adds websockets endpoint to a StompEndpointRegistry
     * @param reg is the StompEndpointRegistry
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry reg){
        reg.addEndpoint("/websocket");
    }

    /**
     * Configures the paths that websocket messages get sent and recieved along
     * Messages are recieved on /topic, and can be sent from /topic to bypass the server,
     * whereas messages sent through /app are processed by the server and sent back through /topic
     * @param config is the MessageBrokerRegistry that configures the paths.
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config){
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

}
