package Data_Structure;


import Data_Structure.edge_data;

import java.awt.*;
import java.util.Arrays;
import java.util.Collection;


public class edge implements edge_data {

    private int src,dest;
    private double weight;
    private Color color;
    public Integer[] key;

    public edge()
    {
        this.src=0;
        this.dest=0;
        this.weight=0;
    }

    public edge(int s,int d,double w)
    {
        this.src=s;
        this.dest=d;
        this.weight=w;
    }



    @Override
    public int getSrc() {
        return this.src;
    }

    @Override
    public int getDest() {
        return this.dest;
    }

    @Override
    public double getWeight() {
        return this.weight;
    }

    @Override
    public String getInfo() {
        return null;
    }

    @Override
    public void setInfo(String s) {
    }

    @Override
    public int getTag() {
        return 0;
    }

    @Override
    public void setTag(int t) {

    }
}
