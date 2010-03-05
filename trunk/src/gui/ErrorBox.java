/*	Copyright (C) 2009-2010  Alex Barfoot
 
 	This file is part of OpenPlot Tool.

    OpenPlot Tool is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    OpenPlot Tool is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with OpenPlot Tool.  If not, see <http://www.gnu.org/licenses/>.
 */

package gui;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import main.OpenPlotTool;

@SuppressWarnings("serial")
public class ErrorBox extends JDialog implements ActionListener {
	private JButton continueButton;
	private JTextArea errorText;

	public ErrorBox(JFrame owner, String title, Exception e) {
		super(owner, title, true);
		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);

		// create error panel
		JPanel errorPanel = new JPanel(new BorderLayout());
		errorPanel.setBorder(BorderFactory
				.createTitledBorder("The following error has occurred:"));
		mainPanel.add(errorPanel, BorderLayout.CENTER);

		// create error text area
		errorText = new JTextArea(16, 50);
		errorText.setEditable(false);
		JScrollPane errorTextPane = new JScrollPane(errorText);
		errorPanel.add(errorTextPane, BorderLayout.CENTER);

		// set error text
		errorText.append(e.getMessage() + "\n");
		errorText
				.append("Please report this problem if you are unable to solve it.\n");
		errorText.append("-----------------------\n");
		errorText.append("Trace: \n");
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		e.printStackTrace(pw);
		pw.flush();
		sw.flush();
		errorText.append(sw.toString());
		errorText.setCaretPosition(0);

		// create button panel
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		mainPanel.add(buttonPanel, BorderLayout.PAGE_END);
		continueButton = new JButton("Continue");
		continueButton.addActionListener(this);
		buttonPanel.add(continueButton);

		// dipose on exit
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// pack dialog
		setSize(500, 300);
		// set dialog location
		if (OpenPlotTool.getMainFrame() != null) {
			Point winLocation = new Point(OpenPlotTool.getMainFrame()
					.getLocation().x + 20, OpenPlotTool.getMainFrame()
					.getLocation().y + 20);
			setLocation(winLocation);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setVisible(false);
	}
}
