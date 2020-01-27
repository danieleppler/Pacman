package Data_Structure;

public class Dijkstra {
    public Vertex[] vertices;
    public int source;

    public Dijkstra(Vertex[] vs, int source) {
        this.source = source;
        vertices = new Vertex[vs.length];
        for (int i = 0; i < vs.length; i++)
            vertices[i] = vs[i];
    }

    public void computePaths(){
        Vertex s = vertices[source];
        s.minDistance = 0;
        HeapMin Q = new HeapMin();
        Q.resize(1);
        Q._a[0]=s;
        //O(nlogn)
        for (int i=1; i<vertices.length; i++){//O(n)
            Q.minHeapInsert(vertices[i]);//O(logn)
        }
        //O(nlogn) + O(mlogn) = O((m+n)logn)
        while (!Q.isEmpty()) {//O(m)
            Vertex u = Q.heapExtractMin();//O(logn)
            // Visit each edge exiting u
            for (edge e : u.edges){
                Vertex v = vertices[e.getDest()];
                if (!v.visited){
                    double minDistanceU = u.minDistance + e.getWeight();
                    if (minDistanceU < v.minDistance) {//relaxation
                        v.minDistance = minDistanceU ;
                        v.prev = vertices[u.tag].tag;
                        Q.heapDecreaseKey(v);//O(logn)
                    }
                }
            }
            u.visited = true;
        }
    }
}