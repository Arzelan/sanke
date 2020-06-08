package xyz.iool;

import jdk.jfr.DataAmount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Time;
import java.util.Random;

/**
 * @author zxn
 */
public class GamePanel extends JPanel implements KeyListener , ActionListener {
    int length;
    int[] snakeX = new int[600]; //蛇x坐标
    int[] snakeY = new int[500];//蛇Y 坐标
    String fx;//初始方向
    boolean isStart = false;
    boolean isFail = false;

    //积分
    int score;
    //食物的坐标
    int foodx;
    int foody;
    Random random = new Random();

    //定时器
    Timer timer = new Timer(100,this);


    //构造器
    public GamePanel() {
        init();
        //获取焦点和键盘事件
        this.setFocusable(true);
        this.addKeyListener(this);
    }

    //初始化方法
    public void init() {
        length = 3;
        snakeX[0] = 100;snakeY[0] = 100;//头的位置
        snakeX[1] = 75;snakeY[1] = 100;//第一个身体的位置
        snakeX[2] = 50;snakeY[2] = 100;//第二个身体的位置
        fx = "R";//初始向右
        timer.start();

        foodx = 25 + 25*random.nextInt(34) ;
        foody = 75 + 25*random.nextInt(24) ;
        score = 0;

    }

    @Override
    protected void paintComponent(Graphics g) {
        //清屏
        super.paintComponent(g);
        this.setBackground(Color.WHITE);

        //头部广告
        Data.header.paintIcon(this, g, 25, 11);
        //游戏界面
        g.fillRect(25, 75, 850, 600);

        //画积分
        g.setColor(Color.WHITE);
        g.setFont(new Font("微软雅黑", Font.BOLD, 18));
        g.drawString("长度"+length,750,35);
        g.drawString("分数"+score,750,55);

        //画蛇
//        if (fx.equals("r")) {
//            Data.right.paintIcon(this,g,snakeX[0],snakeY[0]);
//        }
        switch (fx) {
            case "R" : Data.right.paintIcon(this,g,snakeX[0],snakeY[0]);break;
            case "L" : Data.left.paintIcon(this,g,snakeX[0],snakeY[0]); break;
            case "U" : Data.up.paintIcon(this,g,snakeX[0],snakeY[0]); break;
            case "D" : Data.down.paintIcon(this,g,snakeX[0],snakeY[0]); break;
        }

        for (int i = 1; i < length;i ++) {
            Data.body.paintIcon(this,g,snakeX[i],snakeY[i]);
        }
        //画食物
        Data.food.paintIcon(this,g,foodx,foody);

        //游戏状态
        if (isStart == false) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("按下空格开始游戏",300,300);
        }

        if (isFail) {
            g.setColor(Color.RED);
            g.setFont(new Font("微软雅黑", Font.BOLD, 40));
            g.drawString("游戏失败，按空格重新开始",300,300);
        }
    }
    //键盘监听器
    @Override
    public void keyTyped(KeyEvent e) {

    }
    //键盘事件
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SPACE) {
            if (isFail) {
                isFail = false;
                init();
            }
            isStart = !isStart;//取反
            repaint();
        }
        if (keyCode == KeyEvent.VK_UP) {
            if (fx.equals("D")) {
                fx = "D";
            }else{
                fx = "U";
            }
        }else if(keyCode == KeyEvent.VK_DOWN){
            if (fx.equals("U")) {
                fx = "U";
            }else{
                fx = "D";
            }
        }else if(keyCode == KeyEvent.VK_LEFT){
            if (fx.equals("R")) { fx = "R";
            }else{
            fx = "L";}
        }else if(keyCode == KeyEvent.VK_RIGHT){
            if (fx.equals("L")) {
                fx = "L";
            }else {
            fx = "R";}
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
    //事件监听-通过固定事件来刷新
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isStart && isFail == false) {
            //吃食物
            if (snakeX[0] == foodx && snakeY[0] == foody) {
                length++;
                score = score + 10;
                foodx = 25+ 25*random.nextInt(34) ;
                foody = 75+ 25*random.nextInt(24) ;
            }
            //移动
            for (int i = length - 1; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
            }
            //走向
            if (fx.equals("R")) {
                snakeX[0] = snakeX[0] + 25;
                if (snakeX[0] > 850) {  //边界判断
                    snakeX[0] = 25;
                }
            } else if (fx.equals("L")) {
                snakeX[0] = snakeX[0] - 25;
                if (snakeX[0] < 25) {
                    snakeX[0] = 850;
                }
            }else if (fx.equals("U")) {
                snakeY[0] = snakeY[0] - 25;
                if (snakeY[0] < 75) {
                    snakeY[0] = 650;
                }
            }else if (fx.equals("D")) {
                snakeY[0] = snakeY[0] + 25;
                if (snakeY[0] >650 ) {
                    snakeY[0] = 75;
                }
            }
            //失败判断
            for (int i= 1; i < length ; i++){
                if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                    isFail = true;
                }
            }
            repaint();
        }
        timer.start();
    }
}

