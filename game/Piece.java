

public enum Piece {
	Pawn("Pawn  "), Bishop("Bishop"), Knight("Knight"), Rook("Rook  "), Queen("Queen "), King("King  ");
	private final String name;
	Piece(String string) {
		this.name=string;
	}
	public String toString() {
		return this.name;
	}
}// Ending bracket of enum Pieces.
