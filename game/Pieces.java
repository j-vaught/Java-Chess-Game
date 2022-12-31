package chess.game;



public class Pieces {

	private boolean isWhite;
	private Piece type;
	
	public Pieces(boolean iswhite, Piece tYpe) {

		this.setPieceType(tYpe);
		this.setIsWhite(iswhite);
	}//default constructor
	
	public Pieces() {
		
	}
	
	public void setIsWhite(boolean iswhite) {
		isWhite=iswhite;
	}
	public boolean isWhite() {
		return isWhite;
	}
	public void setPieceType(Piece newpiece) {
		type=newpiece;
	}
	public Piece getPieceType() {
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
