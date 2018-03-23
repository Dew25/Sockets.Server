/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jvm
 */
public class Work implements Runnable{

    boolean running = true;
    List<User> users = new ArrayList<>();
    
    public void writeToAll(String text){
        for(User userToWrite : users){
            userToWrite.clientWriter.println(text);
        }
    }
    
    public void add(User user){
        synchronized(users){
            users.add(user);
            writeToAll(user.toString());
        }
        
    }   
    @Override
    public void run() {
        while(running){
            try {
                Thread.sleep(500);
                synchronized(users){
                    for(User user : users){
                        if(user.fromClient.ready()){
                            while(user.fromClient.ready()){
                                char c = (char)user.fromClient.read();
                                if(c == '\n'){
                                    user.execute(user.linepart);
                                    writeToAll(user.toString());
                                    user.linepart="";
                                }else{
                                    user.linepart += c;
                                }
                            }
                        }
                    }
                }
//                if(user.fromClient.ready()){
//                   String line = user.fromClient.readLine();
//                    System.out.println(line);
//                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Work.class.getName()).log(Level.SEVERE, "Проблема с потоком ", ex);
            } catch (IOException ex) {
                Logger.getLogger(Work.class.getName()).log(Level.SEVERE, "Ошибка чтения из сокета", ex);
            }
            
        }
    }
    
}
