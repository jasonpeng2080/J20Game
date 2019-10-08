package cn.pj.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

/**
 * �ɻ���Ϸ��������
 */

//JFrame����˲�����˸����������ȫ���
//public class MyGameFrame extends JFrame {
public class MyGameFrame extends Frame {
    //������ͼƬ��ɻ�ͼƬ����Ϊ��Ա����
    Image planeImg = GameUtil.getImage("images/plane.png");
    Image bgImg = GameUtil.getImage("images/bg.jpg");

//    int planeX = 250,planeY=250;
    Plane plane = new Plane(planeImg,250,250);

//    Shell shell = new Shell();
    Shell[] shells = new Shell[50];

    Explode bao;    //������ը����
    Date startTime=new Date();//��Ϸ��ʼʱ��
    Date endTime;  //��Ϸ����ʱ��
    int period; //��Ϸ����ʱ��

    //paint���������ǣ������������ڼ��ڲ����ݡ���ϵͳ�Զ����á�
    @Override
    public void paint(Graphics g) { //�Զ������á�g�൱��һֻ����
        Color c=g.getColor();
        super.paint(g); // ��������д���, ��ʾ��ԭ�л������ػ�.���������ϣ���������ȱ��

//        Color c=g.getColor();
//        Font f=g.getFont();
//        g.setColor(Color.BLUE);
//        g.drawLine(100,100,300,300);
//        g.drawRect(100,100,300,300);
//        g.drawOval(100,100,300,300);
//        g.fillRect(100,100,40,40);
//        g.setColor(Color.RED);
//        g.setFont(new Font("����",Font.BOLD,50));
//        g.drawString("Good",200,200);

        g.drawImage(bgImg,0,0,null);
//        g.drawImage(plane,planeX,planeY,null);
//        planeX++;
//        y++;
        plane.drawSelf(g);  //���ɻ�

//        g.setColor(c);
//        g.setFont(f);

        //�������е��ڵ�
        for(int i=0;i<shells.length;i++){
            shells[i].draw(g);

            boolean peng = shells[i].getRect().intersects(plane.getRect());

            //�ɻ����ڵ�����ײ���
            if(peng) {
                plane.live = false;
                if (bao == null) {
                    bao = new Explode(plane.x, plane.y);

                    //�������ʱ��
                    endTime = new Date();
                    period = (int) ((endTime.getTime() - startTime.getTime()) / 1000);
                }
                bao.draw(g);
            }

            //��Ϸ��ʱ���ܣ�����������ʾ
                if(!plane.live) {
                    Font f = new Font("����",Font.BOLD,50);
                    g.setFont(f);
                    g.setColor(Color.red);
                    g.drawString("ʱ�䣺" + period + "��", (int) plane.x, (int) plane.y);
                }
        }
        g.setColor(c);
    }

    /**
     * ����һ���ػ����ڵ��߳��࣬��һ���ڲ���
     */
    class PaintThread extends  Thread{
        @Override
        public  void  run(){
            while (true){
//                System.out.println("���ڻ�һ��");
                repaint();
                try{
                    Thread.sleep(40);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    //������̼������ڲ���
    class  KeyMonitor extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
//            super.keyPressed(e);
//            System.out.println("����" + e.getKeyCode());
            plane.addDirector(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
//            super.keyReleased(e);
//            System.out.println("̧��" + e.getKeyCode());
            plane.minusDirector(e);
        }
    }

    /**
     * ��ʼ������
     */
    public void launchFrame(){
        this.setTitle("Sunny is a small pig");  //����Ϸ���ڴ�ӡ����
        this.setVisible(true);  //����Ĭ�ϲ��ɼ�����Ϊ�ɼ�
//        this.setSize(500,500);    //���ڴ�С�����500���߶�500
        this.setSize(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);//���ڴ�С��תΪ�ó����������壬�����Ժ���
        this.setLocation(300,300);  //�������ϽǶ��������λ��

        //���ӹرմ��ڼ����������û�������Ͻǹر�ͼ�꣬���Թر���Ϸ����
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //super.windowClosed(e);  //�Զ��������ģ�Ҳ��ִ��
                System.exit(0);     //��������д��
            }
        });

        new PaintThread().start();//�����ػ����߳�
        addKeyListener(new KeyMonitor()); //���������Ӽ��̵ļ���

        //��ʼ��50���ڵ�
        for(int i=0;i<shells.length;i++){
            shells[i]=new Shell();
        }
    }

    public static void main(String[] args){
        MyGameFrame f = new MyGameFrame();
        f.launchFrame();
    }

    /**
     * ˫������������˸����
     * ˫���弴���ڴ��д���һ������Ļ��ͼ����һ�µĶ����Ƚ�ͼ�λ��Ƶ��ڴ��е���������ϣ���һ���Խ���������ϵ�ͼ�ο�������Ļ�ϣ������ܴ��ӿ��ͼ���ٶȡ�
     * ��˫���弼�����Ļ�ͼ�������£�
     *       1�����ڴ��д����뻭��һ�µĻ�����
     *       2���ڻ�������ͼ
     *       3����������λͼ��������ǰ������
     *       4���ͷ��ڴ滺����
     */
    private Image offScreenImage = null;

    public void update(Graphics g){
        if(offScreenImage==null)
            offScreenImage=this.createImage(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);

        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage,0,0,null);
    }
}
