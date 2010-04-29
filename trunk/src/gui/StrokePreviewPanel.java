package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import plot.StrokeType;

@SuppressWarnings("serial")
public class StrokePreviewPanel extends JPanel {
	private StrokeType stroke;

	public StrokePreviewPanel(StrokeType stroke) {
		this.stroke = stroke;
	}

	public void setStroke(StrokeType stroke) {
		this.stroke = stroke;
	}

	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(this.getBackground());
		g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		g2d.setColor(Color.BLACK);
		g2d.setStroke(stroke.getStroke());
		int height = this.getHeight() / 2;
		g2d.drawLine(0, height, this.getWidth(), height);
	}
}
