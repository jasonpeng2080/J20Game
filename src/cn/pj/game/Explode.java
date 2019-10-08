package cn.pj.game;

import java.awt.*;

/**
 * 爆炸效果类
 */
public class Explode {
    double x,y;   //爆炸坐标

    static Image[] imgs = new Image[16];  //静态 只加载一次图片
    static {
        for(int i=0;i<16;i++){
            imgs[i] = GameUtil.getImage("images/explode/e"+(i+1)+".gif");
            imgs[i].getWidth(null);
        }
    }

    //轮播16张爆炸效果图片
    int count;
    public void draw(Graphics g){
        if(count<=15){
            g.drawImage(imgs[count],(int)x,(int)y,null);
            count++;
        }
    }

    public Explode(double x,double y){
        this.x = x;
        this.y = y;
    }
}
