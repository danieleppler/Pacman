package Threads;

import Data_Structure.*;
import GUI.MyGameGui;
//import GUI.SingleGameCreator;
import GUI.SingleGameCreator;
import algorithms.Graph_Algo;
import gameClient.automatation;
import org.json.JSONException;


import java.util.List;

public class singleRobotThread implements Runnable {
    private robot r;
    private MyGameGui mg;
    private Graph_Algo ga;
    private SingleGameCreator sgc;
    private automatation a;

    public singleRobotThread(robot r, MyGameGui mg,automatation a) {
        this.mg = mg;
        this.r = r;
        DGraph g = this.mg.getGuiObject().getGraph();
        this.ga = new Graph_Algo(g);
        this.sgc = new SingleGameCreator();
        this.a=a;
    }

    @Override
    public void run() {
        while (this.mg.getGuiObject().getGame().isRunning()) {
          // synchronized (this.a) {
                //chosing the current robot destination
                List<Data_Structure.node_data> way = this.a.chooseFruit(this.ga,this.r,this.mg);
                //moving the robot in the given path
                if (!this.mg.getGuiObject().getGame().isRunning())
                break;
                try {
                    this.a.moveRobots(way,this.r,this.mg);
                } catch (JSONException | InterruptedException e) {
                    e.printStackTrace();
              //  }
            }
        }
    }
}