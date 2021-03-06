package com.eastflag.controller;

import com.eastflag.domain.SnsdicVO;
import com.eastflag.question.QuestionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

/**
 * Created by eastflag on 2017-08-05.
 */
@ServerEndpoint(value="/snsdic")
@RestController
public class WebSocketSnsdic {
    private static Logger logger = LoggerFactory.getLogger(WebSocketSnsdic.class);

    @Autowired
    private QuestionService questionService;

    Set<Session> sessionUsers = Collections.synchronizedSet(new HashSet<Session>());
    ObjectMapper mapper = new ObjectMapper();

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @OnOpen
    public void handleOpen(Session userSession) {
        //연결되면 client id를 client에게 보내서 그 다음부터 해당 id로 접속하게 한다.
        logger.debug("WebSocket : Client Session is Open. ID is "+ userSession.getId());
        sessionUsers.add(userSession);
        //sendMessage(userSession, "S",  "connected", "");

        // 문제방에 join
        questionService.joinQuestionRoom(userSession);
    }

    @OnMessage
    public void handleMessage(Session userSession, String message) throws IOException {
        logger.debug("received message: " + userSession.getId() + ", " + message);
        SnsdicVO receive = mapper.readValue(message, SnsdicVO.class);

        if(sessionUsers.size() > 0) {
            for(Session session : sessionUsers) {
                //broadcast
                if (!session.getId().equals(userSession.getId())) {
                    sendMessage(session, "S", "broadcast", receive.getMessage());
                }
            }
        } else {
            logger.debug("WebSocket : Here is no registered destination.");
        }
    }

    @OnClose
    public void handleClose(Session session) {
        logger.debug("WebSocket : Session remove complete. ID is "+session.getId());
        sessionUsers.remove(session);
    }

    @OnError
    public void onError(Throwable error) {
        logger.debug("error: " + error.toString());
    }

    private void sendMessage(Session userSession, String type, String command, String message) {
        SnsdicVO snsdic = new SnsdicVO();
        snsdic.setClientId(userSession.getId());
        snsdic.setCommand(command);
        snsdic.setMessage(message);

        String jsonString = null;
        try {
            jsonString = mapper.writeValueAsString(snsdic);
            userSession.getBasicRemote().sendText(jsonString);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
