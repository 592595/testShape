package com.testShape;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

//按钮监听类，实现ActionListenerde接口
public class ButtonListener implements ActionListener{


    public DrawBorder db;

    //构造函数
    public ButtonListener(DrawBorder db1) {
        db=db1;
    }

    //监听具体实现
    public void actionPerformed(ActionEvent e) {

		//拿到被选中按钮的对象
        JButton bt =(JButton)e.getSource();

        //拿到被选中按钮的背景颜色
        Color c= bt.getBackground();

        //把背景颜色复制给DrawBorder中的颜色属性
        db.c=c;

        //把左面板中的按钮颜色设置成选中按钮的背景颜色
        db.bt.setBackground(c);

    }

}