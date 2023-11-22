package dev.awlb.opt.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import dev.awlb.opt.OpenPlotTool;

@SuppressWarnings("serial")
public class AboutDialog extends JDialog implements ActionListener {

	private JButton closeBtn;
	private JButton licenseBtn;

	public AboutDialog() {
		super(OpenPlotTool.getInstance().getMainFrame(), "About", true);
		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);

		// create information panel
		JPanel informationPanel = new JPanel(new BorderLayout());
		informationPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		mainPanel.add(informationPanel, BorderLayout.CENTER);
		// create icon label
		JLabel iconLabel = new JLabel("<html><b> OpenPlot Tool 0.4 Dev"
				+ "</b></html>", new ImageIcon("icon/logo.png"),
				SwingConstants.CENTER);
		iconLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
		iconLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		informationPanel.add(iconLabel, BorderLayout.PAGE_START);
		JLabel webLabel = new JLabel("https://awlb.dev",
				SwingConstants.CENTER);
		informationPanel.add(webLabel, BorderLayout.CENTER);
		JLabel copyLabel = new JLabel("(C) 2009 - 2023 Alex Barfoot",
				SwingConstants.CENTER);
		informationPanel.add(copyLabel, BorderLayout.PAGE_END);

		// create license and cancel buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		licenseBtn = new JButton("License");
		licenseBtn.addActionListener(this);
		buttonPanel.add(licenseBtn);
		closeBtn = new JButton("Close");
		closeBtn.addActionListener(this);
		buttonPanel.add(closeBtn);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setResizable(false);
		// pack dialog
		pack();
		// set dialog location
		Point winLocation = new Point(OpenPlotTool.getInstance().getMainFrame()
				.getLocation().x + 20, OpenPlotTool.getInstance()
				.getMainFrame().getLocation().y + 20);
		setLocation(winLocation);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == closeBtn) {
			setVisible(false);
		} else if (e.getSource() == licenseBtn) {
			LicenseDialog licenseDialog = new LicenseDialog();
			licenseDialog.setVisible(true);
		}
	}
}
