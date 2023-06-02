package com.macnonline.serverChat;


import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;

import java.util.ArrayList;
import java.util.List;

public class MainHandler extends SimpleChannelInboundHandler<String> {
    private static SocketChannel channel;
    private static List<Channel> channels = new ArrayList<>();
    private static int clientIndex=1;
    private String clientName;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client connected."+ctx);
        channels.add(ctx.channel());
        clientName="client #"+clientIndex;
        clientIndex++;
    }



    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("Get message "+s);
        String out = String.format("{%s}: %s\n",clientName, s);
        for(Channel client:channels){
            client.writeAndFlush(out);
        }


    }



    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Client "+clientName+" reconnected!");
        channels.remove(ctx.channel());
        ctx.close();
    }
}
