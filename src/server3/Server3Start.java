/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server3;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jvm
 */
public class Server3Start {
    public static final int AREA_WIDTH=400;
    public static final int AREA_HEIGHT=300;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(3001);
            Work work = new Work();
            new Thread(work).start();
            boolean working = true;
            while (working) {
                new User(serverSocket.accept(),work);
            }
        } catch (IOException ex) {
            Logger.getLogger(Server3Start.class.getName()).log(Level.SEVERE, "Не удалось создать сокет", ex);
        }
    }
    
}
