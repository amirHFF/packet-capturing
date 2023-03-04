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
		JOptionPane.showMessageDialog(new JFrame("error"), text, "warning", JOptionPane.WARNING_MESSAGE);
	}

	public static JFrame getJFrame() {
		return frame;
	}

}
