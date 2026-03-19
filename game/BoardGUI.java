

package game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class BoardGUI {

    private JFrame chessGameFrame;
    private final Color oppositeColorofWhite = new Color(211, 211, 211);
    private final Color legalMoveColor = new Color(137, 207, 240);
    private final Color takenMoveColor = new Color(234, 60, 83);
    private final Font defaultFont = new Font(null, Font.PLAIN, 70);
    private JButton[][] square;
    private Position selectedPiecePosition;
    private final Game game = new Game();
    private final String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
    String[] pawnPromotionOptions = {"Queen", "Knight", "Rook", "Bishop"};

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                BoardGUI window = new BoardGUI();
                window.chessGameFrame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public BoardGUI() {
        initialize();
    }

    private void initialize() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        chessGameFrame = new JFrame();
        chessGameFrame.setResizable(false);
        chessGameFrame.setTitle("Chess Game-V 1.3.2");
        chessGameFrame.setBounds(100, 100, 900, 900);
        chessGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel panel_1 = new JPanel();
        chessGameFrame.getContentPane().add(panel_1, BorderLayout.CENTER);
        panel_1.setLayout(new GridLayout(0, 8, 0, 0));

        square = new JButton[8][8];
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                square[i][j] = new JButton(letters[j] + (i + 1));
                if (i % 2 != 0 && j % 2 == 0 || i % 2 == 0 && j % 2 != 0) {
                    square[i][j].setBackground(Color.WHITE);
                }
                if (i % 2 != 0 && j % 2 != 0 || i % 2 == 0 && j % 2 == 0) {
                    square[i][j].setBackground(oppositeColorofWhite);
                }
                final int finalI = i;
                final int finalJ = j;
                square[i][j].addActionListener(e -> handleSquareClick(new Position(finalI, finalJ)));
                panel_1.add(square[i][j]);
            }
        }
        updateBoard();
    }

    private void handleSquareClick(Position position) {
        if (selectedPiecePosition == null) {
            GamePiece piece = game.getBoard().boardPieces[position.getRow()][position.getColumn()];
            if (piece != null && piece.isWhite() == game.isWhiteTurn()) {
                selectedPiecePosition = position;
                highlightLegalMoves(position);
            }
        } else {
            List<Position> legalMoves = game.getBoard().getLegalMoves(selectedPiecePosition);
            if (legalMoves.contains(position)) {
                game.move(selectedPiecePosition, position);
                if (game.getBoard().boardPieces[position.getRow()][position.getColumn()].getPieceType() == PieceType.Pawn && (position.getRow() == 0 || position.getRow() == 7)) {
                    promotePawn(position);
                }
                selectedPiecePosition = null;
                updateBoard();
                if (game.isCheckmate()) {
                    String winner = game.isWhiteTurn() ? "Black" : "White";
                    JOptionPane.showMessageDialog(null, "Checkmate! " + winner + " wins.");
                }
            } else {
                selectedPiecePosition = null;
                updateBoard();
            }
        }
    }

    private void highlightLegalMoves(Position position) {
        List<Position> legalMoves = game.getBoard().getLegalMoves(position);
        List<Position> threatenedMoves = game.getBoard().getThreatenedMoves(position);
        for (Position move : legalMoves) {
            square[move.getRow()][move.getColumn()].setBackground(legalMoveColor);
        }
        for (Position move : threatenedMoves) {
            square[move.getRow()][move.getColumn()].setBackground(takenMoveColor);
        }
    }

    private void updateBoard() {
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < 8; j++) {
                if (i % 2 != 0 && j % 2 == 0 || i % 2 == 0 && j % 2 != 0) {
                    square[i][j].setBackground(Color.WHITE);
                }
                if (i % 2 != 0 && j % 2 != 0 || i % 2 == 0 && j % 2 == 0) {
                    square[i][j].setBackground(oppositeColorofWhite);
                }
                square[i][j].setFont(defaultFont);
                GamePiece piece = game.getBoard().boardPieces[i][j];
                if (piece == null) {
                    square[i][j].setText("");
                } else {
                    square[i][j].setText(getPieceUnicode(piece));
                }
            }
        }
    }

    private String getPieceUnicode(GamePiece piece) {
        switch (piece.getPieceType()) {
            case King:
                return piece.isWhite() ? "\u2654" : "\u265A";
            case Queen:
                return piece.isWhite() ? "\u2655" : "\u265B";
            case Rook:
                return piece.isWhite() ? "\u2656" : "\u265C";
            case Bishop:
                return piece.isWhite() ? "\u2657" : "\u265D";
            case Knight:
                return piece.isWhite() ? "\u2658" : "\u265E";
            case Pawn:
                return piece.isWhite() ? "\u2659" : "\u265F";
            default:
                return "";
        }
    }

    public void promotePawn(Position pieceLocation) {
        int selection = JOptionPane.showOptionDialog(null,
                "Upgrade Your Pawn! Limited time only.",
                "Choose a Promotion",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                pawnPromotionOptions,
                pawnPromotionOptions[0]);
        PieceType selectedPiece;
        switch (selection) {
            case 0:
                selectedPiece = PieceType.Queen;
                break;
            case 1:
                selectedPiece = PieceType.Knight;
                break;
            case 2:
                selectedPiece = PieceType.Rook;
                break;
            case 3:
                selectedPiece = PieceType.Bishop;
                break;
            default:
                return;
        }
        game.promotePawn(pieceLocation, selectedPiece);
    }
}
