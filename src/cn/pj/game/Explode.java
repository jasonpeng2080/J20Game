package cn.pj.game;

import java.awt.*;

/**
 * ��ըЧ����
 */
public class Explode {
    double x,y;   //��ը����

    static Image[] imgs = new Image[16];  //��̬ ֻ����һ��ͼƬ
    static {
        for(int i=0;i<16;i++){
            imgs[i] = GameUtil.getImage("images/explode/e"+(i+1)+".gif");
            imgs[i].getWidth(null);
        }
    }

    //�ֲ�16�ű�ըЧ��ͼƬ
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
