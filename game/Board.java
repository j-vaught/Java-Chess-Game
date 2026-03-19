

package game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Board {

    private static final int BOARD_SIZE = 8;

    public GamePiece[][] boardPieces = {
            {new GamePiece(true, PieceType.Rook), new GamePiece(true, PieceType.Knight), new GamePiece(true, PieceType.Bishop), new GamePiece(true, PieceType.Queen), new GamePiece(true, PieceType.King), new GamePiece(true, PieceType.Bishop), new GamePiece(true, PieceType.Knight), new GamePiece(true, PieceType.Rook)},
            {new GamePiece(true, PieceType.Pawn), new GamePiece(true, PieceType.Pawn), new GamePiece(true, PieceType.Pawn), new GamePiece(true, PieceType.Pawn), new GamePiece(true, PieceType.Pawn), new GamePiece(true, PieceType.Pawn), new GamePiece(true, PieceType.Pawn), new GamePiece(true, PieceType.Pawn)},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {null, null, null, null, null, null, null, null},
            {new GamePiece(false, PieceType.Pawn), new GamePiece(false, PieceType.Pawn), new GamePiece(false, PieceType.Pawn), new GamePiece(false, PieceType.Pawn), new GamePiece(false, PieceType.Pawn), new GamePiece(false, PieceType.Pawn), new GamePiece(false, PieceType.Pawn), new GamePiece(false, PieceType.Pawn)},
            {new GamePiece(false, PieceType.Rook), new GamePiece(false, PieceType.Knight), new GamePiece(false, PieceType.Bishop), new GamePiece(false, PieceType.Queen), new GamePiece(false, PieceType.King), new GamePiece(false, PieceType.Bishop), new GamePiece(false, PieceType.Knight), new GamePiece(false, PieceType.Rook)}
    };

    public GamePiece[][] getBoard() {
        return getBoardCopy(this.boardPieces);
    }

    private GamePiece[][] getBoardCopy(GamePiece[][] board) {
        GamePiece[][] tempBoard = new GamePiece[board.length][];
        for (int j = 0; j < board.length; j++) {
            tempBoard[j] = board[j].clone();
        }
        return tempBoard;
    }

    public Position findPiece(PieceType pieceType, boolean isWhite, GamePiece[][] board) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] != null && board[i][j].isWhite() == isWhite && board[i][j].getPieceType() == pieceType) {
                    return new Position(i, j);
                }
            }
        }
        return null;
    }

    public void movePiece(Position oldLocation, Position newLocation) {
        if (this.getBoard()[oldLocation.getRow()][oldLocation.getColumn()].getPieceType() == PieceType.King && newLocation.getColumn() - oldLocation.getColumn() == 2) {
            this.boardPieces[newLocation.getRow()][5] = this.boardPieces[oldLocation.getRow()][7];
            this.boardPieces[oldLocation.getRow()][7] = null;
        }
        this.boardPieces[newLocation.getRow()][newLocation.getColumn()] = this.boardPieces[oldLocation.getRow()][oldLocation.getColumn()];
        this.boardPieces[oldLocation.getRow()][oldLocation.getColumn()] = null;
    }

    public void promotePawn(Position pieceLocation, PieceType newType) {
        if (this.boardPieces[pieceLocation.getRow()][pieceLocation.getColumn()] == null) {
            return;
        }
        this.boardPieces[pieceLocation.getRow()][pieceLocation.getColumn()].setPieceType(newType);
    }

    private List<Position> getPawnMoves(Position pieceLocation, GamePiece[][] board) {
        List<Position> availablePositions = new ArrayList<>();
        int row = pieceLocation.getRow();
        int column = pieceLocation.getColumn();
        boolean isWhite = board[row][column].isWhite();
        int direction = isWhite ? 1 : -1;
        int startRow = isWhite ? 1 : 6;

        // Forward moves
        Position oneStep = new Position(row + direction, column);
        if (isValid(oneStep) && board[oneStep.getRow()][oneStep.getColumn()] == null) {
            availablePositions.add(oneStep);
            if (row == startRow) {
                Position twoSteps = new Position(row + 2 * direction, column);
                if (isValid(twoSteps) && board[twoSteps.getRow()][twoSteps.getColumn()] == null) {
                    availablePositions.add(twoSteps);
                }
            }
        }

        // Capture moves
        int[] captureCols = {column - 1, column + 1};
        for (int captureCol : captureCols) {
            Position captureMove = new Position(row + direction, captureCol);
            if (isValid(captureMove) && board[captureMove.getRow()][captureMove.getColumn()] != null && board[captureMove.getRow()][captureMove.getColumn()].isWhite() != isWhite) {
                availablePositions.add(captureMove);
            }
        }

        return availablePositions;
    }

    private List<Position> getRookMoves(Position pieceLocation, GamePiece[][] board) {
        List<Position> availablePositions = new ArrayList<>();
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        addSlidingPieceMoves(pieceLocation, board, availablePositions, directions);
        return availablePositions;
    }

    private List<Position> getKnightMoves(Position pieceLocation, GamePiece[][] board) {
        List<Position> availablePositions = new ArrayList<>();
        int row = pieceLocation.getRow();
        int column = pieceLocation.getColumn();
        boolean isWhite = board[row][column].isWhite();
        int[][] moves = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};

        for (int[] move : moves) {
            Position newPos = new Position(row + move[0], column + move[1]);
            if (isValid(newPos)) {
                if (board[newPos.getRow()][newPos.getColumn()] == null || board[newPos.getRow()][newPos.getColumn()].isWhite() != isWhite) {
                    availablePositions.add(newPos);
                }
            }
        }
        return availablePositions;
    }

    private List<Position> getBishopMoves(Position pieceLocation, GamePiece[][] board) {
        List<Position> availablePositions = new ArrayList<>();
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        addSlidingPieceMoves(pieceLocation, board, availablePositions, directions);
        return availablePositions;
    }

    private List<Position> getQueenMoves(Position pieceLocation, GamePiece[][] board) {
        List<Position> availablePositions = new ArrayList<>();
        availablePositions.addAll(this.getRookMoves(pieceLocation, board));
        availablePositions.addAll(this.getBishopMoves(pieceLocation, board));
        return availablePositions;
    }

    private List<Position> getKingMoves(Position pieceLocation, GamePiece[][] board) {
        List<Position> availablePositions = new ArrayList<>();
        int row = pieceLocation.getRow();
        int column = pieceLocation.getColumn();
        boolean isWhite = board[row][column].isWhite();
        int[][] moves = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        for (int[] move : moves) {
            Position newPos = new Position(row + move[0], column + move[1]);
            if (isValid(newPos)) {
                if (board[newPos.getRow()][newPos.getColumn()] == null || board[newPos.getRow()][newPos.getColumn()].isWhite() != isWhite) {
                    availablePositions.add(newPos);
                }
            }
        }

        // Castling
        int castleRow = isWhite ? 0 : 7;
        if (row == castleRow && column == 4) {
            // Kingside
            if (board[castleRow][5] == null && board[castleRow][6] == null && board[castleRow][7] != null && board[castleRow][7].getPieceType() == PieceType.Rook) {
                availablePositions.add(new Position(castleRow, 6));
            }
        }

        return availablePositions;
    }

    private List<Position> getPossibleMoves(Position pieceLocation, GamePiece[][] board) {
        PieceType temporaryPiece = board[pieceLocation.getRow()][pieceLocation.getColumn()].getPieceType();
        switch (temporaryPiece) {
            case King:
                return this.getKingMoves(pieceLocation, board);
            case Queen:
                return this.getQueenMoves(pieceLocation, board);
            case Bishop:
                return this.getBishopMoves(pieceLocation, board);
            case Knight:
                return this.getKnightMoves(pieceLocation, board);
            case Rook:
                return this.getRookMoves(pieceLocation, board);
            case Pawn:
                return this.getPawnMoves(pieceLocation, board);
            default:
                return new ArrayList<>();
        }
    }

    private boolean isKingInCheck(boolean isWhite, GamePiece[][] board) {
        Position kingPosition = this.findPiece(PieceType.King, isWhite, board);
        if (kingPosition == null) {
            return false;
        }
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                if (board[row][column] != null && board[row][column].isWhite() != isWhite) {
                    Position temporaryLocation = new Position(row, column);
                    List<Position> possibleMoves = this.getPossibleMoves(temporaryLocation, board);
                    if (possibleMoves.contains(kingPosition)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private List<Position> getFilteredLegalMoves(Position pieceLocation, GamePiece[][] board) {
        List<Position> returnArrayList = new ArrayList<>();
        List<Position> piecePossibleMoves = this.getPossibleMoves(pieceLocation, board);
        if (piecePossibleMoves != null) {
            for (Position move : piecePossibleMoves) {
                GamePiece[][] temporaryBoard = this.getBoardCopy(board);
                GamePiece temporaryPiece = temporaryBoard[pieceLocation.getRow()][pieceLocation.getColumn()];
                temporaryBoard[pieceLocation.getRow()][pieceLocation.getColumn()] = null;
                temporaryBoard[move.getRow()][move.getColumn()] = temporaryPiece;
                if (!this.isKingInCheck(temporaryPiece.isWhite(), temporaryBoard)) {
                    returnArrayList.add(move);
                }
            }
            return returnArrayList;
        }
        return new ArrayList<>();
    }

    private boolean isPositionUnderAttack(Position pieceLocation, GamePiece[][] board) {
        boolean isWhite = board[pieceLocation.getRow()][pieceLocation.getColumn()].isWhite();
        for (int row = 0; row < BOARD_SIZE; row++) {
            for (int column = 0; column < BOARD_SIZE; column++) {
                if (board[row][column] != null && board[row][column].isWhite() != isWhite) {
                    Position temporaryLocation = new Position(row, column);
                    List<Position> temp = this.getPossibleMoves(temporaryLocation, board);
                    if (temp.contains(pieceLocation)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Set<Position> getThreatenedMoves(Position pieceLocation, GamePiece[][] board) {
        Set<Position> returnArrayList = new HashSet<>();
        List<Position> possiblePieceMoves = this.getLegalMoves(pieceLocation);
        for (Position move : possiblePieceMoves) {
            GamePiece[][] temporaryBoard = this.getBoardCopy(board);
            GamePiece temporaryPiece = temporaryBoard[pieceLocation.getRow()][pieceLocation.getColumn()];
            temporaryBoard[pieceLocation.getRow()][pieceLocation.getColumn()] = null;
            temporaryBoard[move.getRow()][move.getColumn()] = temporaryPiece;
            if (this.isPositionUnderAttack(move, temporaryBoard)) {
                returnArrayList.add(move);
            }
        }
        return returnArrayList;
    }


    public List<Position> getThreatenedMoves(Position pieceLocation) {
        return new ArrayList<>(this.getThreatenedMoves(pieceLocation, this.getBoard()));
    }

    public List<Position> getLegalMoves(Position pieceLocation) {
        return this.getFilteredLegalMoves(pieceLocation, this.boardPieces);
    }

    public boolean isCheckmate(boolean isWhite) {
        if (!isKingInCheck(isWhite, boardPieces)) {
            return false;
        }
        List<Position> allMoves = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (this.boardPieces[i][j] != null && this.boardPieces[i][j].isWhite() == isWhite) {
                    allMoves.addAll(this.getLegalMoves(new Position(i, j)));
                }
            }
        }
        return allMoves.isEmpty();
    }

    public void printBoard(GamePiece[][] board) {
        String[] letters = {"A", "B", "C", "D", "E", "F", "G", "H"};
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print((i + 1));
                System.out.print(letters[j]);
                if (board[i][j] != null) {
                    System.out.print(" " + board[i][j].getPieceType().toString() + " |");
                } else {
                    System.out.print(" Empty  |");
                }
            }
            System.out.print("\n");
        }
    }

    private boolean isValid(Position pos) {
        return pos.getRow() >= 0 && pos.getRow() < BOARD_SIZE && pos.getColumn() >= 0 && pos.getColumn() < BOARD_SIZE;
    }

    private void addSlidingPieceMoves(Position pieceLocation, GamePiece[][] board, List<Position> availablePositions, int[][] directions) {
        int row = pieceLocation.getRow();
        int column = pieceLocation.getColumn();
        boolean isWhite = board[row][column].isWhite();

        for (int[] dir : directions) {
            for (int i = 1; i < BOARD_SIZE; i++) {
                Position newPos = new Position(row + dir[0] * i, column + dir[1] * i);
                if (isValid(newPos)) {
                    if (board[newPos.getRow()][newPos.getColumn()] == null) {
                        availablePositions.add(newPos);
                    } else if (board[newPos.getRow()][newPos.getColumn()].isWhite() != isWhite) {
                        availablePositions.add(newPos);
                        break;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
        }
    }
}