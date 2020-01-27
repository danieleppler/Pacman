package GUI;

import Data_Structure.DGraph;
import Data_Structure.fruit;
import Data_Structure.graph;
import Data_Structure.robot;
import Server.Game_Server;

import java.util.ArrayList;

public class gui_Object {
    private ArrayList<fruit> fruitList;
    private ArrayList<robot> robotsList;
    Game_Server game;
    DGraph g;
    public double finalMovesNum;

    public gui_Object(ArrayList<fruit> f, ArrayList<robot> r, Game_Server g,DGraph g2)
    {
        this.fruitList=f;
        this.robotsList=r;
        this.game=g;
        this.g=g2;
    }

    public ArrayList<fruit> getFruitList()
    {
        return this.fruitList;
    }
    public void setRobotList(ArrayList<robot> r)
    {
        this.robotsList=r;
    }
    public ArrayList<robot> getRobotsList()
    {
        return this.robotsList;
    }

    public void setFruitList(ArrayList<fruit> fruitList) {
        this.fruitList = fruitList;
    }

    public Game_Server getGame()
    {
        return this.game;
    }

    public DGraph getGraph()
    {
        return this.g;
    }
}
