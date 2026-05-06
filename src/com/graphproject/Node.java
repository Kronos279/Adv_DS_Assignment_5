package com.graphproject;

import java.util.ArrayList;
import java.util.List;

public class Node {
    public int id;
    public double x;
    public double y;
    public List<Edge> edges;

    public Node(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.edges = new ArrayList<>();
    }

    public void addEdge(Edge edge) {
        this.edges.add(edge);
    }
}
