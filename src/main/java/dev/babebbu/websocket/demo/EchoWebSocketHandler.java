package dev.babebbu.websocket.demo;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
@RequiredArgsConstructor
public class EchoWebSocketHandler extends TextWebSocketHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("Session Established: {}", session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            // Example of Parsing JSON to Object
            Object incomingMessage = objectMapper.readValue(message.getPayload(), Object.class);
            //SampleJsonMessage incomingMessage = objectMapper.readValue(message.getPayload(), SampleJsonMessage.class);
            logger.info("Received JSON Message: {}", incomingMessage);
        } catch (JsonParseException e) {
            logger.info("Received Text Message: {}", message.getPayload());
        }
        session.sendMessage(message);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("Session Closed: {}", session);
    }


    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        logger.warn("Transport Error: {}, {}", session, exception);
    }
}
