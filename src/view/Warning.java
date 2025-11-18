package view;

import controller.GameController;
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


public class Warning extends JFrame implements ActionListener {
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
    private static Clip ButtonSound;

    public Warning(){
        customfont = loadFont("GAMERIA.ttf", 30f);
        customfont2 = loadFont("GAMERIA.ttf", 30f);
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
        layeredPane.setBounds(0, 0, 400, 200);
        ImageIcon image = new ImageIcon(Objects.requireNonNull(getClass().getResource("Background3.jpg")));
        ImageIcon smallimage = new ImageIcon(image.getImage().getScaledInstance(400, -1, Image.SCALE_DEFAULT));
        JLabel background = new JLabel(smallimage);
        background.setBounds(0, 0, 400, 200);
        layeredPane.add(background, JLayeredPane.DEFAULT_LAYER);
        getContentPane().setPreferredSize(new Dimension(400, 200));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(layeredPane);
        setLayout(null);
        setUndecorated(true);
        setVisible(true);
        setLocationRelativeTo(null);
        addTitle(layeredPane);
        addMainMenu(layeredPane);
        if (ChessGameFrame.getLevel() == 5){
        }
        initializePopSoundEffect();
        pack();
    }

    public static int getLevel() {
        return level;
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
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == mainmenu){
            playPopSound();
            dispose();
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

        if (enlarge && newSize >= 35) {
            enlargeTimer2.stop();
        } else if (!enlarge && newSize <= 30) {
            shrinkTimer2.stop();
        }
    }
    public void addMainMenu(JLayeredPane layeredPane){
            mainmenu = new JButton("OK");
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
            mainmenu.setLocation(9-10,  125);
            mainmenu.setSize(400, 60);
            layeredPane.add(mainmenu, JLayeredPane.PALETTE_LAYER);
    }
    private void addTitle(JLayeredPane layeredPane){
        JLabel title = new JLabel("YOu ARE DUMB");
        JLabel sound = new JLabel("CANNOT SWITCH");
        title.setBounds(0, 0, 500, 100);
        title.setLocation(75-10, -50);
        title.setSize(500, 200);
        title.setFont(customfont);
        title.setForeground(Color.WHITE);
        sound.setBounds(0, 0, 400, 100);
        sound.setLocation(135-20, 60-10);
        sound.setSize(500, 100);
        sound.setFont(customfont);
        sound.setForeground(Color.WHITE);
        layeredPane.add(title, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(sound, JLayeredPane.PALETTE_LAYER);
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
