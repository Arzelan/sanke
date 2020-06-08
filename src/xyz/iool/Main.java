package xyz.iool;

import javax.swing.*;

/**
 * @author zxn
 */
public class Main {

    public static void main(String[] args) {
        JFrame jFrame = new JFrame("贪吃蛇");
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jFrame.setBounds(40, 40, 900, 720);
        jFrame.setResizable(false);
        jFrame.setVisible(true);

        jFrame.add(new GamePanel());

    }
}
