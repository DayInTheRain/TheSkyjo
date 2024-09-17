import java.util.ArrayList;

public class Player {
	
	private Card[][] matrix;
	private int score;
	
	
	public Player() {
		matrix = new Card[3][4];
	}//end of no parameter constructor
	
	public void fillMatrix(int[] values) {
		int i = 0;
		int i1 = (int) (Math.random()* 12);
		int i2 = (int) (Math.random()* 12);
		for(int r  = 0; r < matrix.length; r++ ) {
			for (int c = 0; c < matrix[r].length; c++) {
				if(i == i1 || i == i2) {
					matrix[r][c] = new Card(values[i],true);
					i++;
					continue;
				}
				matrix[r][c] = new Card(values[i], false);
				i++;
			}
		}

			
	}//end of fill Matrix
	
	//not working yet
	public  ArrayList<Card> checkColumns() {
		ArrayList<Card> temp = new ArrayList<Card>();
			for(int c = 0; c < matrix[0].length; c++) {
				if(matrix[0][c].getFace() && matrix[1][c].getFace() && matrix[2][c].getFace()) {
					if(matrix[0][c].equals(matrix[1][c]) && matrix[1][c].equals(matrix[2][c])) {
						if(matrix[0][c].getValue() != -3) {
						temp.add(matrix[0][c]);
						temp.add(matrix[1][c]);
						temp.add(matrix[2][c]);
					
						matrix[0][c] = new Card();
						matrix[1][c] = new Card();
						matrix[2][c] = new Card();
						}
					}
				}
			}
			return temp;	
	}//end of checkColumns
	
	
	public boolean allCardsUp() {
		for(Card[] r : matrix) {
			for(Card c: r) {
				if(!c.getFace())
					return false;
			}
		}
		return true;
	}
	
	public int getCardSum() {
		int sum = 0;
		for(Card[] r : matrix) {
			for(Card c :r) {
				sum += c.getValue();
			}
		}
		return sum;
	}
	
	//getters and setters----------------------------------------
	public int getScore() {return score;}
	public void setScorePlus(int s) {this.score = this.score + s;}
	
	public Card[][] getMatrix(){return matrix;}
	public void setMatrix(int x, int y, Card c) {
		matrix[x][y] = c;
	}
	
}//end of class
