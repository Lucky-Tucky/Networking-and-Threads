package org.lucky;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Server{

    public static void main(String[] args){
        start();
    }

    public static void start(){
        InetSocketAddress address = new InetSocketAddress(5000);
        try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()){
            System.out.println("Server Started...");
            serverSocketChannel.bind(address);
            while(serverSocketChannel.isOpen()){
                SocketChannel clientChannel = serverSocketChannel.accept();
                Writer writer = Channels.newWriter(clientChannel, StandardCharsets.UTF_8);
                PrintWriter printWriter = new PrintWriter(writer);
                printWriter.println("Never Give Up 💪!!!!");

                printWriter.close();
            }

        }catch(IOException exception){
            System.out.println("Server Error -> "+ exception.getMessage());
        }finally {
            System.out.println("Server Closed...");
        }
    }
}
