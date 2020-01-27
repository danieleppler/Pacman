package GUI;

import Data_Structure.*;
import GUI.gui_Object;
import Server.Game_Server;
import org.json.JSONException;

import java.util.*;

import static GUI.MyGameGui.scale;

public class SingleGameCreator {
    private static final double _EPSILON_ = 0.001,_EPSILON2_=_EPSILON_*_EPSILON_;

    public SingleGameCreator() {

    }

     public static gui_Object create(int level,boolean isAutomat,long id) throws JSONException {
        Game_Server.login((int) id);
        Game_Server game= (Game_Server) Game_Server.getServer(level);
        String info = game.toString();
        DGraph g = new DGraph(game.getGraph());
        System.out.println(info);
        ArrayList<fruit> fruitList=new ArrayList<>();
         List<String> fruitsByServer = game.getFruits();
         //invoking the fruit data from the server
         fruitList=findingCurEdge(game);
         //checking if the game is on automat version,if so,put the robot on ideal places
         if (isAutomat==true)
         {
             int temp4=0;
             List<Integer> startNodes=new LinkedList<>();
           for (int i=0;i<fruitList.size();i++)
             {
                 double max=0;
                 node_data temp=new node();
                 int j=0;
                 if (i!=0) {
                     node_data temp2=g.nodeCollection.get(startNodes.get(startNodes.size()-1));
                     while (j<fruitList.size()){
                     temp = g.nodeCollection.get(fruitList.get(j).getCurrEdge().getSrc());
                     double temp3=distanceFormula(temp2.getLocation(),temp.getLocation());
                     if (temp3>max) {
                         max=temp3;
                        temp4=(temp.getTag());
                     }
                     j++;}
                     startNodes.add(temp4);
                 }
                 else
                    startNodes.add(fruitList.get(i).getCurrEdge().getSrc());
             }
             //CHEATING
             //startNodes.add(47);
             //startNodes.add(37);
             //startNodes.add(7);
             for (int i=0;i<startNodes.size();i++)
                    if(game.addRobot(startNodes.get(i)));
                      else break;
         }
         else
            while (game.addRobot(0)) ;
        ArrayList<robot> robotList = new ArrayList<>();
         List<String> robotsByServer = game.getRobots();
        //invoking the robot data from the server
        for (int i = 0; i < robotsByServer.size(); i++) {
            robot r = new robot(robotsByServer.get(i));
            robotList.add(r);
        }
        return new gui_Object(fruitList,robotList,game,g);
    }

    public static ArrayList<fruit> findingCurEdge(Game_Server game) throws JSONException {
        List<String> fruitsByJson = game.getFruits();
        DGraph g=new DGraph(game.getGraph());
        ArrayList<fruit> fruitList = new ArrayList<>();
        for (int i = 0; i < fruitsByJson.size(); i++) {
            fruit temp = new fruit(fruitsByJson.get(i));
            for (Integer[] key : g.edgeCollection.keySet()
            ) {
                double distanceOfFruitFromNodes = distanceFormula(temp.getLocation(), g.nodeCollection.get(key[0]).getLocation()) +
                        distanceFormula(temp.getLocation(), g.nodeCollection.get(key[1]).getLocation());
                //double maxLimit =  (distanceFormula(g.nodeCollection.get(key[0]).getLocation(), g.nodeCollection.get(key[1]).getLocation()) + _EPSILON_);
                //double minLimit =(distanceFormula(g.nodeCollection.get(key[0]).getLocation(), g.nodeCollection.get(key[1]).getLocation()) - _EPSILON_);
                double nodesDistance=distanceFormula(g.nodeCollection.get(key[0]).getLocation(),g.nodeCollection.get(key[1]).getLocation());
                if (nodesDistance>distanceOfFruitFromNodes-_EPSILON2_&&nodesDistance<distanceOfFruitFromNodes+_EPSILON2_)
                    temp.setCurrEdge(g.edgeCollection.get(key));
            }
            temp.setType();
            //scaling the fruit location
            fruitList.add(temp);
        }
        return fruitList;
    }

    public static double distanceFormula(Point3D p1, Point3D p2) {
        double reasult=Math.sqrt((Math.pow(p1.x() - p2.x(), 2) + (Math.pow(p1.y() - p2.y(), 2))));
        return reasult;
    }

}
