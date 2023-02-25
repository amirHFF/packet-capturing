package com.network.controller;

import com.network.PrivateRedirecor.CaptureProcess;
import org.pcap4j.core.PcapNetworkInterface;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultCaret;
import java.awt.*;

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
	private JScrollPane consoleScroller,tableScroller;
	private JTable table;

	public MainSwingController() {
		JFrame frame = new JFrame("private request Redirection");
		frame.setSize(new Dimension(1500, 1000));

		upperPanel = new JPanel();
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		bottomPanel = new JPanel();
		console = new JTextArea();
		table = new JTable();
		TableInitializer tableInitializer=new TableInitializer(table);
		table.addColumn(new TableColumn());
		((DefaultCaret)console.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		consoleScroller = new JScrollPane(console);
		tableScroller = new JScrollPane(table);
		bottomPanel.add(consoleScroller);
		console.setPreferredSize(new Dimension(frame.getWidth()-50,180));
		bottomPanel.setPreferredSize(new Dimension(frame.getWidth(),200));
		consoleScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tableScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		bottomPanel.add(consoleScroller);
		captureProcess = new CaptureProcess(console , tableInitializer);

		capture = new JButton("capture");
		stop = new JButton("stop capture");
		capture.setPreferredSize(new Dimension(120, 40));
		stop.setPreferredSize(new Dimension(120, 40));
		upperPanel.add(capture);
		upperPanel.add(stop);

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

		capture.addActionListener(e -> {
			PcapNetworkInterface selected = captureProcess.selectNIF(networkInterfaceList.getSelectedIndex());
			Thread thread = new Thread(() -> captureProcess.startCapturePacket(selected));
			thread.start();
		});
		stop.addActionListener(e -> {
			captureProcess.stopCapturePacket();
		});

		frame.add(bottomPanel, BorderLayout.SOUTH);

		frame.add(upperPanel, BorderLayout.NORTH);
		frame.add(leftPanel, BorderLayout.WEST);
		frame.add(tableScroller, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		frame.pack();
		frame.setVisible(true);
	}

	private String[] getNetworkInterfaceName() {
		return captureProcess.getInterfaceList().stream().map(PcapNetworkInterface::getName).toArray(String[]::new);
	}
}
