



package game;

public class GamePiece {

	private boolean isWhite;
	private PieceType type;

	public GamePiece(boolean isWhite, PieceType type) {
		this.setPieceType(type);
		this.setIsWhite(isWhite);
	}//default constructor

	public GamePiece() {

	}

	public void setIsWhite(boolean isWhite) {
		this.isWhite = isWhite;
	}
	public boolean isWhite() {
		return isWhite;
	}
	public void setPieceType(PieceType newpiece) {
		type=newpiece;
	}
	public PieceType getPieceType() {
		return type;
	}
	public String getPieceColor() {
		if(this.isWhite()) {
			return "White";
		}else {
			return "Black";
		}
	}
}
