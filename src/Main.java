import controller.GameController;
import model.Chessboard;
import view.ChessGameFrame;
import view.LaunchPage;
import view.NextLevel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LaunchPage launchPage = new LaunchPage();
            launchPage.setLocationRelativeTo(null);
        });
    }
}
