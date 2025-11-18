package view;

import controller.GameController;
import model.ChessPiece;
import model.Chessboard;
import model.ChessboardPoint;
import model.Constant;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.spi.ResourceBundleControlProvider;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame implements ActionListener{
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;

    private final int ONE_CHESS_SIZE;
    private final Timer enlargeTimer2;
    private final Timer shrinkTimer2;
    private JLabel background;
    private GameController gameController;

    private ChessboardComponent chessboardComponent;

    private JLabel statusLabel;
    private JLabel statuslabel2;
    private ImageIcon WoodenTexture = new ImageIcon(Objects.requireNonNull(getClass().getResource("texture.png")));
    private int targetscore = 0;
    private int steps;
    private JButton settings;
    private static int level;
    private int shuffle=3;
    private JLabel statuslabel3;
    public boolean onNextStepLevel;
    NextLevel nextLevel;
    private Timer fallTimer;
    private int fallSpeed;
    public Clip backgroundMusicClip2;
    private Settings settingsWindow;
    private Timer enlargeTimer;
    private Timer shrinkTimer;
    private JButton Return;
    private int buttonOriginalWidth;
    private int buttonOriginalHeight;
    private ImageIcon originalImageIcon;
    private ImageIcon enlargedImageIcon;
    private ImageIcon originalImageIcon2;
    private boolean bich = false;
    private Clip ButtonSound;
    private int shufflecount = 3;
    private int hintcount = 3;
    private JLabel statuslabel4;
    private JLabel statuslabel5;

    public ChessGameFrame(int width, int height) {
        enlargeTimer = new Timer(15, this);
        enlargeTimer.setInitialDelay(0);
        enlargeTimer.setCoalesce(true);
        shrinkTimer = new Timer(15, this);
        shrinkTimer.setInitialDelay(0);
        shrinkTimer.setCoalesce(true);
        enlargeTimer2 = new Timer(15, this);
        enlargeTimer2.setInitialDelay(0);
        enlargeTimer2.setCoalesce(true);
        shrinkTimer2 = new Timer(15, this);
        shrinkTimer2.setInitialDelay(0);
        shrinkTimer2.setCoalesce(true);
        setTitle("2023 CS109 Project Demo"); //设置标题
        this.targetscore =500;
        this.steps =10;
        fallSpeed = 5;
        JPanel panel = new JPanel();
        panel.setLayout(new SpringLayout());
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        level = LaunchPage.getLevel();
        if (NextLevel.getLevel()  != 0){
            level = NextLevel.getLevel();
        }
        if(Loaded.getLevel() != 0){
            level = Loaded.getLevel();
        }
        if (level == 1){
            background = new JLabel(new ImageIcon(getClass().getResource("Background6.gif")));
            background.setBounds(0, 0, 2170, 2000);
            background.setLocation(-330, -500);
            layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
            initializeBackgroundMusic("music2.wav");
            targetscore = 500;
            steps = 10;
        }
        if (level == 2){
            background = new JLabel(new ImageIcon(getClass().getResource("Background5.gif")));
            background.setBounds(0, 0, 2170, 2000);
            background.setLocation(-330, -800);
            layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
            initializeBackgroundMusic("music3.wav");
            targetscore = 1000;
            steps = 17;
        }
        if (level == 3){
            ImageIcon imageIcon = new ImageIcon(getClass().getResource("Background4.gif"));
            ImageIcon imageset = new ImageIcon(imageIcon.getImage().getScaledInstance(1500 , -1, Image.SCALE_DEFAULT));
            background = new JLabel(imageset);
            background.setBounds(0, 0, 2170, 2000);
            background.setLocation(-350, -700);
            layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
            initializeBackgroundMusic("music4.wav");
            targetscore = 1500;
            steps = 24;
        }
        if (level == 4){
            background = new JLabel(new ImageIcon(getClass().getResource("Background.gif")));
            background.setBounds(0, 0, 2170, 2000);
            background.setLocation(-330, -500);
            layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
            initializeBackgroundMusic("music5.wav");
            targetscore = 2000;
            steps = 30;
        }
        if (level == 5){
            background = new JLabel(new ImageIcon(getClass().getResource("Background3.gif")));
            background.setBounds(0, 0, 2170, 2000);
            background.setLocation(-330, -500);
            layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
            initializeBackgroundMusic("music6.wav");
            targetscore = 2500;
            steps = 30;
        }
        this.WIDTH = width;
        this.HEIGTH = height;
        this.ONE_CHESS_SIZE = (HEIGTH * 4 / 5) / 9;
        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        FlowLayout layout = new FlowLayout(FlowLayout.LEFT, 0, 0);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        setLayout(layout);
        addChessboard(layeredPane);
        addLabel(layeredPane);
        addSwapConfirmButton(layeredPane);
        addNextStepButton(layeredPane);
        addShuffleButton(layeredPane);
        addHintButton(layeredPane);
        setContentPane(layeredPane);
        addLevelButton(layeredPane);
        addStepsCounter(layeredPane);
        addMainMenu(layeredPane);
        addSettings(layeredPane);
        addAuto(layeredPane);
        initializePopSoundEffect();
    }
    public void StopMusic(){
        backgroundMusicClip2.stop();
    }
    public static void setLevel(int level){
        ChessGameFrame.level = level;
    }


    public ChessboardComponent getChessboardComponent() {
        return chessboardComponent;
    }

    public void setChessboardComponent(ChessboardComponent chessboardComponent) {
        this.chessboardComponent = chessboardComponent;
    }
    public GameController getGameController() {
        return gameController;
    }
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }
    public void setOnNextStepLevel(boolean onNextStepLevel) {
        this.onNextStepLevel = onNextStepLevel;
    }

    public int getShuffle() {
        return shuffle;
    }

    public int getHintcount() {
        return hintcount;
    }

    public void initializeBackgroundMusic(String path){
        try {
            AudioInputStream audioInput2 = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource(path)));
            backgroundMusicClip2 = AudioSystem.getClip();
            backgroundMusicClip2.open(audioInput2);
            backgroundMusicClip2.loop(Clip.LOOP_CONTINUOUSLY);
            FloatControl gainControl2 = (FloatControl) backgroundMusicClip2.getControl(FloatControl.Type.MASTER_GAIN);
            float gain = (float) (Math.log(Settings.getInitialSliderLocation() / 100.0) / Math.log(10.0) * 20.0);
            gainControl2.setValue(gain-5);
            backgroundMusicClip2.start();
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
            System.out.println("Error initializing background music.");
        }
    }
    public void adjustBackgroundMusicVolume(int volume) {
        FloatControl gainControl2 = (FloatControl) backgroundMusicClip2.getControl(FloatControl.Type.MASTER_GAIN);
        float gain = (float) (Math.log(volume / 100.0) / Math.log(10.0) * 20.0);
        gainControl2.setValue(gain-5);
    }
    private void addChessboard(JLayeredPane layeredPane) {
        chessboardComponent = new ChessboardComponent(ONE_CHESS_SIZE);
        chessboardComponent.setLocation(HEIGTH / 5+200, HEIGTH / 10);
        layeredPane.add(chessboardComponent, JLayeredPane.PALETTE_LAYER);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel(JLayeredPane layeredPane) {
        this.statusLabel = new JLabel("Score: 0"  + "/" + targetscore);
        JLabel image = new JLabel(WoodenTexture);
        image.setLocation(HEIGTH+200, HEIGTH / 10);
        image.setSize(200, 60);
        statusLabel.setLocation(HEIGTH +35+190, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        statusLabel.setForeground(Color.WHITE);
        layeredPane.add(statusLabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(image, JLayeredPane.PALETTE_LAYER);
    }
    private void addStepsCounter(JLayeredPane layeredPane){
        JLabel steplabel = new JLabel("Steps");
        this.statuslabel2 = new JLabel(""+ steps);
        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("Circle2.png")));
        JLabel stepsLabel = new JLabel(image);
        stepsLabel.setBounds(50, HEIGTH / 10 - 50, image.getIconWidth(), image.getIconHeight());
        stepsLabel.setLocation(HEIGTH-50+360, HEIGTH/2+70);
        stepsLabel.setSize(500, 500);
        steplabel.setLocation(HEIGTH+158+360, HEIGTH/2+170+70);
        steplabel.setSize(200, 60);
        steplabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        statusLabel.setForeground(Color.WHITE);
        statuslabel2.setLocation(HEIGTH+160+360, HEIGTH/2+220+70);
        statuslabel2.setSize(200, 60);
        statuslabel2.setFont(new Font("Tahoma", Font.BOLD, 50));
        statuslabel2.setForeground(Color.WHITE);
        layeredPane.add(steplabel, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(statuslabel2, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(stepsLabel, JLayeredPane.PALETTE_LAYER);
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }
    public JLabel getStatuslabel2(){
        return statuslabel2;
    }
    public int getSteps(){
        return steps;
    }

    public int getHEIGTH() {
        return HEIGTH;
    }

    public int getWIDTH() {
        return WIDTH;
    }
    public int getTargetscore() {
        return targetscore;
    }

    public static int getLevel() {
        return level;
    }


    public JLabel getStatuslabel3() {
        return statuslabel3;
    }

    public JLabel getStatuslabe4() {
        return statuslabel4;
    }
    public JLabel getStatuslabe5() {
        return statuslabel5;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */

    private void addSwapConfirmButton(JLayeredPane layeredPane) {
        JButton button = new JButton("CONFIRM SWAP");
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(	224, 255, 255));
        button.setBorder(new LineBorder(new Color(0, 139, 139), 2,true));
        button.setFocusPainted(false);
        button.addActionListener(e -> {
            playPopSound();
            chessboardComponent.swapChess();
            if (GameController.score >= GameController.scoretarget){
                nextLevel = new NextLevel(this);
                nextLevel.setVisible(true);
                nextLevel.setLocationRelativeTo(null);
            }
            if (gameController.getSteps() < 0){
                nextLevel = new NextLevel(this);
                nextLevel.setVisible(true);
                nextLevel.setLocationRelativeTo(null);
            }
        });
        button.setLocation(HEIGTH+200, HEIGTH / 10 + 200-110);
        button.setSize(200, 60);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        layeredPane.add(button, JLayeredPane.PALETTE_LAYER);
    }
    private void addShuffleButton(JLayeredPane layeredPane) {
        JButton button = new JButton();
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(	224, 255, 255));
        button.setBorder(new LineBorder(new Color(0, 139, 139), 2,true));
        button.setFocusPainted(false);
        button.addActionListener(e -> {
            playPopSound();
            gameController.shuffles();
        });
        button.setLocation(HEIGTH/10, HEIGTH / 10 + 200-110);
        button.setSize(200, 60);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        this.statuslabel4 = new JLabel("SHUFFLE: "+ shufflecount);
        statuslabel4.setLocation(120, 170);
        statuslabel4.setSize(200, 60);
        statuslabel4.setFont(new Font("Tahoma", Font.BOLD, 20));
        statuslabel4.setForeground(Color.BLACK);
        layeredPane.add(statuslabel4, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(button, JLayeredPane.PALETTE_LAYER);
    }

    private void addHintButton(JLayeredPane layeredPane) {
        JButton button = new JButton();
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(	224, 255, 255));
        button.setBorder(new LineBorder(new Color(0, 139, 139), 2,true));
        button.setFocusPainted(false);
        button.addActionListener(e -> {
            playPopSound();
            gameController.asasblack();
        });
        button.setLocation(HEIGTH/10, HEIGTH / 10 + 280-110);
        button.setSize(200, 60);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        this.statuslabel5 = new JLabel("HINT: "+ hintcount);
        statuslabel5.setLocation(140, 250);
        statuslabel5.setSize(200, 60);
        statuslabel5.setFont(new Font("Tahoma", Font.BOLD, 20));
        statuslabel5.setForeground(Color.BLACK);
        layeredPane.add(statuslabel5, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(button, JLayeredPane.PALETTE_LAYER);
    }

    private void addAuto(JLayeredPane layeredPane) {
        JButton button;
        if(bich)
            button = new JButton("AUTOMODE: "+ "ON");
        else
            button = new JButton("AUTOMODE: "+ "OFF");

        button.setForeground(Color.BLACK);
        button.setBackground(new Color(	224, 255, 255));
        button.setBorder(new LineBorder(new Color(0, 139, 139), 2,true));
        button.setFocusPainted(false);
        button.addActionListener(e -> {
            playPopSound();
            if(!bich)
            {
                bich=true;
                button.setText("AUTOMODE: "+ "ON");
            }
            else
            {
                bich=false;
                button.setText("AUTOMODE: "+ "OFF");
            }
            gameController.setAuto(bich);
        });
        button.setLocation(HEIGTH/10, HEIGTH / 10 + 360-110);
        button.setSize(200, 60);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        layeredPane.add(button, JLayeredPane.PALETTE_LAYER);
    }

    private void addNextStepButton(JLayeredPane layeredPane) {
        JButton button = new JButton("NEXT STEP");
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(	224, 255, 255));
        button.setBorder(new LineBorder(new Color(0, 139, 139), 2,true));
        button.setFocusPainted(false);
        button.addActionListener(e -> {
            playPopSound();
            chessboardComponent.nextStep();
            if (GameController.score >= GameController.scoretarget){
                nextLevel = new NextLevel(this);
                nextLevel.setVisible(true);
                nextLevel.setLocationRelativeTo(null);
            }
        });
        button.setLocation(HEIGTH+200, HEIGTH / 10 + 280-110);
        button.setSize(200, 60);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        layeredPane.add(button, JLayeredPane.PALETTE_LAYER);
    }

    private void addLevelButton(JLayeredPane layeredPane){
        JButton button = new JButton();
        statuslabel3 = new JLabel("Level " + level);
        statuslabel3.setLocation(50+30,HEIGTH/10 - 50);
        statuslabel3.setFont(new Font("Tahoma", Font.BOLD, 20));
        statuslabel3.setSize(100+60, 30+30);
        statuslabel3.setForeground(Color.WHITE);
        button.setLocation(50,HEIGTH/10 - 50);
        button.setSize(100+30, 30+30);
        button.setBackground(new Color(196, 164, 132));
        button.setFocusPainted(false);
        button.setBorder(new LineBorder(new Color(123, 63, 0), 2));
        button.setUI(new MetalButtonUI() {
            protected Color getDisabledTextColor() {
                return Color.WHITE;
            }
        });
        button.setEnabled(false);
        layeredPane.add(statuslabel3, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(button, JLayeredPane.PALETTE_LAYER);
    }
    public void addSettings(JLayeredPane layeredPane){
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("Settings.png")));
        ImageIcon imageset = new ImageIcon(imageIcon.getImage().getScaledInstance(50 , -1, Image.SCALE_DEFAULT));
        originalImageIcon2 = imageset;
        settings = new JButton(imageset);
        settings.setBounds(HEIGTH + 125+340, HEIGTH/10-160, imageIcon.getIconWidth(), imageIcon.getIconHeight());
        settings.setFocusPainted(false);
        settings.setContentAreaFilled(false);
        settings.setBorderPainted(false);
        settings.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                enlargeTimer2.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                shrinkTimer2.start();
            }
        });
        settings.addActionListener(e -> {
            playPopSound();
            if(settingsWindow !=null){
                settingsWindow.dispose();
            }
            openSettings();
        });
        layeredPane.add(settings, JLayeredPane.PALETTE_LAYER);
    }
    public void addMainMenu(JLayeredPane layeredPane){
        ImageIcon mainmenu = new ImageIcon(Objects.requireNonNull(getClass().getResource("Arrow.png")));
        ImageIcon imageset = new ImageIcon(mainmenu.getImage().getScaledInstance(300 , 168, Image.SCALE_DEFAULT));
        originalImageIcon = imageset;
        Return = new JButton(imageset);
        Return.setBounds(0, 0, 400, 400);
        Return.setFocusPainted(false);
        Return.setContentAreaFilled(false);
        Return.setBorderPainted(false);
        Return.setSize(400, 400);
        Return.setLocation(-145, 540);
        Return.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                enlargeTimer.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                shrinkTimer.start();
            }
        });
        Return.addActionListener(e -> {
            playPopSound();
            dispose();
            setVisible(false);
            backgroundMusicClip2.stop();
            NextLevel.setLevel(0);
            LaunchPage launchPage = new LaunchPage();
            launchPage.setLocationRelativeTo(null);
        });
        layeredPane.add(Return, JLayeredPane.PALETTE_LAYER);
    }
    public void transitlmao(String path)
    {
        gameController.loadGameFromFile(path);
    }
    private void openSettings() {
        if (settingsWindow != null) {
            settingsWindow.dispose();
        }
        settingsWindow = new Settings(this);
        settingsWindow.setLocationRelativeTo(this);
        settingsWindow.setVisible(true);
        settingsWindow.setGameController(getGameController());
    }
    public void writeFileByFileWriter(String path, String username) {
        try {
            FileWriter fileWriter = new FileWriter(path);
            BufferedWriter writer = new BufferedWriter(fileWriter);
            List<String> lines = gameController.convertToList(username);
            for (String line : lines
            ) {
                writer.write(line);
            }
            writer.close();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void animateImageSize(boolean enlarge) {
        int step = 5;

        ImageIcon currentIcon = (ImageIcon) Return.getIcon();
        Image currentImage = currentIcon.getImage();
        int currentWidth = currentIcon.getIconWidth();
        int currentHeight = currentIcon.getIconHeight();
        int newWidth = enlarge ? currentWidth + step : currentWidth - step;
        int newHeight = enlarge ? currentHeight + step : currentHeight - step;
        ImageIcon newImageIcon = new ImageIcon(currentImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT));
        Return.setIcon(newImageIcon);
        Return.repaint();

        if (enlarge && newWidth >= 325) {
            enlargeTimer.stop();
        } else if (!enlarge && newWidth <= originalImageIcon.getIconWidth() && newHeight <= originalImageIcon.getIconHeight()) {
            shrinkTimer.stop();
        }
    }
    private void animateImageSize2(boolean enlarge) {
        int step = 5;

        ImageIcon currentIcon = (ImageIcon) settings.getIcon();
        Image currentImage = currentIcon.getImage();
        int currentWidth = currentIcon.getIconWidth();
        int currentHeight = currentIcon.getIconHeight();
        int newWidth = enlarge ? currentWidth + step : currentWidth - step;
        int newHeight = enlarge ? currentHeight + step : currentHeight - step;
        ImageIcon newImageIcon = new ImageIcon(currentImage.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT));
        settings.setIcon(newImageIcon);
        settings.repaint();

        if (enlarge && newWidth >= 60) {
            enlargeTimer2.stop();
        } else if (!enlarge && newWidth <= originalImageIcon2.getIconWidth()) {
            shrinkTimer2.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enlargeTimer) {
            animateImageSize(true);
        } else if (e.getSource() == shrinkTimer) {
            animateImageSize(false);
        }
        if (e.getSource() == enlargeTimer2) {
            animateImageSize2(true);
        } else if (e.getSource() == shrinkTimer2) {
            animateImageSize2(false);
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

    public void setPopSoundVolume(int volume) {
        FloatControl gainControl2 = (FloatControl) ButtonSound.getControl(FloatControl.Type.MASTER_GAIN);
        float gain = (float) (Math.log(volume / 100.0) / Math.log(10.0) * 20.0);
        gainControl2.setValue(gain+2);
    }

}
