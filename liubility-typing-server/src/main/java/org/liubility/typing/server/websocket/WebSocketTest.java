package org.liubility.typing.server.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/webSocket/test")
@Slf4j
@Component
public class WebSocketTest {
    //建立连接成功调用
    @OnOpen
    public void onOpen(Session session) {
        try {
            session.getBasicRemote().sendText("你好欢迎连接");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //关闭连接时调用
    @OnClose
    public void onClose() {

    }

    //收到客户端信息
    @OnMessage
    public void onMessage(Session session, String message) {
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //错误时调用
    @OnError
    public void onError(Session session, Throwable throwable) {
    }
}
