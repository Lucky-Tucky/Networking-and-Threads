package org.lucky.HttpServer;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class HttpMethod implements Runnable{

    private final Map<String,String > headers = new HashMap<>();
    private final String body = null;
    private String methodType;
    private final SocketChannel socketChannel;
    private String HttpRequest;
    private final StringBuffer log;

    public HttpMethod(SocketChannel socketChannel){
        log = new StringBuffer(LocalDateTime.now().toString()).append("\n");
        this.socketChannel = socketChannel;
    }

    private void cleanUp(){
        try {
            String[] request_parts = HttpRequest.split("\n");
            this.methodType = methodType(request_parts[0]);
            if(this.methodType==null){
                throw new Exception("Method Not Found");
            }
            log.append(request_parts[0]).append("\n");
            int i =1;
            while(i<request_parts.length && request_parts[i].contains(":")){
                String[] header = request_parts[i].split(":",2);
                this.headers.put(header[0],header[1].trim());
                i++;
            }

        }catch (Exception e){
            log.append("Method Not Found").append("\n");
            e.printStackTrace();
        }
    }

    private String methodType(String request_line){
        System.out.println(request_line);
        if(request_line.contains("GET")){
            return "GET";
        } else if (request_line.contains("PUT")) {
            return "PUT";
        }else if (request_line.contains("DELETE")){
            return "DELETE";
        }else if(request_line.contains("POST")){
            return "POST";
        }else{
            return null;
        }
    }

    private static synchronized void logger(String logs){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("logs.txt",true))) {
            writer.append(logs);
            writer.newLine();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try{
            Reader reader = Channels.newReader(socketChannel, StandardCharsets.UTF_8);
            log.append(socketChannel.getRemoteAddress().toString()).append("\n");
            BufferedReader bufferedReader  = new BufferedReader(reader);

            StringBuilder request = new StringBuilder();
            String line = "";
            while((line=bufferedReader.readLine())!=null && !line.isEmpty()) {
                request.append(line).append("\n");
            }
            this.HttpRequest = request.toString();
            if(!this.HttpRequest.isEmpty()){
                this.cleanUp();
            }

        }catch (IOException e){
            log.append("Request not Fulfilled");
            e.printStackTrace();
        }finally {
            HttpMethod.logger(log.toString());
        }
    }
}
