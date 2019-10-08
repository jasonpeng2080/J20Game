package cn.pj.game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;

/**
 * 飞机游戏的主窗口
 */

//JFrame解决了部分闪烁，但不能完全解决
//public class MyGameFrame extends JFrame {
public class MyGameFrame extends Frame {
    //将背景图片与飞机图片定义为成员变量
    Image planeImg = GameUtil.getImage("images/plane.png");
    Image bgImg = GameUtil.getImage("images/bg.jpg");

//    int planeX = 250,planeY=250;
    Plane plane = new Plane(planeImg,250,250);

//    Shell shell = new Shell();
    Shell[] shells = new Shell[50];

    Explode bao;    //创建爆炸对象
    Date startTime=new Date();//游戏开始时间
    Date endTime;  //游戏结束时间
    int period; //游戏持续时间

    //paint方法作用是：画出整个窗口及内部内容。被系统自动调用。
    @Override
    public void paint(Graphics g) { //自动被调用。g相当于一只画笔
        Color c=g.getColor();
        super.paint(g); // 添加上这行代码, 表示在原有基础上重绘.这个必须加上，范例代码缺乏

//        Color c=g.getColor();
//        Font f=g.getFont();
//        g.setColor(Color.BLUE);
//        g.drawLine(100,100,300,300);
//        g.drawRect(100,100,300,300);
//        g.drawOval(100,100,300,300);
//        g.fillRect(100,100,40,40);
//        g.setColor(Color.RED);
//        g.setFont(new Font("宋体",Font.BOLD,50));
//        g.drawString("Good",200,200);

        g.drawImage(bgImg,0,0,null);
//        g.drawImage(plane,planeX,planeY,null);
//        planeX++;
//        y++;
        plane.drawSelf(g);  //画飞机

//        g.setColor(c);
//        g.setFont(f);

        //画出所有的炮弹
        for(int i=0;i<shells.length;i++){
            shells[i].draw(g);

            boolean peng = shells[i].getRect().intersects(plane.getRect());

            //飞机和炮弹的碰撞检测
            if(peng) {
                plane.live = false;
                if (bao == null) {
                    bao = new Explode(plane.x, plane.y);

                    //计算持续时间
                    endTime = new Date();
                    period = (int) ((endTime.getTime() - startTime.getTime()) / 1000);
                }
                bao.draw(g);
            }

            //游戏计时功能，给出文字提示
                if(!plane.live) {
                    Font f = new Font("宋体",Font.BOLD,50);
                    g.setFont(f);
                    g.setColor(Color.red);
                    g.drawString("时间：" + period + "秒", (int) plane.x, (int) plane.y);
                }
        }
        g.setColor(c);
    }

    /**
     * 定义一个重画窗口的线程类，是一个内部类
     */
    class PaintThread extends  Thread{
        @Override
        public  void  run(){
            while (true){
//                System.out.println("窗口画一次");
                repaint();
                try{
                    Thread.sleep(40);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    //定义键盘监听的内部类
    class  KeyMonitor extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
//            super.keyPressed(e);
//            System.out.println("按下" + e.getKeyCode());
            plane.addDirector(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
//            super.keyReleased(e);
//            System.out.println("抬起" + e.getKeyCode());
            plane.minusDirector(e);
        }
    }

    /**
     * 初始化窗口
     */
    public void launchFrame(){
        this.setTitle("Sunny is a small pig");  //在游戏窗口打印标题
        this.setVisible(true);  //窗口默认不可见，设为可见
//        this.setSize(500,500);    //窗口大小：宽度500，高度500
        this.setSize(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);//窗口大小：转为用常量类来定义，便于以后变更
        this.setLocation(300,300);  //窗口左上角顶点的坐标位置

        //增加关闭窗口监听，这样用户点击右上角关闭图标，可以关闭游戏程序
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //super.windowClosed(e);  //自动带出来的，也可执行
                System.exit(0);     //按例题手写的
            }
        });

        new PaintThread().start();//启动重画的线程
        addKeyListener(new KeyMonitor()); //给窗口增加键盘的监听

        //初始化50个炮弹
        for(int i=0;i<shells.length;i++){
            shells[i]=new Shell();
        }
    }

    public static void main(String[] args){
        MyGameFrame f = new MyGameFrame();
        f.launchFrame();
    }

    /**
     * 双缓存解决画面闪烁问题
     * 双缓冲即在内存中创建一个与屏幕绘图区域一致的对象，先将图形绘制到内存中的这个对象上，再一次性将这个对象上的图形拷贝到屏幕上，这样能大大加快绘图的速度。
     * “双缓冲技术”的绘图过程如下：
     *       1、在内存中创建与画布一致的缓冲区
     *       2、在缓冲区画图
     *       3、将缓冲区位图拷贝到当前画布上
     *       4、释放内存缓冲区
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
