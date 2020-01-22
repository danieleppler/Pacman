package Threads;

import Data_Structure.*;
import GUI.MyGameGui;
//import GUI.SingleGameCreator;
import GUI.SingleGameCreator;
import algorithms.Graph_Algo;
import org.json.JSONException;
import utils.Point3D;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class singleRobotThread implements Runnable {
    private robot r;
    private MyGameGui mg;
    private Graph_Algo ga;
    private SingleGameCreator sgc;

    public singleRobotThread(robot r,MyGameGui mg)
    {
        this.mg=mg;
        this.r=r;
        DGraph g=this.mg.getGuiObject().getGraph();
        this.ga=new Graph_Algo(g);
        this.sgc=new SingleGameCreator();
    }

    @Override
    public void run() {
        while (this.mg.getGuiObject().getGame().isRunning()) {
            double min = Integer.MAX_VALUE;
            edge nearestNodeEdge = new edge(0, 0, 0);
            int srcOrDest = -0;
            for (fruit f : this.mg.getGuiObject().getFruitList()
            ) {
                if (!this.mg.getGuiObject().getGame().isRunning())
                    break;
                double temp1 = ga.shortestPathDist(r.getCurrNode(), f.getCurrEdge().key[0]);
                if (temp1 < min) {
                    min = temp1;
                    nearestNodeEdge = f.getCurrEdge();
                }
                double temp2 = ga.shortestPathDist(r.getCurrNode(), f.getCurrEdge().key[1]);
                if (temp2 < min) {
                    min = temp2;
                    nearestNodeEdge = f.getCurrEdge();
                    srcOrDest = 1;
                }
            }
            if (!this.mg.getGuiObject().getGame().isRunning())
                break;
            List<dataStructure.node_data> way = new LinkedList<>();
            if (srcOrDest == 1)
                way = ga.shortestPath(r.getCurrNode(), nearestNodeEdge.getDest());
            else
                way = ga.shortestPath(r.getCurrNode(), nearestNodeEdge.getSrc());
            if (!this.mg.getGuiObject().getGame().isRunning())
                break;
            int i = 1;
            while (i < way.size()) {
                try {
                    moveRobot(this.r, way, i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                r.setCurrNodeDest(-1);
                i++;
            }
            try {
                lastStep(srcOrDest, r, nearestNodeEdge);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            r.setCurrNodeDest(-1);
            }
            this.mg.printReasult();
        }


        public void moveRobot (robot r,List<dataStructure.node_data> way,int i) throws JSONException {
            r.setCurrNodeDest(way.get(i).getTag());
            this.mg.getGuiObject().getGame().chooseNextEdge(r.getId(), r.getCurrNodeDest());
            boolean flag=false;
            if (!this.mg.getGuiObject().getGame().isRunning())
                return;
            while (!flag){
                robot temp=new robot(this.mg.getGuiObject().getGame().getRobots().get(0));
                DGraph temp2=new DGraph(this.mg.getGuiObject().getGame().getGraph());
                Point3D temp3=temp.getLocation();
                Point3D temp4=temp2.nodeCollection.get(way.get(i).getKey()).getLocation();
                if(temp4.x()==temp3.x()&&temp4.y()==temp3.y()) flag=true;
                if (!this.mg.getGuiObject().getGame().isRunning())
                    break;
            }
            r.setCurrNode(way.get(i).getTag());
            }
            public void lastStep(int srcOrDest,robot r,edge nearestNodeEdge) throws JSONException {
            //passing the edge with the fruit to collect the points
            if (srcOrDest==1)
             r.setCurrNodeDest(nearestNodeEdge.getSrc());
            else r.setCurrNodeDest(nearestNodeEdge.getDest());
            this.mg.getGuiObject().getGame().chooseNextEdge(r.getId(), r.getCurrNodeDest());
                boolean flag=false;
                if (!this.mg.getGuiObject().getGame().isRunning())
                    return;
                while (!flag&&this.mg.getGuiObject().getGame().isRunning()){
                    robot temp=new robot(this.mg.getGuiObject().getGame().getRobots().get(0));
                    DGraph temp2=new DGraph(this.mg.getGuiObject().getGame().getGraph());
                    Point3D temp3=temp.getLocation();
                    Point3D temp4=temp2.nodeCollection.get(r.getCurrNodeDest()).getLocation();
                    if(temp4.x()==temp3.x()&&temp4.y()==temp3.y()) flag=true;
                    if (!this.mg.getGuiObject().getGame().isRunning())
                        break;
                }
            if (srcOrDest==1)
                r.setCurrNode(nearestNodeEdge.getSrc());
            else r.setCurrNode(nearestNodeEdge.getDest());
            }}

