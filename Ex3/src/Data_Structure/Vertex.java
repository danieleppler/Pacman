package Data_Structure;

import java.util.Collection;

public class Vertex implements Comparable<Vertex> {
    public int tag;
    public double minDistance;
    public edge[] edges;
    public boolean visited;
    public int prev;

    public Vertex(int name, double dist) {
        this.tag = name;
        this.minDistance = dist;
        this.prev = -1;
        this.visited = false;
    }
    public Vertex(Vertex v){
        this.tag = v.tag;
        this.minDistance =v.minDistance;
        this.visited = v.visited;
        this.edges= v.edges;
    }

    @Override
    public int compareTo(Vertex v) {
        int ans = 0;
        if (this.minDistance - v.minDistance> 0) ans = 1;
        else if (this.minDistance - v.minDistance < 0) ans = -1;
        return ans;
    }
}