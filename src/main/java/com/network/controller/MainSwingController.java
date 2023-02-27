package com.network.controller;

import com.network.PrivateRedirecor.CaptureProcess;
import com.network.model.PacketSummaryModel;
import org.pcap4j.core.PcapNetworkInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainSwingController extends JFrame {
	private static JTextArea console;
	private CaptureProcess captureProcess;
	private JPanel upperPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel middlePanel;
	private JPanel bottomPanel;
	private JButton capture, stop , cleanConsoleButton ,refreshAllButton;
	private JList networkInterfaceList;
	private JScrollPane consoleScroller, tableScroller;
	private JTable table;
	private ImageIcon startIcon ,stopIcon ,cleanConsoleIcon ,refreshAllIcon;
	public static JLabel totalTrafficUsedLabel=new JLabel();
	public JFrame frame;

	private static String totalTrafficUsage;

	public MainSwingController() {
		frame=this;
		setTitle("private request Redirection");
		setSize(new Dimension(1900, 1200));

		upperPanel = new JPanel();
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		bottomPanel = new JPanel();
		console = new JTextArea();
		table = new JTable();
		TableInitializer tableInitializer = new TableInitializer(table);
		table.addColumn(new TableColumn());
		((DefaultCaret) console.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		consoleScroller = new JScrollPane(console);
		tableScroller = new JScrollPane(table);
//		bottomPanel.add(consoleScroller);
		console.setPreferredSize(new Dimension(getWidth() , 180));
		bottomPanel.setPreferredSize(new Dimension(getWidth(), 200));
//		consoleScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		tableScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//		bottomPanel.add(consoleScroller);
		captureProcess = new CaptureProcess(console, tableInitializer);

		try {
			startIcon= new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("start30.png")));
			stopIcon = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("stop30.png")));
			cleanConsoleIcon = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("clean30.png")));
			refreshAllIcon = new ImageIcon(ImageIO.read(getClass().getClassLoader().getResource("refresh30.png")));
			capture=new JButton(startIcon);
			capture.setPreferredSize(new Dimension(startIcon.getIconWidth(),startIcon.getIconHeight()));
			stop=new JButton(stopIcon);
			stop.setPreferredSize(new Dimension(stopIcon.getIconWidth(),stopIcon.getIconHeight()));
			refreshAllButton=new JButton(refreshAllIcon);
			refreshAllButton.setPreferredSize(new Dimension(refreshAllIcon.getIconWidth(),refreshAllIcon.getIconHeight()));
			cleanConsoleButton=new JButton(cleanConsoleIcon);
			cleanConsoleButton.setPreferredSize(new Dimension(cleanConsoleIcon.getIconWidth(),cleanConsoleIcon.getIconHeight()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		upperPanel.add(capture);
		upperPanel.add(stop);
		upperPanel.add(cleanConsoleButton);
		upperPanel.add(refreshAllButton);
		console.setCaretPosition(console.getDocument().getLength());
		Rectangle visibleRect = consoleScroller.getViewport().getViewRect();
		visibleRect.y = console.getHeight() - visibleRect.height;
		consoleScroller.getViewport().scrollRectToVisible(visibleRect);

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
		infoPanel.add(totalTrafficUsedLabel);
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
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent event) {
				new PacketDialog(frame,captureProcess.getPacketStore().get(Long.valueOf(table.getSelectedRow())).getCompleteData());
			}
		});
		refreshAllButton.addActionListener(actionEvent -> {
			((DefaultTableModel)table.getModel()).setRowCount(0);
		});
		cleanConsoleButton.addActionListener(actionEvent -> console.setText(""));

		add(consoleScroller, BorderLayout.SOUTH);

		add(upperPanel, BorderLayout.NORTH);
		add(leftPanel, BorderLayout.WEST);
		add(tableScroller, BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		setVisible(true);
	}

	private String[] getNetworkInterfaceName() {
		return captureProcess.getInterfaceList().stream().map(PcapNetworkInterface::getName).toArray(String[]::new);
	}

	public static void throwWarning(String text){
		JOptionPane.showMessageDialog(new JFrame("error"),"dialog",text,JOptionPane.WARNING_MESSAGE);
	}

}
