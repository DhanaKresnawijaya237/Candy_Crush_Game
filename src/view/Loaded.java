package view;

import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;


public class Loaded extends JFrame implements ActionListener {
    private Timer enlargeTimer;
    private Timer shrinkTimer;
    private JButton Return;
    private ImageIcon originalImageIcon;
    private ChessGameFrame chessGameFrame;
    private static Clip ButtonSound;
    private static int level=0;
    public static int getLevel() {
        return level;
    }
    public Loaded(ChessGameFrame chessGameFrame) throws IOException {
        this.chessGameFrame = chessGameFrame;
        enlargeTimer = new Timer(15, this);
        enlargeTimer.setInitialDelay(0);
        enlargeTimer.setCoalesce(true);
        shrinkTimer = new Timer(15, this);
        shrinkTimer.setInitialDelay(0);
        shrinkTimer.setCoalesce(true);
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
        addUsername1(layeredPane);
        addUsername2(layeredPane);
        addUsername3(layeredPane);
        addReturn(layeredPane);
        initializePopSoundEffect();
        pack();
    }


    private void addUsername1(JLayeredPane layeredPane){
        List<String> line1 = null;
        String errorm;
        JButton button;
        try {
            line1 = Files.readAllLines(Path.of("src\\chessboard.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (line1.isEmpty()) {
            errorm = "";
            button = new JButton(errorm);
        } else {
            try {
                String[] values = line1.get(9).split(",");
                button = new JButton(line1.get(0) + "   Level " + line1.get(9).charAt(0) + "   Score " + values[2] + "/" + values[3]);
            } catch (Exception e) {
                e.printStackTrace();
                button = new JButton("Chessboard not 8*8");
            }
        }
        button.setBackground(new Color(139, 69, 19));
        button.setBorder(new LineBorder(new Color(205, 133, 63), 2, true));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setLocation(30, 30);
        button.setSize(600, 75);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        layeredPane.add(button, JLayeredPane.PALETTE_LAYER);

        button.addActionListener(e -> {
            playPopSound();
            List<String> line2 = null;
            try {
                String fileName = "src\\chessboard.txt";
                if (!fileName.toLowerCase().endsWith(".txt")) {
                    JOptionPane.showMessageDialog(this, "Not Correct File Type");
                    return;
                }
                line2 = Files.readAllLines(Path.of("src\\chessboard.txt"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            if(line2.isEmpty())
                JOptionPane.showMessageDialog(this, "No Save files to load");
            else
            {
                String[] values = line2.get(9).split(",");
                for (int i = 1; i <= 7; i++) {
                    String row = line2.get(i);
                    String[] rowValues = row.split(",");
                    if (rowValues.length != 8) {
                        JOptionPane.showMessageDialog(this, "Chessboard is not 8*8 row");
                        return;
                    }
                }
                for (int i = 1; i <= 7; i++) {
                    String row = line2.get(i);
                    String[] rowValues = row.split(",");
                    for (String value : rowValues) {
                        int intValue = Integer.parseInt(value);
                        if (intValue < 1 || intValue > 5) {
                            JOptionPane.showMessageDialog(this, "Error Loading Image");
                            return;
                        }
                    }
                }
                if(Integer.parseInt(values[0])!=ChessGameFrame.getLevel())
                {
                    level = Integer.parseInt(values[0]);
                    chessGameFrame.dispose();
                    LaunchPage.initializeGame();
                    chessGameFrame.StopMusic();
                    level=0;
                }
                else
                    level=0;
                chessGameFrame.transitlmao("src\\chessboard.txt");
                dispose();
            }
        });
    }


    private void addUsername2(JLayeredPane layeredPane) {
        List<String> line2 = null;
        String errorm;
        JButton button;
        try {
            line2 = Files.readAllLines(Path.of("src\\chessboard2.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (line2.isEmpty()) {
            errorm = "";
            button = new JButton(errorm);
        } else {
            try {
                String[] values = line2.get(9).split(",");
                button = new JButton(line2.get(0) + "   Level " + line2.get(9).charAt(0) + "   Score " + values[2] + "/" + values[3]);
            } catch (Exception e) {
                e.printStackTrace();
                button = new JButton("Chessboard not 8*8");
            }
        }
        button.setForeground(Color.BLACK);
        button.setBackground(new Color(139, 69, 19));
        button.setBorder(new LineBorder(new Color(205, 133, 63), 2, true));
        button.setForeground(Color.WHITE);
        button.setLocation(30, 130);
        button.setFocusPainted(false);
        button.setSize(600, 75);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        layeredPane.add(button, JLayeredPane.PALETTE_LAYER);

        button.addActionListener(e -> {
            playPopSound();
            List<String> line1 = null;
            try {
                String fileName = "src\\chessboard2.txt";
                if (!fileName.toLowerCase().endsWith(".txt")) {
                    JOptionPane.showMessageDialog(this, "Not Correct File Type");
                    return;
                }
                line1 = Files.readAllLines(Path.of("src\\chessboard2.txt"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            if(line1.isEmpty())
                JOptionPane.showMessageDialog(this, "bruh");
            else
            {
                String[] values = line1.get(9).split(",");
                for (int i = 1; i <= 7; i++) {
                    String row = line1.get(i);
                    String[] rowValues = row.split(",");
                    if (rowValues.length != 8) {
                        JOptionPane.showMessageDialog(this, "Chessboard is not 8*8 row");
                        return;
                    }
                }
                for (int i = 1; i <= 7; i++) {
                    String row = line1.get(i);
                    String[] rowValues = row.split(",");
                    for (String value : rowValues) {
                        int intValue = Integer.parseInt(value);
                        if (intValue < 1 || intValue > 5) {
                            JOptionPane.showMessageDialog(this, "Error Loading Image");
                            return;
                        }
                    }
                }
                if(Integer.parseInt(values[0])!=ChessGameFrame.getLevel())
                {
                    level = Integer.parseInt(values[0]);
                    chessGameFrame.dispose();
                    LaunchPage.initializeGame();
                    chessGameFrame.StopMusic();
                    level=0;
                }
                else
                    level=0;
                chessGameFrame.transitlmao("src\\chessboard2.txt");
                dispose();
            }
        });
    }
    private void addUsername3(JLayeredPane layeredPane) {
        List<String> line2 = null;
        String errorm;
        JButton button;
        try {
            line2 = Files.readAllLines(Path.of("src\\chessboard3.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (line2.isEmpty()) {
            errorm = "";
            button = new JButton(errorm);
        } else {
            try {
                String[] values = line2.get(9).split(",");
                button = new JButton(line2.get(0) + "   Level " + line2.get(9).charAt(0) + "   Score " + values[2] + "/" + values[3]);
            } catch (Exception e) {
                e.printStackTrace();
                button = new JButton("Chessboard not 8*8");
            }
        }

        button.setForeground(Color.BLACK);
        button.setBackground(new Color(139, 69, 19));
        button.setBorder(new LineBorder(new Color(205, 133, 63), 2, true));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setLocation(30, 230);
        button.setSize(600, 75);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        layeredPane.add(button, JLayeredPane.PALETTE_LAYER);

        button.addActionListener(e -> {
            playPopSound();
            List<String> line1 = null;
            try {
                String fileName = "src\\chessboard3.txt";
                if (!fileName.toLowerCase().endsWith(".txt")) {
                    JOptionPane.showMessageDialog(this, "Not Correct File Type");
                    return;
                }
                line1 = Files.readAllLines(Path.of("src\\chessboard3.txt"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            if(line1.isEmpty())
                JOptionPane.showMessageDialog(this, "Empyty file");
            else
            {
                String[] values = line1.get(9).split(",");
                for (int i = 1; i <= 7; i++) {
                    String row = line1.get(i);
                    String[] rowValues = row.split(",");
                    if (rowValues.length != 8) {
                        JOptionPane.showMessageDialog(this, "Chessboard is not 8*8 row");
                        return;
                    }
                }
                for (int i = 1; i <= 7; i++) {
                    String row = line1.get(i);
                    String[] rowValues = row.split(",");
                    for (String value : rowValues) {
                        int intValue = Integer.parseInt(value);
                        if (intValue < 1 || intValue > 5) {
                            JOptionPane.showMessageDialog(this, "Error Loading Image");
                            return;
                        }
                    }
                }
                if(Integer.parseInt(values[0])!=ChessGameFrame.getLevel())
                {
                    level = Integer.parseInt(values[0]);
                    chessGameFrame.dispose();
                    LaunchPage.initializeGame();
                    chessGameFrame.StopMusic();
                    level=0;
                }
                else
                    level=0;
                chessGameFrame.transitlmao("src\\chessboard3.txt");
                dispose();
            }
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
        Return.setSize(500, 500);
        Return.setLocation(-200, 115);
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
            enlargeTimer.stop();
        } else if (!enlarge && newWidth <= originalImageIcon.getIconWidth() && newHeight <= originalImageIcon.getIconHeight()) {
            shrinkTimer.stop();
        }
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == enlargeTimer) {
            animateImageSize(true);
        } else if (e.getSource() == shrinkTimer) {
            animateImageSize(false);
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
