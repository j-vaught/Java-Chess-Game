package game;

public class Game {
    private Board board;
    private boolean isWhiteTurn;

    public Game() {
        this.board = new Board();
        this.isWhiteTurn = true;
    }

    public Board getBoard() {
        return board;
    }

    public boolean isWhiteTurn() {
        return isWhiteTurn;
    }

    public void move(Position oldPosition, Position newPosition) {
        board.movePiece(oldPosition, newPosition);
        isWhiteTurn = !isWhiteTurn;
    }

    public boolean isCheckmate() {
        return board.isCheckmate(isWhiteTurn);
    }

    public void promotePawn(Position position, PieceType type) {
        board.promotePawn(position, type);
    }
}