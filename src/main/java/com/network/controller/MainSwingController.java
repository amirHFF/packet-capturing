package com.network.controller;

import com.network.PrivateRedirecor.CaptureProcess;
import org.pcap4j.core.PcapNetworkInterface;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class MainSwingController {
	private CaptureProcess captureProcess;

	private JPanel upperPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel middlePanel;
	private JPanel bottomPanel;
	private JButton capture, stop;
	private JList networkInterfaceList;
	private static JTextArea console;
	private JScrollPane scroll;

	public MainSwingController() {
		JFrame frame = new JFrame("private request Redirection");
		frame.setSize(new Dimension(1500, 1000));

		upperPanel = new JPanel();
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		bottomPanel = new JPanel();
		console = new JTextArea();
		scroll = new JScrollPane(console);
		scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		captureProcess = new CaptureProcess(console);

		capture = new JButton("capture");
		stop = new JButton("stop capture");
		capture.setPreferredSize(new Dimension(100, 40));
		stop.setPreferredSize(new Dimension(100, 40));
		bottomPanel.add(capture);
		bottomPanel.add(stop);

		networkInterfaceList = new JList(getNetworkInterfaceName());
		leftPanel.setLayout(new BorderLayout());
		leftPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 5));
		leftPanel.add(networkInterfaceList, BorderLayout.NORTH);
		networkInterfaceList.setFixedCellHeight(30);
		JPanel infoPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(infoPanel, BoxLayout.Y_AXIS);
		infoPanel.setLayout(boxLayout);
		infoPanel.setPreferredSize(new Dimension(400, 200));
//		infoPanel.setMaximumSize(new Dimension(300,1000));

		JLabel[] networkInfo = new JLabel[3];
		for (int i = 0; i < networkInfo.length; i++) {
			networkInfo[i] = new JLabel();
			infoPanel.add(networkInfo[i]);
		}

		leftPanel.add(infoPanel, BorderLayout.SOUTH);

		networkInterfaceList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				JList selectedList = (JList) e.getSource();
				PcapNetworkInterface selectedNif = captureProcess.selectNIF(selectedList.getSelectedIndex());
				networkInfo[0].setText("name : " + selectedNif.getName());
				networkInfo[1].setText("description : " + selectedNif.getDescription());
				try {
					networkInfo[2].setText("address : " + selectedNif.getAddresses().get(0).getAddress());
				} catch (IndexOutOfBoundsException exception) {
					networkInfo[2].setText("no address ");
				}

			}
		});
		JScrollBar scrollBar = scroll.getVerticalScrollBar();
		console.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				scrollBar.setValue(scrollBar.getMaximum());
			}

			@Override
			public void removeUpdate(DocumentEvent e) {

			}

			@Override
			public void changedUpdate(DocumentEvent e) {
			}
		});

		capture.addActionListener(e -> {
			PcapNetworkInterface selected = captureProcess.selectNIF(networkInterfaceList.getSelectedIndex());
			Thread thread = new Thread(() -> captureProcess.capturePacket(selected));
			thread.start();
		});
		stop.addActionListener(e -> {
			captureProcess.stopCapture();
		});

		frame.add(scroll, BorderLayout.CENTER);

		frame.add(upperPanel, BorderLayout.NORTH);
		frame.add(leftPanel, BorderLayout.WEST);
		frame.add(bottomPanel, BorderLayout.SOUTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.setVisible(true);
	}

	private String[] getNetworkInterfaceName() {
		return captureProcess.getInterfaceList().stream().map(PcapNetworkInterface::getName).toArray(String[]::new);
	}
}
