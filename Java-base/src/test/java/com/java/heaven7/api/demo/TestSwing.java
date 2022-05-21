package com.java.heaven7.api.demo;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TestSwing {

    public static void main(String[] args) {
        new MenuWindow();
    }

    static class MenuWindow extends JFrame implements ActionListener {

        public JTextField text;

        public MenuWindow() throws HeadlessException {
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            JMenuBar menuBar = new JMenuBar();
            setJMenuBar(menuBar);
            JMenu studyMenu = new JMenu("学习");
            JMenu workMenu = new JMenu("工作");
            menuBar.add(studyMenu);
            menuBar.add(workMenu);

            JMenuItem javaItem = new JMenuItem("Java 程序设计");
            JMenuItem projectItem = new JMenuItem("项目设计");
            //
            studyMenu.add(javaItem);
            workMenu.add(projectItem);
            studyMenu.addSeparator();

            text = new JTextField(25);
            getContentPane().add(text);
            setVisible(true);
            setSize(200,150);
            pack();

            javaItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    text.setText("已选择：Java 程序设计");
                }
            });
            projectItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    text.setText("已选择：项目设计");
                }
            });
        }
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
