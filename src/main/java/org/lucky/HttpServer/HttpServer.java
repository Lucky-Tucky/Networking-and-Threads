package org.lucky.HttpServer;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    static void main() {

        InetSocketAddress address = new InetSocketAddress(8080);
        ExecutorService threadPool = Executors.newCachedThreadPool();
        try(ServerSocketChannel channel = ServerSocketChannel.open()){
            channel.bind(address);
            while(channel.isOpen()){
                SocketChannel communicationChannel = channel.accept();
                threadPool.execute(new HttpMethod(communicationChannel));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            threadPool.shutdown();
        }
    }
}
