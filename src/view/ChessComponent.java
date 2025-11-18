package view;


import model.ChessPiece;
import model.ChessboardPoint;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This is the equivalent of the ChessPiece class,
 * but this class only cares how to draw Chess on ChessboardComponent
 */
public class ChessComponent extends JComponent {

    private boolean selected;

    private ChessPiece chessPiece;

    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private static Clip ButtonSound;

    public ChessComponent(int size, ChessPiece chessPiece) {
        this.selected = false;
        this.chessPiece = chessPiece;
        setSize(size - 4, size - 4);
        setLocation(2, 2);
        setVisible(true);
    }
    public ChessPiece getchessPiece(){
        return this.chessPiece;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        if (isSelected()) {
            g2.setColor(Color.white);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
        Font font = new Font("Helvetica", Font.PLAIN, getWidth() / 2);
        g2.setFont(font);
        g2.setColor(this.chessPiece.getColor());
        Image originalImage = this.chessPiece.getName().getImage();

        int newWidth = getWidth()-15;
        int newHeight = getHeight()-15;

        g2.drawImage(originalImage, 5, 5, newWidth, newHeight, this);
    }
}
