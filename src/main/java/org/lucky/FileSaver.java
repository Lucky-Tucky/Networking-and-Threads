package org.lucky;

import java.io.*;

public class FileSaver {

    static class Dummy implements Serializable {
        private static final long serialVersionUID = 1L;

        String name;
        int id;
        int stats;

        Dummy(String name, int id, int stats) {
            this.name = name;
            this.id = id;
            this.stats = stats;
        }
    }

    private void saveFile() throws Exception {

//        FileOutputStream fileOutputStream =
//                new FileOutputStream("MyFile.txt");
        FileInputStream fileOutputStream =
                new FileInputStream("MyFile.txt");


        ObjectInputStream objectOutputStream =
                new ObjectInputStream(fileOutputStream);

//        objectOutputStream.writeObject(
//                new Dummy("Lakshay", 1, 23)
//        );
//
//        objectOutputStream.writeObject(
//                new Dummy("Chauhan", 2, 24)
//        );
        Dummy val1 = (Dummy) objectOutputStream.readObject();

        System.out.println(val1.name);
    }

    private void newSave() throws Exception{

        FileWriter fileWriter = new FileWriter("MyFile.txt");
        fileWriter.write("Hi");
        fileWriter.close();
    }

    public static void main(String[] args) {
        try {
            new FileSaver().newSave();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}