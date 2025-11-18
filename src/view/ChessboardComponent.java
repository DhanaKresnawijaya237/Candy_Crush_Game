package view;


import controller.GameController;
import model.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static model.Constant.CHESSBOARD_COL_SIZE;
import static model.Constant.CHESSBOARD_ROW_SIZE;

/**
 * This class represents the checkerboard component object on the panel
 */
public class ChessboardComponent extends JComponent {
    private final int CHESS_SIZE;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();

    private GameController gameController;
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private ChessboardPoint selectedCell;
    private Chessboard model;
    private ChessboardComponent view;
    private static Clip ButtonSound;

    public ChessboardComponent(int chessSize) {
        CHESS_SIZE = chessSize;
        int width = CHESS_SIZE * 8;
        int height = CHESS_SIZE * 8;
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setLayout(null);
        setSize(width, height);
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", width, height, CHESS_SIZE);
        initiateGridComponents();
    }


    /**
     * This method represents how to initiate ChessComponent
     * according to Chessboard information
     */
    public void initiateChessComponent(Chessboard chessboard) {
        Cell[][] grid = chessboard.getGrid();
        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                // TODO: Implement the initialization checkerboard

                if (grid[i][j].getPiece() != null) {
                    ChessPiece chessPiece = grid[i][j].getPiece();
                    gridComponents[i][j].add(new ChessComponent(CHESS_SIZE, chessPiece));
                }
            }
        }

    }

    public void initiateGridComponents() {

        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
                ChessboardPoint temp = new ChessboardPoint(i, j);
                CellComponent cell;
                if (riverCell.contains(temp)) {
                    cell = new CellComponent(Color.CYAN, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                } else {
                    cell = new CellComponent(Color.LIGHT_GRAY, calculatePoint(i, j), CHESS_SIZE);
                    this.add(cell);
                }
                gridComponents[i][j] = cell;
            }
        }
    }

    public void registerController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setChessComponentAtGrid(ChessboardPoint point, ChessComponent chess) {
        getGridComponentAt(point).add(chess);
    }

    public void removeAllChessComponentsAtGrids(){
        //todo:  complete the method
        for(int i=0; i<Constant.CHESSBOARD_ROW_SIZE.getNum();i++)
        {
            for(int j=0;j<Constant.CHESSBOARD_COL_SIZE.getNum();j++){
                if(gridComponents[i][j].getComponents().length>0)
                    removeChessComponentAtGrid(new ChessboardPoint(i,j));
            }
        }
    }

    public ChessComponent removeChessComponentAtGrid(ChessboardPoint point) {
        ChessComponent chess = (ChessComponent) getGridComponentAt(point).getComponents()[0];
        getGridComponentAt(point).removeAll();
        getGridComponentAt(point).revalidate();
        chess.setSelected(false);
        return chess;
    }

    public CellComponent getGridComponentAt(ChessboardPoint point) {
        return gridComponents[point.getRow()][point.getCol()];
    }

    private ChessboardPoint getChessboardPoint(Point point) {
        System.out.println("[" + point.y/CHESS_SIZE +  ", " +point.x/CHESS_SIZE + "] Clicked");
        return new ChessboardPoint(point.y/CHESS_SIZE, point.x/CHESS_SIZE);
    }
    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void swapChess(){
        gameController.onPlayerSwapChess();
    }

    public void nextStep(){
        gameController.onPlayerNextStep();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            initializePopSoundEffect();
            playPopSound();
            JComponent clickedComponent = (JComponent) getComponentAt(e.getX(), e.getY());
            if (clickedComponent.getComponentCount() == 0) {
                System.out.print("None chess here and ");
                playPopSound();
                gameController.onPlayerClickCell(getChessboardPoint(e.getPoint()), (CellComponent) clickedComponent);
            } else {
                System.out.print("One chess here and ");
                gameController.onPlayerClickChessPiece(getChessboardPoint(e.getPoint()), (ChessComponent) clickedComponent.getComponents()[0]);
            }
        }
    }
    private void initializePopSoundEffect() {
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("woodensound.wav")));
            ButtonSound = AudioSystem.getClip();
            ButtonSound.open(audioInput);
            FloatControl gainControl2 = (FloatControl) ButtonSound.getControl(FloatControl.Type.MASTER_GAIN);
            float gain = (float) (Math.log(Settings.getInitialSliderLocation() / 100.0) / Math.log(10.0) * 20.0);
            gainControl2.setValue(gain+2);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
            System.out.println("Error initializing pop sound effect.");
        }
    }
    private void playPopSound() {
        if (ButtonSound != null) {
            ButtonSound.setFramePosition(0);
            ButtonSound.start();
        }
    }

    public static void setPopSoundVolume(int volume) {
        FloatControl gainControl2 = (FloatControl) ButtonSound.getControl(FloatControl.Type.MASTER_GAIN);
        float gain = (float) (Math.log(volume / 100.0) / Math.log(10.0) * 20.0);
        gainControl2.setValue(gain+2);
    }
}
