package GUI;

import Data_Structure.*;
import GUI.gui_Object;
import Server.Game_Server;
import org.json.JSONException;
import utils.Point3D;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import static GUI.MyGameGui.scale;

public class SingleGameCreator {
    private static final double _EPSILON_ = 0.01;

    public SingleGameCreator() {

    }

     public static gui_Object create(int level) throws JSONException {
        Game_Server game= (Game_Server) Game_Server.getServer(level);
        String info = game.toString();
        DGraph g = new DGraph(game.getGraph());
        System.out.println(info);
        ArrayList<fruit> fruitList=new ArrayList<>();
         List<String> fruitsByServer = game.getFruits();
         //invoking the fruit data from the server
         for (int i = 0; i < fruitsByServer.size(); i++) {
             fruit f= new fruit(fruitsByServer.get(i));
             fruitList.add(f);
         }
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
            edge deafultEdge = null;
            for (Integer[] key : g.edgeCollection.keySet()
            ) {
                double tempEdge = distanceFormula(temp.getLocation(), g.nodeCollection.get(key[0]).getLocation()) +
                        distanceFormula(temp.getLocation(), g.nodeCollection.get(key[1]).getLocation());
                double maxLimit =  (distanceFormula(g.nodeCollection.get(key[0]).getLocation(), g.nodeCollection.get(key[1]).getLocation()) + _EPSILON_);
                double minLimit =(distanceFormula(g.nodeCollection.get(key[0]).getLocation(), g.nodeCollection.get(key[1]).getLocation()) - _EPSILON_);
                if (tempEdge > minLimit && tempEdge < maxLimit)
                    temp.setCurrEdge(g.edgeCollection.get(key));
                if (key[0]==0&&key[1]==1)
                    deafultEdge=g.edgeCollection.get(key);
            }
            if (temp.getCurrEdge()==null) {
                temp.setCurrEdge(deafultEdge);
            }
            temp.setType();
            //scaling the fruit location
            double x = scale(temp.getLocation().x(), 35, 36, 50, 400);
            double y = scale(temp.getLocation().y(), 32, 33, 0, 250);
            temp.setLocation(new Point3D(x, y, 0));
            fruitList.add(temp);
        }
        return fruitList;
    }

    private static double distanceFormula(Point3D p1, Point3D p2) {
        return Math.sqrt((Math.pow(p1.x() - p2.x(), 2) - (Math.pow(p1.y() - p2.y(), 2))));
    }

}
