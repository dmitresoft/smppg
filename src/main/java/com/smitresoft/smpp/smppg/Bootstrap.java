package com.smitresoft.smpp.smppg;

import com.cloudhopper.smpp.type.SmppChannelException;

public class Bootstrap {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger("bootstrap");

    public static void main(String[] args) throws InterruptedException, SmppChannelException {

        MainServer server = new MainServer();
        server.start();


    }

}