package com.cts.trader.fix;

import org.springframework.stereotype.Component;
import quickfix.*;

@Component
public class InitiatorApplication extends MessageCracker implements Application {
    //private static volatile SessionID sessionID;

    @Override
    public void onCreate(SessionID sessionId) {
        System.out.println("服务器启动时候调用此方法创建: " + sessionId + " onCreate");
    }

    @Override
    public void onLogon(SessionID sessionId) {
        System.out.println("客户端登陆成功时候调用此方法: " + sessionId + " onLogon");
        //sessionID = sessionId;
    }

    @Override
    public void onLogout(SessionID sessionId) {
        System.out.println("客户端断开连接时候调用此方法: " + sessionId + " onLogout");
        //sessionID = null;
    }

    @Override
    public void toAdmin(Message message, SessionID sessionId) {
        System.out.println("发送会话消息时候调用此方法: " + sessionId + " toAdmin");
    }

    @Override
    public void fromAdmin(Message message, SessionID sessionId) {
        System.out.println("接收会话类型消息时调用此方法: " + sessionId + " fromAdmin");
    }

    @Override
    public void toApp(Message message, SessionID sessionId) {
        System.out.println("发送业务消息时候调用此方法: " + sessionId + " toApp");
    }

    @Override
    public void fromApp(Message message, SessionID sessionId) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        System.out.println("接收业务消息时调用此方法: " + sessionId + " fromApp");
        crack(message, sessionId);
    }

    @Override
    protected void onMessage(Message message, SessionID sessionID) throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
        System.out.println("业务逻辑实现统一写在此方法中: " + sessionID + " onMessage");
        System.out.println("收到消息: " + message);
    }
}
