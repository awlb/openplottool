package dev.awlb.opt.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dev.awlb.opt.OpenPlotTool;
import net.miginfocom.swing.MigLayout;

@SuppressWarnings("serial")
public class ExportDialog extends JDialog implements ActionListener {

	public static int EXPORT_PRESSED = 1;
	private JButton cancelBtn, exportBtn, selectLocationBtn;
	private JTextField locationField, nameField;
	private int pressed = 0;
	private JComboBox typeCombo;

	public ExportDialog() {
		super(OpenPlotTool.getInstance().getMainFrame(), "Export", true);
		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		// create input panel
		JPanel inputPanel = new JPanel(new MigLayout("", "", "25px"));
		inputPanel.setBorder(BorderFactory.createTitledBorder("Export File: "));
		mainPanel.add(inputPanel, BorderLayout.CENTER);
		// create name field
		inputPanel.add(new JLabel("Name: "));
		nameField = new JTextField(15);
		inputPanel.add(nameField, "grow, span");
		// create location field
		inputPanel.add(new JLabel("Location: "));
		locationField = new JTextField(15);
		inputPanel.add(locationField, "grow");
		// create select location btn
		selectLocationBtn = new JButton("...");
		selectLocationBtn.addActionListener(this);
		inputPanel.add(selectLocationBtn, "wrap");
		// create type selection combobox
		inputPanel.add(new JLabel("Type: "));
		String[] formatNames = ImageIO.getReaderFormatNames();
		Set<String> set = new HashSet<String>();
		for (int i = 0; i < formatNames.length; i++) {
			// only add formats with extension of length three
			if (formatNames[i].length() == 3) {
				String name = formatNames[i].toLowerCase();
				set.add(name);
			}
		}
		formatNames = set.toArray(new String[0]);
		typeCombo = new JComboBox(formatNames);
		inputPanel.add(typeCombo, "grow, span");

		// create ok and cancel buttons
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.TRAILING));
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		exportBtn = new JButton("Export");
		exportBtn.addActionListener(this);
		buttonPanel.add(exportBtn);
		cancelBtn = new JButton("Cancel");
		cancelBtn.addActionListener(this);
		buttonPanel.add(cancelBtn);

		// make dialog none Resizable
		setResizable(false);

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
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exportBtn) {
			pressed = EXPORT_PRESSED;
			setVisible(false);
		} else if (e.getSource() == selectLocationBtn) {
			// show file chooser so user can select directory
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.showOpenDialog(this);
			// get selected directory
			File selFile = fileChooser.getSelectedFile();
			if (selFile != null) {
				locationField.setText(selFile.getAbsolutePath());
			}
		} else {
			setVisible(false);
		}
	}

	public File getFile() {
		if (nameField.getText().equals("")
				|| locationField.getText().equals("")) {
			return null;
		} else {
			// return file to be exported to
			File file = new File(locationField.getText() + File.separator
					+ nameField.getText() + "."
					+ (String) typeCombo.getSelectedItem());
			return file;
		}
	}

	public String getFormat() {
		// return export format
		return (String) typeCombo.getSelectedItem();
	}

	public int getPressed() {
		return pressed;
	}
}
