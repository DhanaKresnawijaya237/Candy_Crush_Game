package model;
import controller.GameController;
import view.ChessComponent;

import java.awt.*;
import javax.swing.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * This class store the real chess information.
 * The Chessboard has 8 * 8 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;
    private ImageIcon image1;
    private ImageIcon image2;
    private ImageIcon image3;
    private ImageIcon image4;
    private ImageIcon image5;

    public Chessboard() {
        this.grid =
                new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];

        initGrid();
        initPieces();
    }

    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }
    }

    public void initPieces() {
        image1 = new ImageIcon(Objects.requireNonNull(getClass().getResource("Diamond.png")));
        image2 = new ImageIcon(Objects.requireNonNull(getClass().getResource("Circle.png")));
        image3 = new ImageIcon(Objects.requireNonNull(getClass().getResource("Hexagon.png")));
        image4 = new ImageIcon(Objects.requireNonNull(getClass().getResource("Triangle.png")));
        image5 = new ImageIcon(Objects.requireNonNull(getClass().getResource("Star.png")));
        ImageIcon [] shapes2 = {image1, image2, image3, image4, image5};
        Random random = new Random();

        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                ImageIcon randomShape;
                do {
                    randomShape = shapes2[random.nextInt(shapes2.length)];
                } while (j >= 2 && randomShape.equals(grid[i][j - 1].getPiece().getName())
                        && randomShape.equals(grid[i][j - 2].getPiece().getName()));

                if (i >= 2 && (randomShape.equals(grid[i - 1][j].getPiece().getName()) && randomShape.equals(grid[i - 2][j].getPiece().getName()))) {
                    do {
                        randomShape = shapes2[random.nextInt(shapes2.length)];
                    } while (randomShape.equals(grid[i - 1][j].getPiece().getName()) &&
                            randomShape.equals(grid[i - 2][j].getPiece().getName()));
                }
                grid[i][j].setPiece(new ChessPiece(randomShape));
            }
        }
        for (int col = 0; col<Constant.CHESSBOARD_COL_SIZE.getNum(); col++){
            for (int b = 0; b<Constant.CHESSBOARD_ROW_SIZE.getNum(); b++){
                int match = 0;
                for (int j = b+1; j < Constant.CHESSBOARD_ROW_SIZE.getNum(); j++){
                    if (getChessPieceAt(new ChessboardPoint(b, col)) == null || getChessPieceAt(new ChessboardPoint(j, col)) == null){
                        break;
                    }
                    ChessPiece chessPieceAt1 = getChessPieceAt(new ChessboardPoint(b, col));
                    ChessPiece chessPieceAt2 = getChessPieceAt(new ChessboardPoint(j, col));
                    if (chessPieceAt1.getName().toString().equals(chessPieceAt2.getName().toString())){
                        match +=1;
                    }
                    else{
                        break;
                    }
                }
                if(match > 1){
                    removeAllChessPiece();
                    initPieces();
                }
            }
        }
        for (int row = 0; row<Constant.CHESSBOARD_ROW_SIZE.getNum(); row++){
            for (int b = 0; b<Constant.CHESSBOARD_COL_SIZE.getNum(); b++){
                int match = 0;
                for (int j = b+1; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++){
                    if (getChessPieceAt(new ChessboardPoint(row, b)) == null || getChessPieceAt(new ChessboardPoint(row, j)) == null){
                        break;
                    }
                    ChessPiece chessPieceAt1 = getChessPieceAt(new ChessboardPoint(row, b));
                    ChessPiece chessPieceAt2 = getChessPieceAt(new ChessboardPoint(row, j));
                    if (chessPieceAt1.getName().toString().equals(chessPieceAt2.getName().toString())){
                        match +=1;
                    }
                    else{
                        break;
                    }

                }
                if(match > 1){
                    removeAllChessPiece();
                    initPieces();
                }
            }
        }
    }

    public void initPiecebyFile(List<String> lines){
        image1 = new ImageIcon(Objects.requireNonNull(getClass().getResource("Diamond.png")));
        image2 = new ImageIcon(Objects.requireNonNull(getClass().getResource("Circle.png")));
        image3 = new ImageIcon(Objects.requireNonNull(getClass().getResource("Hexagon.png")));
        image4 = new ImageIcon(Objects.requireNonNull(getClass().getResource("Triangle.png")));
        image5 = new ImageIcon(Objects.requireNonNull(getClass().getResource("Star.png")));
        for (int i = 1; i <= Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            int c=0;
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum()*2-1; j++) {
                if(j%2==0 && j>1)
                    c++;
                switch (lines.get(i).charAt(j)){
                    case '1':
                        grid[i-1][j-c].setPiece(new ChessPiece(image1));
                        break;
                    case '2':
                        grid[i-1][j-c].setPiece(new ChessPiece(image2));
                        break;
                    case '3':
                        grid[i-1][j-c].setPiece(new ChessPiece(image3));
                        break;
                    case '4':
                        grid[i-1][j-c].setPiece(new ChessPiece(image4));
                        break;
                    case '5':
                        grid[i-1][j-c].setPiece(new ChessPiece(image5));
                        break;
                    case 'n':
                        break;
                    default:
                        break;

                }
            }
        }


    };

    public void saveChessBoard(String path){
        try{
            Files.write(Path.of(path),this.saveChessBoardToLines(), Charset.defaultCharset());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> saveChessBoardToLines(){
        List<String> lines = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++)
        {
            sb.setLength(0);
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                sb.append(j).append(",");
            }
            sb.setLength(sb.length() - 1);
            lines.add(sb.toString());
        }
        return lines;
    }

    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    public Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private int calculateDistance(ChessboardPoint src, ChessboardPoint dest) {
        return Math.abs(src.getRow() - dest.getRow()) + Math.abs(src.getCol() - dest.getCol());
    }

    public ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    public void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }


    public void swapChessPiece(ChessboardPoint point1, ChessboardPoint point2) {
        var p1 = getChessPieceAt(point1);
        var p2 = getChessPieceAt(point2);
        setChessPiece(point1, p2);
        setChessPiece(point2, p1);
    }


    public Cell[][] getGrid() {
        return grid;
    }


    public void removeAllChessPiece() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                getGridAt(new ChessboardPoint(i,j)).removePiece();
            }
        }

    }
}
