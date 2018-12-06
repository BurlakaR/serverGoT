package com.communication;

import org.springframework.stereotype.Component;

import java.io.*;
import java.net.Socket;

@Component
public class Bridge {
    private static InputStream in;
    private static OutputStream out;

    public void send(Socket socket, Object object){
        try {
            out = socket.getOutputStream();

            ObjectOutputStream oos = new ObjectOutputStream(out);
            oos.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Object receive(Socket socket){
        try {
            in = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(in);
            Object toWrite = ois.readObject();
            return toWrite;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Connection troubles");
        }
        return null;
    }
}