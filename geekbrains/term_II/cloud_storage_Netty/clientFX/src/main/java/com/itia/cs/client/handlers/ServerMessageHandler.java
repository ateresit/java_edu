package com.itia.cs.client.handlers;

import com.itia.cs.client.ServerMessage;
import com.itia.cs.commands.Command;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.StandardCharsets;

public class ServerMessageHandler extends SimpleChannelInboundHandler {
    private ServerMessage serverMSG;
    private boolean authenticated;

    public ServerMessageHandler(ServerMessage serverMessage) {
        this.serverMSG = serverMessage;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        String incomeMSG = buf.toString(StandardCharsets.UTF_8).trim();

        if (incomeMSG.startsWith(Command.AUTHY_OK)) {
            authenticated = true;
            System.out.println("Аутентификация ОК");
        } else {
            authenticated = false;
        }
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
