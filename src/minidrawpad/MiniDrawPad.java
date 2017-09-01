package minidrawpad;

import javax.swing.UIManager;


//主函数类
public class MiniDrawPad {


    public static void main(String[] args) {

		try {//将界面设置为当前windows界面风格
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}
        DrawPad drawpad = new DrawPad("小小绘图板");
    }

}
