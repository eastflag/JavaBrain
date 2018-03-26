package com.eastflag.controller;

import com.eastflag.domain.ChatVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@ServerEndpoint(value="/api/chat")
@RestController
public class WebSocketChat {
    private static Logger logger = LoggerFactory.getLogger(WebSocketChat.class);

    // 클라이언트마다 이 변수는 새로 생성되므로 공유되는 변수로 세팅해야 한다. 큐로 하던가,,,
    public static Map<String, Session> mSessionUsers = Collections.synchronizedMap(new HashMap<String, Session>());
    ObjectMapper mMapper = new ObjectMapper();

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

    @OnOpen
    public void handleOpen(Session userSession) {
        //연결되면 client id를 client에게 보내서 그 다음부터 해당 id로 접속하게 한다.
        logger.debug("WebSocket : Client Session is Open. ID is "+ userSession.getId());
        mSessionUsers.put(userSession.getId(), userSession);
        logger.debug("mSessiion:" + mSessionUsers);
        ChatVO chat = new ChatVO();
        chat.setCommand("WhoAreYou");
        sendMessage(userSession,  chat);
    }

    @OnMessage
    public void handleMessage(Session userSession, String message) throws IOException {
        ChatVO chat = mMapper.readValue(message, ChatVO.class);
        logger.debug("received message: " + userSession.getId() + ", " + chat);

        if ("IAmTom".equals(chat.getCommand())) {
            saveUser(userSession, chat);
        } else if ("Send".equals(chat.getCommand())) {
            logger.debug("Send, size: " + mSessionUsers.size());
            chat.setCommand("Receive");
            broadcast(userSession, chat);
        }
    }

    @OnClose
    public void handleClose(Session session) {
        logger.debug("WebSocket : Session remove complete. ID is "+session.getId());
        mSessionUsers.remove(session.getId());
    }

    @OnError
    public void onError(Throwable error) {
        logger.debug("error: " + error.toString());
    }

    /**
     * userSession으로 chat 메시지를 보낸다.
     * @param userSession
     * @param chat
     */
    private void sendMessage(Session userSession, ChatVO chat) {
        try {
            logger.debug("WebSocket : send message " + userSession.getId() + "," + chat.getMessage());
            userSession.getBasicRemote().sendText(mMapper.writeValueAsString(chat));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * IAmTom을 받고 사용자 정보를 저장한다.
     * @param userSession
     * @param chat
     */
    private void saveUser(Session userSession, ChatVO chat) {
        logger.debug("WebSocket : save user " + chat);
    }

    private void broadcast(Session userSession, ChatVO chat) {
        logger.debug("boradcaser id: " + userSession.getId());
        logger.debug(" id size : " + mSessionUsers.size());
        for(String id : mSessionUsers.keySet()) {
            logger.debug("id:" + id);
            if (!userSession.getId().equals(id)) {
                sendMessage(mSessionUsers.get(id), chat);
            }
        }
    }
}
