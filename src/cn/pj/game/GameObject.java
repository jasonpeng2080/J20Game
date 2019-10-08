package cn.pj.game;

import java.awt.*;

/**
 * ��Ϸ���ж���ĸ��ࣨ�ɻ����ڵ��ȣ�
 * ���������еĶ���(�ɻ����ڵ��ȵ�)���кܶ๲�ԣ���ͼƬ��������λ�á������ٶȡ���Ⱥ͸߶ȡ�
 */

public class GameObject {
     Image img;     //�������Ӧ��ͼƬ����
      double x,y;   //�����������
      int speed;    //������������ٶ�
      int width,height; //���������ھ�������Ŀ�Ⱥ͸߶�

    /**
     * ��ô�����Ʊ�����
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
     * �����������ڵľ��Ρ����ں�������ײ���
     * @return
     */
    public Rectangle getRect(){
        return new Rectangle((int)x,(int)y,width,height);
    }
}
