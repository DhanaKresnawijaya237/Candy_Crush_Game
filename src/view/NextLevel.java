package view;

import controller.GameController;
import model.ChessPiece;
import model.Chessboard;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


public class NextLevel extends JFrame implements ActionListener {
    private final Timer shrinkTimer2;
    private final Timer enlargeTimer2;
    private final Timer shrinkTimer;
    private final Timer enlargeTimer;
    private Font customfont2;
    Font customfont;
    JButton mainmenu;
    JButton nextlevel;
    private int scoretarget;
    private static int level;
    private int steps;
    private ChessGameFrame chessGameFrame;
    private static Clip ButtonSound;
    private static Clip backgroundMusicClip2;

    public NextLevel(ChessGameFrame chessGameFrame){
        this.chessGameFrame = chessGameFrame;
        customfont = loadFont("GAMERIA.ttf", 40f);
        customfont2 = loadFont("GAMERIA.ttf", 40f);
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
        JPanel panel = new JPanel();
        panel.setLayout(new SpringLayout());
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setLayout(null);
        layeredPane.setBounds(0, 0, 700, 400);
        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("Background3.jpg")));
        ImageIcon smallimage = new ImageIcon(image.getImage().getScaledInstance(700, -1, Image.SCALE_DEFAULT));
        JLabel background = new JLabel(smallimage);
        background.setBounds(0, 0, 700, 400);
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
        getContentPane().setPreferredSize(new Dimension(700, 400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(layeredPane);
        setLayout(null);
        setUndecorated(true);
        setVisible(true);
        setLocationRelativeTo(null);
        addNextLevel(layeredPane);
        addCongartulations(layeredPane);
        addMainMenu(layeredPane);
        if (ChessGameFrame.getLevel() == 5 && GameController.score >=GameController.scoretarget){
            nextlevel.setVisible(false);
        }
        initializePopSoundEffect();
        pack();
    }
    public static int getLevel() {
        return level;
    }
    public static void setLevel(int level1){
        level = level1;
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
    } // TODO
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == nextlevel){
            playPopSound();
            if (GameController.score >= GameController.scoretarget){
                playPopSound();
                level = ChessGameFrame.getLevel();
                level++;
                chessGameFrame.dispose();
                LaunchPage.initializeGame();
                chessGameFrame.StopMusic();
                dispose();
            }
        }
        if(e.getSource() == mainmenu){
            playPopSound();
            chessGameFrame.StopMusic();
            chessGameFrame.dispose();
            dispose();
            LaunchPage launchPage = new LaunchPage();
            launchPage.setLocationRelativeTo(null);
        }else if (e.getSource() == enlargeTimer) {
            animateFont(true);
        } else if (e.getSource() == shrinkTimer) {
            animateFont(false);
        } else if (e.getSource() == enlargeTimer2) {
            animateFont2(true);
        } else if (e.getSource() == shrinkTimer2) {
            animateFont2(false);
        }

    }
    private void animateFont(boolean enlarge) {
        float newSize;
        if (enlarge) {
            newSize = customfont.getSize2D() + 1;
        } else {
            newSize = customfont.getSize2D() - 1;
        }

        customfont = customfont.deriveFont(newSize);
        nextlevel.setFont(customfont);
        nextlevel.repaint();

        if (enlarge && newSize >= 50) {
            enlargeTimer.stop();
        } else if (!enlarge && newSize <= 40) {
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
        mainmenu.setFont(customfont2);
        mainmenu.repaint();

        if (enlarge && newSize >= 50) {
            enlargeTimer2.stop();
        } else if (!enlarge && newSize <= 40) {
            shrinkTimer2.stop();
        }
    }
    public void addNextLevel(JLayeredPane layeredPane){
        if (GameController.score >= GameController.scoretarget){
            nextlevel = new JButton("NEXT LEVEL");
            nextlevel.setBackground(new Color(	224, 255, 255));
            nextlevel.setBorder(new LineBorder(new Color(0, 139, 139), 2,true));
            nextlevel.setFocusPainted(false);
            nextlevel.setContentAreaFilled(false);
            nextlevel.setBorderPainted(false);
            nextlevel.setForeground(Color.WHITE);
            nextlevel.addActionListener(this);
            nextlevel.addMouseListener(new MouseAdapter() {
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
            nextlevel.setLocation(400-100,  275);
            nextlevel.setSize(400, 60);
            nextlevel.setFont(customfont);
            layeredPane.add(nextlevel, JLayeredPane.PALETTE_LAYER);
        }
        if (GameController.steps < 0){
            nextlevel = new JButton("RESTART");
            nextlevel.setBackground(new Color(	224, 255, 255));
            nextlevel.setBorder(new LineBorder(new Color(0, 139, 139), 2,true));
            nextlevel.setFocusPainted(false);
            nextlevel.setContentAreaFilled(false);
            nextlevel.setBorderPainted(false);
            nextlevel.setForeground(Color.WHITE);
            nextlevel.addActionListener(e -> {
                playPopSound();
                dispose();
                GameController.restart();
            });
            nextlevel.addMouseListener(new MouseAdapter() {
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
            nextlevel.setLocation(400-100,  275);
            nextlevel.setSize(400, 60);
            nextlevel.setFont(customfont);
            layeredPane.add(nextlevel, JLayeredPane.PALETTE_LAYER);
        }
    }
    public void addMainMenu(JLayeredPane layeredPane){
        if (GameController.level != 5){
            mainmenu = new JButton("MAIN MENU");
            mainmenu.setBackground(new Color(	224, 255, 255));
            mainmenu.setBorder(new LineBorder(new Color(0, 139, 139), 2,true));
            mainmenu.setFocusPainted(false);
            mainmenu.setContentAreaFilled(false);
            mainmenu.setBorderPainted(false);
            mainmenu.setForeground(Color.WHITE);
            mainmenu.addActionListener(this);
            mainmenu.setFont(customfont2);
            mainmenu.addMouseListener(new MouseAdapter() {
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
            mainmenu.setLocation(-10,  275);
            mainmenu.setSize(400, 60);
            layeredPane.add(mainmenu, JLayeredPane.PALETTE_LAYER);
        }
        if (GameController.level == 5) {
            if(GameController.score >=GameController.scoretarget) {
                initializeBackgroundMusic();
                backgroundMusicClip2.start();
                chessGameFrame.StopMusic();
                mainmenu = new JButton("MAIN MENU");
                mainmenu.setBackground(new Color(224, 255, 255));
                mainmenu.setBorder(new LineBorder(new Color(0, 139, 139), 2, true));
                mainmenu.setFocusPainted(false);
                mainmenu.setContentAreaFilled(false);
                mainmenu.setBorderPainted(false);
                mainmenu.setForeground(Color.WHITE);
                mainmenu.addActionListener(e -> {
                    playPopSound();
                    backgroundMusicClip2.stop();
                    chessGameFrame.StopMusic();
                    chessGameFrame.dispose();
                    dispose();
                    LaunchPage launchPage = new LaunchPage();
                    launchPage.setLocationRelativeTo(null);
                });
                mainmenu.setFont(customfont2);
                mainmenu.addMouseListener(new MouseAdapter() {
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
                mainmenu.setLocation(150, 275);
            }
            if (GameController.steps<0){

                mainmenu = new JButton("MAIN MENU");
                mainmenu.setBackground(new Color(	224, 255, 255));
                mainmenu.setBorder(new LineBorder(new Color(0, 139, 139), 2,true));
                mainmenu.setFocusPainted(false);
                mainmenu.setContentAreaFilled(false);
                mainmenu.setBorderPainted(false);
                mainmenu.setForeground(Color.WHITE);
                mainmenu.addActionListener(this);
                mainmenu.setFont(customfont2);
                mainmenu.addMouseListener(new MouseAdapter() {
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
                mainmenu.setLocation(-10,  275);
            }
            mainmenu.setSize(400, 60);
            layeredPane.add(mainmenu, JLayeredPane.PALETTE_LAYER);
        }

    }
    public void addCongartulations(JLayeredPane layeredPane){

        if (GameController.score >= GameController.scoretarget){
            if (GameController.level != 5){
                ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("Congratulations.png")));
                ImageIcon imageset = new ImageIcon(image.getImage().getScaledInstance(1000 , -1, Image.SCALE_DEFAULT));
                JLabel congrats = new JLabel(imageset);
                congrats.setLocation(105,  0);
                congrats.setSize(500, 300);
                congrats.setForeground(Color.WHITE);
                layeredPane.add(congrats, JLayeredPane.PALETTE_LAYER);
            }
            else {
                ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("Congratulation2.png")));
                ImageIcon imageset = new ImageIcon(image.getImage().getScaledInstance(1000 , -1, Image.SCALE_DEFAULT));
                JLabel congrats = new JLabel(imageset);
                congrats.setLocation(105,  0);
                congrats.setSize(500, 300);
                congrats.setForeground(Color.WHITE);
                layeredPane.add(congrats, JLayeredPane.PALETTE_LAYER);
            }
        }
        if (GameController.steps < 0){
            ImageIcon image1 = new ImageIcon(Objects.requireNonNull(getClass().getResource("Failed.png")));
            ImageIcon imageset2 = new ImageIcon(image1.getImage().getScaledInstance(1000 , -1, Image.SCALE_DEFAULT));
            JLabel congrats = new JLabel(imageset2);
            congrats.setLocation(105,  0);
            congrats.setSize(500, 300);
            congrats.setForeground(Color.WHITE);
            layeredPane.add(congrats, JLayeredPane.PALETTE_LAYER);
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
            ButtonSound.setFramePosition(0);  // Rewind the sound
            ButtonSound.start();
        }
        else{

        }
    }

    public static void setPopSoundVolume(int volume) {
        FloatControl gainControl2 = (FloatControl) ButtonSound.getControl(FloatControl.Type.MASTER_GAIN);
        float gain = (float) (Math.log(volume / 100.0) / Math.log(10.0) * 20.0);
        gainControl2.setValue(gain+2);
    }
    public void initializeBackgroundMusic(){
        try {
            AudioInputStream audioInput2 = AudioSystem.getAudioInputStream(Objects.requireNonNull(getClass().getResource("ending.wav")));
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
    public static void adjustBackgroundMusicVolume(int volume) {
        FloatControl gainControl2 = (FloatControl) backgroundMusicClip2.getControl(FloatControl.Type.MASTER_GAIN);
        float gain = (float) (Math.log(volume / 100.0) / Math.log(10.0) * 20.0);
        gainControl2.setValue(gain-5);
    }

}
