package controller;

import listener.GameListener;
import model.*;
import view.*;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and
 * onPlayerClickChessPiece()]
 */
public class GameController implements GameListener {

    private static Chessboard model;
    private static ChessboardComponent view;

    private ChessboardPoint selectedPoint;
    private ChessboardPoint selectedPoint2;
    public static int score;

    private static JLabel statusLabel;
    private static JLabel statusLabel2;
    private static int match;
    public static int scoretarget;
    public static int liststeps[];
    public static int steps;
    private ArrayList<Integer> arrayrow1 = new ArrayList<>();
    private ArrayList<Integer> arraycol1 = new ArrayList<>();
    private ArrayList<ChessComponent> chessComponents = new ArrayList<>();
    private ArrayList<Integer> x = new ArrayList<>();
    private ArrayList<Integer> y = new ArrayList<>();
    private ArrayList<Integer> z = new ArrayList<>();
    private ArrayList<ChessComponent> chessComponentss = new ArrayList<>();
    private ArrayList<Integer> xx = new ArrayList<>();
    private ArrayList<Integer> yy = new ArrayList<>();
    private ArrayList<Integer> zz = new ArrayList<>();
    private static int turncount=0;
    private static int donecheck=0;
    private static int donecheck2=0;
    private static int nextstep = -1;
    private int HEIGHT;
    private int WIDTH;
    public static int level = 1;
    private static int shuffle;
    private static int hint;
    private static JLabel statuslabel3;
    private static boolean openNextLevel = false;
    private boolean auto = false;
    private int fallspeed = 1;
    private Timer timer;
    private Timer timer2;
    private ImageIcon image;
    private int bruh;
    private ChessComponent chessComponent;
    private Clip popSoundClip;
    private int count = 0;
    private int count1 = -1;
    private int next = 0;
    private int next1 = 0;
    private int autocounter = 0;
    private int hintcounter = 0;
    private boolean load = false;
    private boolean load1 = false;
    private ChessGameFrame chessGameFrame;
    private static JLabel statuslabel4;
    private static JLabel statuslabel5;
    private Clip popSoundClip2;
    public JLabel getStatusLabel() {
        return statusLabel;
    }
    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }
    public void setStatusLabel2(JLabel statuslabel2){
        this.statusLabel2 = statuslabel2;
    }
    public void setStatuslabel3(JLabel statuslabel3){
        this.statuslabel3 = statuslabel3;
    }
    public void setSteps(int steps){
        this.steps = steps;
    }
    public void setChessGameFrame(ChessGameFrame chessGameFrame) {
        this.chessGameFrame = chessGameFrame;
    }
    public void setHEIGHT(int HEIGHT) {
        this.HEIGHT = HEIGHT;
    }

    public void setWIDTH(int WIDTH) {
        this.WIDTH = WIDTH;
    }
    public void setTargetScore(int scoretarget){
        this.scoretarget = scoretarget;
    }
    public void setLevel(int level){
        this.level = level;
    }
    public void setAuto(boolean auto){
        this.auto = auto;
    }
    public static void setShuffle(int shuffle){GameController.shuffle = shuffle;}

    public static void setHint(int hint) {
        GameController.hint = hint;
    }

    public boolean getOpenNextLevel(){
        return this.openNextLevel;
    }
    public int getShuffle(){
        return GameController.shuffle;
    }
    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.timer = new Timer(75, this::ActionPerformed);
        this.timer2 = new Timer(75, this::ActionPerformed2);
        this.liststeps = new int[]{10, 17, 24, 30, 30, 53, 62, 71, 81, 90};
        steps = liststeps[level-1];
        score=0;
        turncount = 0;
        initializePopSoundEffect();
        view.registerController(this);
        view.initiateChessComponent(model);
        view.repaint();
    }

    public int getSteps() {
        return steps;
    }

    public int getScore() {
        return score;
    }
    public int getLevel(){return level;}

    public int getScoretarget() {
        return scoretarget;
    }

    public void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                //todo: complete it when restart game
            }
        }
    }
    public java.util.List<String> convertToList(String username) {
        List<String> lines = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        ImageIcon image1 = new ImageIcon(Objects.requireNonNull(model.getClass().getResource("Diamond.png")));
        ImageIcon image2 = new ImageIcon(Objects.requireNonNull(model.getClass().getResource("Circle.png")));
        ImageIcon image3 = new ImageIcon(Objects.requireNonNull(model.getClass().getResource("Hexagon.png")));
        ImageIcon image4 = new ImageIcon(Objects.requireNonNull(model.getClass().getResource("Triangle.png")));
        ImageIcon image5 = new ImageIcon(Objects.requireNonNull(model.getClass().getResource("Star.png")));
        sb.append(username);
        lines.add(sb.toString());
        lines.add("\n");
        for (int x = 0; x< Constant.CHESSBOARD_ROW_SIZE.getNum(); x++) {
            sb.setLength(0);
            for (int y=0; y<Constant.CHESSBOARD_COL_SIZE.getNum(); y++) {
                if(model.getChessPieceAt(new ChessboardPoint(x,y))==null)
                    sb.append("n");
                else if(model.getChessPieceAt(new ChessboardPoint(x,y)).getName().toString().equals(new ChessPiece(image1).getName().toString()))
                    sb.append("1");
                else if(model.getChessPieceAt(new ChessboardPoint(x,y)).getName().toString().equals(new ChessPiece(image2).getName().toString()))
                    sb.append("2");
                else if(model.getChessPieceAt(new ChessboardPoint(x,y)).getName().toString().equals(new ChessPiece(image3).getName().toString()))
                    sb.append("3");
                else if(model.getChessPieceAt(new ChessboardPoint(x,y)).getName().toString().equals(new ChessPiece(image4).getName().toString()))
                    sb.append("4");
                else if(model.getChessPieceAt(new ChessboardPoint(x,y)).getName().toString().equals(new ChessPiece(image5).getName().toString()))
                    sb.append("5");

                sb.append(",");
            }
            sb.setLength(sb.length() - 1);
            lines.add(sb.toString());
            lines.add("\n");
        }
        sb.setLength(0);
        sb.append(level).append(",").append(steps).append(",").append(score).append(",").append(scoretarget).append(",").append(nextstep).append(",").append(turncount).append(",").append(donecheck2).append(",").append(shuffle).append(",").append(turncount).append(",").append(donecheck).append(",").append(hint);
        lines.add(sb.toString());
        return lines;
    }


    public void loadGameFromFile(String path){
        try{
            List<String> lines = Files.readAllLines(Path.of(path));

            String[] values = lines.get(9).split(",");
            for(int x=0; x<11; x++){

                if(x==0)
                    level = Integer.parseInt(values[x]);
                else if(x==1)
                    steps = Integer.parseInt(values[x]);
                else if(x==2)
                    score = Integer.parseInt(values[x]);
                else if(x==3)
                    scoretarget = Integer.parseInt(values[x]);
                else if(x==4)
                    nextstep = Integer.parseInt(values[x]);
                else if(x==5)
                    turncount = Integer.parseInt(values[x]);
                else if(x==6)
                    donecheck2 = Integer.parseInt(values[x]);
                else if(x==7)
                    shuffle = Integer.parseInt(values[x]);
                else if(x==8)
                    turncount = Integer.parseInt(values[x]);
                else if(x==9)
                    donecheck = Integer.parseInt(values[x]);
                else
                    hint = Integer.parseInt(values[x]);
            }
            model.removeAllChessPiece();
            view.removeAllChessComponentsAtGrids();
            model.initPiecebyFile(lines);
            view.initiateChessComponent(model);
            statusLabel.setText("Score: " + score + "/" + scoretarget);
            statusLabel2.setText("" + steps);
            statuslabel3.setText("Level " + level);
            statuslabel4.setText("SHUFFLE: " + shuffle);
            statuslabel5.setText("HINT: " + hint);
            view.repaint();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void restart(){
        model.removeAllChessPiece();
        model.initPieces();
        view.removeAllChessComponentsAtGrids();
        view.initiateChessComponent(model);
        view.repaint();
        score=0;
        steps = liststeps[level-1];
        nextstep=-1;
        donecheck2=0;
        donecheck=0;
        turncount=0;
        match=0;
        openNextLevel=false;
        statusLabel.setText("Score: " + score + "/" + scoretarget);
        statusLabel2.setText("" + steps);
        statuslabel3.setText("Level " + level);
    }
    public void restart2(){
        model.removeAllChessPiece();
        model.initPieces();
        view.removeAllChessComponentsAtGrids();
        view.initiateChessComponent(model);
        view.repaint();
        nextstep=-1;
        donecheck2=0;
        donecheck=0;
        turncount=0;
        score = 0;
        shuffle=3;
        hint=3;
        steps = liststeps[level-1];
        statusLabel.setText("Score: " + score + "/" + scoretarget);
        statusLabel2.setText("" + steps);
        statuslabel3.setText("Level " + level);
    }
    public void shuffles(){
        if(timer.isRunning() || timer2.isRunning())
            return;
        if(shuffle==0)
        {
            JOptionPane.showMessageDialog(view, "No Shuffles Left");
            return;
        }
        model.removeAllChessPiece();
        model.initPieces();
        view.removeAllChessComponentsAtGrids();
        view.initiateChessComponent(model);
        view.repaint();
        arrayrow1.clear();
        arraycol1.clear();
        nextstep=-1;
        turncount=0;
        shuffle--;
        view.repaint();
        statuslabel4.setText("SHUFFLE: " + shuffle);
    }
    public void asasblack() {
        selectedPoint2=null;
        selectedPoint=null;
        if(hint==0)
        {
            JOptionPane.showMessageDialog(view, "NO MORE HINTS BRUH");
            return;
        }
        if(turncount>0)
        {
            return;
        }
        for (int col = 0; col < Constant.CHESSBOARD_COL_SIZE.getNum(); col++) {
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                int j = i+1;
                if(j>=Constant.CHESSBOARD_ROW_SIZE.getNum())
                    continue;
                if (model.getChessPieceAt(new ChessboardPoint(i, col)) == null || model.getChessPieceAt(new ChessboardPoint(j, col)) == null) {
                    break;
                }
                ChessPiece chessPieceAt1 = model.getChessPieceAt(new ChessboardPoint(i, col));
                ChessPiece chessPieceAt2 = model.getChessPieceAt(new ChessboardPoint(j, col));
                ChessPiece chessPieceAt3;
                if (chessPieceAt1.getName().toString().equals(chessPieceAt2.getName().toString())) {
                    if (i - 2 >= 0) {
                        chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(i - 2, col));
                        if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                            onPlayerClickChessPiece(new ChessboardPoint(i - 1, col), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(i - 1, col)).getComponent(0) );
                            onPlayerClickChessPiece(new ChessboardPoint(i - 2, col), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(i - 2, col)).getComponent(0) );
                            hint--;
                            statuslabel5.setText("HINT: " + hint);
                            return;
                        }
                    }
                    if (i - 1 >= 0)
                    {
                        if(col-1>=0)
                        {
                            chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(i-1, col-1));
                            if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {

                                onPlayerClickChessPiece(new ChessboardPoint(i - 1, col-1), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(i - 1, col-1)).getComponent(0) );
                                onPlayerClickChessPiece(new ChessboardPoint(i - 1, col), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(i - 1, col)).getComponent(0) );
                                hint--;
                                statuslabel5.setText("HINT: " + hint);
                                return;
                            }
                        }
                        if(col+1<=Constant.CHESSBOARD_COL_SIZE.getNum()-1)
                        {
                            chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(i-1, col+1));
                            if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {

                                onPlayerClickChessPiece(new ChessboardPoint(i - 1, col+1), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(i - 1, col+1)).getComponent(0) );
                                onPlayerClickChessPiece(new ChessboardPoint(i - 1, col), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(i - 1, col)).getComponent(0) );
                                hint--;
                                statuslabel5.setText("HINT: " + hint);
                                return;
                            }
                        }
                    }
                    if (j + 2 <= Constant.CHESSBOARD_ROW_SIZE.getNum() - 1) {
                        chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(j + 2, col));
                        if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                            onPlayerClickChessPiece(new ChessboardPoint(j+1, col), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(j+1, col)).getComponent(0) );
                            onPlayerClickChessPiece(new ChessboardPoint(j+2, col), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(j+2, col)).getComponent(0) );
                            hint--;
                            statuslabel5.setText("HINT: " + hint);
                            return;
                        }
                    }
                    if (j + 1 <= Constant.CHESSBOARD_ROW_SIZE.getNum() - 1)
                    {
                        if(col-1>=0)
                        {
                            chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(j+1, col-1));
                            if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                                onPlayerClickChessPiece(new ChessboardPoint(j+1, col-1), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(j+1, col-1)).getComponent(0) );
                                onPlayerClickChessPiece(new ChessboardPoint(j+1, col), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(j+1, col)).getComponent(0) );
                                hint--;
                                statuslabel5.setText("HINT: " + hint);
                                return;
                            }
                        }
                        if(col+1<=Constant.CHESSBOARD_COL_SIZE.getNum()-1)
                        {
                            chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(j+1, col+1));
                            if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                                onPlayerClickChessPiece(new ChessboardPoint(j+1, col+1), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(j+1, col+1)).getComponent(0) );
                                onPlayerClickChessPiece(new ChessboardPoint(j+1, col), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(j+1, col)).getComponent(0) );
                                hint--;
                                statuslabel5.setText("HINT: " + hint);
                                return;
                            }
                        }
                    }
                }
            }
        }
        for (int row = 0; row < Constant.CHESSBOARD_ROW_SIZE.getNum(); row++) {
            for (int i = 0; i < Constant.CHESSBOARD_COL_SIZE.getNum(); i++) {
                match = 0;
                int j = i+1;
                if(j>=Constant.CHESSBOARD_COL_SIZE.getNum())
                    continue;
                if (model.getChessPieceAt(new ChessboardPoint(row, i)) == null || model.getChessPieceAt(new ChessboardPoint(row, j)) == null) {
                    break;
                }
                ChessPiece chessPieceAt1 = model.getChessPieceAt(new ChessboardPoint(row, i));
                ChessPiece chessPieceAt2 = model.getChessPieceAt(new ChessboardPoint(row, j));
                ChessPiece chessPieceAt3;
                if (chessPieceAt1.getName().toString().equals(chessPieceAt2.getName().toString())) {
                    if (i - 2 >= 0) {
                        chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(row, i-2));
                        if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                            onPlayerClickChessPiece(new ChessboardPoint(row, i-1), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(row, i-1)).getComponent(0) );
                            onPlayerClickChessPiece(new ChessboardPoint(row, i-2), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(row, i-2)).getComponent(0) );
                            hint--;
                            statuslabel5.setText("HINT: " + hint);
                            return;
                        }
                    }
                    if (i - 1 >= 0)
                    {
                        if(row-1>=0)
                        {
                            chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(row-1, i-1));
                            if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                                onPlayerClickChessPiece(new ChessboardPoint(row-1, i-1), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(row-1, i-1)).getComponent(0) );
                                onPlayerClickChessPiece(new ChessboardPoint(row, i-1), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(row, i-1)).getComponent(0) );
                                hint--;
                                statuslabel5.setText("HINT: " + hint);
                                return;
                            }
                        }
                        if(row+1<=Constant.CHESSBOARD_ROW_SIZE.getNum()-1)
                        {
                            chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(row+1, i-1));
                            if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                                onPlayerClickChessPiece(new ChessboardPoint(row+1, i-1), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(row+1, i-1)).getComponent(0) );
                                onPlayerClickChessPiece(new ChessboardPoint(row, i-1), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(row, i-1)).getComponent(0) );
                                hint--;
                                statuslabel5.setText("HINT: " + hint);
                                return;
                            }
                        }
                    }
                    if (j + 2 <= Constant.CHESSBOARD_COL_SIZE.getNum() - 1) {
                        chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(row, j+2));
                        if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                            onPlayerClickChessPiece(new ChessboardPoint(row, j+2), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(row, j+2)).getComponent(0) );
                            onPlayerClickChessPiece(new ChessboardPoint(row, j+1), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(row, j+1)).getComponent(0) );
                            hint--;
                            statuslabel5.setText("HINT: " + hint);
                            return;
                        }
                    }
                    if (j + 1 <= Constant.CHESSBOARD_COL_SIZE.getNum() - 1)
                    {
                        if(row-1>=0)
                        {
                            chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(row-1, j+1));
                            if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                                onPlayerClickChessPiece(new ChessboardPoint(row-1, j+1), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(row-1, j+1)).getComponent(0) );
                                onPlayerClickChessPiece(new ChessboardPoint(row, j+1), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(row, j+1)).getComponent(0) );
                                hint--;
                                statuslabel5.setText("HINT: " + hint);
                                return;
                            }
                        }
                        if(row+1<=Constant.CHESSBOARD_ROW_SIZE.getNum()-1)
                        {
                            chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(row+1, j+1));
                            if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                                onPlayerClickChessPiece(new ChessboardPoint(row+1, j+1), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(row+1, j+1)).getComponent(0) );
                                onPlayerClickChessPiece(new ChessboardPoint(row, j+1), (ChessComponent) view.getGridComponentAt(new ChessboardPoint(row, j+1)).getComponent(0) );
                                hint--;
                                statuslabel5.setText("HINT: " + hint);
                                return;
                            }
                        }
                    }
                }
                JOptionPane.showMessageDialog(view, "NO MORE MOVES");
            }
        }
    }
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
    }

    @Override
    public void onPlayerSwapChess() {
        if(turncount>0){
            JOptionPane.showMessageDialog(view, "WHY U CLICK SWAP BRUH");
            return;
        }
        if(selectedPoint==null || selectedPoint2==null) {
            return;
        }
        if(timer.isRunning() || timer2.isRunning())
        {
            return;
        }
        nextstep = -1;
        // TODO: Init your swap function here.
        model.swapChessPiece(selectedPoint2, selectedPoint);
        view.setChessComponentAtGrid(selectedPoint2, (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0));
        view.setChessComponentAtGrid(selectedPoint, (ChessComponent) view.getGridComponentAt(selectedPoint2).getComponent(0));
        ChessComponent bruh1 = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
        ChessComponent bruh2 = (ChessComponent) view.getGridComponentAt(selectedPoint2).getComponent(0);
        ChessboardPoint wow2 = selectedPoint;
        ChessboardPoint wow1 = selectedPoint2;

        ((ChessComponent) view.getGridComponentAt(selectedPoint2).getComponent(0)).setSelected(false);
        ((ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0)).setSelected(false);
        selectedPoint = null;
        selectedPoint2 = null;
        donecheck=0;



        int elimrow = 0;
        int elimcol = 0;
        for (int col = 0; col<Constant.CHESSBOARD_COL_SIZE.getNum(); col++){
            for (int i = 0; i<Constant.CHESSBOARD_ROW_SIZE.getNum(); i++){
                match = 0;
                for (int j = i+1; j < Constant.CHESSBOARD_ROW_SIZE.getNum(); j++){
                    if (model.getChessPieceAt(new ChessboardPoint(i, col)) == null || model.getChessPieceAt(new ChessboardPoint(j, col)) == null){
                        break;
                    }
                    ChessPiece chessPieceAt1 = model.getChessPieceAt(new ChessboardPoint(i, col));
                    ChessPiece chessPieceAt2 = model.getChessPieceAt(new ChessboardPoint(j, col));
                    if (chessPieceAt1.getName().toString().equals(chessPieceAt2.getName().toString())){
                        match +=1;
                        elimrow = j;
                    }
                    else{
                        break;
                    }

                }
                if(match > 1){
                    for (int d = 0; d<=match; d++){
                        elimrow--;
                        arrayrow1.add(elimrow+1);
                        arraycol1.add(col);
                        donecheck++;
                    }
                }
            }
        }
        for (int row = 0; row<Constant.CHESSBOARD_ROW_SIZE.getNum(); row++){
            for (int i = 0; i<Constant.CHESSBOARD_COL_SIZE.getNum(); i++){
                match = 0;
                for (int j = i+1; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++){
                    if (model.getChessPieceAt(new ChessboardPoint(row, i)) == null || model.getChessPieceAt(new ChessboardPoint(row, j)) == null){
                        break;
                    }
                    ChessPiece chessPieceAt1 = model.getChessPieceAt(new ChessboardPoint(row, i));
                    ChessPiece chessPieceAt2 = model.getChessPieceAt(new ChessboardPoint(row, j));
                    if (chessPieceAt1.getName().toString().equals(chessPieceAt2.getName().toString())){
                        match +=1;
                        elimcol = j;
                    }
                    else{
                        break;
                    }

                }
                if(match > 1){
                    for (int d = 0; d<=match; d++){
                        elimcol--;
                        arrayrow1.add(row);
                        arraycol1.add(elimcol+1);
                        donecheck++;
                    }
                }
            }
        }
        if(donecheck==0)
        {
            model.swapChessPiece(wow1, wow2);
            view.setChessComponentAtGrid(wow1, bruh1);
            view.setChessComponentAtGrid(wow2, bruh2);
            ((ChessComponent) view.getGridComponentAt(wow1).getComponent(0)).setSelected(false);
            ((ChessComponent) view.getGridComponentAt(wow2).getComponent(0)).setSelected(false);
            wow1 = null;
            wow2 = null;
            Warning warning = new Warning();
            warning.setLocationRelativeTo(null);
            playErrorSound();
            nextstep=-500;
        }
        else
        {
            nextstep = -1;
            turncount++;
            donecheck = 0;
            if(auto)
                onPlayerNextStep();
        }
    }

    @Override
    public void onPlayerNextStep() {
        // TODO: PNS
        if(auto && nextstep<-1)
        {
            JOptionPane.showMessageDialog(view, "UR IN AUTOMODE U DUMBASS"); //AUDIO
            return;
        }
        if(turncount==0)
        {
            JOptionPane.showMessageDialog(view, "WHY U PRESS NEXTSTEP U DUMB"); //AUDIO
            return;
        }
        if(timer.isRunning() || timer2.isRunning())
            return;
        if(donecheck2>0)
            return;

        nextstep++;
        if (nextstep == 0)
        {
            playPopSound();
            for (int d = 0; d<arrayrow1.size(); d++){
                ChessPiece chessPieceAt1 = model.getChessPieceAt(new ChessboardPoint(arrayrow1.get(d), arraycol1.get(d)));

                if(chessPieceAt1!=null)
                {
                    model.removeChessPiece(new ChessboardPoint(arrayrow1.get(d), arraycol1.get(d)));
                    view.removeChessComponentAtGrid(new ChessboardPoint(arrayrow1.get(d), arraycol1.get(d)));
                    score +=10;
                }
            }
            arrayrow1.clear();
            arraycol1.clear();

            if (score >= scoretarget){
                score = scoretarget;
                if(auto) {
                    NextLevel nn = new NextLevel(chessGameFrame);
                    nn.setLocationRelativeTo(null);
                    auto=false;
                }
                else
                    openNextLevel = true;
            }
            if(autocounter==0)
                steps--;
            if(steps<0)
            {
                NextLevel nn = new NextLevel(chessGameFrame);
                nn.setLocationRelativeTo(null);
                return;
            }
            this.statusLabel.setText("Score: " + score + "/" + scoretarget);
            this.statusLabel2.setText("" + steps);
            this.statuslabel3.setText("Level " + level);
            view.repaint();
            if(auto && !openNextLevel)
                nextstep++;
        }
        if(nextstep==1)
        {
            int bruh2 = 0;
            int bruh3 = 0;
            for (int y = 0; y < Constant.CHESSBOARD_COL_SIZE.getNum(); y++) {
                int bruh = 0;
                int bruh1 = 0;
                for (int z = Constant.CHESSBOARD_ROW_SIZE.getNum() - 1; z >= 0; z--) {
                    if (model.getChessPieceAt(new ChessboardPoint(z, y)) == null && z - 1 - bruh >= 0) {
                        bruh3++;
                        while (model.getChessPieceAt(new ChessboardPoint(z - 1 - bruh, y)) == null) {
                            bruh++;
                            if (z - 1 - bruh < 0)
                                break;
                        }
                        if (bruh == z)
                            break;

                        if (z-1-bruh >= 0)
                        {
                            if (model.getChessPieceAt(new ChessboardPoint(z-1-bruh, y)) == null && z - 1 - bruh > 0)
                            {
                                while(model.getChessPieceAt(new ChessboardPoint(z-1-bruh, y)) == null)
                                {
                                    bruh++;
                                    if (z - 1 - bruh < 0)
                                        break;
                                }
                            }
                            this.bruh = bruh;
                            x.add(z-1-bruh);
                            this.y.add(y);
                            this.z.add(z);
                            chessComponent = (ChessComponent) view.getGridComponentAt(new ChessboardPoint(z - 1 - bruh, y)).getComponent(0);
                            chessComponents.add(chessComponent);
                            model.swapChessPiece(new ChessboardPoint(z-1-bruh, y), new ChessboardPoint(z, y));
                            bruh1++;
                        }
                    }
                    else if (z-1-bruh >= 0 && bruh1 != 0)
                    {
                        if (model.getChessPieceAt(new ChessboardPoint(z-1-bruh, y)) == null && z - 1 - bruh > 0)
                        {
                            while(model.getChessPieceAt(new ChessboardPoint(z-1-bruh, y)) == null)
                            {
                                bruh++;
                                if (z - 1 - bruh < 0)
                                    break;
                            }
                        }
                        this.bruh = bruh;
                        x.add(z-1-bruh);
                        this.y.add(y);
                        this.z.add(z);
                        chessComponent = (ChessComponent) view.getGridComponentAt(new ChessboardPoint(z-1-bruh, y)).getComponent(0);
                        chessComponents.add(chessComponent);
                        model.swapChessPiece(new ChessboardPoint(z-1-bruh, y), new ChessboardPoint(z, y));
             /*   view.setChessComponentAtGrid(new ChessboardPoint(z, y), chessComponent);
                view.repaint();*/
                    }
                }
           /*  if (bruh2 > 0)
                    continue;
                else if (bruh1 > 0) {
                    bruh2 = 1;
                    nextstep = -1;
                    continue;
                }*/
                //nextstep = 1;
            }
            for(int d = 0; d < z.size(); d++) {
            }
            timer.start();
        }
        if(nextstep>1 && !auto)
            frfrfr();
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        // TODO
        if (selectedPoint2 != null) {
            var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());
            var distance2point2 = Math.abs(selectedPoint2.getCol() - point.getCol()) + Math.abs(selectedPoint2.getRow() - point.getRow());
            var point1 = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            var point2 = (ChessComponent) view.getGridComponentAt(selectedPoint2).getComponent(0);
            if (distance2point1 == 0 && point1 != null) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = null;
            } else if (distance2point2 == 0 && point2 != null) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = null;
            } else if (distance2point1 == 1 && point2 != null) {
                point2.setSelected(false);
                point2.repaint();
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            } else if (distance2point2 == 1 && point1 != null) {
                point1.setSelected(false);
                point1.repaint();
                selectedPoint = selectedPoint2;
                selectedPoint2 = point;
                component.setSelected(true);
                component.repaint();
            }
            return;
        }


        if (selectedPoint == null) {
            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
            return;
        }

        var distance2point1 = Math.abs(selectedPoint.getCol() - point.getCol()) + Math.abs(selectedPoint.getRow() - point.getRow());

        if (distance2point1 == 0) {
            selectedPoint = null;
            component.setSelected(false);
            component.repaint();
            return;
        }

        if (distance2point1 == 1) {
            selectedPoint2 = point;
            component.setSelected(true);
            component.repaint();
        } else {
            selectedPoint2 = null;

            var grid = (ChessComponent) view.getGridComponentAt(selectedPoint).getComponent(0);
            if (grid == null) return;
            grid.setSelected(false);
            grid.repaint();

            selectedPoint = point;
            component.setSelected(true);
            component.repaint();
        }

        if(selectedPoint!=null && selectedPoint2!=null && auto)
            onPlayerSwapChess();
    }
    public void frfrfr1(){
        // TODO
        int elimrow = 0;
        int elimcol = 0;
        for (int col = 0; col<Constant.CHESSBOARD_COL_SIZE.getNum(); col++){
            for (int i = 0; i<Constant.CHESSBOARD_ROW_SIZE.getNum(); i++){
                match = 0;
                for (int j = i+1; j < Constant.CHESSBOARD_ROW_SIZE.getNum(); j++){
                    if (model.getChessPieceAt(new ChessboardPoint(i, col)) == null || model.getChessPieceAt(new ChessboardPoint(j, col)) == null){
                        break;
                    }
                    ChessPiece chessPieceAt1 = model.getChessPieceAt(new ChessboardPoint(i, col));
                    ChessPiece chessPieceAt2 = model.getChessPieceAt(new ChessboardPoint(j, col));
                    if (chessPieceAt1.getName().toString().equals(chessPieceAt2.getName().toString())){
                        match +=1;
                        elimrow = j;
                    }
                    else{
                        break;
                    }

                }
                if(match > 1){
                    for (int d = 0; d<=match; d++){
                        elimrow--;
                        arrayrow1.add(elimrow+1);
                        arraycol1.add(col);
                        donecheck++;
                    }
                }
            }
        }
        for (int row = 0; row<Constant.CHESSBOARD_ROW_SIZE.getNum(); row++){
            for (int i = 0; i<Constant.CHESSBOARD_COL_SIZE.getNum(); i++){
                match = 0;
                for (int j = i+1; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++){
                    if (model.getChessPieceAt(new ChessboardPoint(row, i)) == null || model.getChessPieceAt(new ChessboardPoint(row, j)) == null){
                        break;
                    }
                    ChessPiece chessPieceAt1 = model.getChessPieceAt(new ChessboardPoint(row, i));
                    ChessPiece chessPieceAt2 = model.getChessPieceAt(new ChessboardPoint(row, j));
                    if (chessPieceAt1.getName().toString().equals(chessPieceAt2.getName().toString())){
                        match +=1;
                        elimcol = j;
                    }
                    else{
                        break;
                    }

                }
                if(match > 1){
                    for (int d = 0; d<=match; d++){
                        elimcol--;
                        arrayrow1.add(row);
                        arraycol1.add(elimcol+1);
                        donecheck++;
                    }
                }
            }
        }
        if(donecheck==0)
        {
            turncount=0;
            autocounter=0;
            nextstep=-500;
            boolean b=false;
            while(!checkss(b))
            {
                JOptionPane.showMessageDialog(view, "NO MOVES LMAO L+RATIO");
                model.removeAllChessPiece();
                model.initPieces();
                view.removeAllChessComponentsAtGrids();
                view.initiateChessComponent(model);
                view.repaint();
            }
        }
        else
        {
            nextstep=-1;
            turncount++;
            donecheck=0;
            donecheck2=0;
            autocounter=1;
            if(auto)
                onPlayerNextStep();
        }
    }
    public void frfrfr(){
        //TODO FRFR
        ImageIcon image1 = new ImageIcon(Objects.requireNonNull(model.getClass().getResource("Diamond.png")));
        ImageIcon image2 = new ImageIcon(Objects.requireNonNull(model.getClass().getResource("Circle.png")));
        ImageIcon image3 = new ImageIcon(Objects.requireNonNull(model.getClass().getResource("Hexagon.png")));
        ImageIcon image4 = new ImageIcon(Objects.requireNonNull(model.getClass().getResource("Triangle.png")));
        ImageIcon image5 = new ImageIcon(Objects.requireNonNull(model.getClass().getResource("Star.png")));
        ImageIcon [] shapes2 = {image1, image2, image3, image4, image5};
        ArrayList<ImageIcon> bro = new ArrayList<>();
        for(int x=0; x<16;x++)
            bro.add(Util.RandomPick(shapes2));
        int yy=-1;
        for (int i = 0; i < Constant.CHESSBOARD_COL_SIZE.getNum(); i++) {
            for (int j = Constant.CHESSBOARD_ROW_SIZE.getNum()-1; j>=0; j--) {
                if (model.getChessPieceAt(new ChessboardPoint(i, j)) == null)
                {
                    yy++;
                    model.setChessPiece(new ChessboardPoint(i, j), new ChessPiece(bro.get(yy)));
                    view.setChessComponentAtGrid(new ChessboardPoint(i,j), new ChessComponent(72, new ChessPiece(bro.get(yy))));
                    this.yy.add(j);
                    this.zz.add(i);
                    chessComponent = (ChessComponent) view.getGridComponentAt(new ChessboardPoint(i, j)).getComponent(0);
                    chessComponentss.add(chessComponent);
                    view.removeChessComponentAtGrid(new ChessboardPoint(i,j));
                }
            }
        }
        next1 = chessComponentss.size()-1;
        bro.clear();
        int elimrow = 0;
        int elimcol = 0;
        donecheck=0;
        for (int col = 0; col<Constant.CHESSBOARD_COL_SIZE.getNum(); col++){
            for (int i = 0; i<Constant.CHESSBOARD_ROW_SIZE.getNum(); i++){
                match = 0;
                for (int j = i+1; j < Constant.CHESSBOARD_ROW_SIZE.getNum(); j++)
                {
                    ChessPiece chessPieceAt1 = model.getChessPieceAt(new ChessboardPoint(i, col));
                    ChessPiece chessPieceAt2 = model.getChessPieceAt(new ChessboardPoint(j, col));
                    if (chessPieceAt1.getName().toString().equals(chessPieceAt2.getName().toString())){
                        match +=1;
                        elimrow = j;
                    }
                    else break;
                }
                if(match > 1)
                {
                    for (int d = 0; d<=match; d++){
                        elimrow--;
                        arrayrow1.add(elimrow+1);
                        arraycol1.add(col);
                        donecheck++;
                    }
                }
            }
        }
        for (int row = 0; row<Constant.CHESSBOARD_ROW_SIZE.getNum(); row++){
            for (int i = 0; i<Constant.CHESSBOARD_COL_SIZE.getNum(); i++){
                match = 0;
                for (int j = i+1; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++){

                    ChessPiece chessPieceAt1 = model.getChessPieceAt(new ChessboardPoint(row, i));
                    ChessPiece chessPieceAt2 = model.getChessPieceAt(new ChessboardPoint(row, j));
                    if (chessPieceAt1.getName().toString().equals(chessPieceAt2.getName().toString())){
                        match +=1;
                        elimcol = j;
                    }
                    else break;
                }
                if(match > 1)
                {
                    for (int d = 0; d<=match; d++){
                        elimcol--;
                        arrayrow1.add(row);
                        arraycol1.add(elimcol+1);
                        donecheck++;
                    }
                }
            }
        }
        if(donecheck==0)
        {
            turncount = 0;
            nextstep = -500;
            timer2.start();
        }
        else
        {
            nextstep=-1;
            if(!auto)
                turncount++;
            donecheck=0;
            timer2.start();
        }
    }
    public boolean checkss(boolean b){
        for (int col = 0; col < Constant.CHESSBOARD_COL_SIZE.getNum(); col++) {
            for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
                int j = i+1;
                if(j>=Constant.CHESSBOARD_ROW_SIZE.getNum())
                    continue;
                if (model.getChessPieceAt(new ChessboardPoint(i, col)) == null || model.getChessPieceAt(new ChessboardPoint(j, col)) == null) {
                    break;
                }
                ChessPiece chessPieceAt1 = model.getChessPieceAt(new ChessboardPoint(i, col));
                ChessPiece chessPieceAt2 = model.getChessPieceAt(new ChessboardPoint(j, col));
                ChessPiece chessPieceAt3;
                if (chessPieceAt1.getName().toString().equals(chessPieceAt2.getName().toString())) {
                    if (i - 2 >= 0) {
                        chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(i - 2, col));
                        if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                            b=true;
                            return b;
                        }
                    }
                    if (i - 1 >= 0)
                    {
                        if(col-1>=0)
                        {
                            chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(i-1, col-1));
                            if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                                b=true;
                                return b;
                            }
                        }
                        if(col+1<=Constant.CHESSBOARD_COL_SIZE.getNum()-1)
                        {
                            chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(i-1, col+1));
                            if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                                b=true;
                                return b;
                            }
                        }
                    }
                    if (j + 2 <= Constant.CHESSBOARD_ROW_SIZE.getNum() - 1) {
                        chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(j + 2, col));
                        if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                            b=true;
                            return b;
                        }
                    }
                    if (j + 1 <= Constant.CHESSBOARD_ROW_SIZE.getNum() - 1)
                    {
                        if(col-1>=0)
                        {
                            chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(j+1, col-1));
                            if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                                b=true;
                                return b;
                            }
                        }
                        if(col+1<=Constant.CHESSBOARD_COL_SIZE.getNum()-1)
                        {
                            chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(j+1, col+1));
                            if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                                b=true;
                                return b;
                            }
                        }
                    }
                }
            }
        }
        for (int row = 0; row < Constant.CHESSBOARD_ROW_SIZE.getNum(); row++) {
            for (int i = 0; i < Constant.CHESSBOARD_COL_SIZE.getNum(); i++) {
                match = 0;
                int j = i + 1;
                if (j >= Constant.CHESSBOARD_COL_SIZE.getNum())
                    continue;
                if (model.getChessPieceAt(new ChessboardPoint(row, i)) == null || model.getChessPieceAt(new ChessboardPoint(row, j)) == null) {
                    break;
                }
                ChessPiece chessPieceAt1 = model.getChessPieceAt(new ChessboardPoint(row, i));
                ChessPiece chessPieceAt2 = model.getChessPieceAt(new ChessboardPoint(row, j));
                ChessPiece chessPieceAt3;
                if (chessPieceAt1.getName().toString().equals(chessPieceAt2.getName().toString())) {
                    if (i - 2 >= 0) {
                        chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(row, i - 2));
                        if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                            b=true;
                            return b;
                        }
                    }
                    if (i - 1 >= 0) {
                        if (row - 1 >= 0) {
                            chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(row - 1, i - 1));
                            if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                                b=true;
                                return b;
                            }
                        }
                        if (row + 1 <= Constant.CHESSBOARD_ROW_SIZE.getNum() - 1) {
                            chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(row + 1, i - 1));
                            if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                                b=true;
                                return b;
                            }
                        }
                    }
                    if (j + 2 <= Constant.CHESSBOARD_COL_SIZE.getNum() - 1) {
                        chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(row, j + 2));
                        if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                            b=true;
                            return b;
                        }
                    }
                    if (j + 1 <= Constant.CHESSBOARD_COL_SIZE.getNum() - 1) {
                        if (row - 1 >= 0) {
                            chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(row - 1, j + 1));
                            if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                                b=true;
                                return b;
                            }
                        }
                        if (row + 1 <= Constant.CHESSBOARD_ROW_SIZE.getNum() - 1) {
                            chessPieceAt3 = model.getChessPieceAt(new ChessboardPoint(row + 1, j + 1));
                            if (chessPieceAt1.getName().toString().equals(chessPieceAt3.getName().toString())) {
                                b=true;
                                return b;
                            }
                        }
                    }
                }
            }
        }
        return b;
    }
    public void ActionPerformed (ActionEvent e){
        // TODO : N
        count+=1;
        if(next >= chessComponents.size()){
            frfrfr();
            timer.stop();
            x.clear();
            z.clear();
            y.clear();
            next = 0;
            chessComponents.clear();
            count = 0;
            load = true;
        }
        view.setChessComponentAtGrid(new ChessboardPoint(x.get(next)+count, y.get(next)), chessComponents.get(next));
        if (x.get(next)+count == z.get(next)){
            next++;
            count = 0;
        }
        view.repaint();
    }
    public void ActionPerformed2 (ActionEvent e){
        //TODO N2
        count1++;
        if(next1 < 0){
            xx.clear();
            zz.clear();
            yy.clear();
            chessComponentss.clear();
            count1 = -1;
            load1 = true;
            timer2.stop();
            frfrfr1();
        }
        view.setChessComponentAtGrid(new ChessboardPoint(count1, yy.get(next1)), chessComponentss.get(next1));
        if (count1 == zz.get(next1)){
            next1--;
            count1 = -1;
        }
        view.repaint();
    }
    private void initializePopSoundEffect() {
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("pop.wav")));
            popSoundClip = AudioSystem.getClip();
            popSoundClip.open(audioInput);
            FloatControl gainControl2 = (FloatControl) popSoundClip.getControl(FloatControl.Type.MASTER_GAIN);
            float gain = (float) (Math.log(Settings.getInitialSliderLocation() / 100.0) / Math.log(10.0) * 20.0);
            gainControl2.setValue(gain+2);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
            System.out.println("Error initializing pop sound effect.");
        }
    }
    private void playPopSound() {
        if (popSoundClip != null) {
            popSoundClip.setFramePosition(0);
            popSoundClip.start();
        }
    }
    private void playErrorSound() {
        if (popSoundClip2 != null) {
            popSoundClip2.setFramePosition(0);
            popSoundClip2.start();
        }
    }
    public void setPopSoundVolume(int volume) {
        FloatControl gainControl2 = (FloatControl) popSoundClip.getControl(FloatControl.Type.MASTER_GAIN);
        FloatControl gainControl = (FloatControl) popSoundClip2.getControl(FloatControl.Type.MASTER_GAIN);
        float gain = (float) (Math.log(volume / 100.0) / Math.log(10.0) * 20.0);
        gainControl2.setValue(gain+2);
        gainControl.setValue(gain+6);

    }

    public void setStatuslabel4(JLabel statuslabe4) {
        this.statuslabel4 = statuslabe4;
    }
    public void setStatuslabel5(JLabel statuslabe5) {
        this.statuslabel5 = statuslabe5;
    }

}


