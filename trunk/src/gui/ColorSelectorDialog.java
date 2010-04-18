package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ColorSelectorDialog extends JDialog implements ActionListener {
	private JButton cancelButton;
	private JColorChooser chooser;
	private Color initialColor;
	private JButton okButton;
	private JButton resetButton;
	private Color selectedColor;

	public ColorSelectorDialog(JFrame parent) {
		super(parent, "Color Selector", true);
		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		// create color chooser
		chooser = new JColorChooser();
		mainPanel.add(chooser, BorderLayout.CENTER);
		// create button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		okButton = new JButton("Ok");
		okButton.addActionListener(this);
		buttonPanel.add(okButton);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		buttonPanel.add(cancelButton);
		resetButton = new JButton("Reset");
		resetButton.addActionListener(this);
		buttonPanel.add(resetButton);
		// pack dialog size
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == okButton) {
			selectedColor = chooser.getColor();
			setVisible(false);
		} else if (e.getSource() == cancelButton) {
			setVisible(false);
		} else if (e.getSource() == resetButton) {
			chooser.setColor(initialColor);
		}
	}

	public Color getSelectedColor() {
		return selectedColor;
	}

	public void showDialog(Color color) {
		chooser.setColor(color);
		selectedColor = color;
		initialColor = color;
		setVisible(true);
	}
}
