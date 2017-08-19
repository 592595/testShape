package com.testShape;

//java.awt包,即java抽象窗口工具包
import java.awt.Graphics;//java.awt.Graphics是一个用来绘制2D图像必须导入的java包，提供对图形图像的像素，颜色的绘制

//导入边框布局
import java.awt.BorderLayout;
import java.awt.Color;

//尺寸
import java.awt.Dimension;

//流式布局
import java.awt.FlowLayout;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
//swing是图形可视包
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
//该类实现简单的双线斜面边框。
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class DrawBorder extends JFrame{

    public Color c=Color.BLACK;//默认颜色为黑色

    public JButton bt;//按钮属性，便于其他类访问

    public void initFrame(){

        //设置窗体的相关属性
        this.setSize(600,500);
        this.setTitle("pandade的画板");
        this.setDefaultCloseOperation(3);//直接关闭应用程序,System.exit(0)。一个main函数对应一整个程序。
        this.setLocationRelativeTo(null);//使窗口显示在屏幕中央
        ////////////////////////////////////////

        //窗体添加主面板
        JPanel panel=new JPanel();
        panel.setLayout(new BorderLayout());
        this.add(panel);

        JPanel panelcenter = new JPanel();
        panelcenter.setBackground(Color.white);
        panel.add(panelcenter);

        //主面板中添加左面板
        JPanel panelleft=new JPanel();
        panelleft.setPreferredSize(new Dimension(25,0));
        panelleft.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        panelleft.setBackground(new Color(233,235,238));
        panel.add(panelleft,BorderLayout.WEST);

        //面板中添加按钮
        ButtonGroup bg=new ButtonGroup();
        //画板中的按钮统一管理，互相排斥，保证只能选择一个按钮
        for(int i=0;i<16;i++){
            JRadioButton jrb=new JRadioButton();

            //给按钮添加图片，按钮选中、按下、默认、边框特效设置
            ImageIcon img1=new ImageIcon("images/draw"+i+".jpg");
            ImageIcon img2=new ImageIcon("images/draw"+i+"-1.jpg");
            ImageIcon img3=new ImageIcon("images/draw"+i+"-2.jpg");
            ImageIcon img4=new ImageIcon("images/draw"+i+"-3.jpg");

            jrb.setIcon(img1);//默认图片
            jrb.setRolloverIcon(img2);//鼠标停留在按钮上显示的图片
            jrb.setPressedIcon(img3);//鼠标按下按钮上显示的图片
            jrb.setSelectedIcon(img4);//鼠标选中按钮上显示的图片
            jrb.setBorder(null);//设置按钮没有边框

            //设置默认选中的按钮
            if(i==10){
                jrb.setSelected(true);
            }
            jrb.setActionCommand("pic"+i);////设置按钮名称

            bg.add(jrb);
            panelleft.add(jrb);
        }

        //主面板添加下方面板
        JPanel paneldown=new JPanel();
        paneldown.setPreferredSize(new Dimension(0,60));
        paneldown.setBackground(Color.GRAY);
        paneldown.setLayout(null);
        panel.add(paneldown,BorderLayout.SOUTH);

        //下方面板添加子面板
        JPanel paneldownchild=new JPanel();
        paneldownchild.setBackground(Color.cyan);
        paneldownchild.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        paneldownchild.setBounds(10,10,280,40);
        paneldown.add(paneldownchild);

        //按钮特效
        BevelBorder bb=new BevelBorder(0,Color.gray,Color.white);
        BevelBorder bb1=new BevelBorder(1,Color.gray,Color.white);

        //下方子面板paneldownchild中的左面板
        JPanel left=new JPanel();
        left.setBackground(Color.white);
        left.setLayout(null);
        left.setBorder(bb);
        left.setPreferredSize(new Dimension(40,40));

        //left面板中的两个颜色按钮
        bt=new JButton();
        bt.setBounds(5,5,20,20);
        bt.setBorder(bb1);
        bt.setBackground(Color.black);

        JButton bt1=new JButton();
        bt1.setBounds(15,15,20,20);
        bt1.setBorder(bb1);
        left.add(bt);
        left.add(bt1);

        //paneldownchild中的右面板
        JPanel right = new JPanel();
        right.setBackground(Color.BLUE);
        right.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        right.setPreferredSize(new Dimension(240,40));

        //添加左右面板
        paneldownchild.add(left);
        paneldownchild.add(right);

        ////给右面板的颜色按钮天添加监听器，注意传递this对象
        ButtonListener bl =new ButtonListener(this);
        Color []colors = {new Color(0,56,67),new Color(89,3,14),new Color(189,3,14)
                ,new Color(89,93,14),new Color(89,113,14),new Color(89,73,14)
                ,new Color(89,3,14),new Color(89,3,14),new Color(29,83,14)
                ,new Color(89,3,184),new Color(189,233,14),new Color(89,253,14)
                ,new Color(89,93,14),new Color(89,89,94),new Color(1,3,14)
                ,new Color(9,83,94),new Color(89,178,147),new Color(9,33,164)
                ,new Color(34,23,14),new Color(89,173,154),new Color(8,193,194)
                ,new Color(9,253,76),new Color(89,240,104),new Color(199,73,4)};

        //循环添加右面板中的颜色按钮
        for(int i=0;i<24;i++){
            JButton bt3=new JButton();
            Color c=new Color(i*10,30-i,i*7+50);//根据i产生不同的颜色
            bt3.setBackground(colors[i]);
            bt3.setPreferredSize(new Dimension(20,20));
            bt3.setBorder(bb);
            bt3.addActionListener(bl);
            right.add(bt3);
        }

        this.setVisible(true);

        //画笔必须在setVisible后才能拿
        Graphics g=panelcenter.getGraphics();//因为是在中间面板省绘制图形，所以画笔应该在中间画板上获取，那么同样，鼠标监听的应该也是中间画板</span>

        //传递画笔，按钮组管理对象，以及this对象
        DrawListener dl =new DrawListener(g,bg,this);

        // //添加普通鼠标监听器
        panelcenter.addMouseListener(dl);

        //添加鼠标拖动监听器
        panelcenter.addMouseMotionListener(dl);
    }
}