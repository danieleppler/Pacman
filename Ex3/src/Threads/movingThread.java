package Threads;

import Data_Structure.robot;
import GUI.gui_Object;
import Server.Game_Server;
import org.json.JSONException;

import java.util.List;

public class movingThread implements Runnable{
    private gui_Object go;
    private int movesNum;
    private int dinamicSleep;

    public movingThread(gui_Object g){
        this.go=g;this.movesNum=0;this.dinamicSleep=100;
    }
    @Override
    public void run() {
       while (this.go.getGame().isRunning()) {
           movesNum++;
           double robotMaxSpeed=0;
           List<String> log=this.go.getGame().move();
           for (int i=0;i<log.size();i++) {
               robot r= null;
               try {
                   r = new robot(log.get(i));
                   if (robotMaxSpeed<r.getSpeed())
                       robotMaxSpeed=r.getSpeed();
               } catch (JSONException e) {
                   e.printStackTrace();
               }
               System.out.println(movesNum+") "+ r.toJSON());
           }
           try {
               Thread.sleep((long) ((dinamicSleep+(robotMaxSpeed))));
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
       this.go.finalMovesNum=movesNum;
    }

}
