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

package page;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;

import plot.Plot;
import plot.PlotData;

@SuppressWarnings("serial")
public class Page extends JPanel {
	// data tree
	private JTree dataTree;
	// plot panel
	private Plot plot;
	private JSplitPane splitPane;
	private HashMap<String, DefaultMutableTreeNode> nodeMap = null;
	private DefaultTreeModel dataTreeModel;

	public Page(Plot plot) {
		super(new BorderLayout());
		// set plot for page
		this.plot = plot;
		// create data tree
		DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(plot
				.getPlotType());
		dataTreeModel = new DefaultTreeModel(rootNode);
		dataTree = new JTree(dataTreeModel);
		dataTree.getSelectionModel().setSelectionMode(
				TreeSelectionModel.SINGLE_TREE_SELECTION);
		dataTree.setShowsRootHandles(true);

		// add data categories
		nodeMap = new HashMap<String, DefaultMutableTreeNode>();
		for (String dataTypeStr : plot.getDataTypes()) {
			DefaultMutableTreeNode dataTypeNode = new DefaultMutableTreeNode(
					dataTypeStr);
			rootNode.add(dataTypeNode);
			nodeMap.put(dataTypeStr, dataTypeNode);
		}
		// create left panel
		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.add(dataTree, BorderLayout.CENTER);
		JScrollPane scroll = new JScrollPane(leftPanel);

		// create split pane
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, plot);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(180);
		this.add(splitPane, BorderLayout.CENTER);
	}

	public void addData(PlotData plotData) {
		// create and add node to represent data item
		DefaultMutableTreeNode dataTypeNode = nodeMap.get(plotData
				.getDataType());
		DefaultMutableTreeNode dataNode = new DefaultMutableTreeNode(plotData);
		dataTreeModel.insertNodeInto(dataNode, dataTypeNode, dataTypeNode
				.getChildCount());
		// add to page plot
		plot.addData(plotData);
		// repaint tree and plot
		dataTree.repaint();
		plot.repaint();
	}

	public void removeData() {
		// get selected node
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) dataTree
				.getLastSelectedPathComponent();
		if (selectedNode != null) {
			Object selectedObject = selectedNode.getUserObject();
			if (selectedObject instanceof PlotData) {
				// remove data node from tree and data from plot
				PlotData plotData = (PlotData) selectedObject;
				dataTreeModel.removeNodeFromParent(selectedNode);
				plot.removeData(plotData);
				// repaint tree and plot
				dataTree.repaint();
				plot.repaint();
			}
		}
	}

	public Plot getPlot() {
		return plot;
	}
}
