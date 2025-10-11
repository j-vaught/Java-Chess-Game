

package game;

public enum PieceType {
	Pawn("Pawn  "), Bishop("Bishop"), Knight("Knight"), Rook("Rook  "), Queen("Queen "), King("King  ");
	private final String name;
	PieceType(String string) {
		this.name=string;
	}
	public String toString() {
		return this.name;
	}
}// Ending bracket of enum Pieces.
