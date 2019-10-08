package cn.pj.game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * ������
 * ��һЩ�����ԵĹ��߷���ͨͨ�ŵ�GameUtil�У������ظ����á�
 */
public class GameUtil {
    //��������ý�������˽�л�
    private  GameUtil(){
    }

    /**
     * ����ָ��·���ļ���ͼƬ����
     * @param path
     * @return
     */
    public static Image getImage(String path){
        BufferedImage bi = null;
        try {
            //��ó����������������������Դ�ĸ�Ŀ¼���Ӷ���������Դλ��
            URL u = GameUtil.class.getClassLoader().getResource(path);
            //���ķ������������Ƕ�ȡͼƬ��Ϣ��������Image����
            bi = ImageIO.read(u);
        } catch (IOException e){
            e.printStackTrace();
        }
        return bi;
    }
}
