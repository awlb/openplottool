package dev.awlb.opt.view;

import dev.awlb.opt.plot.Plot;
import dev.awlb.opt.plot.PlotData;
import dev.awlb.opt.utils.TreeUtils;

import java.awt.BorderLayout;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

@SuppressWarnings("serial")
public class PlotView extends JPanel {
	// data tree
	private JTree dataTree = null;
	private DefaultTreeModel dataTreeModel;
	// plot this view is for
	private Plot plot = null;
	private DefaultMutableTreeNode rootNode;
	// map of data type nodes
	HashMap<String, DefaultMutableTreeNode> typeNodeMap = new HashMap<String, DefaultMutableTreeNode>();

	public PlotView(Plot plot) {
		this.plot = plot;
		// create layout
		this.setLayout(new BorderLayout());
		// create left hand tree
		rootNode = new DefaultMutableTreeNode("Data");
		dataTreeModel = new DefaultTreeModel(rootNode);
		dataTree = new JTree(dataTreeModel);
		// create split pane and add to view
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				dataTree, plot);
		splitPane.setDividerLocation(150);
		splitPane.setOneTouchExpandable(true);
		this.add(splitPane, BorderLayout.CENTER);
		// build the view data tree
		buildDataTree();
	}

	public void addData(PlotData plotData) {
		// create and add node to represent data item
		DefaultMutableTreeNode dataTypeNode = typeNodeMap.get(plotData
				.getDataType());
		DefaultMutableTreeNode dataNode = new DefaultMutableTreeNode(plotData);
		dataTreeModel.insertNodeInto(dataNode, dataTypeNode,
				dataTypeNode.getChildCount());
		// get path for data type node
		TreePath typeNodePath = TreeUtils.getPath(dataTypeNode);
		dataTree.expandPath(typeNodePath);
		// add to page plot
		plot.addData(plotData);
		// repaint tree and plot
		dataTree.repaint();
		plot.repaint();
	}

	private void buildDataTree() {
		// get data types for plot
		String[] dataTypes = plot.getDataTypes();
		// add data type nodes
		for (String dataType : dataTypes) {
			DefaultMutableTreeNode typeNode = new DefaultMutableTreeNode(
					dataType);
			typeNodeMap.put(dataType, typeNode);
			rootNode.add(typeNode);
		}
		// get data nodes
		PlotData[] plotDataArray = new PlotData[0];
		plotDataArray = plot.getPlotDataList().toArray(plotDataArray);
		for (PlotData plotData : plotDataArray) {
			// create and add node to represent data item
			DefaultMutableTreeNode dataTypeNode = typeNodeMap.get(plotData
					.getDataType());
			DefaultMutableTreeNode dataNode = new DefaultMutableTreeNode(
					plotData);
			dataTreeModel.insertNodeInto(dataNode, dataTypeNode,
					dataTypeNode.getChildCount());
		}
		// expand root node
		dataTree.expandPath(TreeUtils.getPath(rootNode));
	}

	public DefaultMutableTreeNode findDataNode(PlotData plotData) {
		// Get the enumeration
		Enumeration<TreeNode> treeEnum = rootNode
				.breadthFirstEnumeration();

		// iterate through the enumeration
		while (treeEnum.hasMoreElements()) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeEnum.nextElement();
			if (node.getUserObject() == plotData) {
				return node;
			}
		}
		return null;
	}

	public Plot getPlot() {
		return plot;
	}

	public PlotData getSelectedData() {
		PlotData selectedData = null;
		// get selected node
		DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) dataTree
				.getLastSelectedPathComponent();
		Object selectedObject = selectedNode.getUserObject();
		if (selectedNode != null) {
			if (selectedObject instanceof PlotData) {
				selectedData = (PlotData) selectedObject;
			}
		}
		return selectedData;
	}

	public void removeData(PlotData plotData) {
		// get selected node
		DefaultMutableTreeNode dataNode = findDataNode(plotData);
		if (dataNode != null) {
			dataTreeModel.removeNodeFromParent(dataNode);
			plot.removeData(plotData);
			// repaint tree and plot
			dataTree.repaint();
			plot.repaint();
		}
	}

	public void replaceData(PlotData oldData, PlotData newData) {
		// get old data node
		DefaultMutableTreeNode dataNode = findDataNode(oldData);
		if (dataNode != null) {
			// replace data
			dataNode.setUserObject(newData);
			plot.replaceData(oldData, newData);
			// repaint tree and plot
			dataTree.repaint();
			plot.repaint();
		}
	}
}
