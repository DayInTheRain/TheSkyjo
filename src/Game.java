import java.util.ArrayList;

public class Game {
	 private Player[] players;
	 private String gameState;

	 private int[] numCards = new int[] {5, 10, 15, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10}; // going from -2 to 12;
	 private ArrayList<Card> deck = new ArrayList<Card>();
	 private ArrayList<Card> discardPile = new ArrayList<Card>();
	 
	 private int currentPlayer;
	 private int nextPlayer;
	 
	public Game(int numPlayers) {
		players = new Player[numPlayers];
		
		dealCardsToPlayers();
		shuffleDeck();
		addToDiscard(new Card());
		addToDiscard(getTopDeck());
		removeFromDeck();
		gameState = "beginningRound";
	}//one parameter constructor
	
	
	
	//game mechanic methods ---------------------------------------
	public void shuffleDeck() { //shuffles the deck
		int cardsLeft = 0;
		
		for(int i = numCards.length - 1; i >= 0; i--)
			cardsLeft += numCards[i];
		
		for(int j = 1; j <= cardsLeft; j++) {
			int valCard = (int) (Math.random()*15) - 2;
			while(numCards[valCard + 2] == 0) {	//will give another card if the card does not have any more instances
				valCard = (int) (Math.random()*15) - 2;
			}
			deck.add(new Card (valCard));
			
		}
	}//end of shuffleDeck
	
	public void switchCurrentPlayer() {
		if(currentPlayer == players.length - 1) {
			currentPlayer = 0;
			nextPlayer = 1;
		}else if(nextPlayer == players.length -1){
			nextPlayer = 0;
			currentPlayer++;
		} else {
			currentPlayer++;
			nextPlayer ++;
			
		}
		System.out.println("switchingPlayer" + currentPlayer + nextPlayer);
		startTurn();
	}//end of switchCurrentPlayer

	public void dealCardsToPlayers(){
		for(int p = 0; p < players.length; p++){
			int[] temp = new int[12];
			//fill temp with available values
			for (int i = 0; i < temp.length; i ++) {
				int valCard = (int) (Math.random()*15) - 2;
				while(numCards[valCard + 2] == 0) {	//will give another card if the card does not have any more instances
					valCard = (int) (Math.random()*15) - 2;
				}
				
				temp[i] = valCard;
				numCards[i + 2] -= 1;
			}
			
			players[p] = new Player();
			players[p].fillMatrix(temp);
			gameState = "dealingCards";
			//System.out.println(gameState);
			
		}
	}
	
	public void addToDiscard(Card c) {
		discardPile.add(c);
		discardPile.getLast().setFaceUp(true);
	}//end of addToDiscard
	
	public Card removeDiscard() {
		try {
			return discardPile.removeLast();
		}catch (Exception E) {
			System.out.println("something went wrong");
			return new Card();
		}
	}
	
	public Card removeFromDeck() {
		try {
			return deck.removeLast();
		}catch (Exception E) {
			System.out.println("something went wrong");
			return new Card();
		}
	}

	
	//--------------------------------------------------------------
	
	public void beginningRound() {
		for(int p = 0; p > players.length; p++){
			ArrayList<Card> toBeAdded = players[p].resetMatrix();
			if(toBeAdded.size() > 0){
				for(Card c : toBeAdded){
					deck.add(c);
				}
			}
		}//adds the cards from the matrix to the deck
		for(int i = discardPile.size() - 1; i >= 0; i++){
			Card temp = removeDiscard();
			deck.add(temp);
		}//adds the cards from the discard pile to the deck

		dealCardsToPlayers();
		shuffleDeck();
		addToDiscard(new Card());
		addToDiscard(getTopDeck());
		removeFromDeck();
		gameState = "beginningRound";
		startTurn();
	}
	
	public void startTurn() {
		gameState = "draw";
		System.out.println(gameState);
	}//end of turn
	
	public void draw() {
		gameState = "replaceCard";
	}//end of draw
	
	public void replaceCard(int x, int y, Card c, Card r) {
		Card temp = c;
		temp.setFaceUp(true);
		players[currentPlayer].setMatrix(y, x, r);
		discardPile.add(temp);
		System.out.println(gameState);
		
	}//end of turn
	
	public void endingTurn() {
		gameState = "endingTurn";
		checkForEquals();
	}
	
	public void checkForEquals() {
		ArrayList<Card> discardedCards = players[currentPlayer].checkColumns();
		if(discardedCards.size() > 0) {
			discardPile.add(discardedCards.get(0));
			discardPile.add(discardedCards.get(1));
			discardPile.add(discardedCards.get(2));
			System.out.println("discarded a finished column");
		}
	}
	
	public void discardDrawCard(Card c) {
		addToDiscard(c);
		System.out.println(gameState);
		endingTurn();
	}
	
	public void mustFlipCard() {
		gameState = "mustFlipCard";
	}
	
	public void endRound(int playerEnding) {
		int[] scores = new int[players.length];

		for(int p = 0; p < players.length; p++){
			scores[p] = players[p].getCardSum();
		}//adds all of the scores of the players to a single array
		
		for(int i = 0; i < players.length; i++) {
			if(i == playerEnding){
				break;
			} else{
				if(scores[playerEnding] >= scores[i]){
					scores[playerEnding] *= 2;
					break;
				}//if any score is less than or equal to another player, the player ending the round
				//has their score double
			}
		}

		for(int p = 0; p < players.length; p++){
			players[p].setScorePlus(scores[p]);
		}

	}//endRound
	
	
	//getters and setters-------------------------------------------
	public Player getCurrentPlayer() {return players[currentPlayer];}
	public int getPlayerName() {return currentPlayer;}
	public Player getNextPlayer() {return  players[nextPlayer];}
	public int getPlayerNum() {return players.length;}
	public Player getPlayer(int i ) {return players[i];}
	
	public ArrayList<Card> getDeck() {return deck;}
	public Card getTopDeck() { return deck.getLast();}
	
	public ArrayList<Card> getDiscardPile() {return discardPile;}
	public Card getTopDiscard() {
		if(discardPile.size() != 0)
			return discardPile.getLast();
		else
			return new Card();
	}
	
	public String gameState() { return this.gameState;}
	 
}//end of class
