package com.smitresoft.smpp.smppg;

import com.cloudhopper.smpp.*;
import com.cloudhopper.smpp.impl.DefaultSmppServer;
import com.cloudhopper.smpp.pdu.BaseBind;
import com.cloudhopper.smpp.pdu.BaseBindResp;
import com.cloudhopper.smpp.pdu.PduRequest;
import com.cloudhopper.smpp.pdu.PduResponse;
import com.cloudhopper.smpp.type.RecoverablePduException;
import com.cloudhopper.smpp.type.SmppChannelException;
import com.cloudhopper.smpp.type.SmppProcessingException;
import com.cloudhopper.smpp.type.UnrecoverablePduException;
import io.sentry.protocol.Request;

public class MainServer implements SmppServerHandler, SmppSessionHandler {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger("server");


    public void start() throws SmppChannelException {

        SmppServerConfiguration conf = new SmppServerConfiguration();
        conf.setPort(4442);
        conf.setName("test gate");
        conf.setSystemId("system-id");
        conf.setBindTimeout(5_000);
        conf.setDefaultSessionCountersEnabled(true);
        conf.setDefaultWindowSize(10);
        conf.setDefaultWindowWaitTimeout(10_000);

        DefaultSmppServer a = new DefaultSmppServer(conf, this);
        a.start();

    }

    // SERVER

    @Override
    public void sessionBindRequested(Long sessionId, SmppSessionConfiguration sessionConfiguration, BaseBind request) throws SmppProcessingException {

        log.debug("got bind request: {}", request);
        log.debug("sessionBindRequested, session id: {}, sysid: {} pass:{}", sessionId, request.getSystemId(), request.getPassword());

    }

    @Override
    public void sessionCreated(Long sessionId, SmppServerSession session, BaseBindResp resp) throws SmppProcessingException {
        log.debug("sessionCreated");
        try {
            session.serverReady(this);
        } catch (Exception e) {
            log.error("failed to send resp: {}", resp, e);
        }

    }

    @Override
    public void sessionDestroyed(Long sessionId, SmppServerSession session) {
        log.debug("sessionDestroyed");
    }

    // SESSION

    @Override
    public void fireChannelUnexpectedlyClosed() {
        log.error("fireChannelUnexpectedlyClosed");
    }

    @Override
    public PduResponse firePduRequestReceived(PduRequest request) {
        log.debug("firePduRequestReceived: {}", request);
        return request.createResponse();
    }

    @Override
    public void firePduRequestExpired(PduRequest pduRequest) {
        log.debug("firePduRequestExpired: {}", pduRequest);
    }

    @Override
    public void fireExpectedPduResponseReceived(PduAsyncResponse pduAsyncResponse) {
        log.debug("fireExpectedPduResponseReceived: {}", pduAsyncResponse);
    }

    @Override
    public void fireUnexpectedPduResponseReceived(PduResponse pduResponse) {
        log.debug("fireUnexpectedPduResponseReceived: {}", pduResponse);
    }

    @Override
    public void fireUnrecoverablePduException(UnrecoverablePduException e) {
        log.error("fireUnrecoverablePduException", e);
    }

    @Override
    public void fireRecoverablePduException(RecoverablePduException e) {
        log.debug("fireRecoverablePduException");
    }

    @Override
    public void fireUnknownThrowable(Throwable t) {
        log.error("fireUnknownThrowable", t);
    }

    @Override
    public String lookupResultMessage(int commandStatus) {
        return null;
    }

    @Override
    public String lookupTlvTagName(short tag) {
        return null;
    }

}
