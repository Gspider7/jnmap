package com.jia.jnmap.websocket;

import com.jia.jnmap.config.WebSocketEndpointConfigure;
import com.jia.jnmap.domain.ScanStatusVO;
import com.jia.jnmap.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * websocket服务实例
 *
 * @date 2018-11-23 09:58
 */
@Slf4j
@Component
@Scope("prototype")         // 为每个webSocket连接创建一个实例
@ServerEndpoint(value = "/websocket", configurator = WebSocketEndpointConfigure.class)
public class JnmapWebsocket {

    // 记录当前在线连接数
    private static AtomicInteger onlineCount = new AtomicInteger(0);

    // 静态线程安全Set，存放当前实例
    private static CopyOnWriteArraySet<JnmapWebsocket> websocketSet = new CopyOnWriteArraySet<>();

    // 连接会话，需要通过它来给客户端发送数据
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        websocketSet.add(this);

        int nowCount = onlineCount.addAndGet(1);
        log.info("有新连接加入！当前在线客户端数量为{}", nowCount);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        websocketSet.remove(this);

        int nowCount = onlineCount.addAndGet(-1);
        log.info("检测到连接关闭！当前在线客户端数量为{}", nowCount);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 连接会话，可选
     */
    @OnMessage
    public void onMessage(String message, Session session) {

        log.info("收到来自客户端的webSocket消息:{}", message);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("webSocket发生错误", error);
    }

//    public void sendMessage(WebSocketMessageVO messageVO) {
//        try {
//            if (websocketSet.contains(this)) {
//                String messageBody = uqJson.obj2string(messageVO);
//                this.session.getBasicRemote().sendText(messageBody);
//            }
//        } catch (IOException e) {
//            log.error("webSocket发送消息失败", e);
//        }
//    }

    /**
     * 给所有websockt连接客户端发送消息
     *
     * @param scanStatusVO  扫描执行状态
     */
    public void sendMessageToAll(ScanStatusVO scanStatusVO) {
        try {
            String messageBody = JacksonUtil.writeValueAsString(scanStatusVO);
            for (JnmapWebsocket websocket : websocketSet) {
                websocket.getSession().getBasicRemote().sendText(messageBody);
            }
        } catch (IOException e) {
            log.error("发送websocket消息失败", e);
        }
    }


    // ------------------------------------------------------------------------------------

    public Session getSession() {
        return session;
    }
}
