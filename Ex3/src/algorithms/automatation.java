package algorithms;

import Data_Structure.edge;
import Data_Structure.fruit;
import Data_Structure.robot;
import GUI.MyGameGui;
import Server.RobotG;
import Threads.singleRobotThread;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import utils.Point3D;

import java.awt.*;
import java.util.ArrayList;

public class automatation  {
    private MyGameGui mg;
    String kmlPath;

    public automatation(int l,String kmlPath) throws JSONException, InterruptedException {
        this.mg = new MyGameGui(l);
        this.kmlPath=kmlPath;
        mg.initGui();
    }


    public void startGame(int l) throws JSONException, InterruptedException {
        this.mg.initGame();
        if (this.mg.getGuiObject().getRobotsList().size()==1) {
            singleRobotThread srt1=new singleRobotThread(this.mg.getGuiObject().getRobotsList().get(0),this.mg);
            Thread t1 = new Thread(srt1);
            t1.setName("robot 1 moving");
            t1.start();
            t1.join();
        }
        else  if (this.mg.getGuiObject().getRobotsList().size()==2) {
            singleRobotThread srt1=new singleRobotThread(this.mg.getGuiObject().getRobotsList().get(0),this.mg);
            Thread t1 = new Thread(srt1);
            singleRobotThread srt2=new singleRobotThread(this.mg.getGuiObject().getRobotsList().get(1),this.mg);
            Thread t2 = new Thread(srt2);
            t1.start();
            t1.setName("robot 1 moving");
            t2.start();
            t2.setName("robot 2 moving");
            t1.join();
            t2.join();
        }
        else  if (this.mg.getGuiObject().getRobotsList().size()==3) {
            singleRobotThread srt1=new singleRobotThread(this.mg.getGuiObject().getRobotsList().get(0),this.mg);
            Thread t1 = new Thread(srt1);
            t1.setName("robot 1 moving");
            singleRobotThread srt2=new singleRobotThread(this.mg.getGuiObject().getRobotsList().get(1),this.mg);
            Thread t2 = new Thread(srt2);
            t2.setName("robot 2 moving");
            singleRobotThread srt3=new singleRobotThread(this.mg.getGuiObject().getRobotsList().get(2),this.mg);
            Thread t3 = new Thread(srt3);
            t3.setName("robot 3 moving");
            t1.start();
            t2.start();
            t3.start();
        t1.join();
        t2.join();
        t3.join();
        }
        this.mg.kl.writeKml(l,this.kmlPath,this.mg.kl.placeMarkList);
    }
}
