package gui;

import handlers.StrokeTypeHandler;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import main.OpenPlotTool;

@SuppressWarnings("serial")
public class StrokeEditor extends JDialog implements ActionListener,
		ListSelectionListener {

	private DefaultListModel strokeItemListModel;
	private JList strokeItemList;
	private JButton addStrokeButton;
	private JButton editStrokeButton;
	private JButton removeStrokeButton;
	private StrokePreviewPanel strokePreviewPanel;

	public StrokeEditor() {
		super(OpenPlotTool.getMainFrame(), "Stroke Editor", true);

		// create main panel
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		setContentPane(mainPanel);
		
		// create list panel
		JPanel strokeListPanel = new JPanel(new BorderLayout());
		strokeListPanel
				.setBorder(BorderFactory.createTitledBorder("Strokes: "));
		mainPanel.add(strokeListPanel, BorderLayout.CENTER);

		// create stroke list
		strokeItemListModel = new DefaultListModel();
		String[] strokeTypes = StrokeTypeHandler.getStrokeTypeNames();
		for (int i = 0; i < strokeTypes.length; i++) {
			strokeItemListModel.addElement(strokeTypes[i]);
		}
		strokeItemList = new JList(strokeItemListModel);
		strokeItemList.addListSelectionListener(this);
		strokeItemList.setPreferredSize(new Dimension(150, 50));
		strokeItemList.setSelectedIndex(0);
		JScrollPane strokeItemListScroll = new JScrollPane(strokeItemList,
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		strokeListPanel.add(strokeItemListScroll, BorderLayout.CENTER);

		// create side panel
		JPanel sidePanel = new JPanel(new BorderLayout());
		mainPanel.add(sidePanel, BorderLayout.LINE_END);
		// create stroke button panel
		JPanel strokeButtonPanel = new JPanel(new GridLayout(3, 1));
		sidePanel.add(strokeButtonPanel, BorderLayout.PAGE_START);
		// create buttons
		addStrokeButton = new JButton("Add");
		addStrokeButton.addActionListener(this);
		strokeButtonPanel.add(addStrokeButton);
		editStrokeButton = new JButton("Edit");
		editStrokeButton.addActionListener(this);
		strokeButtonPanel.add(editStrokeButton);
		removeStrokeButton = new JButton("Remove");
		removeStrokeButton.addActionListener(this);
		strokeButtonPanel.add(removeStrokeButton);
		
		// create preview panel
		JPanel previewPanel = new JPanel(new BorderLayout());
		previewPanel.setPreferredSize(new Dimension(150, 60));
		previewPanel.setBorder(BorderFactory.createTitledBorder("Preview: "));
		mainPanel.add(previewPanel, BorderLayout.PAGE_END);
		// create preview
		strokePreviewPanel = new StrokePreviewPanel(StrokeTypeHandler
				.getStrokeType(strokeTypes[0]));
		previewPanel.add(strokePreviewPanel, BorderLayout.CENTER);

		// pack dialog size
		pack();

	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		String selection = (String) strokeItemList.getSelectedValue();
		if(selection != null && strokePreviewPanel != null) {
			strokePreviewPanel.setStroke(StrokeTypeHandler.getStrokeType(selection));
			strokePreviewPanel.repaint();
		}
	}
}
