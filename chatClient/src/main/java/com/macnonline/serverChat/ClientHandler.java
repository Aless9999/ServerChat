package com.macnonline.serverChat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<String> {
    private final Callback messageReceivedCallback;

    public ClientHandler(Callback messageReceivedCallback) {
        this.messageReceivedCallback = messageReceivedCallback;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        if(messageReceivedCallback!=null){
            messageReceivedCallback.abracadabra(s);
        }
    }
}
