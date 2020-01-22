package algorithms;

import Data_Structure.DGraph;
import Data_Structure.edge;
import Data_Structure.node;
import com.google.gson.*;
import dataStructure.graph;
import dataStructure.node_data;

import java.io.*;
import java.util.*;

/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */
public class Graph_Algo implements graph_algorithms {

	private Data_Structure.DGraph singleGraph;

	public Graph_Algo(){this.singleGraph=new Data_Structure.DGraph();};

	public Graph_Algo(Data_Structure.graph g){this.init(g);};

	@Override
	public void init(Data_Structure.graph g) {
		// TODO Auto-generated method stub
			this.singleGraph= (Data_Structure.DGraph) g;
	}

	@Override
	public void init(String file_name) {
		// TODO Auto-generated method stub
		try {
			BufferedReader br = new BufferedReader(new FileReader(file_name));
			Data_Structure.DGraph temp=new Data_Structure.DGraph();
			Gson gson=new Gson();
			JsonElement root=new JsonParser().parse(br.readLine());
			JsonObject jsonNodeCollection = root.getAsJsonObject().get("nodeCollection").getAsJsonObject();
			for (Map.Entry<String, JsonElement> entry : jsonNodeCollection.entrySet())
				temp.nodeCollection.put(gson.fromJson(entry.getKey(), Integer.class),gson.fromJson(entry.getValue(), node.class));
			JsonArray jsonedgeCollection = root.getAsJsonObject().get("edgeCollection").getAsJsonArray();
			Iterator<JsonElement> it=jsonedgeCollection.iterator();
			while (it.hasNext())
			{
				JsonArray je= (JsonArray) it.next();
				JsonArray key=je.get(0).getAsJsonArray();
				Integer[] key2={key.get(0).getAsInt(),key.get(1).getAsInt()};
				temp.edgeCollection.put(key2,gson.fromJson(je.get(1).getAsJsonObject(), edge.class));
			}
			temp.setMC(root.getAsJsonObject().get("MC").getAsInt());
			this.init(temp.copy());
		}
		catch (Exception e) {
		}
	}

	@Override
	public void save(String file_name) throws IOException {
		// TODO Auto-generated method stub
		Gson gson=new GsonBuilder().enableComplexMapKeySerialization().create();
		String ourJson =gson.toJson(this.singleGraph);
		try
		{
			PrintWriter pw = new PrintWriter(new File(file_name));
			pw.write(ourJson);
			pw.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.out.println("could not find the specific file");
		}
	}

	@Override
	public boolean isConnected() {
		boolean flag=true;
			for (Integer[] key:this.singleGraph.edgeCollection.keySet()
				 ) {
				int fromConnection=key[0];
				int toConnection=key[1];
				for (Map.Entry<Integer, node> entry :this.singleGraph.nodeCollection.entrySet()
					 ) {
					if(entry.getValue().getTag()==fromConnection)
						entry.getValue().Connections[0]=1;
					else if(entry.getValue().getTag()==toConnection)
						entry.getValue().Connections[1]=1;
				}
			}
			for (Map.Entry<Integer,node> entry:this.singleGraph.nodeCollection.entrySet()
				 ) {
				if(entry.getValue().Connections[0]==0||entry.getValue().Connections[1]==0)
					flag=false;
			}
		if (flag)
			return true;
		else return false;
		}


	@Override
	public double shortestPathDist(int src, int dest) {
		// TODO Auto-generated method stub
		return nodeTagging(src, dest,dest,new LinkedList<Integer>());
	}


	@Override
	public List<node_data> shortestPath(int src, int dest) {
		List<node_data> shortestPath= new LinkedList<>();
		nodeTagging(src,dest,dest,new LinkedList<Integer>());
		node_data tempNode=this.singleGraph.nodeCollection.get(dest);
		shortestPath.add(tempNode);
		while (src!=dest)
		{
			ArrayList<node_data> possilbeConnections=new ArrayList<>();
			for (Map.Entry<Integer[],edge> entry:this.singleGraph.edgeCollection.entrySet()
				 ) {
				if(entry.getKey()[1]==tempNode.getTag())
					possilbeConnections.add(this.singleGraph.nodeCollection.get(entry.getKey()[0]));
			}
			Iterator<node_data> it=possilbeConnections.listIterator();
			node_data minNode=possilbeConnections.get(0);
			while (it.hasNext())
			{
				tempNode=it.next();
				if (tempNode.getWeight()<minNode.getWeight())
					minNode=tempNode;
			}
			shortestPath.add(minNode);
			dest=minNode.getTag();
			for (Map.Entry<Integer,node> entry:this.singleGraph.nodeCollection.entrySet()
			) {
				if(entry.getValue().getTag()==dest)
					tempNode=entry.getValue();
			}
		}
		Stack<node_data> reverseHelpingStack=new Stack<>();
		Iterator<node_data> it=shortestPath.iterator();
		while (it.hasNext()) {
			tempNode = it.next();
			reverseHelpingStack.push(tempNode);
		}
		shortestPath=new ArrayList<>();
		while (!reverseHelpingStack.empty())
			shortestPath.add(reverseHelpingStack.pop());
		return shortestPath;
	}


	@Override
	public List<node_data> TSP(List<Integer> targets) {
		// TODO Auto-generated method stub
		//input checks
		//check if the graph is directional connected
		if (!this.isConnected())
			return null;
		//check if all the nodes in targets are in the given graph
		int temp = 0;
		Iterator<Integer> it = targets.iterator();
		while (it.hasNext()) {
			boolean flag = false;
			temp = it.next();
			for (Map.Entry<Integer, node> entry : this.singleGraph.nodeCollection.entrySet()
			) {
				if(temp == entry.getValue().getTag());
					flag = true;
			}
			if (flag == false)
				return null;
		}
		List<node_data> TSPList = new LinkedList<>();
		TSPList.add(this.singleGraph.nodeCollection.get(targets.get(0)));
		int checkedNode = 0;
		while (!targets.isEmpty()&&targets.size()>1) {
			node_data checkedNode3 = new node();
			checkedNode=targets.get(0);
			targets.remove(0);
			double min = Integer.MAX_VALUE;
			Iterator<Integer> it3 = targets.iterator();
			while (it3.hasNext()&&targets.size()>1) {
				node_data checkedNode2 = this.singleGraph.nodeCollection.get(it3.next());
				double reasult = shortestPathDist(checkedNode, checkedNode2.getTag());
				if (reasult < min) {
					min = reasult;
					checkedNode3=checkedNode2;
				}
			}
			TSPList.add(checkedNode3);
		}
		return TSPList;
	}


	public double nodeTagging(int srcKey,int destKey,int fromKey,List<Integer> passedNodes)
	{
		if (destKey==srcKey){
			this.singleGraph.nodeCollection.get(destKey).setWeight(0);
			return 0;}

		double[][] possilbeConnections=new double[2][this.singleGraph.edgeCollection.size()];
		int tempNodeTag=this.singleGraph.nodeCollection.get(destKey).getTag();
		int i=0;
		int connectionsNumber=0;
		//checking neighbors
		for (Map.Entry<Integer[],edge> entry:this.singleGraph.edgeCollection.entrySet()
			 ) {
			boolean flag=true;
			int temp=entry.getKey()[1];
			for (int k=0;k<passedNodes.size();k++)
				if (temp==passedNodes.get(k))
					flag=false;
			if (flag!=false){
			if ( temp==tempNodeTag && entry.getKey()[0] != fromKey) {
				possilbeConnections[0][i] = entry.getKey()[0];
				possilbeConnections[1][i] = entry.getValue().getWeight();
				connectionsNumber++;
				i++;
			}
			}
		}
		if (connectionsNumber==0)
			return Integer.MAX_VALUE;
		if(connectionsNumber==1) {
			passedNodes.add(destKey);
			this.singleGraph.nodeCollection.get(destKey).setWeight(nodeTagging(srcKey, (int) possilbeConnections[0][0], destKey, passedNodes)
					+ possilbeConnections[1][0]);
		}
			else
				{
					passedNodes.add(destKey);
					double min=possilbeConnections[1][0]+nodeTagging(srcKey, (int) possilbeConnections[0][0],destKey,passedNodes);
					for (i=1;i<connectionsNumber;i++) {
						double temp=possilbeConnections[1][i]+nodeTagging(srcKey, (int)possilbeConnections[0][i],destKey,passedNodes);
						if(temp<min)
						min=temp;
				}
				this.singleGraph.nodeCollection.get(destKey).setWeight(min);
				}
			passedNodes.remove(passedNodes.size()-1);
			return this.singleGraph.nodeCollection.get(destKey).getWeight();
	}


	@Override
	public Data_Structure.graph copy() {
		// TODO Auto-generated method stub
		DGraph clone=this.singleGraph.copy();
		return clone;
	}
}
