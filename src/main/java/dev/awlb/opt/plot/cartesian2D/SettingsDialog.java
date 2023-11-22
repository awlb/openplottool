package dev.awlb.opt.plot.cartesian2D;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import dev.awlb.opt.OpenPlotTool;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class SettingsDialog extends JDialog implements ActionListener {
	public static int UPDATE_PRESSED = 1;
	private int pressed = 0;
	private JButton updateBtn;
	private JButton cancelBtn;
	private Cartesian2DSettings settings;

	public SettingsDialog(Cartesian2DSettings settings) {
		super(OpenPlotTool.getInstance().getMainFrame(), "Plot Settings", true);
		this.settings = settings;

		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);

		// create input tab pane
		JTabbedPane settingsPane = new JTabbedPane();
		mainPanel.add(settingsPane, BorderLayout.CENTER);
		
		// create axis size panel
		JPanel axisSizePanel = new JPanel(new MigLayout("", "", "25px"));
		settingsPane.addTab("Axis Size", axisSizePanel);
		
		// create axis size panel
		JPanel axisColorPanel = new JPanel(new MigLayout("", "", "25px"));
		settingsPane.addTab("Axis Color", axisColorPanel);

		// create update and cancel buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		updateBtn = new JButton("Create");
		updateBtn.addActionListener(this);
		buttonPanel.add(updateBtn);
		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(this);
		buttonPanel.add(cancelBtn);

		// set close action
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// set dialog location
		Point parentPoint = OpenPlotTool.getInstance().getMainFrame()
				.getLocation();
		Point winLocation = new Point(parentPoint.x + 20, parentPoint.y + 20);
		setLocation(winLocation);

		// pack dialog
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		if (ev.getSource() == updateBtn) {
			pressed = UPDATE_PRESSED;
		}
		setVisible(false);
	}
	
	public int getPressed() {
		return pressed;
	}
	
	public Cartesian2DSettings getSettings() {
		return settings;
	}
}
