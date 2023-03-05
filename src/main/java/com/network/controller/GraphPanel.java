package com.network.controller;

import com.network.PrivateRedirecor.CaptureProcess;
import com.network.dto.chart.ChartObjectMap;
import com.network.dto.chart.ChartableKey;
import com.network.dto.chart.ChartableValue;
import com.network.dto.chart.chartObject;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class GraphPanel extends JPanel {

	private JFreeChart trafficChartComponent, countChartComponent;
	private ChartPanel trafficChartPanel, countChartPanel;
	private DefaultPieDataset trafficPieDataset = new DefaultPieDataset();
	private DefaultPieDataset countPieDataset = new DefaultPieDataset();
	private JPanel upperPanel = new JPanel();
	private JPanel centerPanel = new JPanel();
	private JComboBox<String> chartableFilterCombobox;
	private JComboBox<Short> chartSize;
	private short defaultChartSize = 5;

	public GraphPanel() {
		setLayout(new BorderLayout());
		chartableFilterCombobox = new JComboBox<>(Arrays.stream(ChartType.values()).map(ChartType::getName).toArray(String[]::new));
		chartableFilterCombobox.setSelectedIndex(0);
		chartSize = new JComboBox<>(new Short[]{ 5, 10, 15 });
		chartSize.setSelectedIndex(0);

		trafficChartComponent = ChartFactory.createPieChart("traffic usage of " + chartableFilterCombobox.getItemAt(chartableFilterCombobox.getSelectedIndex()), trafficPieDataset, true, true, false);
		countChartComponent = ChartFactory.createPieChart("count of " + chartableFilterCombobox.getItemAt(chartableFilterCombobox.getSelectedIndex()), countPieDataset, true, true, false);
		trafficChartPanel = new ChartPanel(trafficChartComponent);
		countChartPanel = new ChartPanel(countChartComponent);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		countChartPanel.setPreferredSize(new Dimension((dimension.width / 2) - 50, (dimension.height / 2) - 50));
		trafficChartPanel.setPreferredSize(new Dimension((dimension.width / 2) - 50, (dimension.height / 2) - 50));

		chartableFilterCombobox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
					createIpRouteChart(getDataMapForChart(), defaultChartSize);
					validate();
				}
		});
		chartSize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createIpRouteChart(getDataMapForChart(), chartSize.getItemAt(chartSize.getSelectedIndex()));
				validate();
			}
		});
		centerPanel.add(trafficChartPanel);
		centerPanel.add(countChartPanel);

		upperPanel.setLayout(new FlowLayout());
		centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
		upperPanel.add(chartableFilterCombobox);
		upperPanel.add(chartSize);

		add(upperPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
	}

	public void createIpRouteChart(Map<ChartableKey, ChartableValue> chartObject, short limit) {
		trafficPieDataset.clear();
		countPieDataset.clear();
		List<chartObject> chartObjectList = chartObject.entrySet().stream().map(x -> new chartObject(x.getKey(), x.getValue()))
				.sorted((first, second) -> Long.compare(second.getChartableValue().getTrafficUsage(), first.getChartableValue().getTrafficUsage())).limit(limit)
				.collect(Collectors.toList());

		for (com.network.dto.chart.chartObject object : chartObjectList) {
			trafficPieDataset.setValue(object.getChartableKey().getName(), object.getChartableValue().getTrafficUsage());
		}
		for (com.network.dto.chart.chartObject object : chartObjectList) {
			countPieDataset.setValue(object.getChartableKey().getName(), object.getChartableValue().getCount());
		}
		trafficChartPanel = new ChartPanel(trafficChartComponent);
		countChartPanel = new ChartPanel(countChartComponent);

	}

	private Map<ChartableKey,ChartableValue> getDataMapForChart(){
		if (chartableFilterCombobox.getItemAt(chartableFilterCombobox.getSelectedIndex()).equals(ChartType.IP_ROUTE.getName())){
			return CaptureProcess.ipRouteChartMap;
		}
		if (chartableFilterCombobox.getItemAt(chartableFilterCombobox.getSelectedIndex()).equals(ChartType.protocol.getName())){
			return CaptureProcess.protocolChartMap;
		}
		else if (chartableFilterCombobox.getItemAt(chartableFilterCombobox.getSelectedIndex()).equals(ChartType.port.getName())){
			return CaptureProcess.portChartMap;
		}
		else return new ChartObjectMap<>();
	}
}
