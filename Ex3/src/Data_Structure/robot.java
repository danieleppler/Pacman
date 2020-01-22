package Data_Structure;

import com.google.gson.Gson;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;

import java.awt.*;
import java.util.StringTokenizer;

public class robot {
    public static final double _STARTINGPOINTS_ = 0.0D;
    public static final double _DEFAULT_SPEED_ = 1.0D;
    private graph currGraph;
    private int id;
    private Point3D position;
    private double speed;
    private int currNodeDest;
    private int currNode;
    private double points;

    public robot() {
    }

    public robot(graph g, int start_node, int id) {
        this.currGraph = g;
        this.points = _STARTINGPOINTS_;
        this.currNode = start_node;
        this.position = this.currGraph.getNode(currNode).getLocation();
        this.id = id;
        this.speed = _DEFAULT_SPEED_;
    }

    public robot(String jsonString) throws JSONException {
        JSONObject fruit=new JSONObject(jsonString);
        this.id=fruit.getJSONObject("Robot").getInt("id");
        this.points=fruit.getJSONObject("Robot").getDouble("value");
        this.currNode=fruit.getJSONObject("Robot").getInt("src");
        this.currNodeDest=fruit.getJSONObject("Robot").getInt("dest");
        this.speed=fruit.getJSONObject("Robot").getDouble("speed");
        StringTokenizer st=new StringTokenizer(fruit.getJSONObject("Robot").getString("pos"), ",");
        this.position=new Point3D(Double.parseDouble(st.nextToken()),Double.parseDouble(st.nextToken()),Double.parseDouble(st.nextToken()));
    }


    public String toJSON() {
        String ans = new Gson().toJson(this);
        return ans;
    }


    public int getId(){return this.id;}

    public void setId(int id) {
        this.id = id;
    }

    public void setStartingPoint(int p) {
        this.currNode = p;
    }

    public Point3D getLocation()
    {
        return this.position;
    }

    public void setLocation(Point3D p)
    {
        this.position=p;
    }

    public int getCurrNode(){
        return this.currNode;
    }
    public void setCurrNode(int n)
    {
        this.currNode=n;
    }

    public void setCurrNodeDest(int n)
    {
        this.currNodeDest=n;
    }
    public double getPoints(){
        return this.points;
    }
    public void setPoints(double v){ this.points=v;}
    public int getCurrNodeDest(){
        return this.currNodeDest;
    }
}