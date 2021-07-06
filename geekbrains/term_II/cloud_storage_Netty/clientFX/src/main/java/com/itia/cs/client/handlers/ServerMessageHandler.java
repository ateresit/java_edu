package com.itia.cs.client.handlers;

import com.itia.cs.client.ServerMessage;
import com.itia.cs.commands.Command;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

public class ServerMessageHandler extends ChannelInboundHandlerAdapter {
    private ServerMessage serverMSG;
    private boolean authenticated;

    public ServerMessageHandler(ServerMessage serverMessage) {
        this.serverMSG = serverMessage;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("ServerMessageHandler worked");
        ByteBuf buf = (ByteBuf) msg;
        String incomeMSG = buf.toString(StandardCharsets.UTF_8).trim();

        if (incomeMSG.startsWith(Command.AUTHY_OK)) {
            authenticated = true;
            System.out.println("Аутентификация ОК");
        } else {
            authenticated = false;
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush()
    }
}
