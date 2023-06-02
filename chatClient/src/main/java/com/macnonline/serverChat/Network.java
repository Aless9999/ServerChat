package com.macnonline.serverChat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;


import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


public class Network {
    private static final String HOST = "localhost";
    private static final int PORT = 8189;
    private SocketChannel channel;


    public Network(Callback messageReceivedCallback) {

        Thread t = new Thread(() -> {
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                Bootstrap b = new Bootstrap();
                b.group(workerGroup)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {


                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                channel = socketChannel;
                                socketChannel.pipeline().addLast(new StringDecoder(), new StringEncoder(), new ClientHandler(messageReceivedCallback)
                                );
                            }
                        });
                ChannelFuture future = b.connect(HOST, PORT).sync();
                future.channel().closeFuture().sync();//чтобы клиент сразу не закрылся
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                workerGroup.shutdownGracefully();
            }

        });
        t.setDaemon(true);
        t.start();
    }

    public void sendMessage(String str) {
        channel.writeAndFlush(str);
    }
}
