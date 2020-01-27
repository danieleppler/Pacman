package algorithms;

import Data_Structure.*;
import GUI.MyGameGui;
import GUI.Point3D;
import Server.RobotG;
import Threads.secondRobotMoving;
import Threads.singleRobotThread;
import Threads.thirdRobotMoving;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class automatation {
    public MyGameGui mg;
    private String kmlPath;
    public ArrayList<fruit> eatenFruits = new ArrayList<>();

    public automatation(int l, String kmlPath) throws JSONException, InterruptedException {
        this.mg = new MyGameGui(l, true);
        this.kmlPath = kmlPath;
        mg.initGui();
    }

    public automatation() {
    }


    public void startGame(int l) throws JSONException, InterruptedException {
        this.mg.initGame();
        automatation a = new automatation();
        if (this.mg.getGuiObject().getRobotsList().size() == 1) {
            singleRobotThread srt1 = new singleRobotThread(this.mg.getGuiObject().getRobotsList().get(0), this.mg, a);
            Thread t1 = new Thread(srt1);
            t1.setName("robot 1 moving");
            t1.start();
        } else if (this.mg.getGuiObject().getRobotsList().size() == 2) {
            singleRobotThread srt1 = new singleRobotThread(this.mg.getGuiObject().getRobotsList().get(0), this.mg, a);
            Thread t1 = new Thread(srt1);
            singleRobotThread srt2 = new singleRobotThread(this.mg.getGuiObject().getRobotsList().get(1), this.mg, a);
            Thread t2 = new Thread(srt2);
            t1.start();
            t1.setName("robot 1 moving");
            t2.start();
            t2.setName("robot 2 moving");
        } else if (this.mg.getGuiObject().getRobotsList().size() == 3) {
            singleRobotThread srt1 = new singleRobotThread(this.mg.getGuiObject().getRobotsList().get(0), this.mg, a);
            Thread t1 = new Thread(srt1);
            t1.setName("robot 1 moving");
            singleRobotThread srt2 = new singleRobotThread(this.mg.getGuiObject().getRobotsList().get(1), this.mg, a);
            Thread t2 = new Thread(srt2);
            t2.setName("robot 2 moving");
            singleRobotThread srt3 = new singleRobotThread(this.mg.getGuiObject().getRobotsList().get(2), this.mg, a);
            Thread t3 = new Thread(srt3);
            t3.setName("robot 3 moving");
            t2.start();
            t1.start();
            t3.start();
        }
        Thread.currentThread().join();
        String kml = this.mg.kl.writeKml(l, this.kmlPath, this.mg.kl.placeMarkList);
        this.mg.getGuiObject().getGame().sendKML(kml);
    }

    public void moveRobots(List<Data_Structure.node_data> way, robot r, MyGameGui mg) throws JSONException, InterruptedException {
        if (!mg.getGuiObject().getGame().isRunning())
            return;
        if (way == null)
            return;
        System.out.println(Thread.currentThread().getName() + " moved!");
        int i = 0;
        fruit f1 = null;
        for (fruit f2 : mg.getGuiObject().getFruitList()
        ) {
            int spotA = mg.getGuiObject().getGraph().nodeCollection.get(way.get(way.size() - 1).getKey()).getTag();
            int spotB;
            if (way.size() > 1)
                spotB = mg.getGuiObject().getGraph().nodeCollection.get(way.get(way.size() - 2).getKey()).getTag();
            else spotB = r.getCurrNode();
            if (f2.getCurrEdge().key[0] == spotA && f2.getCurrEdge().key[1] == spotB || f2.getCurrEdge().key[1] == spotA &&
                    f2.getCurrEdge().key[0] == spotB) {
                f1 = f2;
                break;
            }
        }
        if (!mg.getGuiObject().getGame().isRunning())
            return;
        while (i < way.size() && mg.getGuiObject().getGame().isRunning()) {
            r.setCurrNodeDest(way.get(i).getTag());
            mg.getGuiObject().getGame().chooseNextEdge(r.getId(), r.getCurrNodeDest());
            boolean flag = false;
            if (!mg.getGuiObject().getGame().isRunning())
                return;
            DGraph temp2 = new DGraph(mg.getGuiObject().getGame().getGraph());
            Point3D temp4 = temp2.nodeCollection.get(way.get(i).getKey()).getLocation();
            while (!flag) {
                robot temp = new robot(mg.getGuiObject().getGame().getRobots().get(r.getId()));
                Point3D temp3 = temp.getLocation();
                if (temp4.x() == temp3.x() && temp4.y() == temp3.y()) flag = true;
            }
            r.setCurrNode(way.get(i).getTag());
            i++;
        }
        //checking if the fruit indeed got eaten or skipped by the robot,if didnt got eaten, remove him from the eaten list
        //previously we identify the fruit that suppose to be eaten.
        int j = 0;
        while (j < mg.getGuiObject().getFruitList().size() && mg.getGuiObject().getGame().isRunning()) {
            if (f1.getLocation().x() == mg.getGuiObject().getFruitList().get(j).getLocation().x() && f1.getLocation().y() ==
                    mg.getGuiObject().getFruitList().get(j).getLocation().y())
                this.eatenFruits.remove(f1);
            j++;
        }
        r.setCurrNodeDest(-1);
    }

    synchronized public List<Data_Structure.node_data> chooseFruit(graph_algorithms ga, robot r, MyGameGui mg) {
        List<Data_Structure.node_data> way = new LinkedList<>();
        if (mg.getGuiObject().getGame().isRunning()) {
            double min = Integer.MAX_VALUE;
            edge nearestNodeEdge = new edge(0, 0, 0);
            int srcOrDest = 0;
            int i = 0;
            fruit eatenFruit = null;
            ArrayList<fruit> currentFruitsOnBoard = mg.getGuiObject().getFruitList();
            while (i < currentFruitsOnBoard.size() && mg.getGuiObject().getGame().isRunning()) {
                boolean flag = true;
                if (!mg.getGuiObject().getGame().isRunning())
                    break;
                fruit f = currentFruitsOnBoard.get(i);
                System.out.println(Thread.currentThread().getName() + " was here!");
                Iterator<fruit> it = this.eatenFruits.iterator();
                while (it.hasNext() && mg.getGuiObject().getGame().isRunning()) {
                    fruit isEqual = it.next();
                    if (f.getLocation().x() == isEqual.getLocation().x() && f.getLocation().y() == isEqual.getLocation().y())
                        flag = false;
                }
                //we are chosing the unchodes fruits by the condition: chose the fruit that is the most distanced from the
                //last chosen fruit
                if (flag) {
                    double temp1 = ga.shortestPathDist(r.getCurrNode(), f.getCurrEdge().key[0]);
                    if (temp1 < min) {
                        min = temp1;
                        nearestNodeEdge = f.getCurrEdge();
                        srcOrDest = 0;
                        eatenFruit = f;
                    }
                    double temp2 = ga.shortestPathDist(r.getCurrNode(), f.getCurrEdge().key[1]);
                    if (temp2 < min) {
                        min = temp2;
                        nearestNodeEdge = f.getCurrEdge();
                        srcOrDest = 1;
                        eatenFruit = f;
                    }
                }
                i++;
            }
            if (!mg.getGuiObject().getGame().isRunning())
                return null;
            if (eatenFruit != null)
                this.eatenFruits.add(eatenFruit);
            else return way;
            if (srcOrDest == 1) {
                way = ga.shortestPath(r.getCurrNode(), nearestNodeEdge.getDest());
                way.add(mg.getGuiObject().getGraph().nodeCollection.get(nearestNodeEdge.getSrc()));
            } else {
                way = ga.shortestPath(r.getCurrNode(), nearestNodeEdge.getSrc());
                way.add(mg.getGuiObject().getGraph().nodeCollection.get(nearestNodeEdge.getDest()));
            }
        }
        return way;
    }
}

