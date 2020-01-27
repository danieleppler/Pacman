package Data_Structure;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.*;


import java.util.Collection;
        import java.util.HashMap;
import java.util.Scanner;

public class DGraph implements graph {

    public HashMap<Integer[],edge> edgeCollection;
    public  HashMap<Integer, node> nodeCollection;

    private int MC;

    public HashMap<Integer, node> getECollection()
    {
        return this.nodeCollection;
    }
    public DGraph(String file_name) {
        try {
            this.init();
            String jsonString =file_name;
            JSONObject graph = new JSONObject(jsonString);
            JSONArray nodes = graph.getJSONArray("Nodes");
            JSONArray edges = graph.getJSONArray("Edges");

            int i;
            int s;
            for(i = 0; i < nodes.length(); ++i) {
                s = nodes.getJSONObject(i).getInt("id");
                String pos = nodes.getJSONObject(i).getString("pos");
                //Point3D p = new Point3D(pos);
                node temp= new node(s,s);
                temp.setInfo(pos);
                this.addNode(temp);
            }

            for(i = 0; i < edges.length(); ++i) {
                s = edges.getJSONObject(i).getInt("src");
                int d = edges.getJSONObject(i).getInt("dest");
                double w = edges.getJSONObject(i).getDouble("w");
                this.connect(s, d, w);
            }
        } catch (Exception var12) {
            var12.printStackTrace();
        }

    }

    public void init() {
        this.nodeCollection=new HashMap<Integer,node>();
        this.edgeCollection=new HashMap<Integer[],edge>();
    }

    public DGraph()
    {
        init();
    }

    @Override
    public Data_Structure.node_data getNode(int key) {
        // TODO Auto-generated method stub
        return (Data_Structure.node_data) nodeCollection.get(key);
    }

    @Override
    public Data_Structure.edge_data getEdge(int src, int dest) {
        // TODO Auto-generated method stub
        int key[]={src,dest};
        return (Data_Structure.edge_data) edgeCollection.get(key);
    }

    @Override
    public void addNode(Data_Structure.node_data n) {
        this.nodeCollection.put(n.getKey(), (node) n);
    }


    @Override
    public void connect(int src, int dest, double w) {
        edge e=new edge(src,dest,w);
        Integer key[]={src,dest};
        e.key=key;
        this.edgeCollection.put(key,e);
    }

    @Override
    public Collection<Data_Structure.node_data> getV() {
        // TODO Auto-generated method stub
        Collection<Data_Structure.node_data> temp=new HashSet<>();
        for (Map.Entry<Integer,node> entrty:this.nodeCollection.entrySet())
            temp.add(entrty.getValue());
        return temp;
    }


    @Override
    public Collection<Data_Structure.edge_data> getE(int node_id)
    {
        Collection<Data_Structure.edge_data> temp= new HashSet<>();
        for (Map.Entry<Integer[],edge> entry:this.edgeCollection.entrySet()
        ) {
            if (entry.getKey()[0] == node_id)
                temp.add(entry.getValue());
        }
        return temp;
    }

    @Override
    public Data_Structure.node_data removeNode(int key) {
        // TODO Auto-generated method stub
        node n=this.nodeCollection.get(key);
        this.nodeCollection.remove(key);
        return (Data_Structure.node_data) n;
    }

    @Override
    public Data_Structure.edge_data removeEdge(int src, int dest) {
        Integer key[]={src,dest};
        edge_data e= (edge_data) edgeCollection.get(key);
        edgeCollection.remove(key);
        return (Data_Structure.edge_data) e;
    }

    @Override
    public int nodeSize() {
        // TODO Auto-generated method stub
        return nodeCollection.size();
    }

    @Override
    public int edgeSize() {
        return edgeCollection.size();
    }

    public void setMC(int mc)
    {
        this.MC=mc;
    }

    @Override
    public int getMC() {
        return this.MC;
    }

    public DGraph copy()
    {
        DGraph d=new DGraph();
        for (Map.Entry<Integer,node> entry:this.nodeCollection.entrySet()
        ) {
           d.nodeCollection.put(entry.getKey(),entry.getValue());
        }
        for (Map.Entry<Integer[],edge> entry2:this.edgeCollection.entrySet()
        ) {
            d.edgeCollection.put(entry2.getKey(),entry2.getValue());
        }
        d.setMC(this.getMC());

        return d;
    }
}
