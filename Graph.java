import java.util.*;

class Graph {
    private int vertices;
    private Map<Integer, List<int[]>> adjList;
    private Set<Integer> restrictedNodes;

    public Graph(int vertices) {
        this.vertices = vertices;
        adjList = new HashMap<>();
        restrictedNodes = new HashSet<>();
    }

    public void addEdge(int u, int v, int weight) {
        if (!adjList.containsKey(u)) {
            adjList.put(u, new ArrayList<>());
        }
        if (!adjList.containsKey(v)) {
            adjList.put(v, new ArrayList<>());
        }
        adjList.get(u).add(new int[] { v, weight });
        adjList.get(v).add(new int[] { u, weight });
    }

    public void addRestrictedNode(int node) {
        restrictedNodes.add(node);
    }

    public void dijkstra(int start, int end) {
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        Map<Integer, Integer> distance = new HashMap<>();
        Set<Integer> visited = new HashSet<>();

        for (int i = 0; i < vertices; i++) {
            distance.put(i, Integer.MAX_VALUE);
        }
        distance.put(start, 0);
        pq.add(new int[] { start, 0 });

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];
            int dist = current[1];

            if (visited.contains(node) || restrictedNodes.contains(node)) {
                continue;
            }
            visited.add(node);

            if (node == end) {
                System.out.println("Shortest Path Distance: " + dist);
                return;
            }

            for (int[] neighbor : adjList.getOrDefault(node, new ArrayList<>())) {
                int nextNode = neighbor[0];
                int weight = neighbor[1];
                if (!visited.contains(nextNode) && !restrictedNodes.contains(nextNode)) {
                    int newDist = dist + weight;
                    if (newDist < distance.get(nextNode)) {
                        distance.put(nextNode, newDist);
                        pq.add(new int[] { nextNode, newDist });
                    }
                }
            }
        }
        System.out.println("No valid path found.");
    }

    public static void main(String[] args) {
        Graph graph = new Graph(6);
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 2, 2);
        graph.addEdge(1, 2, 5);
        graph.addEdge(1, 3, 10);
        graph.addEdge(2, 4, 3);
        graph.addEdge(4, 3, 4);
        graph.addEdge(3, 5, 11);

        graph.addRestrictedNode(2);

        graph.dijkstra(0, 5);
    }
}