package minidrawpad;

import java.awt.*;
import java.awt.event.*;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.swing.*;

// 主界面类
public class DrawPad extends JFrame implements ActionListener  ,Runnable{

    private static final long serialVersionUID = -2551980583852173918L;
    private JToolBar buttonpanel;//定义按钮面板
    private JMenuBar bar ;//定义菜单条
    private Thread gamethread;
    private JMenu file,color,stroke,type,ball;//定义菜单
    private JMenuItem newfile,openfile,savefile,exit;//file 菜单中的菜单项
    private JMenuItem colorchoice,strokeitem;
    private JMenuItem typeitem0,typeitem1,typeitem2;
    private JMenuItem ballitem0,ballitem1;
    private JLabel startbar;//状态栏
    private DrawArea drawarea;//画布类的定义
    private FileClass fileclass ;//文件对象
    //定义工具栏图标的名称
    private String names[] = {"newfile","openfile","savefile","pen","line"
            ,"rect","oval","rubber","color"
            ,"stroke","word"};//定义工具栏图标的名称
    private Icon icons[];//定义图象数组

    private String tiptext[] = {//这里是鼠标移到相应的按钮上给出相应的提示
            "新建","打开","保存","铅笔","直线"
            ,"矩形","椭圆", "橡皮擦","颜色","选择线条的粗细","文字的输入"};
    JButton button[];//定义工具条中的按钮组

    public DrawPad(String string) {
        // TODO 主界面的构造函数
        super(string);
        //菜单的初始化
        file = new JMenu("文件");
        color = new JMenu("颜色");
        stroke = new JMenu("画笔");
        type = new JMenu("类型");
        ball = new JMenu("弹泡泡");
        bar = new JMenuBar();//菜单条的初始化

        //菜单条添加菜单
        bar.add(file);
        bar.add(color);
        bar.add(stroke);
        bar.add(type);
        bar.add(ball);

        //界面中添加菜单条
        setJMenuBar(bar);

        //菜单中添加快捷键
        file.setMnemonic('F');//既是ALT+“F”
        color.setMnemonic('C');//既是ALT+“C”
        stroke.setMnemonic('S');//既是ALT+“S”

        //File 菜单项的初始化
        try {
            Reader reader = new InputStreamReader(getClass().getResourceAsStream("/icon"));//读取文件以类路径为基准
        } catch (Exception e) {
            // TODO 文件读取错误
            JOptionPane.showMessageDialog(this,"图片读取错误！","错误",JOptionPane.ERROR_MESSAGE);
        }
        newfile = new JMenuItem("新建");
        openfile = new JMenuItem("打开");
        savefile = new JMenuItem("保存");
        exit = new JMenuItem("退出");

        //File 菜单中添加菜单项

        file.add(savefile);
        file.add(openfile);
        file.add(newfile);
        file.add(exit);

        //File 菜单项添加快捷键
        newfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
        openfile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
        savefile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_MASK));

        //File 菜单项的注册监听
        newfile.addActionListener(this);
        openfile.addActionListener(this);
        savefile.addActionListener(this);
        exit.addActionListener(this);

        //Color 菜单项的初始化
        colorchoice = new JMenuItem("调色板");
        colorchoice.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,InputEvent.CTRL_MASK));
        colorchoice.addActionListener(this);
        color.add(colorchoice);


        //Stroke 菜单项的初始化
        strokeitem = new JMenuItem("设置画笔");
        strokeitem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,InputEvent.CTRL_MASK));
        stroke.add(strokeitem);
        strokeitem.addActionListener(this);

        //type 菜单项的初始化
        typeitem0 = new JMenuItem("实线");
        typeitem1 = new JMenuItem("虚线");
        typeitem2 = new JMenuItem("点线");

        //type 菜单中添加菜单项
        type.add(typeitem0);
        type.add(typeitem1);
        type.add(typeitem2);

        //type 菜单项的注册监听
        typeitem0.addActionListener(this);
        typeitem1.addActionListener(this);
        typeitem2.addActionListener(this);

        //ball 菜单项的初始化
        ballitem0 = new JMenuItem("开始");

        //ball 菜单中添加菜单项
        ball.add(ballitem0);

        //ball 菜单项的注册监听
        ballitem0.addActionListener(this);

        //工具栏的初始化
        buttonpanel = new JToolBar( JToolBar.HORIZONTAL);
        icons = new ImageIcon[names.length];
        button = new JButton[names.length];
        for(int i = 0 ;i<names.length;i++)
        {
            icons[i] = new ImageIcon(getClass().getResource("/icon/"+names[i]+".png" +
                    ""));//获得图片（以类路径为基准）
            button[i] = new JButton("",icons[i]);//创建工具条中的按钮
            button[i].setToolTipText(tiptext[i]);//这里是鼠标移到相应的按钮上给出相应的提示
            buttonpanel.add(button[i]);
            button[i].setBackground(Color.white);
            if(i<3)button[i].addActionListener(this);
            else if(i<=16) button[i].addActionListener(this);
        }

        //状态栏的初始化
        startbar = new JLabel("绘图板");


        //绘画区的初始化
        drawarea = new DrawArea(this);
        fileclass = new FileClass(this,drawarea);



        Container con = getContentPane();//得到内容面板
        con.add(buttonpanel, BorderLayout.NORTH);
        con.add(drawarea,BorderLayout.CENTER);
        con.add(startbar,BorderLayout.SOUTH);
        Toolkit tool = getToolkit();//得到一个Tolkit类的对象（主要用于得到屏幕的大小）
        Dimension dim = tool.getScreenSize();//得到屏幕的大小 （返回Dimension对象）
        setBounds(40,40,dim.width-70,dim.height-100);
        setVisible(true);
        validate();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //设置状态栏显示的字符
    public void setStratBar(String s) {
        startbar.setText(s);
    }

    public void actionPerformed(ActionEvent e) {
        // TODO 事件的处理
        for(int i = 3; i<=7;i++)
        {
            if(e.getSource() ==button[i])
            {
                drawarea.setCurrentChoice(i);
                drawarea.createNewitem();
                drawarea.repaint();
            }

        }
        if(e.getSource() == newfile||e.getSource() == button[0])//新建
        {fileclass.newFile();}
        else if(e.getSource() == openfile||e.getSource() == button[1])//打开
        {fileclass.openFile();}
        else if(e.getSource() == savefile||e.getSource() == button[2])//保存
        {fileclass.saveFile();}
        else if(e.getSource() == exit)//退出程序
        {System.exit(0);}

        else if(e.getSource() == typeitem0)//实线
        { drawarea.setSolid(); }
        else if(e.getSource() == typeitem1)//虚线
        { drawarea.setDashed(); }
        else if(e.getSource() == typeitem2)//点线
        { drawarea.setDotted(); }

        else if(e.getSource()==ballitem0)//开始弹泡泡
        {
          gamethread = new Thread(this);
          gamethread.start();
          DrawArea.type=100;
        }
        else if(e.getSource() == colorchoice||e.getSource() == button[8])//弹出颜色对话框
        {
            drawarea.chooseColor();//颜色的选择
        }
        else if(e.getSource() == button[9]||e.getSource()==strokeitem)//画笔粗细
        {
            drawarea.setStroke();//画笔粗细的调整
        }
        else if(e.getSource() == button[10])//添加文字
        {   JOptionPane.showMessageDialog(null, "请单击画板以确定输入文字的位置！","提示"
                ,JOptionPane.INFORMATION_MESSAGE);
            drawarea.setCurrentChoice(8);
            drawarea.createNewitem();
            drawarea.repaint();
        }

    }


    @Override
    public void run() {
        while (true) {
            //System.out.print("000");
            drawarea.gameUpdate();
            drawarea.gameRender();
            drawarea.gamePaint();
        }
    }
}


