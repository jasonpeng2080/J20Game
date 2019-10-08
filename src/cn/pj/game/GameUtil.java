package cn.pj.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * 工具类
 * 将一些辅助性的工具方法通通放到GameUtil中，便于重复调用。
 */
public class GameUtil {
    //工具类最好将构造器私有化
    private  GameUtil(){
    }

    /**
     * 返回指定路径文件的图片对象
     * @param path
     * @return
     */
    public static Image getImage(String path){
        BufferedImage bi = null;
        try {
            //获得程序运行类加载器，加载资源的根目录，从而获得相对资源位置
            URL u = GameUtil.class.getClassLoader().getResource(path);
            //核心方法，帮助我们读取图片信息，并返回Image对象
            bi = ImageIO.read(u);
        } catch (IOException e){
            e.printStackTrace();
        }
        return bi;
    }
}
