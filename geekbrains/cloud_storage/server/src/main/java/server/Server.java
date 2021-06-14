package server;

import server.handlers.ClientHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class Server {
    public static final int SERVER_PORT = 7799;
    public static final int AUTH_POOL_QTY = 1;

    public static void main(String[] args) {
        EventLoopGroup auth = new NioEventLoopGroup(AUTH_POOL_QTY); // создаём один пул потоков для авторизации
        EventLoopGroup worker = new NioEventLoopGroup(); // создаём автоматический пул потоков для работы с клиентами

        try {
            ServerBootstrap bs = new ServerBootstrap(); // инициализируем предварительные настройки сервера

            // открываем сокеты для подключения клиентов
            bs.group(auth, worker)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        // конвейер
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    // in handlers
                                    new StringEncoder(),
                                    new ClientHandler(),
                                    // out handlers
                                    new StringDecoder()
                            );
                        }
                    });

            // старт сервера
            ChannelFuture server = bs.bind(SERVER_PORT).sync();
            System.out.println("Server started at port: " + SERVER_PORT);

            // ожидание выключения сервера, блокирующая операция
            server.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // закрытие потоков
            auth.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }
}
