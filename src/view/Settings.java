package view;

import controller.GameController;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.SliderUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.prefs.Preferences;

public class Settings extends JFrame implements ChangeListener, ActionListener {
    private Font customfont2;
    private Font customfont;
    private ChessGameFrame chessGameFrame;
    public GameController gameController;
    private JSlider slider;
    private static final String SLIDER_LOCATION_KEY = "sliderLocation";
    private String fontPath = "GAMERIA.ttf";
    private Font customfont3;
    private Font customfont4;
    private JButton save;
    private Timer enlargeTimer;
    private Timer shrinkTimer;
    private Timer enlargeTimer2;
    private Timer shrinkTimer2;
    private Timer enlargeTimer3;
    private Timer shrinkTimer3;
    private JButton load;
    private ImageIcon originalImageIcon;
    private JButton Return;
    private String username1;
    private String username2;
    private String username3;
    private String paths;
    private Clip ButtonSound;

    public Settings(ChessGameFrame chessGameFrame){
        this.chessGameFrame = chessGameFrame;
        customfont = loadFont("GAMERIA.ttf", 50f);
        customfont2 = loadFont("GAMERIA.ttf", 30f);
        customfont3 = loadFont("GAMERIA.ttf", 40f);
        customfont4 = loadFont("GAMERIA.ttf", 40f);
        enlargeTimer = new Timer(15, this);
        shrinkTimer = new Timer(15, this);
        shrinkTimer.setInitialDelay(0);
        shrinkTimer.setCoalesce(true);
        enlargeTimer.setInitialDelay(0);
        enlargeTimer.setCoalesce(true);
        enlargeTimer2 = new Timer(15, this);
        enlargeTimer2.setInitialDelay(0);
        enlargeTimer2.setCoalesce(true);
        shrinkTimer2 = new Timer(15, this);
        shrinkTimer2.setInitialDelay(0);
        shrinkTimer2.setCoalesce(true);

        enlargeTimer3 = new Timer(15, this);
        enlargeTimer3.setInitialDelay(0);
        enlargeTimer3.setCoalesce(true);
        shrinkTimer3 = new Timer(15, this);
        shrinkTimer3.setInitialDelay(0);
        shrinkTimer3.setCoalesce(true);
        int initialSliderLocation = loadSliderLocation();
        slider = new JSlider(0, 100, initialSliderLocation);
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
        addSave(layeredPane);
        addLoadButton(layeredPane);
        addReturn(layeredPane);
        addSlider(layeredPane);
        addTitle(layeredPane);
        initializePopSoundEffect();
        pack();
    }
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
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
            newSize = customfont3.getSize2D() + 1;
        } else {
            newSize = customfont3.getSize2D() - 1;
        }

        customfont3 = customfont3.deriveFont(newSize);
        save.setFont(customfont3);
        save.repaint();

        if (enlarge && newSize >= 50) {
            enlargeTimer.stop();
        } else if (!enlarge && newSize <= 40) {
            shrinkTimer.stop();
        }
    }
    private void animateFont2(boolean enlarge) {
        float newSize;
        if (enlarge) {
            newSize = customfont4.getSize2D() + 1;
        } else {
            newSize = customfont4.getSize2D() - 1;
        }

        customfont4 = customfont4.deriveFont(newSize);
        load.setFont(customfont4);
        load.repaint();

        if (enlarge && newSize >= 50) {
            enlargeTimer2.stop();
        } else if (!enlarge && newSize <= 40) {
            shrinkTimer2.stop();
        }
    }

    private void addTitle(JLayeredPane layeredPane){
        JLabel title = new JLabel("SETTINGS");
        JLabel sound = new JLabel("Sound Volume");
        title.setBounds(0, 0, 300, 100);
        title.setLocation(245, -50);
        title.setSize(300, 200);
        title.setFont(customfont);
        title.setForeground(Color.WHITE);
        sound.setBounds(0, 0, 400, 100);
        sound.setLocation(250, 85);
        sound.setSize(400, 100);
        sound.setFont(customfont2);
        sound.setForeground(Color.WHITE);
        layeredPane.add(title, JLayeredPane.PALETTE_LAYER);
        layeredPane.add(sound, JLayeredPane.PALETTE_LAYER);
    }

    private void addSave(JLayeredPane layeredPane) {
        save = new JButton("SAVE");
        save.setBackground(new Color(	224, 255, 255));
        save.setBorder(new LineBorder(new Color(0, 139, 139), 2,true));
        save.setFocusPainted(false);
        save.setContentAreaFilled(false);
        save.setBorderPainted(false);
        save.setForeground(Color.WHITE);
        save.setFont(customfont3);
        save.addMouseListener(new MouseAdapter() {
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
        save.addActionListener(e -> {
            System.out.println("Click save");
            dispose();
            try {
                playPopSound();
                SaveLoad lmao = new SaveLoad(chessGameFrame);
                lmao.setLocationRelativeTo(null);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        save.setLocation(100,  275);
        save.setSize(200, 60);
        layeredPane.add(save, JLayeredPane.PALETTE_LAYER);
    }
    public String getUsername1(){return username1;}
    public String getUsername2(){return username2;}
    public String getUsername3(){return username3;}
    private void addLoadButton(JLayeredPane layeredPane) {
        load = new JButton("LOAD");
        load.setForeground(Color.BLACK);
        load.setBackground(new Color(	224, 255, 255));
        load.setBorder(new LineBorder(new Color(0, 139, 139), 2,true));
        load.setFocusPainted(false);
        load.setContentAreaFilled(false);
        load.setBorderPainted(false);
        load.setForeground(Color.WHITE);
        load.setLocation(400, 275);
        load.setSize(200, 60);
        load.setFont(customfont4);
        load.addMouseListener(new MouseAdapter() {
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
        layeredPane.add(load, JLayeredPane.PALETTE_LAYER);

        load.addActionListener(e -> {
            System.out.println("Click load");
            dispose();
            Loaded bananas = null;
            try {
                playPopSound();
                bananas = new Loaded(chessGameFrame);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            bananas.setLocationRelativeTo(null);
        });
    }
    public void addReturn(JLayeredPane layeredPane){
        ImageIcon mainmenu = new ImageIcon(Objects.requireNonNull(getClass().getResource("Arrow.png")));
        ImageIcon imageset = new ImageIcon(mainmenu.getImage().getScaledInstance(300 , -1, Image.SCALE_DEFAULT));
        originalImageIcon = imageset;
        Return = new JButton(imageset);
        Return.setBounds(0, 0, 500, 500);
        Return.setFocusPainted(false);
        Return.setContentAreaFilled(false);
        Return.setBorderPainted(false);
        Return.setSize(100, 100);
        Return.setLocation(0, 300);
        Return.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                enlargeTimer3.start();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                shrinkTimer3.start();
            }
        });
        Return.addActionListener(e -> {
            playPopSound();
            dispose();
        });
        layeredPane.add(Return, JLayeredPane.PALETTE_LAYER);
    }
    public void addSlider (JLayeredPane layeredPane){
        int initialSliderLocation = loadSliderLocation();
        slider = new JSlider(0, 100, initialSliderLocation);
        slider.setBounds(50, 50, 400, 200);
        slider.setLocation(160, 100);
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(10);
        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(25);
        slider.setPaintLabels(true);
        slider.setFont(new Font("Tahoma", Font.BOLD, 15));
        slider.setForeground(Color.WHITE);
        slider.setOrientation(SwingConstants.HORIZONTAL);
        slider.setOpaque(false);
        slider.addChangeListener(this);
        layeredPane.add(slider, JLayeredPane.PALETTE_LAYER);
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        int volume = slider.getValue();
        chessGameFrame.adjustBackgroundMusicVolume(volume);
        gameController.setPopSoundVolume(volume);
        chessGameFrame.setPopSoundVolume(volume);
        LaunchPage.setPopSoundVolume(volume);
        LaunchPage.adjustBackgroundMusicVolume(volume);
        setPopSoundVolume(volume);
        NextLevel.setPopSoundVolume(volume);
        Loaded.setPopSoundVolume(volume);
        SaveLoad.setPopSoundVolume(volume);
        Warning.setPopSoundVolume(volume);
        ChessboardComponent.setPopSoundVolume(volume);
    }
    private void saveSliderLocation() {
        Preferences prefs = Preferences.userNodeForPackage(Settings.class);
        prefs.putInt(SLIDER_LOCATION_KEY, slider.getValue());
    }

    private int loadSliderLocation() {
        Preferences prefs = Preferences.userNodeForPackage(Settings.class);
        return prefs.getInt(SLIDER_LOCATION_KEY, 50);
    }

    @Override
    public void dispose() {
        saveSliderLocation();
        super.dispose();
    }
    public static int getInitialSliderLocation() {
        Preferences prefs = Preferences.userNodeForPackage(Settings.class);
        return prefs.getInt(SLIDER_LOCATION_KEY, 50);
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enlargeTimer) {
            animateFont(true);
        } else if (e.getSource() == shrinkTimer) {
            animateFont(false);
        }else if (e.getSource() == enlargeTimer2) {
            animateFont2(true);
        }else if (e.getSource() == shrinkTimer2) {
            animateFont2(false);
        }
        if (e.getSource() == enlargeTimer3) {
            animateImageSize(true);
        } else if (e.getSource() == shrinkTimer3) {
            animateImageSize(false);
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
            enlargeTimer3.stop();
        } else if (!enlarge && newWidth <= originalImageIcon.getIconWidth()) {
            shrinkTimer3.stop();
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
