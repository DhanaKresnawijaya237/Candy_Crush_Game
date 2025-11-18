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
import java.nio.file.StandardOpenOption;
import java.rmi.server.RemoteRef;
import java.util.List;
import java.util.Objects;

public class SaveLoad extends JFrame implements ActionListener {
    private Timer enlargeTimer;
    private Timer shrinkTimer;
    private JButton Return;
    private ImageIcon originalImageIcon;
    private ChessGameFrame chessGameFrame;
    private String username1;
    private String username2;
    private String username3;
    private static Clip ButtonSound;

    public SaveLoad(ChessGameFrame chessGameFrame) throws IOException {
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
        java.util.List<String> line1 = null;
        String errorm;
        JButton button;
        try {
            line1 = Files.readAllLines(Path.of("src\\chessboard.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(line1.isEmpty())
        {
            errorm = "";
            button= new JButton(errorm);
        }
        else
        {
            String[] values = line1.get(9).split(",");
            button = new JButton(line1.get(0) + "   Level " + line1.get(9).charAt(0)+ "   Score " + values[2] + "/" + values[3]);
        }

        button.setForeground(Color.BLACK);
        button.setBackground(new Color(139, 69, 19));
        button.setBorder(new LineBorder(new Color(205, 133, 63), 2, true));
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setLocation(30, 30);
        button.setSize(600, 75);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        layeredPane.add(button, JLayeredPane.PALETTE_LAYER);

        button.addActionListener(e -> {
            playPopSound();
            Path filePath = Path.of("src\\chessboard.txt");


            java.util.List<String> line2 = null;
            try {
                line2 = Files.readAllLines(Path.of("src\\chessboard.txt"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            if(line2.isEmpty())
            {
                username1 = JOptionPane.showInputDialog(this, "Give username");
                if(username1!=null)
                {
                    chessGameFrame.writeFileByFileWriter("src\\chessboard.txt", username1);
                    dispose();
                }
            }
            else
            {
                int result = JOptionPane.showConfirmDialog(this, "Do you want to overwrite the savefile","Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    username1 = JOptionPane.showInputDialog(this, "Give username");
                    if(username1!=null)
                    {
                        try {
                            Files.write(filePath, new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
                            System.out.println("File content erased successfully.");
                        } catch (IOException r) {
                            r.printStackTrace();
                        }
                        chessGameFrame.writeFileByFileWriter("src\\chessboard.txt", username1);
                        dispose();
                    }

                }
            }
        });
    }


    private void addUsername2(JLayeredPane layeredPane) {
        java.util.List<String> line2 = null;
        String errorm;
        JButton button;
        try {
            line2 = Files.readAllLines(Path.of("src\\chessboard2.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(line2.isEmpty())
        {
            errorm = "";
            button= new JButton(errorm);
        }
        else
        {
            String[] values = line2.get(9).split(",");
            button = new JButton(line2.get(0)+ "   Level " + line2.get(9).charAt(0)+ "   Score " + values[2] + "/" + values[3]);
        }

        button.setForeground(Color.BLACK);
        button.setBackground(new Color(139, 69, 19));
        button.setBorder(new LineBorder(new Color(205, 133, 63), 2, true));
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setLocation(30, 160-30);
        button.setSize(600, 75);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        layeredPane.add(button, JLayeredPane.PALETTE_LAYER);

        button.addActionListener(e -> {
            playPopSound();
            Path filePath = Path.of("src\\chessboard2.txt");


            java.util.List<String> line1 = null;
            try {
                line1 = Files.readAllLines(Path.of("src\\chessboard2.txt"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            if(line1.isEmpty())
            {
                username2 = JOptionPane.showInputDialog(this, "Give username");
                if(username2!=null)
                {
                    chessGameFrame.writeFileByFileWriter("src\\chessboard2.txt", username2);
                    dispose();
                }

            }
            else
            {
                int result = JOptionPane.showConfirmDialog(this, "Do you want to overwrite the savefile","Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    username2 = JOptionPane.showInputDialog(this, "Give username");
                    if(username2!=null)
                    {
                        try {
                            Files.write(filePath, new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
                            System.out.println("File content erased successfully.");
                        } catch (IOException r) {
                            r.printStackTrace();
                        }
                        chessGameFrame.writeFileByFileWriter("src\\chessboard2.txt", username2);
                        dispose();
                    }
                }
            }
        });
    }
    private void addUsername3(JLayeredPane layeredPane) {
        java.util.List<String> line2 = null;
        String errorm;
        JButton button;
        try {
            line2 = Files.readAllLines(Path.of("src\\chessboard3.txt"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(line2.isEmpty())
        {
            errorm = "";
            button= new JButton(errorm);
        }
        else
        {
            String[] values = line2.get(9).split(",");
            button = new JButton(line2.get(0)+ "   Level " + line2.get(9).charAt(0)+ "   Score " + values[2] + "/" + values[3]);
        }

        button.setForeground(Color.BLACK);
        button.setBackground(new Color(139, 69, 19));
        button.setBorder(new LineBorder(new Color(205, 133, 63), 2, true));
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setLocation(30, 290-60);
        button.setSize(600, 75);
        button.setFont(new Font("Tahoma", Font.BOLD, 20));
        layeredPane.add(button, JLayeredPane.PALETTE_LAYER);

        button.addActionListener(e -> {
            playPopSound();
            Path filePath = Path.of("src\\chessboard3.txt");


            java.util.List<String> line1 = null;
            try {
                line1 = Files.readAllLines(Path.of("src\\chessboard3.txt"));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            if(line1.isEmpty())
            {
                username3 = JOptionPane.showInputDialog(this, "Give username");
                if(username3!=null)
                {
                    chessGameFrame.writeFileByFileWriter("src\\chessboard3.txt", username3);
                    dispose();
                }

            }
            else
            {
                int result = JOptionPane.showConfirmDialog(this, "Do you want to overwrite the savefile","Confirmation", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    username3 = JOptionPane.showInputDialog(this, "Give username");
                    if(username3!=null)
                    {
                        try {
                            Files.write(filePath, new byte[0], StandardOpenOption.TRUNCATE_EXISTING);
                            System.out.println("File content erased successfully.");
                        } catch (IOException r) {
                            r.printStackTrace();
                        }
                        chessGameFrame.writeFileByFileWriter("src\\chessboard3.txt", username3);
                        dispose();
                    }
                }
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
