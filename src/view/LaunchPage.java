package view;
import controller.GameController;
import model.Chessboard;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.logging.Level;

public class LaunchPage extends JFrame implements ActionListener {
    private final Font customfont6;
    private final Timer shrinkTimer6;
    private final Timer enlargeTimer6;
    private Font customfont5;
    private Font customfont3;
    private ImageIcon Title;
    private JButton StartButton;
    private JButton ExitButton;
    private Font customfont;
    private Font customfont2;
    private Font customfont4;
    private Timer enlargeTimer;
    private Timer shrinkTimer;
    private Timer enlargeTimer2;
    private Timer shrinkTimer2;
    private ImageIcon woodenPlank;
    private ImageIcon smallPlank;
    private static Clip backgroundMusicClip;
    private Clip backgroundMusicClip2;
    private Timer enlargeTimer3;
    private Timer shrinkTimer3;
    private JLabel plank1;
    private JLabel plank2;
    private JButton Button1;
    private JButton Button4;
    private Timer enlargeTimer4;
    private Timer shrinkTimer4;
    private JButton Button5;
    private Timer enlargeTimer5;
    private Timer shrinkTimer5;
    private JButton Button2;
    private JButton Button3;
    private static int level;
    private boolean gameInitialized = false;
    private ImageIcon originalImageIcon;
    private JButton Return;
    private JLabel label;
    private JLabel plank4;
    private JLabel plank5;
    private JLabel plank6;
    private JLabel plank8;
    private JLabel plank7;
    private static Clip ButtonSound;

    public LaunchPage() {
        customfont = loadFont("GAMERIA.ttf", 30f);
        customfont2 = loadFont("GAMERIA.ttf", 30f);
        customfont3 = loadFont("GAMERIA.ttf", 30f);
        customfont4 = loadFont("GAMERIA.ttf", 30f);
        customfont5 = loadFont("GAMERIA.ttf", 30f);
        customfont6 = loadFont("GAMERIA.ttf", 40f);
        Button1 = new JButton("1");
        Button2 = new JButton("2");
        Button3 = new JButton("3");
        Button4 = new JButton("4");
        Button5 = new JButton("5");
        enlargeTimer = new Timer(15, this);
        enlargeTimer.setInitialDelay(0);
        enlargeTimer.setCoalesce(true);
        enlargeTimer2 = new Timer(15, this);
        enlargeTimer2.setInitialDelay(0);
        enlargeTimer2.setCoalesce(true);
        shrinkTimer = new Timer(15, this);
        shrinkTimer.setInitialDelay(0);
        shrinkTimer.setCoalesce(true);
        shrinkTimer2 = new Timer(15, this);
        shrinkTimer2.setInitialDelay(0);
        shrinkTimer2.setCoalesce(true);
        enlargeTimer3 = new Timer(15, this);
        enlargeTimer3.setInitialDelay(0);
        enlargeTimer3.setCoalesce(true);
        shrinkTimer3 = new Timer(15, this);
        shrinkTimer3.setInitialDelay(0);
        shrinkTimer3.setCoalesce(true);
        enlargeTimer4 = new Timer(15, this);
        enlargeTimer4.setInitialDelay(0);
        enlargeTimer4.setCoalesce(true);
        shrinkTimer4 = new Timer(15, this);
        shrinkTimer4.setInitialDelay(0);
        shrinkTimer4.setCoalesce(true);
        enlargeTimer5 = new Timer(15, this);
        enlargeTimer5.setInitialDelay(0);
        enlargeTimer5.setCoalesce(true);
        shrinkTimer5 = new Timer(15, this);
        shrinkTimer5.setInitialDelay(0);
        shrinkTimer5.setCoalesce(true);
        enlargeTimer6 = new Timer(15, this);
        enlargeTimer6.setInitialDelay(0);
        enlargeTimer6.setCoalesce(true);
        shrinkTimer6 = new Timer(15, this);
        shrinkTimer6.setInitialDelay(0);
        shrinkTimer6.setCoalesce(true);
        woodenPlank = new ImageIcon(Objects.requireNonNull(getClass().getResource("WoodenPlank.png")));
        smallPlank = new ImageIcon(woodenPlank.getImage().getScaledInstance(210 , -1, Image.SCALE_DEFAULT));
        initializeBackgroundMusic();
        JPanel panel = new JPanel();
        panel.setLayout(new SpringLayout());
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setBounds(0, 0, 2000, 900);
        JLabel background = new JLabel(new ImageIcon(Objects.requireNonNull(getClass().getResource("Background2.gif"))));
        background.setBounds(0, -1200, 2000, 2100);
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
        getContentPane().setPreferredSize(new Dimension(2000, 2100));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(layeredPane);
        setLayout(null);
        addTitle(layeredPane);
        addStart(layeredPane);
        addExit(layeredPane);
        addSubTitle(getLayeredPane());
        addLevel1(getLayeredPane());
        addLevel2(getLayeredPane());
        addLevel3(getLayeredPane());
        addLevel4(getLayeredPane());
        addLevel5(getLayeredPane());
        addMainMenu(getLayeredPane());
        initializePopSoundEffect();
        Return.setVisible(false);
        label.setVisible(false);
        Button1.setVisible(false);
        Button2.setVisible(false);
        Button3.setVisible(false);
        Button4.setVisible(false);
        Button5.setVisible(false);
        plank4.setVisible(false);
        plank5.setVisible(false);
        plank6.setVisible(false);
        plank7.setVisible(false);
        plank8.setVisible(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setUndecorated(true);
        pack();
        setVisible(true);
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        LaunchPage.level = level;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == StartButton) {
            playPopSound();
            StartButton.setVisible(false);
            ExitButton.setVisible(false);
            plank2.setVisible(false);
            plank1.setVisible(false);
            Return.setVisible(true);
            label.setVisible(true);
            plank4.setVisible(true);
            plank5.setVisible(true);
            plank6.setVisible(true);
            plank7.setVisible(true);
            plank8.setVisible(true);
            Button1.setVisible(true);
            Button2.setVisible(true);
            Button3.setVisible(true);
            Button4.setVisible(true);
            Button5.setVisible(true);
        } else if (e.getSource() == ExitButton) {
            playPopSound();
            dispose();
            System.exit(0);
        } else if (e.getSource() == Return) {
            playPopSound();
            Return.setVisible(false);
            label.setVisible(false);
            Button1.setVisible(false);
            Button2.setVisible(false);
            Button3.setVisible(false);
            Button4.setVisible(false);
            Button5.setVisible(false);
            plank4.setVisible(false);
            plank5.setVisible(false);
            plank6.setVisible(false);
            plank7.setVisible(false);
            plank8.setVisible(false);
            StartButton.setVisible(true);
            ExitButton.setVisible(true);
            plank2.setVisible(true);
            plank1.setVisible(true);

        } else if (e.getSource() == enlargeTimer) {
            animateFont(true);
        } else if (e.getSource() == shrinkTimer) {
            animateFont(false);
        } else if (e.getSource() == enlargeTimer2) {
            animateFont2(true);
        } else if (e.getSource() == shrinkTimer2) {
            animateFont2(false);
        } else if (e.getSource() == enlargeTimer3) {
            animateFont3(true);
        } else if (e.getSource() == shrinkTimer3) {
            animateFont3(false);
        } else if (e.getSource() == enlargeTimer4) {
            animateFont4(true);
        } else if (e.getSource() == shrinkTimer4) {
            animateFont4(false);
        } else if (e.getSource() == enlargeTimer5) {
            animateFont5(true);
        } else if (e.getSource() == shrinkTimer5) {
            animateFont5(false);
        }if (e.getSource() == enlargeTimer6) {
            animateImageSize(true);
        } else if (e.getSource() == shrinkTimer6) {
            animateImageSize(false);
        }else if (e.getSource() == Button1 && !gameInitialized) {
            playPopSound();
            level = 1;
            initializeGame();
            gameInitialized = true;
            backgroundMusicClip.stop();
            dispose();
        }else if (e.getSource() == Button2 && !gameInitialized) {
            playPopSound();
            level = 2;
            initializeGame();
            gameInitialized = true;
            backgroundMusicClip.stop();
            dispose();
        }else if (e.getSource() == Button3 && !gameInitialized) {
            playPopSound();
            level = 3;
            initializeGame();
            gameInitialized = true;
            backgroundMusicClip.stop();
            dispose();
        }else if (e.getSource() == Button4 && !gameInitialized) {
            playPopSound();
            level = 4;
            initializeGame();
            gameInitialized = true;
            backgroundMusicClip.stop();
            dispose();
        }else if (e.getSource() == Button5 && !gameInitialized) {
            playPopSound();
            level = 5;
            initializeGame();
            gameInitialized = true;
            backgroundMusicClip.stop();
            dispose();
        }

    }

    private void initializeBackgroundMusic() {
        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("music1.wav")));
            backgroundMusicClip = AudioSystem.getClip();
            backgroundMusicClip.open(audioInput);
            backgroundMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
            backgroundMusicClip.start();
            FloatControl gainControl2 = (FloatControl) backgroundMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
            float gain = (float) (Math.log(Settings.getInitialSliderLocation()  / 100.0) / Math.log(10.0) * 20.0);
            gainControl2.setValue(gain-5);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
            System.out.println("Error initializing background music.");
        }
    }
    public static void adjustBackgroundMusicVolume(int volume) {
        FloatControl gainControl2 = (FloatControl) backgroundMusicClip.getControl(FloatControl.Type.MASTER_GAIN);
        float gain = (float) (Math.log(volume / 100.0) / Math.log(10.0) * 20.0);
        gainControl2.setValue(gain-5);
    }
    private Font loadFont(String fontPath, float size) {
        try (InputStream stream = getClass().getResourceAsStream(fontPath)) {
            if (stream != null) {
                return Font.createFont(Font.TRUETYPE_FONT, stream).deriveFont(size);
            } else {
                System.err.println("Font not found: " + fontPath);
            }
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void animateFont(boolean enlarge) {
        float newSize;
        if (enlarge) {
            newSize = customfont.getSize2D() + 1;
        } else {
            newSize = customfont.getSize2D() - 1;
        }

        customfont = customfont.deriveFont(newSize);
        StartButton.setFont(customfont);
        StartButton.repaint();
        Button1.setFont(customfont);
        Button1.repaint();

        if (enlarge && newSize >= 40) {
            enlargeTimer.stop();
        } else if (!enlarge && newSize <= 30) {
            shrinkTimer.stop();
        }
    }
    private void animateFont2(boolean enlarge) {
        float newSize;
        if (enlarge) {
            newSize = customfont2.getSize2D() + 1;
        } else {
            newSize = customfont2.getSize2D() - 1;
        }

        customfont2 = customfont2.deriveFont(newSize);
        ExitButton.setFont(customfont2);
        ExitButton.repaint();
        Button2.setFont(customfont2);
        Button2.repaint();

        if (enlarge && newSize >= 40) {
            enlargeTimer2.stop();
        } else if (!enlarge && newSize <= 30) {
            shrinkTimer2.stop();
        }
    }
    private void animateFont3(boolean enlarge) {
        float newSize;
        if (enlarge) {
            newSize = customfont3.getSize2D() + 1;
        } else {
            newSize = customfont3.getSize2D() - 1;
        }

        customfont3 = customfont3.deriveFont(newSize);
        Button3.setFont(customfont3);
        Button3.repaint();

        if (enlarge && newSize >= 40) {
            enlargeTimer3.stop();
        } else if (!enlarge && newSize <= 30) {
            shrinkTimer3.stop();
        }
    }
    private void animateFont4(boolean enlarge) {
        float newSize;
        if (enlarge) {
            newSize = customfont4.getSize2D() + 1;
        } else {
            newSize = customfont4.getSize2D() - 1;
        }

        customfont4 = customfont4.deriveFont(newSize);
        Button4.setFont(customfont4);
        Button4.repaint();

        if (enlarge && newSize >= 40) {
            enlargeTimer4.stop();
        } else if (!enlarge && newSize <= 30) {
            shrinkTimer4.stop();
        }
    }
    private void animateFont5(boolean enlarge) {
        float newSize;
        if (enlarge) {
            newSize = customfont5.getSize2D() + 1;
        } else {
            newSize = customfont5.getSize2D() - 1;
        }

        customfont5 = customfont5.deriveFont(newSize);
        Button5.setFont(customfont5);
        Button5.repaint();

        if (enlarge && newSize >= 40) {
            enlargeTimer5.stop();
        } else if (!enlarge && newSize <= 30) {
            shrinkTimer5.stop();
        }
    }

    public void addTitle(JLayeredPane layeredPane) {
        Title = new ImageIcon(Objects.requireNonNull(getClass().getResource("Title.png")));
        JLabel label = new JLabel(Title);
        int labelX = 1100 - Title.getIconWidth();
        int labelY = -100;
        label.setBounds(labelX, labelY, Title.getIconWidth(), Title.getIconHeight());
        layeredPane.add(label, JLayeredPane.PALETTE_LAYER);
    }
    public void addSubTitle(JLayeredPane layeredPane) {
        label = new JLabel("Please Choose a Level");
        int labelX = 1100 - label.getWidth();
        int labelY = 400;
        label.setFont(customfont6);
        label.setBounds(labelX, labelY, 600, 200);
        label.setLocation(550, 100);
        label.setSize(600, 200);
        label.setForeground(Color.WHITE);
        layeredPane.add(label, JLayeredPane.PALETTE_LAYER);
    }

    public void addStart(JLayeredPane layeredPane) {
        StartButton = new JButton("Start");
        StartButton.setFont(customfont);
        StartButton.setBounds(100, 160, 200, 40);
        StartButton.setFocusable(false);
        StartButton.addActionListener(this);
        StartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                enlargeTimer.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                enlargeTimer.stop();
                shrinkTimer.start();
            }
        });

        int buttonX = (1530 - StartButton.getWidth()) / 2;
        int buttonY = (610 - StartButton.getHeight()) / 2;
        StartButton.setLocation(buttonX, buttonY);
        StartButton.setBorder(BorderFactory.createEmptyBorder());
        StartButton.setContentAreaFilled(false);
        StartButton.setForeground(Color.WHITE);

        plank2 = new JLabel(smallPlank);
        plank2.setBounds(buttonX, buttonY-85, smallPlank.getIconWidth(), smallPlank.getIconHeight());

        layeredPane.add(StartButton, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(plank2, JLayeredPane.PALETTE_LAYER);
    }

    public void addExit(JLayeredPane layeredPane) {
        ExitButton = new JButton("Exit");
        ExitButton.setFont(customfont2);
        ExitButton.addActionListener(this);
        ExitButton.setBounds(100, 210, 200, 40);
        ExitButton.setFocusable(false);
        ExitButton.addActionListener(this);
        ExitButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                enlargeTimer2.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                enlargeTimer2.stop();
                shrinkTimer2.start();
            }
        });
        int buttonX = (1530 - StartButton.getWidth()) / 2;
        int buttonY = (800 - StartButton.getHeight()) / 2;
        ExitButton.setLocation(buttonX, buttonY);
        ExitButton.setBorder(BorderFactory.createEmptyBorder());
        ExitButton.setContentAreaFilled(false);
        ExitButton.setForeground(Color.WHITE);
        plank1 = new JLabel(smallPlank);
        plank1.setBounds(buttonX, buttonY-85, smallPlank.getIconWidth(), smallPlank.getIconHeight());
        layeredPane.add(ExitButton, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(plank1, JLayeredPane.PALETTE_LAYER);
    }
    public void addLevel1(JLayeredPane layeredPane){
        Button1.setFont(customfont);
        Button1.addActionListener(this);
        Button1.setBounds(100, 150, 200, 40);
        Button1.setFocusable(false);
        Button1.addActionListener(this);
        Button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                enlargeTimer.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                enlargeTimer.stop();
                shrinkTimer.start();
            }
        });
        int buttonX = (1530 - Button1.getWidth()) / 2;
        int buttonY = (610 - Button1.getHeight()) / 2;
        Button1.setLocation(buttonX, buttonY);
        Button1.setBorder(BorderFactory.createEmptyBorder());
        Button1.setContentAreaFilled(false);
        Button1.setForeground(Color.WHITE);
        plank4 = new JLabel(smallPlank);
        plank4.setBounds(buttonX, buttonY-85, smallPlank.getIconWidth(), smallPlank.getIconHeight());
        layeredPane.add(Button1, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(plank4, JLayeredPane.PALETTE_LAYER);
    }
    public void addLevel2(JLayeredPane layeredPane){
        Button2.setFont(customfont2);
        Button2.addActionListener(this);
        Button2.setBounds(100, 150, 200, 40);
        Button2.setFocusable(false);
        Button2.addActionListener(this);
        Button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                enlargeTimer2.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                enlargeTimer2.stop();
                shrinkTimer2.start();
            }
        });
        int buttonX = (1530 - Button2.getWidth()) / 2;
        int buttonY = (610+150 - Button2.getHeight()) / 2;
        Button2.setLocation(buttonX, buttonY);
        Button2.setBorder(BorderFactory.createEmptyBorder());
        Button2.setContentAreaFilled(false);
        Button2.setForeground(Color.WHITE);
        plank5 = new JLabel(smallPlank);
        plank5.setBounds(buttonX, buttonY-85, smallPlank.getIconWidth(), smallPlank.getIconHeight());
        layeredPane.add(Button2, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(plank5, JLayeredPane.PALETTE_LAYER);
    }
    public void addLevel3(JLayeredPane layeredPane){
        Button3.setFont(customfont3);
        Button3.addActionListener(this);
        Button3.setBounds(100, 150, 200, 40);
        Button3.setFocusable(false);
        Button3.addActionListener(this);
        Button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                enlargeTimer3.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                enlargeTimer3.stop();
                shrinkTimer3.start();
            }
        });
        int buttonX = (1530 - Button3.getWidth()) / 2;
        int buttonY = (610+300 - Button3.getHeight()) / 2;
        Button3.setLocation(buttonX, buttonY);
        Button3.setBorder(BorderFactory.createEmptyBorder());
        Button3.setContentAreaFilled(false);
        Button3.setForeground(Color.WHITE);
        plank6 = new JLabel(smallPlank);
        plank6.setBounds(buttonX, buttonY-85, smallPlank.getIconWidth(), smallPlank.getIconHeight());
        layeredPane.add(Button3, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(plank6, JLayeredPane.PALETTE_LAYER);
    }
    public void addLevel4(JLayeredPane layeredPane){
        Button4.setFont(customfont4);
        Button4.addActionListener(this);
        Button4.setBounds(100, 150, 200, 40);
        Button4.setFocusable(false);
        Button4.addActionListener(this);
        Button4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                enlargeTimer4.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                enlargeTimer4.stop();
                shrinkTimer4.start();
            }
        });
        int buttonX = (1530 - Button4.getWidth()) / 2;
        int buttonY = (610+450 - Button4.getHeight()) / 2;
        Button4.setLocation(buttonX, buttonY);
        Button4.setBorder(BorderFactory.createEmptyBorder());
        Button4.setContentAreaFilled(false);
        Button4.setForeground(Color.WHITE);
        plank7 = new JLabel(smallPlank);
        plank7.setBounds(buttonX, buttonY-85, smallPlank.getIconWidth(), smallPlank.getIconHeight());
        layeredPane.add(Button4, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(plank7, JLayeredPane.PALETTE_LAYER);
    }
    public void addLevel5(JLayeredPane layeredPane){
        Button5.setFont(customfont5);
        Button5.addActionListener(this);
        Button5.setBounds(100, 150, 200, 40);
        Button5.setFocusable(false);
        Button5.addActionListener(this);
        Button5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                enlargeTimer5.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                enlargeTimer5.stop();
                shrinkTimer5.start();
            }
        });
        int buttonX = (1530 - Button5.getWidth()) / 2;
        int buttonY = (610+600 - Button5.getHeight()) / 2;
        Button5.setLocation(buttonX, buttonY);
        Button5.setBorder(BorderFactory.createEmptyBorder());
        Button5.setContentAreaFilled(false);
        Button5.setForeground(Color.WHITE);
        plank8 = new JLabel(smallPlank);
        plank8.setBounds(buttonX, buttonY-85, smallPlank.getIconWidth(), smallPlank.getIconHeight());
        layeredPane.add(Button5, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(plank8, JLayeredPane.PALETTE_LAYER);
    }
    public static void initializeGame(){
        ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
        GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
        mainFrame.setGameController(gameController);
        gameController.setChessGameFrame(mainFrame);
        gameController.setStatusLabel(mainFrame.getStatusLabel());
        gameController.setStatusLabel2(mainFrame.getStatuslabel2());
        gameController.setSteps(mainFrame.getSteps());
        gameController.setWIDTH(mainFrame.getWIDTH());
        gameController.setHEIGHT(mainFrame.getHEIGTH());
        gameController.setTargetScore(mainFrame.getTargetscore());
        gameController.setLevel(mainFrame.getLevel());
        GameController.setShuffle(mainFrame.getShuffle());
        GameController.setHint(mainFrame.getHintcount());
        gameController.setStatuslabel5(mainFrame.getStatuslabe5());
        gameController.setStatuslabel4(mainFrame.getStatuslabe4());
        gameController.setStatuslabel3(mainFrame.getStatuslabel3());
        mainFrame.setOnNextStepLevel(gameController.getOpenNextLevel());
        mainFrame.setVisible(true);
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
        Return.addActionListener(this);
        Return.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                enlargeTimer6.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                shrinkTimer6.start();
            }
        });

        layeredPane.add(Return, JLayeredPane.PALETTE_LAYER);
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
            enlargeTimer6.stop();
        } else if (!enlarge && newWidth <= originalImageIcon.getIconWidth() && newHeight <= originalImageIcon.getIconHeight()) {
            shrinkTimer6.stop();
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
