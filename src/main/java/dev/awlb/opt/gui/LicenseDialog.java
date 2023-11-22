package dev.awlb.opt.gui;

import dev.awlb.opt.OpenPlotTool;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

@SuppressWarnings("serial")
public class LicenseDialog extends JDialog implements ActionListener {
	private JButton closeBtn;
	private JTextArea licenseText;

	public LicenseDialog() {
		super(OpenPlotTool.getInstance().getMainFrame(), "License", true);
		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);

		// create license text area
		licenseText = new JTextArea(16, 40);
		licenseText.setEditable(false);
		licenseText.setLineWrap(true);
		licenseText.setWrapStyleWord(true);
		JScrollPane scroll = new JScrollPane(licenseText);
		mainPanel.add(scroll, BorderLayout.CENTER);

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		// set dialog location
		Point winLocation = new Point(OpenPlotTool.getInstance().getMainFrame()
				.getLocation().x + 20, OpenPlotTool.getInstance()
				.getMainFrame().getLocation().y + 20);
		setLocation(winLocation);
		// create close button
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		closeBtn = new JButton("Close");
		closeBtn.addActionListener(this);
		buttonPanel.add(closeBtn);

		showLicense();
		// pack dialog
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == closeBtn) {
			setVisible(false);
		}
	}

	// show license text
	public void showLicense() {
		String text = "";
		try {
			BufferedReader in = new BufferedReader(new FileReader(
					"license/license.txt"));
			String str;
			while ((str = in.readLine()) != null) {
				// read each line of file into string
				text = text + str + "\n";
			}
			in.close();
		} catch (IOException e) {
		}
		licenseText.setText(text);
		licenseText.setCaretPosition(0);
	}
}
