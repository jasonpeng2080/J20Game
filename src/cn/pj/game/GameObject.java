package cn.pj.game;

import java.awt.*;

/**
 * 游戏所有对象的父类（飞机、炮弹等）
 * 窗口中所有的对象(飞机、炮弹等等)都有很多共性：“图片对象、坐标位置、运行速度、宽度和高度”
 */

public class GameObject {
     Image img;     //该物体对应的图片对象
      double x,y;   //该物体的坐标
      int speed;    //该物体的运行速度
      int width,height; //该物体所在矩形区域的宽度和高度

    /**
     * 怎么样绘制本对象
     * @param g
     */
    public  void  drawSelf(Graphics g)
    {
        g.drawImage(img,(int)x,(int)y,null);
    }

    public GameObject(Image img, double x, double y, int speed, int width, int height) {
        this.img = img;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.width = width;
        this.height = height;
    }

    public GameObject(Image img, double x, double y) {
        this.img = img;
        this.x=x;
        this.y=y;
        if(img!=null){
            this.width = img.getWidth(null);
            this.height = img.getHeight(null);
        }
    }

    public GameObject(){
    }

    /**
     * 返回物体所在的矩形。便于后续的碰撞检测
     * @return
     */
    public Rectangle getRect(){
        return new Rectangle((int)x,(int)y,width,height);
    }
}
