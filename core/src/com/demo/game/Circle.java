package com.demo.game;

public class Circle {

    public float R;
    public Point pos;

    public Circle(Point pos, float R){
        this.pos = new Point(pos);
        this.R = R;
    }

    public boolean isContains(Point point){
        float dx = pos.getX() - point.getX();
        float dy = pos.getY() - point.getY();
        return dx * dx + dy * dy <= R * R;
    }

    public boolean isContains(float x, float y){
        float dx = pos.getX() - x;
        float dy = pos.getY() - y;
        return dx * dx + dy * dy <= R * R;
    }

    public boolean Overlaps(Circle c){
        float dx = pos.getX() - c.pos.getX();
        float dy = pos.getY() - c.pos.getY();
        float d = dx * dx + dy * dy;
        float sumR = c.R + R;
        return d < sumR * sumR;
    }
}
