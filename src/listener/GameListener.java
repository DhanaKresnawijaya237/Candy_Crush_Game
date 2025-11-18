package listener;

import model.ChessboardPoint;
import view.CellComponent;
import view.ChessComponent;

import java.awt.event.ActionEvent;

public interface GameListener {
    void onPlayerClickCell(ChessboardPoint point, CellComponent component);
    void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component);
    public void onPlayerSwapChess();
    public void onPlayerNextStep();
    public void ActionPerformed(ActionEvent e);
}
