package server.handlers;

import commands.Command;
import server.AuthService;
import server.DBAuthService;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.StandardCharsets;

public class ClientHandler extends ChannelInboundHandlerAdapter {
    private AuthService authService;


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
                authService = new DBAuthService();
                String rootFolder = getAuthService().getDesktopByLoginAndPassword(token[1], token[2]);

                if (rootFolder == null) {
                    System.out.println("BAD client authy: " + ctx.channel());
                    ctx.writeAndFlush(Command.AUTHY_ERROR);
                } else {
                    System.out.println("client authy OK.");
                    ctx.writeAndFlush(Command.AUTHY_OK);
//                    ctx.writeAndFlush(String.format("%s %s", Command.AUTHY_OK, rootFolder));
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

    public AuthService getAuthService() {
        return authService;
    }

}
