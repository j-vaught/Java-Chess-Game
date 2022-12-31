package chess.game;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.UIManager;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BoardGUI {

	private JFrame chessGameFrame;
	private Color oppositeColorofWhite = new Color(211,211,211);
	private Color legalMoveColor = new Color(137, 207, 240);
	private Color takenMoveColor = new Color(234, 60, 83);
	private Font defaultFont=new Font(null, Font.PLAIN, 70);
	private JButton Square[][];
	private int[] movePiecelocation;
	private boolean isWhiteTurn=true;
	private Board board=new Board();
	private String [] letters = {"A","B","C","D","E","F","G","H"};
	String[] pawnPromotionOptions = {"Queen", "Knight", "Rook", "Bishop"};
	//Server server = new Server(3333);
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BoardGUI window = new BoardGUI();
					window.chessGameFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}//end of catch
			}//end of method run
		});
	}//end of maiun mehtod

	public BoardGUI() {
		initialize();
	}//end of boardgui

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//server.initialize();
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");} catch (Exception e) {}
		chessGameFrame = new JFrame();
		chessGameFrame.setResizable(false);
		chessGameFrame.setTitle("Chess Game-V 1.3.2");
		chessGameFrame.setBounds(100, 100, 900, 900);
		chessGameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel panel_1 = new JPanel();
		chessGameFrame.getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 8, 0, 0));

		Square= new JButton[8][8]; 
		for(int i =7; i>=0;i--) {
			for(int j=0;j<8;j++) {
				Square[i][j] = new JButton(letters[j]+(i+1));
				if(i%2!=0&&j%2==0||i%2==0&&j%2!=0) {
					Square[i][j].setBackground(Color.WHITE);
				}if(i%2!=0&&j%2!=0||i%2==0&&j%2==0) {
					Square[i][j].setBackground(oppositeColorofWhite);
				}// end of if
				Square[i][j].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						determinePiecemovement( getbuttonLocation( (JButton) e.getSource() ) );
					}});
				panel_1.add(Square[i][j]);
			}//end of for
		}//end of for
		painter();
	}//end of method initialize

	public boolean determinePiecemovement(int[] pieceLocation) {
		if((Square[pieceLocation[0]][pieceLocation[1]].getBackground()==legalMoveColor||Square[pieceLocation[0]][pieceLocation[1]].getBackground()==takenMoveColor)&&movePiecelocation!=null ) {
			board.movePiece(movePiecelocation, pieceLocation);
			
			//Adding Networking capabilities
			//server.sendString(movePiecelocation.toString()+pieceLocation.toString());
			
			isWhiteTurn=!isWhiteTurn;
			movePiecelocation=null;
			painter();
			
			if(board.boardPieces[pieceLocation[0]][pieceLocation[1]].getPieceType()==Piece.Pawn&&(pieceLocation[0]==0||pieceLocation[0]==7)) {
				boolean changePawnBoolean=false;
				do{
					changePawnBoolean=changePawnType(pieceLocation);
				}while(!changePawnBoolean);
				painter();
			}//end of if
			return true;
		}else if(!board.isInCheckMate(isWhiteTurn)){
			painter();
			if(board.boardPieces[pieceLocation[0]][pieceLocation[1]]!=null&&board.boardPieces[pieceLocation[0]][pieceLocation[1]].isWhite()==isWhiteTurn) {
			//edit above line for network
				//if(board.boardPieces[pieceLocation[0]][pieceLocation[1]].isWhite()==true) {
				possiblemovespainter(board.getRealLegalMoves(pieceLocation), legalMoveColor);
				possiblemovespainter(board.getTakenMoves(pieceLocation), takenMoveColor);
				movePiecelocation= pieceLocation.clone();
				return true;
				//}
			}//end of if
		}else if(board.isInCheckMate(isWhiteTurn)) {
			String text="Black";
			if(isWhiteTurn) {
				text="White";
			}//end of if
			JOptionPane.showMessageDialog( null, text+ " is in checkmate. \nPlease Close The Chess Game Window!" );
			return true;
		}// end of else if 
		return false;
	}//end of determine piece movement method

	public void possiblemovespainter(ArrayList<Integer> locations, Color color) {
		if(locations.size()>0) {
			for(int i=0; i<locations.size()/2;i++) {
				Square[locations.get(2*i)][locations.get(2*i+1)].setBackground(color);
			}//end of for
		}//end of if
	}//end of method possiblemovespainter
	public void painter() {
		for(int i =7; i>=0;i--) {
			for(int j=0;j<8;j++) {
				if(i%2!=0&&j%2==0||i%2==0&&j%2!=0) {
					Square[i][j].setBackground(Color.WHITE);
				}if(i%2!=0&&j%2!=0||i%2==0&&j%2==0) {
					Square[i][j].setBackground(oppositeColorofWhite);
				}// end of if
				Square[i][j].setFont(defaultFont);
				if(board.boardPieces[i][j]==null) {
					Square[i][j].setText("");
				}else {
					String text=null;
					if(board.boardPieces[i][j].isWhite()) {
						switch(board.boardPieces[i][j].getPieceType()) {
						case King:
							text="\u2654";
							break;
						case Queen:
							text="\u2655";
							break;
						case Rook:
							text="\u2656";
							break;
						case Bishop:
							text="\u2657";
							break;
						case Knight:
							text="\u2658";
							break;
						case Pawn:
							text="\u2659";
							break;
						default:
							text="";
						}//end of switch
					}else if(!board.boardPieces[i][j].isWhite()) {
						
						switch(board.boardPieces[i][j].getPieceType()) {
						case King:
							text="\u265A";
							break;
						case Queen:
							text="\u265B";
							break;
						case Rook:
							text="\u265C";
							break;
						case Bishop:
							text="\u265D";
							break;
						case Knight:
							text="\u265E";
							break;
						case Pawn:
							text="\u265F";
							break;
						default:
							text="";
						}//edn of switch
					}//edn of else if
					Square[i][j].setText(text);
				}//edn of else
			}//end of for
		}//end of for
	}//end of method

	public int[] getbuttonLocation(JButton temp) {
		int [] location;
		for(int i =0; i<8;i++) {
			for(int j=0;j<8;j++) {
				if(Square[i][j]==temp) {
					location= new int[2];
					location[0]=i;
					location[1]=j;
					return location;
				}//End of if
			}//end of for
		}//end of for
		return null;
	}//end of class

	public boolean changePawnType(int[] piecelocation) {
		int selection = JOptionPane.showOptionDialog(null,
				"Upgrade Your Pawn! Limited time only.",
				"Choose a Promotion",
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,
				pawnPromotionOptions,
				pawnPromotionOptions[0]);
		switch (selection){
		case 0:
			board.changePawn(piecelocation, Piece.Queen);
			return true;
		case 1:
			board.changePawn(piecelocation, Piece.Knight);
			return true;
		case 2:
			board.changePawn(piecelocation, Piece.Rook);
			return true;
		case 3:
			board.changePawn(piecelocation, Piece.Bishop);
			return true;
		default:
			return false;
		}//end of switch
	}//end of piecelocation method
	
}//end of GUI class
