package com.network.controller;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainSwingController extends JFrame {
	private static JFrame frame;
	private JTabbedPane tabbedPane;
	private final FilterPanel filterPanel = new FilterPanel();
	private final GraphPanel graphPanel = new GraphPanel();
	private final MainPanel mainPanel = new MainPanel();

	public MainSwingController() {
		frame = this;
		tabbedPane = new JTabbedPane();
		tabbedPane.add("packet tracer",mainPanel);
		tabbedPane.add("packet graph",graphPanel);
		tabbedPane.add("packet filtering",filterPanel);
		add(tabbedPane);
		frame.setTitle("private request Redirection");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

//		this.pack();
		setVisible(true);
		frame.setLocationRelativeTo(null);
	}

	public static void throwWarning(String text) {
		JOptionPane jOptionPane =new JOptionPane( text, JOptionPane.WARNING_MESSAGE, JOptionPane.DEFAULT_OPTION);
		JDialog dialog=jOptionPane.createDialog(" warning");
		dialog.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dialog.dispose();
			}
		}});

	}

	public static JFrame getJFrame() {
		return frame;
	}

}
