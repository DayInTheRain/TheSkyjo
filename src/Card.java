
public class Card {
	private int value;
	private boolean faceUp;
	
	public Card() {
		value = -3;
		faceUp = true;
	}
	
	public Card(int x) {
		value = x; 
		faceUp = false;
	}
	
	public Card(int x, boolean f) {
		value = x; 
		faceUp = f;
	}
	
	public int getValue() {
		return value;
	}
	
	public boolean getFace() {
		return faceUp;
	}
	
	public Card setFaceUp(boolean f) {
		faceUp = f;
		return this;
	}
	
	public boolean equals(Card c) {
		return this.value == c.getValue();
	}
}
