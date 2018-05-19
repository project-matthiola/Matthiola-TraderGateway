package com.cts.trader.fix;

import com.cts.trader.model.Order;
import org.springframework.stereotype.Service;
import quickfix.*;

import java.util.UUID;

public class FixInitiator {
    private static SocketInitiator initiator = null;

    public SocketInitiator getInitiator() {
        return initiator;
    }

    public FixInitiator() throws Exception {
        if (initiator != null) return;

        SessionSettings settings = new SessionSettings("fixInitiator.cfg");
        Application application = new InitiatorApplication();
        FileStoreFactory fileStoreFactory = new FileStoreFactory(settings);
        ScreenLogFactory screenLogFactory = new ScreenLogFactory(settings);
        DefaultMessageFactory defaultMessageFactory = new DefaultMessageFactory();
        initiator = new SocketInitiator(application, fileStoreFactory, settings, screenLogFactory, defaultMessageFactory);
        initiator.start();

    }

    public void startInitiator() throws Exception {
        initiator.start();
    }

    public void stopInitiator() throws Exception {
        initiator.stop();
    }

    public static void main(String[] args) throws Exception {
        FixInitiator fixInitiator = new FixInitiator();

        Order order = new Order(UUID.randomUUID(), "AU_FEB18", "LIMIT", "BUY", (float)28.8, 300, "BROKERA");
        Message message = new Message();
        message.getHeader().setField(new StringField(8, "FIX.4.4"));
        message.getHeader().setField(new StringField(49, "client"));
        message.getHeader().setField(new StringField(56, order.getBrokerName()));
        message.getHeader().setField(new CharField(35, 'F'));
        message.setField(new StringField(16, order.getType()));

        SessionID sessionID = fixInitiator.getInitiator().getSessions().get(0);
        Session.sendToTarget(message, sessionID);
        Thread.sleep(5000);
    }
}
