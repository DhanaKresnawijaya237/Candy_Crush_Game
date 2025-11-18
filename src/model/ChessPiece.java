package model;

import java.awt.*;
import javax.swing.*;

import java.util.Random;

public class ChessPiece {
    // Diamond, Circle, ...
    private ImageIcon name;

    private Color color;

    public ChessPiece(ImageIcon name) {
        this.name = name;
        this.color = Constant.colorMap.get(name);
    }

    public ImageIcon getName() {
        return name;
    }

    public Color getColor(){return color;}

}
