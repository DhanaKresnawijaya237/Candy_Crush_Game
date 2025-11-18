package view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class CellComponent extends JPanel {
    private Color background;


    public CellComponent(Color background, Point location, int size) {
        setOpaque(true);
        setLayout(null);
        setLocation(location);
        setSize(size, size);
        this.background = background;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;


        Color startColor = Color.BLACK;
        Color endColor = background;

        GradientPaint gradientPaint = new GradientPaint(
                new Point2D.Float(0, 0),
                startColor,
                new Point2D.Float(getWidth(), getHeight()),
                endColor
        );

        g2d.setPaint(gradientPaint);

        g2d.fillRect(1, 1, getWidth() - 5, getHeight() - 5);
    }
}
