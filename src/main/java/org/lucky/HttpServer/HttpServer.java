package org.lucky.HttpServer;

import java.io.BufferedReader;
import java.io.Reader;
import java.net.InetSocketAddress;
import java.nio.channels.Channels;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class HttpServer {

    static void main() {

        InetSocketAddress address = new InetSocketAddress(8080);

        try(ServerSocketChannel channel = ServerSocketChannel.open()){
            channel.bind(address);

            while(channel.isOpen()){
                SocketChannel communicationChannel = channel.accept();
                Reader reader = Channels.newReader(communicationChannel, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(reader);

                StringBuffer request = new StringBuffer();
                String line = "";
                while((line=bufferedReader.readLine())!=null){
                    request.append(line+"\n");
                }
                System.out.println(request.toString());
//                new Thread(new HttpMethod(request.toString())).start();
                bufferedReader.close();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
