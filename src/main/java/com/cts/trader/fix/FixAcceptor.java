package com.cts.trader.fix;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import quickfix.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.concurrent.CountDownLatch;

public class FixAcceptor {
    private static SocketAcceptor acceptor = null;

    private static SocketAcceptor getAcceptor() {
        return acceptor;
    }

    public FixAcceptor() throws Exception {
        if (acceptor != null) return;

        SessionSettings settings = new SessionSettings("fixAcceptor.cfg");
        Application application = new AcceptorApplication();
        FileStoreFactory fileStoreFactory = new FileStoreFactory(settings);
        ScreenLogFactory screenLogFactory = new ScreenLogFactory(settings);
        DefaultMessageFactory defaultMessageFactory = new DefaultMessageFactory();
        acceptor = new SocketAcceptor(application, fileStoreFactory, settings, screenLogFactory, defaultMessageFactory);
        acceptor.start();

    }

    public void startAcceptor() throws Exception {
        acceptor.start();
    }

    public void stopAcceptor() throws Exception {
        acceptor.stop();
    }

    public static void main(String[] args) throws Exception {
        FixAcceptor fixAcceptor = new FixAcceptor();
        CountDownLatch latch = new CountDownLatch(1);
        latch.await();
    }
}
