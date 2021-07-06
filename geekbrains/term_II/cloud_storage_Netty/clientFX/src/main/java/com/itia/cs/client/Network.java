package com.itia.cs.client;

import com.itia.cs.client.handlers.ClientMessageHandler;
import com.itia.cs.client.handlers.ServerMessageHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.nio.charset.StandardCharsets;

public class Network {
    public static final String SERVER_IP = "localhost";
    public static final int SERVER_PORT = 7799;
    private SocketChannel channel;
    private ServerMessage serverMessage;

    public Network () {
        Thread t = new Thread(() -> {
            EventLoopGroup worker = new NioEventLoopGroup();
            try {
                Bootstrap bs = new Bootstrap();
                bs.group(worker)
                        .channel(NioSocketChannel.class)
                        .handler(new ChannelInitializer<SocketChannel>() {
                            @Override
                            protected void initChannel(SocketChannel socketChannel) throws Exception {
                                channel = socketChannel;
                                socketChannel.pipeline().addLast(
                                        // in handlers
                                        new StringDecoder(), //in-1
                                        new ServerMessageHandler(serverMessage), //in-2
                                        // out handlers
                                        new ClientMessageHandler(), //out-1
                                        new StringEncoder() //out-final
                                );
                            }
                        });
                ChannelFuture client = bs.connect(SERVER_IP, SERVER_PORT).sync();
                client.channel().closeFuture().sync(); // ожидание команды на остановку снаружи. блокирующая операция
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                worker.shutdownGracefully();
            }

        });
        t.start();
    }

    public void setServerMessage(ServerMessage serverMessage) {
        this.serverMessage = serverMessage;
    }
/*
    public void sendMessage (String message) {
            channel.writeAndFlush(message.getBytes(StandardCharsets.UTF_8));
    }

 */

    public void closeConnection(){
        channel.close();
    }

}
