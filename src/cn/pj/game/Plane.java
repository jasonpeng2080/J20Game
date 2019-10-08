package cn.pj.game;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * 飞机类
 * 继承GameObject这个父类
 */
public class Plane extends GameObject {
    boolean left,up,right,down;

    boolean live = true;

    public void drawSelf(Graphics g) {
        if (live) {
            g.drawImage(img, (int) x, (int) y, null);
            //        x++;
            if (left) {
                x -= speed;
            }
            if (right) {
                x += speed;
            }
            if (up) {
                y -= speed;
            }
            if (down) {
                y += speed;
            }
        }
        else {
            System.out.println("Game Over");
        }
    }

    public Plane(Image img,double x,double y){
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed=3;
        this.width=img.getWidth((null));
        this.height=img.getHeight(null);
    }

    //按下某个键，增加相应的方向
    public void addDirector(KeyEvent e){
        switch (e.getKeyCode()){
//            case 37:
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case 38:
                up = true;
                break;
            case 39:
                right = true;
                break;
            case 40:
                down = true;
                break;
        }
    }

    //抬起某个键，取消相应的方向
    public void minusDirector(KeyEvent e){
        switch (e.getKeyCode()){
//            case 37:
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case 38:
                up = false;
                break;
            case 39:
                right = false;
                break;
            case 40:
                down = false;
                break;
        }
    }
}
