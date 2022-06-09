package algorithms.datastructures.graphs;

import javax.print.attribute.standard.Sides;

public class BFSGraph {
    private class Vertex {
        public char label;
        public boolean wasVisited;

        public Vertex(char label) {
            this.label = label;
            wasVisited = false;
        }
    }

    private class Queue {
        private final int SIZE = 20;
        private int[] queueArray;
        private int front;
        private int rear;

        public Queue() {
            queueArray = new int[SIZE];
            front = 0;
            rear = -1;
        }

        public void insert(int value) {
            if (rear == SIZE - 1) {
                rear = -1;
            }
            queueArray[++rear] = value;
        }

        public int remove() {
            int temp = queueArray[front++];
            if (front == SIZE) {
                front = 0;
            }
            return temp;
        }

        public boolean isEmpty() {
            return rear + 1 == front || front + SIZE - 1 == rear;
        }
    }

    private final int MAX_VERTS = 20;
    private Vertex[] vertexList;
    private int[][] adjMatrix;
    private int nVerts;
    private Queue queue;

    public BFSGraph() {
        vertexList = new Vertex[MAX_VERTS];
        adjMatrix = new int[MAX_VERTS][MAX_VERTS];
        nVerts = 0;

        for (int row = 0; row < MAX_VERTS; row++) {
            for (int col = 0; col < MAX_VERTS; col++) {
                adjMatrix[row][col] = 0;
            }
        }
        queue = new Queue();
    }

    public void addVertex(char label) {
        vertexList[nVerts++] = new Vertex(label);
    }

    public void addEdge(int start, int end) {
        adjMatrix[start][end] = 1;
        adjMatrix[end][start] = 1;
    }

    public void displayVertex(int v) {
        System.out.print(vertexList[v].label);
    }

    /** Breadth-first search */
    public void bfs() {
        vertexList[0].wasVisited = true;
        displayVertex(0);
        queue.insert(0);
        int v2;

        while (!queue.isEmpty()) {
            int v1 = queue.remove();
            while ((v2 = getAdjUnvisitedVertex(v1)) != -1) {
                vertexList[v2].wasVisited = true;
                displayVertex(v2);
                queue.insert(v2);
            }
        }

        for (int i = 0; i < nVerts; i++) {
            vertexList[i].wasVisited = false;
        }
    }

    /** Returns an unvisited vertex adj to row */
    public int getAdjUnvisitedVertex(int row) {
        for (int col = 0; col < nVerts; col++) {
            if (adjMatrix[row][col] == 1 
                && vertexList[col].wasVisited == false) {
                return col;
            }
        }
        return -1;
    } 

    public static void main(String[] args) {
        BFSGraph theGraph = new BFSGraph();

        theGraph.addVertex('A'); // 0  (start for bfs)
        theGraph.addVertex('B'); // 1
        theGraph.addVertex('C'); // 2
        theGraph.addVertex('D'); // 3
        theGraph.addVertex('E'); // 4

        theGraph.addEdge(0, 1); // AB
        theGraph.addEdge(1, 2); // BC
        theGraph.addEdge(0, 3); // AD
        theGraph.addEdge(3, 4); // DE

        System.out.print("Visits: ");
        theGraph.bfs(); // breadth-first search
        System.out.println();
    }
}
