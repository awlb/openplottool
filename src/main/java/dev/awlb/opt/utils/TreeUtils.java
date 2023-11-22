package dev.awlb.opt.utils;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class TreeUtils {
	public static TreePath getPath(TreeNode node) {
		ArrayList<TreeNode> list = new ArrayList<TreeNode>();

		// Add all nodes to list
		while (node != null) {
			list.add(node);
			node = node.getParent();
		}
		Collections.reverse(list);

		// Convert array of nodes to TreePath
		return new TreePath(list.toArray());
	}
}
