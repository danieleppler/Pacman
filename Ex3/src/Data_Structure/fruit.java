package Data_Structure;

import com.google.gson.JsonObject;
import dataStructure.edge_data;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;

import java.util.StringTokenizer;

public class fruit {
    private Point3D pos;
    private double value;
    private edge currEdge;
    private int type;

    public fruit() {
    }
    public fruit(String jsonString) throws JSONException {
        JSONObject fruit=new JSONObject(jsonString);
        this.type=fruit.getJSONObject("Fruit").getInt("type");
        this.value=fruit.getJSONObject("Fruit").getDouble("value");
        StringTokenizer st=new StringTokenizer(fruit.getJSONObject("Fruit").getString("pos"), ",");
        this.pos=new Point3D(Double.parseDouble(st.nextToken()),Double.parseDouble(st.nextToken()),Double.parseDouble(st.nextToken()));
    }
    public fruit(double value, Point3D pos, edge currE,int type) {
        this.value = value;
        this.pos = new Point3D(pos);
        this.currEdge = currE;
        this.type=type;
    }

    public void setType() {
        int ans =0;
        ans = this.currEdge.getDest() - this.currEdge.getSrc();
        if (ans>=0)
            this.type=1;
        else this.type=-1;
    }

    public int getType()
    {
        return this.type;
    }
    public void setLocation(Point3D p){this.pos=p;}
    public Point3D getLocation() {
        return new Point3D(this.pos);
    }
    public edge getCurrEdge()
    {
        return this.currEdge;
    }
    public String toJSON1() {
        String ans = "{\"Fruit\":{\"value\":10,\"type\":1,\"pos\":\"35.187615443099276,32.103800431932775,0.0\"}}";
        return ans;
    }
    public void setCurrEdge(edge e)
    {
        this.currEdge=e;
    }

}
