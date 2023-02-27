package com.network.controller;

import javax.swing.*;
import java.awt.*;

public class PacketDialog {

    private JDialog dialog;
    private String packetInfo;
    private JTextArea textArea;

    public PacketDialog(JFrame frame , String packetInfo) {
        dialog=new JDialog(frame,"Packet Info" , true);
        this.packetInfo=packetInfo;

        textArea=new JTextArea();
        JScrollPane scrollPane=new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        textArea.setText(packetInfo);
        dialog.setLayout( new FlowLayout() );

        dialog.add(scrollPane);
        dialog.setSize(450,800);
        dialog.pack();
        dialog.setVisible(true);
        dialog.setLocationRelativeTo(frame);
    }

}
