

import java.util.ArrayList;

public class Board {

	/**
	 * The Board
	 */
	public Pieces boardPieces [][]={
			{new Pieces(true,Piece.Rook),new Pieces(true,Piece.Knight),new Pieces(true,Piece.Bishop),new Pieces(true,Piece.Queen),new Pieces(true,Piece.King),new Pieces(true,Piece.Bishop),new Pieces(true,Piece.Knight),new Pieces(true,Piece.Rook)},
			{new Pieces(true,Piece.Pawn),new Pieces(true,Piece.Pawn),new Pieces(true,Piece.Pawn),new Pieces(true,Piece.Pawn),new Pieces(true,Piece.Pawn),new Pieces(true,Piece.Pawn),new Pieces(true,Piece.Pawn),new Pieces(true,Piece.Pawn)},
			{null,null,null,null,null,null,null,null},
			{null,null,null,null,null,null,null,null},
			{null,null,null,null,null,null,null,null},
			{null,null,null,null,null,null,null,null},
			{new Pieces(false,Piece.Pawn),new Pieces(false,Piece.Pawn),new Pieces(false,Piece.Pawn),new Pieces(false,Piece.Pawn),new Pieces(false,Piece.Pawn),new Pieces(false,Piece.Pawn),new Pieces(false,Piece.Pawn),new Pieces(false,Piece.Pawn)},
			{new Pieces(false,Piece.Rook),new Pieces(false,Piece.Knight),new Pieces(false,Piece.Bishop),new Pieces(false,Piece.Queen),new Pieces(false,Piece.King),new Pieces(false,Piece.Bishop),new Pieces(false,Piece.Knight),new Pieces(false,Piece.Rook)}
	};
//Server server = new Server(3333);
	/**
	 * @return Copy of Board(boardPieces)
	 */
	public Pieces[][] getBoard(){
		return getBoardCopy(this.boardPieces);
	}
	
	/**
	 * @param inputs <code>board</code> of Pieces
	 * @return returns copy of <code>board</code> of Pieces
	 */
	private Pieces[][] getBoardCopy(Pieces[][] board){
		Pieces tempBoard[][]=new Pieces[board.length][];
		for(int j = 0; j < board.length; j++)
			tempBoard[j] = board[j].clone();
		return tempBoard;
	}

	/**
	 * @param Piece <code>pieceType</code> 
	 * @param <code>isWhite</code> the color of piece
	 * @return int[] location of piece searching for(usually just kings)
	 */
	public int[] findPiece(Piece pieceType, boolean isWhite, Pieces[][] board) {
		for(int i =0; i<8;i++) {
			for(int j=0;j<8;j++) {
				if(board[i][j]!=null && board[i][j].isWhite()==isWhite && board[i][j].getPieceType()==pieceType) {
					int[] temp = {i,j};
					return temp;
				}//end of if
			}//end of for
		}//end of for
		return null;
	}//end of find piece method

	/**
	 * @param <code>oldLocation</code> , Location of piece in question in array form so a1==[0,0], and a8=[7,0]
	 * @param <code>newLocation</code> , Location of piece in question in array form so a1==[0,0], and a8=[7,0]
	 */
	public void movePiece(int[] oldLocation, int[] newLocation) {
		//rook and king switch places
		//server.initialize();
		//server.sendString("["+oldLocation[0]+","+oldLocation[1]+","+newLocation[0]+","+newLocation[1]+"]");
		if(this.getBoard()[oldLocation[0]][oldLocation[1]].getPieceType()==Piece.King&&newLocation[1]-oldLocation[1]==2) {
			this.boardPieces[newLocation[0]][5]=this.boardPieces[oldLocation[0]][7];
			this.boardPieces[oldLocation[0]][7]=null;
		}//end of if
		this.boardPieces[newLocation[0]][newLocation[1]]=this.boardPieces[oldLocation[0]][oldLocation[1]];
		this.boardPieces[oldLocation[0]][oldLocation[1]]=null;
	}//end of move piece method

	/**
	 * @param <code>pieceLocation</code> , Location of piece in question in array form so a1==[0,0], and a8=[7,0]
	 * @param <code>newType</code>, new type to replace pawn
	 */
	public void changePawn(int[] pieceLocation, Piece newType) {
		if(this.boardPieces[pieceLocation[0]][pieceLocation[1]]==null) { return;};
		this.boardPieces[pieceLocation[0]][pieceLocation[1]].setPieceType(newType);
	}//end of method changePawn

	/**
	 * @param <code>pieceLocation</code> , Location of piece in question in array form so a1==[0,0], and a8=[7,0]
	 * @return ArrayList of possible moves for Pawn, first digit is row, and second is column, ex. 2,3,6,7 is 2,3 and 6,7 and 2 and 6 are rows while 3 and 7 are columns
	 */
	private ArrayList<Integer> Pawn(int[] pieceLocation, Pieces[][] board) {
		ArrayList<Integer> availablePositions = new ArrayList<Integer>();
		int row= pieceLocation[0];
		int column=pieceLocation[1];
		boolean isWhite=board[row][column].isWhite();
		int array[][] = {{1,1,1,2},
				{0,-1,1,0}};
		int repeater =1;
		int multiplier=1;
		if(!isWhite) {
			multiplier=-1;
		}if(row==1||row==6) {
			repeater=2;
		}//end of if

		for(int i=0; i<repeater; i++) {
			int j=i*3;
			int rowAddition=array[0][j]*multiplier;
			int columnAddition=array[1][j]*multiplier;
			boolean isRowBetwwen0and8 = row+rowAddition>=0&&8>row+rowAddition;
			boolean isColumnBetween0and8 = column+columnAddition>=0&&8>column+columnAddition;
			if(isRowBetwwen0and8&isColumnBetween0and8) {
				if(board[row+rowAddition][column+columnAddition]==null) {
					availablePositions.add(row+rowAddition);
					availablePositions.add(column+columnAddition);
				}else {
					break;
				}//End of else
			}//end of if
		}//End of for
		for(int i=1; i<3; i++) {
			int rowAddition=array[0][i]*multiplier;
			int columnAddition=array[1][i]*multiplier;
			boolean isRowBetwwen0and8 = row+rowAddition>=0&&8>row+rowAddition;
			boolean isColumnBetween0and8 = column+columnAddition>=0&&8>column+columnAddition;
			if(isRowBetwwen0and8&isColumnBetween0and8) {
				if(board[row+rowAddition][column+columnAddition]==null) {
				}else if(board[row+rowAddition][column+columnAddition].isWhite()!=isWhite){
					availablePositions.add(row+rowAddition);
					availablePositions.add(column+columnAddition);
				}//end of if
			}//end of if
		}//end of for

		return availablePositions;
	}//end of Pawns checking system
	/**
	 * @param <code>pieceLocation</code> , Location of piece in question in array form so a1==[0,0], and a8=[7,0]
	 * @return ArrayList of possible moves for Rook, first digit is row, and second is column, ex. 2,3,6,7 is 2,3 and 6,7 and 2 and 6 are rows while 3 and 7 are columns
	 */
	private ArrayList<Integer> Rook(int[] pieceLocation, Pieces[][] board) {
		ArrayList<Integer> availablePositions = new ArrayList<Integer>();
		int row= pieceLocation[0];
		int column=pieceLocation[1];
		boolean isWhite=board[row][column].isWhite();
		int array[][] = {{0,0,1,-1},
				{1,-1,0,0}};
		for(int i=0; i<4; i++) {
			for(int j=1;j<8;j++) {

				int rowAddition=array[0][i]*j;
				int columnAddition=array[1][i]*j;
				boolean isRowBetwwen0and8 = row+rowAddition>=0&&8>row+rowAddition;
				boolean isColumnBetween0and8 = column+columnAddition>=0&&8>column+columnAddition;

				if(isRowBetwwen0and8&isColumnBetween0and8) {
					if(board[row+rowAddition][column+columnAddition]==null) {
						availablePositions.add(row+rowAddition);
						availablePositions.add(column+columnAddition);
					}else if(board[row+rowAddition][column+columnAddition].isWhite()!=isWhite){
						availablePositions.add(row+rowAddition);
						availablePositions.add(column+columnAddition);
						break;
					}else {
						break;
					}//end of else
				}//end of if
			}//end of for
		}//End of for
		return availablePositions;
	}
	/**
	 * @param <code>pieceLocation</code> , Location of piece in question in array form so a1==[0,0], and a8=[7,0]
	 * @return ArrayList of possible moves for Knight, first digit is row, and second is column, ex. 2,3,6,7 is 2,3 and 6,7 and 2 and 6 are rows while 3 and 7 are columns
	 */
	private ArrayList<Integer> Knight(int[] pieceLocation, Pieces[][] board) {
		ArrayList<Integer> availablePositions = new ArrayList<Integer>();
		int row= pieceLocation[0];
		int column=pieceLocation[1];
		boolean isWhite=board[row][column].isWhite();
		int array[][] = {{2,2,-2,-2,1,-1,1,-1},
				{1,-1,1,-1,2,2,-2,-2}};
		for(int i=0; i<8; i++) {
			int rowAddition=array[0][i];
			int columnAddition=array[1][i];
			boolean isRowBetwwen0and8 = row+rowAddition>=0&&8>row+rowAddition;
			boolean isColumnBetween0and8 = column+columnAddition>=0&&8>column+columnAddition;

			if(isRowBetwwen0and8&isColumnBetween0and8) {
				if(board[row+rowAddition][column+columnAddition]==null||board[row+rowAddition][column+columnAddition].isWhite()!=isWhite) {
					availablePositions.add(row+rowAddition);
					availablePositions.add(column+columnAddition);
				}//end of if
			}//end of if
		}//end of for
		return availablePositions;
	}//end of knight
	/**
	 * @param <code>pieceLocation</code> , Location of piece in question in array form so a1==[0,0], and a8=[7,0]
	 * @return ArrayList of possible moves for Bishop, first digit is row, and second is column, ex. 2,3,6,7 is 2,3 and 6,7 and 2 and 6 are rows while 3 and 7 are columns
	 */
	private ArrayList<Integer> Bishop(int[] pieceLocation, Pieces[][] board) {
		ArrayList<Integer> availablePositions = new ArrayList<Integer>();
		int row= pieceLocation[0];
		int column=pieceLocation[1];
		boolean isWhite=board[row][column].isWhite();
		int array[][] = {{1,1,-1,-1},
				{1,-1,1,-1}};

		for(int i=0; i<4; i++) {
			for(int j=1;j<8;j++) {

				int rowAddition=array[0][i]*j;
				int columnAddition=array[1][i]*j;
				boolean isRowBetwwen0and8 = row+rowAddition>=0&&8>row+rowAddition;
				boolean isColumnBetween0and8 = column+columnAddition>=0&&8>column+columnAddition;

				if(isRowBetwwen0and8&isColumnBetween0and8) {
					if(board[row+rowAddition][column+columnAddition]==null) {
						availablePositions.add(row+rowAddition);
						availablePositions.add(column+columnAddition);
					}else if(board[row+rowAddition][column+columnAddition].isWhite()!=isWhite){
						availablePositions.add(row+rowAddition);
						availablePositions.add(column+columnAddition);
						break;
					}else {
						break;
					}//end of else
				}//end of if
			}//end of for
		}//End of for
		return availablePositions;
	}//end of BIshop
	/**
	 * @param <code>pieceLocation</code> , Location of piece in question in array form so a1==[0,0], and a8=[7,0]
	 * @return ArrayList of possible moves for Queen, first digit is row, and second is column, ex. 2,3,6,7 is 2,3 and 6,7 and 2 and 6 are rows while 3 and 7 are columns
	 */
	private ArrayList<Integer> Queen(int[] pieceLocation, Pieces[][] board) {
		ArrayList<Integer> availablePositions = new ArrayList<Integer>();
		availablePositions.addAll(this.Rook(pieceLocation, board));
		availablePositions.addAll(this.Bishop(pieceLocation,board));
		return availablePositions;
	}//end of Queen
	/**
	 * @param <code>pieceLocation</code> , Location of piece in question in array form so a1==[0,0], and a8=[7,0]
	 * @return ArrayList of possible moves for King, first digit is row, and second is column, ex. 2,3,6,7 is 2,3 and 6,7 and 2 and 6 are rows while 3 and 7 are columns
	 */
	private ArrayList<Integer> King(int[] pieceLocation, Pieces[][] board) {
		ArrayList<Integer> availablePositions = new ArrayList<Integer>();
		int row= pieceLocation[0];
		int column=pieceLocation[1];
		boolean isWhite=board[row][column].isWhite();
		int array[][] = {{1,1,-1,-1,0,0,1,-1},
				{1,-1,1,-1,1,-1,0,0}};
		int rowforrook=0;
		//rook and king switch places
		if(!isWhite) {
			rowforrook=7;
		}if((column==4&&row==rowforrook)&&(board[rowforrook][5]==null&&board[rowforrook][6]==null)&&board[rowforrook][7].getPieceType()==Piece.Rook&&board[rowforrook][7].isWhite()==isWhite) {
			availablePositions.add(rowforrook);
			availablePositions.add(6);
		}
		
		for(int i=0;i<8;i++) {
			int rowAddition=array[0][i];
			int columnAddition=array[1][i];
			boolean isRowBetwwen0and8 = row+rowAddition>=0&&8>row+rowAddition;
			boolean isColumnBetween0and8 = column+columnAddition>=0&&8>column+columnAddition;

			if(isRowBetwwen0and8&isColumnBetween0and8) {
				if(board[row+rowAddition][column+columnAddition]==null) {
					availablePositions.add(row+rowAddition);
					availablePositions.add(column+columnAddition);
				}else if(board[row+rowAddition][column+columnAddition].isWhite()!=isWhite){
					availablePositions.add(row+rowAddition);
					availablePositions.add(column+columnAddition);
				}//end of if
			}//end of if
		}//End of for
		return availablePositions;
	}//end of King

	/**
	 * @param <code>pieceLocation</code> , Location of piece in question in array form so a1==[0,0], and a8=[7,0]
	 * @param <code>board</code>, The board where the pieces are located, a 2 dimensional array of Pieces
	 * @return ArrayList of possible moves for chosen piece but does not check for king in check, first digit is row, and second is column, ex. 2,3,6,7 is 2,3 and 6,7 and 2 and 6 are rows while 3 and 7 are columns
	 */
	private ArrayList<Integer> possibleLegalMoves(int[] pieceLocation, Pieces[][] board) {
		int row= pieceLocation[0];
		int column=pieceLocation[1];
		Piece temporaryPiece = board[row][column].getPieceType();
		switch(temporaryPiece) {
		case King:
			return this.King(pieceLocation,board);
		case Queen:
			return this.Queen(pieceLocation,board);
		case Bishop:
			return this.Bishop(pieceLocation,board);
		case Knight:
			return this.Knight(pieceLocation,board);
		case Rook:
			return this.Rook(pieceLocation,board);
		case Pawn:
			return this.Pawn(pieceLocation,board);
		default:
			return null;
		}//end of switch
	}//End of getLegalMoves
	
	/**
	 * @param <code>isWhite</code> the color of piece
	 * @param <code>board</code>, The board where the pieces are located, a 2 dimensional array of Pieces
	 * @return true if king is in check
	 */
	private boolean isKingInCheck(boolean isWhite, Pieces[][] board) {
		int[] array = this.findPiece(Piece.King, isWhite,board);
		for(int row =0; row<8;row++) {
			for(int column=0;column<8;column++) {
				if(board[row][column]!=null && board[row][column].isWhite()!=isWhite) {
					int[] temporaryLocation = {row,column};
					ArrayList<Integer> possibleMoves = this.possibleLegalMoves(temporaryLocation,board);
					if(canPieceBeTakenHelper(array,possibleMoves)) {
						return true;
					}//end of if
				}//end of if
			}//end of for
		}//End of for
		return false;
	}//end of king in check method

	/**
	 * @param <code>pieceLocation</code> , Location of piece in question in array form so a1==[0,0], and a8=[7,0]
	 * @param <code>board</code>, The board where the pieces are located, a 2 dimensional array of Pieces
	 * @return ArrayList of possible moves for chosen piece, first digit is row, and second is column, ex. 2,3,6,7 is 2,3 and 6,7 and 2 and 6 are rows while 3 and 7 are columns
	 */
	private ArrayList<Integer>getLegalMoves(int[] pieceLocation, Pieces[][] board) {
		ArrayList<Integer> returnArrayList = new ArrayList<Integer>();
		ArrayList<Integer> piecePossibleMoves =this.possibleLegalMoves(pieceLocation, board);
		if(piecePossibleMoves!=null) {
			for(int i=0; i<piecePossibleMoves.size()/2;i++) {
				Pieces temporaryBoard[][]=this.getBoard();
				Pieces temporaryPiece=temporaryBoard[pieceLocation[0]][pieceLocation[1]];
				temporaryBoard[pieceLocation[0]][pieceLocation[1]]=null;
				int temporaryRow=piecePossibleMoves.get(i*2);
				int temporaryColumn=piecePossibleMoves.get(i*2+1);
				temporaryBoard[temporaryRow][temporaryColumn]=temporaryPiece;
				if(!this.isKingInCheck(temporaryBoard[temporaryRow][temporaryColumn].isWhite(), temporaryBoard)) {
					returnArrayList.add(temporaryRow);
					returnArrayList.add(temporaryColumn);
				}//end of if
			}//end of for
			return returnArrayList;
		}//end of if
		return null;
	}//end of method getRealLegalMoves

	/**
	 * @param <code>pieceLocation</code> , Location of piece in question in array form so a1==[0,0], and a8=[7,0], etc.
	 * @param <code>attackingPiecePossibleMoves</code>, Arraylist of possible moves from opponent
	 * @return true or false
	 */
	private boolean canPieceBeTakenHelper(int[] pieceLocation, ArrayList<Integer> attackingPiecePossibleMoves){
		if(attackingPiecePossibleMoves!=null&&attackingPiecePossibleMoves.size()>0) {
			for(int i=0;i<attackingPiecePossibleMoves.size()/2;i++) {
				int temporaryRow=attackingPiecePossibleMoves.get(i*2);
				int temporaryColumn=attackingPiecePossibleMoves.get(i*2+1);
				if(temporaryRow==pieceLocation[0]&&temporaryColumn==pieceLocation[1]) {
					return true;
				}//end of if
			}//End of for
		}//end of if
		return false;
	}//end of method can piece be taken.

	/**
	 * @param <code>pieceLocation</code> , Location of piece in question in array form so a1==[0,0], and a8=[7,0]
	 * @param <code>board</code>, The board where the pieces are located, a 2 dimensional array of Pieces
	 * @return true or false depending on whether piece can be taken at position it is in.
	 */
	private boolean canPieceBeTaken(int[] pieceLocation, Pieces[][] board) {
		boolean isWhite = board[pieceLocation[0]][pieceLocation[1]].isWhite();
		for(int row=0; row<8;row++) {
			for(int column=0;column<8;column++) {
				if(board[row][column]!=null && board[row][column].isWhite()!=isWhite) {
					int[] temporaryLocation = {row,column};
					ArrayList<Integer> temp =this.possibleLegalMoves(temporaryLocation,board);
					if(canPieceBeTakenHelper(pieceLocation,temp)) {
						return true;
					}//end of if
				}//end of if
			}//end of for
		}//end of for
		return false;
	}//end of can piece be taken at position method
	
	/**
	 * @param <code>inputArrayList</code>, ArrayList to remove duplicate locations from
	 * @return ArrayList without the duplicate locations in it.
	 */
	private ArrayList<Integer> duplicateLocationRemoverforArrayList(ArrayList<Integer> inputArrayList){
		ArrayList<Integer> returnArrayList = new ArrayList<Integer>();
		if(inputArrayList!=null&&inputArrayList.size()>0) {
			for(int i=0;i<inputArrayList.size()/2;i++) {
				int temporaryRow=inputArrayList.get(i*2);
				int temporaryColumn=inputArrayList.get(i*2+1);
				if(returnArrayList!=null) {
					boolean skipAdditionToArrayList=false;
					for(int j=0;j<returnArrayList.size()/2;j++) {
						if(temporaryRow==returnArrayList.get(j*2)&&temporaryColumn==returnArrayList.get(j*2+1)) {
							skipAdditionToArrayList=true;
							break;
						}//end of if
					}//end of for
					if(!skipAdditionToArrayList) {
						returnArrayList.add(temporaryRow);
						returnArrayList.add(temporaryColumn);
					}//end of if
				}//end of if
			}//end of for
		}//end of if
		return returnArrayList;
	}//end of duplicate locations in arraylist remover method


	/**
	 * @param <code>pieceLocation</code> , Location of piece in question in array form so a1==[0,0], and a8=[7,0]
	 * @param <code>board</code>, The board where the pieces are located, a 2 dimensional array of Pieces
	 * @return ArrayList of Locations where piece can be taken in one move
	 */
	private ArrayList<Integer> pieceTakenLocations(int[] pieceLocation, Pieces[][] board) {
		ArrayList<Integer> returnArrayList = new ArrayList<Integer>();
		ArrayList<Integer> possiblePieceMoves=this.getRealLegalMoves(pieceLocation);
		//repeats move for each possible move
		for(int i=0;i<possiblePieceMoves.size()/2;i++) {
			//creating tempboard
			Pieces temporaryBoard[][]=this.getBoardCopy(board);
			//moving piece now
			Pieces temporaryPiece=temporaryBoard[pieceLocation[0]][pieceLocation[1]];
			temporaryBoard[pieceLocation[0]][pieceLocation[1]]=null;
			int temporaryRow=possiblePieceMoves.get(i*2);
			int temporaryColumn=possiblePieceMoves.get(i*2+1);
			int [] temporaryPieceLocation= {temporaryRow,temporaryColumn};
			temporaryBoard[temporaryRow][temporaryColumn]=temporaryPiece;
			//Piece has been moved
			//add to arraylist
			if(this.canPieceBeTaken(temporaryPieceLocation, temporaryBoard)) {
				returnArrayList.add(temporaryRow);
				returnArrayList.add(temporaryColumn);
			}//end of if
		}//end of for
		return this.duplicateLocationRemoverforArrayList(returnArrayList);
		//add to arraylist
	}//edn of place taken locations method


	/**
	 * @param <code>pieceLocation</code> , Location of piece in question in array form so a1==[0,0], and a8=[7,0]
	 * @return Arraylist of moves that can be taken by piece.
	 */
	public ArrayList<Integer> getTakenMoves(int[] pieceLocation){
		return this.pieceTakenLocations(pieceLocation, this.getBoard());
	}//end of get taken moves method

	/**
	 * @param <code>pieceLocation</code> , Location of piece in question in array form so a1==[0,0], and a8=[7,0]
	 * @return Legal moves, which includes if a king is in check, so you have to protect them
	 */
	public ArrayList<Integer> getRealLegalMoves(int[] pieceLocation) {
		return this.getLegalMoves(pieceLocation, this.boardPieces);
	}//end of real legal moves method

	/**
	 * @param <code>isWhite</code> the color of piece
	 * @return boolean true if is in checkMate
	 */
	public boolean isInCheckMate(boolean isWhite) {
		//run get real legal moves for every piece of chosen color.
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for(int i =0; i<8;i++) {
			for(int j=0;j<8;j++) {
				if(this.boardPieces[i][j]!=null && this.boardPieces[i][j].isWhite()==isWhite) {
					int[] location = {i,j};
					temp.addAll(this.getRealLegalMoves(location));
				}//end of if
			}//end of for
		}//end of for
		if(temp.size()>0) {
			//if your color can move then king is not in check mate
			return false;
		}//end of if
		return true;
	}//end of check mate method

	/**
	 * @param <code>board</code>, The board where the pieces are located, a 2 dimensional array of Pieces
	 * Prints board onto console
	 */
	public void commandLinePrinter(Pieces[][] board) {
		String [] letters = {"A","B","C","D","E","F","G","H"};
		for(int i =0; i<8;i++) {
			for(int j=0;j<8;j++) {
				System.out.print((i+1));
				System.out.print(letters[j]);
				if(board[i][j]!=null) {
					System.out.print(" "+board[i][j].getPieceType().toString()+" |");
				}else {
					System.out.print(" Empty  |");
				}//end of else
			}//end of for
			System.out.print("\n");
		}//End of for
	}//end of printer

	
	
	
}//End of class