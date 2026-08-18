package org.lucky;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class Listen{
    public static void go(){
        InetSocketAddress socketAddress = new InetSocketAddress("127.0.0.1",5000);
//        SocketChannel socketChannel = SocketChannel.open();
//
        try(SocketChannel socketChannel = SocketChannel.open(socketAddress)){
            System.out.println("Client Started..");
            Reader channelReader = Channels.newReader(socketChannel, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(channelReader);

            String quote = reader.readLine();
            System.out.println(quote);
            reader.close();
        }catch (IOException exception){
            System.out.println("Error -> "+exception.getMessage());
        }
    }
}
