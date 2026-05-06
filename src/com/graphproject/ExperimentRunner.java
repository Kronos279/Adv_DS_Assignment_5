package com.graphproject;

import java.util.Random;

public class ExperimentRunner {

    public static void main(String[] args) {
        System.out.println("Graph Algorithms Experiment Runner");
        System.out.println("==================================");

        runGridExperiment(100, 100);

        System.out.println();
        runRandomSpatialGraphExperiment(5000, 20000);
    }

    private static void runGridExperiment(int rows, int cols) {
        System.out.println("Running Grid Graph Experiment (" + rows + "x" + cols + ")...");
        Graph graph = new Graph();
        int idCounter = 0;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                graph.addNode(idCounter++, i, j);
            }
        }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int currentId = i * cols + j;
                if (j < cols - 1) graph.addEdge(currentId, currentId + 1, 1.0);
                if (j > 0) graph.addEdge(currentId, currentId - 1, 1.0);
                if (i < rows - 1) graph.addEdge(currentId, currentId + cols, 1.0);
                if (i > 0) graph.addEdge(currentId, currentId - cols, 1.0);
            }
        }

        int startId = 0;
        int goalId = rows * cols - 1;

        ShortestPathAlgorithms.PathResult dijkstraResult = ShortestPathAlgorithms.runDijkstra(graph, startId, goalId);
        ShortestPathAlgorithms.PathResult aStarResult = ShortestPathAlgorithms.runAStar(graph, startId, goalId);

        System.out.printf("Dijkstra -> Cost: %.2f | Nodes Visited: %d | Time: %.2f ms\n",
                dijkstraResult.cost, dijkstraResult.nodesVisited, dijkstraResult.executionTimeNs / 1_000_000.0);
        System.out.printf("A*       -> Cost: %.2f | Nodes Visited: %d | Time: %.2f ms\n",
                aStarResult.cost, aStarResult.nodesVisited, aStarResult.executionTimeNs / 1_000_000.0);
    }

    private static void runRandomSpatialGraphExperiment(int numNodes, int numEdges) {
        System.out.println("Running Random Spatial Graph Experiment (" + numNodes + " nodes, " + numEdges + " edges)...");
        Graph graph = new Graph();
        Random rand = new Random(42);

        for (int i = 0; i < numNodes; i++) {
            graph.addNode(i, rand.nextDouble() * 1000, rand.nextDouble() * 1000);
        }

        for (int i = 0; i < numEdges; i++) {
            int u = rand.nextInt(numNodes);
            int v = rand.nextInt(numNodes);
            if (u != v) {
                Node n1 = graph.nodes.get(u);
                Node n2 = graph.nodes.get(v);
                double dist = Math.sqrt(Math.pow(n1.x - n2.x, 2) + Math.pow(n1.y - n2.y, 2));
                double trafficMultiplier = 1.0 + rand.nextDouble() * 5.0;
                graph.addEdge(u, v, dist * trafficMultiplier);
            }
        }

        int startId = 0;
        int goalId = numNodes - 1;

        ShortestPathAlgorithms.PathResult dijkstraResult = ShortestPathAlgorithms.runDijkstra(graph, startId, goalId);
        ShortestPathAlgorithms.PathResult aStarResult = ShortestPathAlgorithms.runAStar(graph, startId, goalId);

        System.out.printf("Dijkstra -> Cost: %.2f | Nodes Visited: %d | Time: %.2f ms\n",
                dijkstraResult.cost, dijkstraResult.nodesVisited, dijkstraResult.executionTimeNs / 1_000_000.0);
        System.out.printf("A*       -> Cost: %.2f | Nodes Visited: %d | Time: %.2f ms\n",
                aStarResult.cost, aStarResult.nodesVisited, aStarResult.executionTimeNs / 1_000_000.0);
    }
}
