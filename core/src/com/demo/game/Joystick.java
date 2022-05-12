package com.demo.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Joystick {
    public Player player;
    private final Texture circleImg, stickImg;
    public Circle circleBounds, stickBounds;
    public float rcircle, rstick;
    public Point direction;
    private int pointer = -1;
    private float length;
    private float dx;
    private float dy;
    public float dist;

    public Joystick(Texture cimg, Texture simg, Point point, float size, Player p) {
        circleImg = cimg;
        stickImg = simg;
        rcircle = size / 2;
        rstick = rcircle / 2;
        player = p;
        circleBounds = new Circle(point, rcircle);
        stickBounds = new Circle(point, rstick);
        direction = new Point(0, 0);
    }

    public void draw(SpriteBatch batch){
        batch.draw(circleImg,circleBounds.pos.getX() - rcircle,circleBounds.pos.getY() - rcircle,rcircle * 2,rcircle * 2);
        batch.draw(stickImg,stickBounds.pos.getX() - rstick,stickBounds.pos.getY() - rstick,rstick * 2,rstick * 2);
    }

    public Point getDirection() {
        return direction;
    }

    public void update(float x, float y, boolean isDownTouch, int pointer){
        if(pointer == this.pointer) {
            dx = circleBounds.pos.getX() - x;
            dy = circleBounds.pos.getY() - y;
        }
        length = (float) Math.sqrt(dx * dx + dy * dy);
        if(isDownTouch && circleBounds.isContains(x, y) && this.pointer == -1) this.pointer=pointer;
        if(circleBounds.Overlaps(stickBounds)&&isDownTouch&&this.pointer == pointer) {
            player.isMove = true;
            atControl(x, y);
        }
        if(!circleBounds.isContains(stickBounds.pos)){
            stickBounds.pos.setX(-rcircle/ length * dx +circleBounds.pos.getX());
            stickBounds.pos.setY(-rcircle/ length * dy +circleBounds.pos.getY());
        }
        if(!isDownTouch && this.pointer == pointer){
            player.isMove = false;
            stickBounds.pos.setPoint(circleBounds.pos);
            this.pointer = -1;
        }
    }

    public void atControl(float x, float y){
        stickBounds.pos.setPoint(x, y);
        if(player.speed < 3.5){
            player.speed += 0.0025;
        }
        float dx = circleBounds.pos.getX() - x;
        float dy = circleBounds.pos.getY() - y;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        this.dist = dist;
        if(dist != 0){
            direction.setPoint(-(dx / dist), -(dy / dist));
            if(direction.getY() < 0){
                player.angle = (float) -Math.toDegrees(Math.acos(direction.getX()));
            }
            else{player.angle = (float) Math.toDegrees(Math.acos(direction.getX()));}
        }
    }
}