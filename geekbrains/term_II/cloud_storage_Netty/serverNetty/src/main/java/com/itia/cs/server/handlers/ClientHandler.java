package com.itia.cs.server.handlers;

import com.itia.cs.commands.Command;
import com.itia.cs.server.AuthService;
import com.itia.cs.server.DBAuthService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private DBAuthService authService;


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client connected: " + ctx.channel());
    }

    /**
     * Обработка входящих сообщений от клиента
     * @param ctx
     * @param msg прилетают байты
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        ByteBuf buf = (ByteBuf) msg;
        String incomeMSG = buf.toString(StandardCharsets.UTF_8).trim();

        if (incomeMSG.startsWith(Command.AUTHY)) {
            String[] token = incomeMSG.split("\\s");

            /** Проверка пользователя и получение рабочей папки пользователя*/
            if (token.length == 3) {
                if (!SQLHandler.connectDB()) {
                    RuntimeException e = new RuntimeException("Невозможно подключиться к БД");
                    throw e;
                }
                try {
                    String rootFolder = authService.getDesktopByLoginAndPassword(token[1],token[2]);
                    System.out.println(rootFolder);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        buf.release();


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("Client lost connection." + ctx.channel());
        cause.printStackTrace();
        ctx.close();

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client disconnected: " + ctx.channel());
        ctx.close();
    }
}
