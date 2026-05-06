package com.graphproject;

public class Edge {
    public Node target;
    public double weight;

    public Edge(Node target, double weight) {
        this.target = target;
        this.weight = weight;
    }
}
