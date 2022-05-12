package com.demo.game;

public class Point {
    private float x, y;

    public Point(Point p){
        this.x = p.getX();
        this.y = p.getY();
    }

    public Point(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void add(float x, float y){
        this.x += x;
        this.y += y;
    }
    public float getY() { return y; }
    public float getX() { return x; }
    public void setY(float y) { this.y = y; }
    public void setX(float x) { this.x = x; }

    public void setPoint(Point point){
        x = point.getX();
        y = point.getY();
    }

    public void setPoint(float x, float y){
        this.x = x;
        this.y = y;
    }
}
