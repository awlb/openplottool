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

import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

import plot.Plot;

@SuppressWarnings("serial")
public class Page extends JPanel {
	// plot panel
	Plot plot;
	// data tree
	JTree dataTree;
	private JSplitPane splitPane;

	public Page(Plot plot) {
		super(new BorderLayout());
		// set plot for page
		this.plot = plot;
		// create data tree
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Plot Data");
		dataTree = new JTree(root);
		// add data categories
		for(String dataTypeStr : plot.getDataTypes()) {
			DefaultMutableTreeNode dataTypeNode = new DefaultMutableTreeNode(dataTypeStr);
			root.add(dataTypeNode);
		}
		// create split pane
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, dataTree,
				plot);
		splitPane.setOneTouchExpandable(true);
		splitPane.setDividerLocation(180);
		this.add(splitPane, BorderLayout.CENTER);
	}
}
