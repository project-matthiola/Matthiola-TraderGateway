package com.cts.trader.fix;

import com.cts.trader.model.Order;
import org.springframework.stereotype.Service;
import quickfix.*;
import quickfix.field.OrderQty;
import quickfix.field.TradeDate;
import quickfix.fix50sp2.NewOrderSingle;

import java.util.UUID;

//@Service("FixInitiator")
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
        MessageFactory messageFactory = new DefaultMessageFactory();
        initiator = new SocketInitiator(application, fileStoreFactory, settings, screenLogFactory, messageFactory);
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

        NewOrderSingle orderSingle = new NewOrderSingle();
        orderSingle.set(new TradeDate());
        orderSingle.set(new OrderQty());


        /*
        Order order = new Order(UUID.randomUUID(), "AU_FEB18", "LIMIT", "BUY", 28.8, 300.0, "BROKERA", 111L);
        Message message = new Message();
        message.getHeader().setField(new StringField(8, "FIX.4.4"));
        message.getHeader().setField(new StringField(49, "client"));
        message.getHeader().setField(new StringField(56, order.getBrokerName()));
        message.getHeader().setField(new CharField(35, 'F'));
        message.setField(new StringField(16, order.getType()));

        SessionID sessionID = fixInitiator.getInitiator().getSessions().get(0);
        Session.sendToTarget(message, sessionID);
        Thread.sleep(5000);
        */
    }
}
