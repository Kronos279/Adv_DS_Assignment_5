package com.graphproject;

import java.util.HashMap;
import java.util.Map;

public class Graph {
    public Map<Integer, Node> nodes;

    public Graph() {
        this.nodes = new HashMap<>();
    }

    public void addNode(int id, double x, double y) {
        nodes.put(id, new Node(id, x, y));
    }

    public void addEdge(int sourceId, int targetId, double weight) {
        Node source = nodes.get(sourceId);
        Node target = nodes.get(targetId);
        if (source != null && target != null) {
            source.addEdge(new Edge(target, weight));
        }
    }
}