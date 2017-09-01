package minidrawpad;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.MouseMotionAdapter;
import java.util.Random;
import java.util.Vector;
//绘图区类（各种图形的绘制和鼠标事件）
public class DrawArea extends JPanel {
    public static int type=1;
    Toolkit tool = getToolkit();//得到一个Tolkit类的对象（主要用于得到屏幕的大小）
    Dimension dim = tool.getScreenSize();//得到屏幕的大小 （返回Dimension对象）
    DrawPad drawpad =null;
    Drawing[] itemList =new Drawing[5000]; //绘制图形类
    public Vector<Ball> ballVector = new Vector<Ball>();
    private Image im;
    private int min=60;
    private int max=100;
    private int currentChoice = 3;//设置默认基本图形状态为随笔画
    int index = 0;//当前已经绘制的图形数目
    private Color color = Color.black;//当前画笔的颜色
    int R,G,B;//用来存放当前颜色的彩值
    int f1,f2;//用来存放当前字体的风格
    String stytle ;//存放当前字体
    float stroke = 1.0f;//设置画笔的粗细 ，默认的是 1.0
    int cap=BasicStroke.CAP_BUTT;
    int join=BasicStroke.JOIN_ROUND;
    float miterlimit=3.5f;
    float[] dash=new float[]{15,0,};
    float dash_phase=0f;
    private int ballsign=1;
  DrawArea(DrawPad dp) {
        //////弹性泡泡的初始化
        Random random = new Random();
        Color mycolor=new Color( random.nextInt(256), random.nextInt(256), random.nextInt(256));
        int s = random.nextInt(max)%(max-min+1) + min;
        Ball a=new Ball(5,50,1,1,mycolor,s);
        ballVector.add(a);
        mycolor=new Color( random.nextInt(256), random.nextInt(256), random.nextInt(256));
        s = random.nextInt(max)%(max-min+1) + min;
        Ball b=new Ball(100,10,1,1,mycolor,s);
        ballVector.add(b);

        drawpad = dp;

        // 把鼠标设置成十字形
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        // setCursor 设置鼠标的形状 ，getPredefinedCursor()返回一个具有指定类型的光标的对象

        setBackground(Color.white);// 设置绘制区的背景是白色
        addMouseListener(new MouseA());// 添加鼠标事件
        addMouseMotionListener(new MouseB());
        createNewitem();

    }
    public void gamePaint() {//前屏显示
        Graphics g;
        g = this.getGraphics();
        g.drawImage(im, 0, 0, null);//从后屏复制到前屏
        g.dispose();//释放当前屏幕Graphcis对象
    }
    public void gameRender() {//离屏绘制
        im = createImage(this.getWidth() , this.getHeight());//建立image对象
        Graphics dbg = im.getGraphics();//获取Graphics对象
        for(int i=0;i<ballVector.size();i++)
        {
            dbg.setColor(ballVector.get(i).getColor());
            dbg.fillOval(ballVector.get(i).getX(),ballVector.get(i).getY(),ballVector.get(i).getR(),ballVector.get(i).getR());
        }
    }
    public void gameUpdate() {
        for(int i=0;i<ballVector.size();i++)
        {
            ballVector.get(i).setX((int) (ballVector.get(i).getDx() + ballVector.get(i).getX()));
            ballVector.get(i).setY((int) (ballVector.get(i).getDy() + ballVector.get(i).getY()));

        }
        for(int i=0;i<ballVector.size();i++) {
            if (ballVector.get(i).getX() < 0) {
                Random random = new Random();
                Color mycolor=new Color( random.nextInt(256), random.nextInt(256), random.nextInt(256));
                ballVector.get(i).setColor(mycolor);
                ballVector.get(i).setDx(-(ballVector.get(i).getDx()));
            }
            if (ballVector.get(i).getX() + 30 > dim.width-70) {
                Random random = new Random();
                Color mycolor=new Color( random.nextInt(256), random.nextInt(256), random.nextInt(256));
                ballVector.get(i).setColor(mycolor);
                ballVector.get(i).setDx(-(ballVector.get(i).getDx()));
            }
            if (ballVector.get(i).getY() < 0) {
                Random random = new Random();
                Color mycolor=new Color( random.nextInt(256), random.nextInt(256), random.nextInt(256));
                ballVector.get(i).setColor(mycolor);
                ballVector.get(i).setDy(-(ballVector.get(i).getDy()));
            }
            if (ballVector.get(i).getY() + 30 > dim.height-280) {
                Random random = new Random();
                Color mycolor=new Color( random.nextInt(256), random.nextInt(256), random.nextInt(256));
                ballVector.get(i).setColor(mycolor);
                ballVector.get(i).setDy(-(ballVector.get(i).getDy()));
            }
        }
    }

    //定义铅笔
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        int  j = 0;
        while(j<=index)
        {
            draw(g2d,itemList[j]);
            j++;
        }

    }

    //将画笔传到个各类的子类中，用来完成各自的绘图
    void draw(Graphics2D g2d , Drawing i) {

        i.draw(g2d);
    }

    //新建一个图形的基本单元对象的程序段
    void createNewitem(){
        if(currentChoice == 8)//字体的输入光标相应的设置为文本输入格式
            setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
        else
            setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        switch(currentChoice){
            case 3: itemList[index] = new Pencil();break;
            case 4: itemList[index] = new Line();break;
            case 5: itemList[index] = new Rect();break;
            case 6: itemList[index] = new Oval();break;
            case 7: itemList[index] = new Rubber();break;
            case 8: itemList[index] = new Word();break;

    }
        itemList[index].type = currentChoice;
        itemList[index].R = R;
        itemList[index].G = G;
        itemList[index].B = B;
        itemList[index].stroke = stroke ;
        itemList[index].cap = cap;
        itemList[index].join = join;
        itemList[index].miterlimit = miterlimit;
        itemList[index].dash = dash;
        itemList[index].dash_phase = dash_phase;

    }
    //设置index的接口
    public void setIndex(int x){
        index = x;
    }

    //设置index的接口
    public int getIndex(){
        return index ;
    }

    //设置颜色的值
    public void setColor(Color color) {
        this.color = color;
    }

    //设置画笔粗细的接口
    public void setStroke(float f) {
        stroke = f;
    }

    //设置实线
    public void setSolid() {

        this.stroke=1f;
        this.cap=BasicStroke.CAP_BUTT;
        this.join=BasicStroke.JOIN_ROUND;
        this.miterlimit=3.5f;
        this.dash=new float[]{15,0};
        this.dash_phase=0f;

        itemList[index].stroke = stroke;
        itemList[index].cap = cap;
        itemList[index].join = join;
        itemList[index].miterlimit = miterlimit;
        itemList[index].dash = dash;
        itemList[index].dash_phase = dash_phase;


    }

    //设置虚线
    public void setDashed() {

        this.stroke=1f;
        this.cap=BasicStroke.CAP_BUTT;
        this.join=BasicStroke.JOIN_ROUND;
        this.miterlimit=3.5f;
        this.dash=new float[]{15,10};
        this.dash_phase=0f;
        //System.out.print("hhhhhh");

        itemList[index].stroke = stroke;
        itemList[index].cap = cap;
        itemList[index].join = join;
        itemList[index].miterlimit = miterlimit;
        itemList[index].dash = dash;
        itemList[index].dash_phase = dash_phase;


    }

    //设置点线
    public void setDotted() {

        this.stroke=1f;
        this.cap=BasicStroke.CAP_BUTT;
        this.join=BasicStroke.JOIN_ROUND;
        this.miterlimit=3.5f;
        this.dash=new float[]{3,3};
        this.dash_phase=0f;

        itemList[index].stroke = stroke;
        itemList[index].cap = cap;
        itemList[index].join = join;
        itemList[index].miterlimit = miterlimit;
        itemList[index].dash = dash;
        itemList[index].dash_phase = dash_phase;


    }

    //选择当前颜色
    public void chooseColor() {
        color = JColorChooser.showDialog(drawpad, "请选择颜色", color);
        try {
            R = color.getRed();
            G = color.getGreen();
            B = color.getBlue();
        } catch (Exception e) {
            R = 0;
            G = 0;
            B = 0;
        }
        itemList[index].R = R;
        itemList[index].G = G;
        itemList[index].B = B;
    }

    //画笔粗细的调整
    public void setStroke(){
        String input ;
        input = JOptionPane.showInputDialog("请输入画笔的粗细( >0 )");
        try {
            stroke = Float.parseFloat(input);

        } catch (Exception e) {
            stroke = 1.0f;

        }itemList[index].stroke = stroke;

    }

    //文字的输入
    public void setCurrentChoice(int i ){
        currentChoice = i;
    }


    // TODO 鼠标事件MouseA类继承了MouseAdapter
    //用来完成鼠标的响应事件的操作（鼠标的按下、释放、单击、移动、拖动、何时进入一个组件、何时退出、何时滚动鼠标滚轮 )
    class MouseA extends MouseAdapter {



        @Override
        public void mouseEntered(MouseEvent me) {
            // TODO 鼠标进入
            drawpad.setStratBar("鼠标进入在：["+me.getX()+" ,"+me.getY()+"]");
        }

        @Override
        public void mouseExited(MouseEvent me) {
            // TODO 鼠标退出
            drawpad.setStratBar("鼠标退出在：["+me.getX()+" ,"+me.getY()+"]");
        }

        @Override
        public void mousePressed(MouseEvent me) {
            // TODO 鼠标按下
            drawpad.setStratBar("鼠标按下在：["+me.getX()+" ,"+me.getY()+"]");//设置状态栏提示

            itemList[index].x1 = itemList[index].x2 = me.getX();
            itemList[index].y1 = itemList[index].y2 = me.getY();

            if(type==100&&me.getButton()==MouseEvent.BUTTON1) {
                Random random = new Random();
                Color mycolor=new Color( random.nextInt(256), random.nextInt(256), random.nextInt(256));
                int s = random.nextInt(max)%(max-min+1) + min;
                ballVector.add(new Ball(me.getX(),me.getY(),1,1,mycolor,s));
            }

            //如果当前选择为铅笔或橡皮擦 ，则进行下面的操作
            if(currentChoice == 3||currentChoice ==7){
                itemList[index].x1 = itemList[index].x2 = me.getX();
                itemList[index].y1 = itemList[index].y2 = me.getY();
                index++;
                createNewitem();//创建新的图形的基本单元对象
            }
            //如果选择图形的文字输入，则进行下面的操作
            if(currentChoice == 8){
                itemList[index].x1 = me.getX();
                itemList[index].y1 = me.getY();
                String input ;
                input = JOptionPane.showInputDialog("请输入你要写入的文字！");
                itemList[index].s1 = input;
                itemList[index].x2 = f1;
                itemList[index].y2 = f2;
                itemList[index].s2 = stytle;

                index++;
                currentChoice = 8;
                createNewitem();//创建新的图形的基本单元对象
                repaint();
            }

        }

        @Override
        public void mouseReleased(MouseEvent me) {
            // TODO 鼠标松开
            drawpad.setStratBar("鼠标松开在：["+me.getX()+" ,"+me.getY()+"]");
            if(currentChoice == 3||currentChoice ==7){
                itemList[index].x1 = me.getX();
                itemList[index].y1 = me.getY();
            }
            itemList[index].x2 = me.getX();
            itemList[index].y2 = me.getY();
            repaint();
            index++;
            createNewitem();//创建新的图形的基本单元对象
        }

    }

    // 鼠标事件MouseB继承了MouseMotionAdapter
    // 用来处理鼠标的滚动与拖动
    class MouseB extends MouseMotionAdapter {
        //鼠标的拖动
        public void mouseDragged(MouseEvent me) {
            drawpad.setStratBar("鼠标拖动在：["+me.getX()+" ,"+me.getY()+"]");
            if(currentChoice == 3||currentChoice ==7){
                itemList[index-1].x1 = itemList[index].x2 = itemList[index].x1 =me.getX();
                itemList[index-1].y1 = itemList[index].y2 = itemList[index].y1 = me.getY();
                index++;
                createNewitem();//创建新的图形的基本单元对象
            }
            else
            {
                itemList[index].x2 = me.getX();
                itemList[index].y2 = me.getY();
            }
            repaint();
        }

        //鼠标的移动
        public void mouseMoved(MouseEvent me) {
            drawpad.setStratBar("鼠标移动在：["+me.getX()+" ,"+me.getY()+"]");
        }
    }

}
