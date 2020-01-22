package Data_Structure;

import dataStructure.node_data;
import utils.Point3D;

import java.util.StringTokenizer;

public class node implements node_data {

    private double weight;
    private int key;
    private double x,y,z;
    public int[] Connections;
    private int tag;

    public node() {
        int[] temparr={0,0};
        this.Connections=temparr;
    }

    public node(int key,int Tag) {
        this.key = key;
        this.tag=Tag;
        this.x = x;
        this.y = y;
        this.z = z;
        int[] temparr={0,0};
        this.Connections=temparr;
    }

    @Override
    public Point3D getLocation() {

        Point3D temp = new Point3D(this.x, this.y, this.z);
        return temp;
    }

    @Override
    public void setLocation(Point3D p) {
        this.x = p.x();
        this.y = p.y();
        this.z = p.z();
    }

    @Override
    public double getWeight() {
        // TODO Auto-generated method stub
        return this.weight;
    }

    @Override
    public void setWeight(double w) {
        this.weight = w;
    }

    @Override
    public String getInfo() {
        String temp = "location: "+this.x + ","+this.y+","+this.z;
        // TODO Auto-generated method stub
        return temp;
    }

    @Override
    public void setInfo(String s) {
        // TODO Auto-generated method stub
        StringTokenizer st=new StringTokenizer(s,",");
        this.x= Double.parseDouble(st.nextToken());
        this.y=Double.parseDouble(st.nextToken());
        this.z=Double.parseDouble(st.nextToken());
    }

    @Override
    public int getTag() {
        // TODO Auto-generated method stub
        return this.tag;
    }

    @Override
    public void setTag(int t) {
        // TODO Auto-generated method stub
        this.tag=t;
    }

    @Override
    public int getKey() {
        // TODO Auto-generated method stub
        return this.key;
    }

}