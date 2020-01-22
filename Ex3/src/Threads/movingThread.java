package Threads;

import GUI.gui_Object;
import Server.Game_Server;

public class movingThread implements Runnable{
    private gui_Object go;

    public movingThread(gui_Object g){
        this.go=g;
    }
    @Override
    public void run() {
       while (this.go.getGame().isRunning()) {
           this.go.getGame().move();
          // System.out.println("the robot is moving");
           try {
               Thread.sleep(50);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }
}
