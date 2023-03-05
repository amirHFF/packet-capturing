package com.network.controller;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GlobalActionListener implements ActionListener {
    private JComponent component;

    public GlobalActionListener(JComponent component) {
        this.component = component;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
            if (actionEvent.getSource()==component){
            System.out.println("thats worked");
        }
    }
}
