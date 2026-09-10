package org.lucky.HttpServer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HttpMethod implements Runnable{

    private final String request;
    private Map<String,String > headers = new HashMap<>();
    private String body = null;
    private String method;

    public HttpMethod(String request){
        this.request = request;
    }

    private void cleanUp(){
        try {
            String[] request_parts = request.split("\n");
            String[] request_line =  request_parts[0].split(" ");
            this.method = request_line[0];
            int i =1;
            while(request_parts[i].contains(":")){
                String[] header = request_parts[i].split(":");
                this.headers.put(header[0],header[1]);
                i++;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            HttpMethod.logger(LocalDateTime.now() + " "+this.method!=null ? this.method : "Error" );
        }
    }

    private static synchronized void logger(String text){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("logs.txt",true))) {
            writer.append(text);
            writer.newLine();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
//        cleanUp();
    }
}
