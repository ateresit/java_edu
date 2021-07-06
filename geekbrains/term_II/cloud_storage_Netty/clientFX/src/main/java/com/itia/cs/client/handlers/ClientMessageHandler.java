package com.itia.cs.client.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;

public class ClientMessageHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        System.out.println("ClientMessageHandler worked");
        ctx.writeAndFlush("/authy asd asd");
    }
}
