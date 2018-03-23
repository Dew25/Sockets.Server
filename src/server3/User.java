/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jvm
 */
public class User implements Runnable {

    Socket socket;
    Work work;
    PrintWriter clientWriter;
    BufferedReader fromClient;
    String userName;
    String linepart = "";
    double x, y, angle, step, dangle;

    public User(Socket socket, Work work) {
        this.socket = socket;
        this.work = work;
        x = Server3Start.AREA_WIDTH * Math.random();
        y = Server3Start.AREA_HEIGHT * Math.random();
        angle = 6.28 * Math.random();
        step = 5;
        dangle = 0.1;
        new Thread(this).start();

    }

    @Override
    public void run() {
        try {
            clientWriter = new PrintWriter(socket.getOutputStream(), true);
            fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            clientWriter.print("Yeu name, please: ");
            userName = fromClient.readLine();
            work.add(this);
        } catch (IOException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, "Ошибка создания инструмента записи в сокет", ex);
        }
    }

    @Override
    public String toString() {
        return userName + "," + (int) x + "," + (int) y + "," + (int) Math.toDegrees(angle);
    }

    public void front() {
        x += step * Math.cos(angle);
        y += step * Math.sin(angle);
    }
    public void back() {
        x -= step * Math.cos(angle);
        y -= step * Math.sin(angle);
    }

    public void right() {
        angle += dangle;
    }

    public void left() {
        angle -= dangle;
    }

    public void execute(String command) {
        command = command.trim();
        if (command.equals("f")) {
            front();
        }
        if (command.equals("r")) {
            right();
        }
        if (command.equals("l")) {
            left();
        }
        if (command.equals("b")) {
            back();
        }
    }

}
