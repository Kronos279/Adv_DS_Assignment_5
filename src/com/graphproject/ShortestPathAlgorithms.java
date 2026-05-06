package com.graphproject;

import java.util.*;

public class ShortestPathAlgorithms {

    public static class PathResult {
        public double cost;
        public int nodesVisited;
        public long executionTimeNs;

        public PathResult(double cost, int nodesVisited, long executionTimeNs) {
            this.cost = cost;
            this.nodesVisited = nodesVisited;
            this.executionTimeNs = executionTimeNs;
        }
    }

    // Dijkstra's Algorithm
    public static PathResult runDijkstra(Graph graph, int startId, int goalId) {
        long startTime = System.nanoTime();
        
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(Comparator.comparingDouble(nd -> nd.distance));
        Map<Integer, Double> distances = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        
        for (int nodeId : graph.nodes.keySet()) {
            distances.put(nodeId, Double.POSITIVE_INFINITY);
        }
        
        Node startNode = graph.nodes.get(startId);
        distances.put(startId, 0.0);
        pq.add(new NodeDistance(startNode, 0.0));
        
        int nodesVisited = 0;

        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            Node currentNode = current.node;
            
            if (!visited.add(currentNode.id)) continue;
            nodesVisited++;

            if (currentNode.id == goalId) {
                break;
            }

            for (Edge edge : currentNode.edges) {
                if (visited.contains(edge.target.id)) continue;

                double newDist = distances.get(currentNode.id) + edge.weight;
                if (newDist < distances.get(edge.target.id)) {
                    distances.put(edge.target.id, newDist);
                    pq.add(new NodeDistance(edge.target, newDist));
                }
            }
        }
        
        long endTime = System.nanoTime();
        return new PathResult(distances.get(goalId), nodesVisited, endTime - startTime);
    }

    // A* Algorithm
    public static PathResult runAStar(Graph graph, int startId, int goalId) {
        long startTime = System.nanoTime();
        
        Node goalNode = graph.nodes.get(goalId);
        PriorityQueue<NodeDistance> pq = new PriorityQueue<>(Comparator.comparingDouble(nd -> nd.distance));
        Map<Integer, Double> gScores = new HashMap<>();
        Set<Integer> visited = new HashSet<>();
        
        for (int nodeId : graph.nodes.keySet()) {
            gScores.put(nodeId, Double.POSITIVE_INFINITY);
        }
        
        Node startNode = graph.nodes.get(startId);
        gScores.put(startId, 0.0);
        pq.add(new NodeDistance(startNode, heuristic(startNode, goalNode)));
        
        int nodesVisited = 0;

        while (!pq.isEmpty()) {
            NodeDistance current = pq.poll();
            Node currentNode = current.node;
            
            if (!visited.add(currentNode.id)) continue;
            nodesVisited++;

            if (currentNode.id == goalId) {
                break;
            }

            for (Edge edge : currentNode.edges) {
                if (visited.contains(edge.target.id)) continue;

                double tentativeGScore = gScores.get(currentNode.id) + edge.weight;
                if (tentativeGScore < gScores.get(edge.target.id)) {
                    gScores.put(edge.target.id, tentativeGScore);
                    double fScore = tentativeGScore + heuristic(edge.target, goalNode);
                    pq.add(new NodeDistance(edge.target, fScore));
                }
            }
        }
        
        long endTime = System.nanoTime();
        return new PathResult(gScores.get(goalId), nodesVisited, endTime - startTime);
    }

    private static double heuristic(Node a, Node b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));
    }

    private static class NodeDistance {
        Node node;
        double distance;

        NodeDistance(Node node, double distance) {
            this.node = node;
            this.distance = distance;
        }
    }
}
