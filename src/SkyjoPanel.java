import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import static java.lang.System.out;


public class SkyjoPanel extends JPanel implements MouseListener{
	private static final long serialVersionUID = 1L;
	private final ArrayList<Image> images = new ArrayList<>();
	private Card heldCard;
    private Game skyjo;
    private Image table;
    private String playState;
    private int playersLeft;
	private int pNumber = -1;

    public SkyjoPanel() {
    	skyjo = new Game(2);
    	
        if (images.size() == 0) {
            try {
                images.add(ImageLoader.get("/Images/skyjo card images/neg2-card.jpg").getScaledInstance(120, 150, Image.SCALE_SMOOTH));
                images.add(ImageLoader.get("/Images/skyjo card images/neg1-card.jpg").getScaledInstance(120, 150, Image.SCALE_SMOOTH));
                images.add(ImageLoader.get("/Images/skyjo card images/0-card.jpg").getScaledInstance(120, 150, Image.SCALE_SMOOTH));
                images.add(ImageLoader.get("/Images/skyjo card images/1-card.jpg").getScaledInstance(120, 150, Image.SCALE_SMOOTH));
                images.add(ImageLoader.get("/Images/skyjo card images/2-card.jpg").getScaledInstance(120, 150, Image.SCALE_SMOOTH));
                images.add(ImageLoader.get("/Images/skyjo card images/3-card.jpg").getScaledInstance(120, 150, Image.SCALE_SMOOTH));
                images.add(ImageLoader.get("/Images/skyjo card images/4-card.jpg").getScaledInstance(120, 150, Image.SCALE_SMOOTH));
                images.add(ImageLoader.get("/Images/skyjo card images/5-card.jpg").getScaledInstance(120, 150, Image.SCALE_SMOOTH));
                images.add(ImageLoader.get("/Images/skyjo card images/6-card.jpg").getScaledInstance(120, 150, Image.SCALE_SMOOTH));
                images.add(ImageLoader.get("/Images/skyjo card images/7-card.jpg").getScaledInstance(120, 150, Image.SCALE_SMOOTH));
                images.add(ImageLoader.get("/Images/skyjo card images/8-card.jpg").getScaledInstance(120, 150, Image.SCALE_SMOOTH));
                images.add(ImageLoader.get("/Images/skyjo card images/9-card.jpg").getScaledInstance(120, 150, Image.SCALE_SMOOTH));
                images.add(ImageLoader.get("/Images/skyjo card images/10-card.jpg").getScaledInstance(120, 150, Image.SCALE_SMOOTH));
                images.add(ImageLoader.get("/Images/skyjo card images/11-card.jpg").getScaledInstance(120, 150, Image.SCALE_SMOOTH));
                images.add(ImageLoader.get("/Images/skyjo card images/12-card.jpg").getScaledInstance(120, 150, Image.SCALE_SMOOTH));
                images.add(ImageLoader.get("/Images/skyjo card images/back-card.jpg").getScaledInstance(120, 150, Image.SCALE_SMOOTH));
                table = ImageLoader.get("/Images/table top wood.jpg").getScaledInstance(612, 373, Image.SCALE_SMOOTH);
            }catch(Exception E) {
                out.println("Exception Error");
            }
        }
        addMouseListener(this);
        heldCard = new Card();
        playState = "playing";
        playersLeft = skyjo.getPlayerNum();
    }

    public void paint(Graphics g) {
    	g.drawImage(table, 55, 55, 55, 55, null);
    	Card[][] matrix = skyjo.getCurrentPlayer().getMatrix();
		Card[][] nextMatrix = skyjo.getNextPlayer().getMatrix();
    	//ArrayList
        //make the three by three array
        int x1;
        int y1 = getHeight()/4 + 150;
        int WDCard = 120, HGCard = 150;
        super.paint(g);
        Font f = new Font("dialog", 1, 15);

        //fill the array with the different cards from matrix face down
        for (Card[] cards : matrix) {
            x1 = (int) (getWidth() / 2 - 280);
            for (int j = 0; j < cards.length; j++) {
                Card c = cards[j];
                g.drawImage(getImage(c), x1, y1, WDCard, HGCard, null);
                x1 += 140;
            }
            y1 += 160;
        }

		//draws the next player's matrix
		int y2 = 500;
		for (Card[] cards : nextMatrix) {
            int x2 = (int) (getWidth() / 2 + 300);
            for (int j = 0; j < cards.length; j++) {
                Card c = cards[j];
                g.drawImage(getImage(c), x2, y2, 60, 75, null);
                x2 += 70;
            }
            y2 += 80;
        }
        
        g.drawImage(getImage(skyjo.getTopDeck()), (getWidth()/2) + 20, getHeight()/6 - 50, WDCard, HGCard, null);
        
        if(skyjo.getDiscardPile().size() > 0 ) {
        g.drawImage(getImage(skyjo.getTopDiscard()), (getWidth()/2) - 140, getHeight()/6 - 50, WDCard, HGCard, null);
        } else if (skyjo.getDiscardPile().size() == 0){
            g.drawImage(getImage(new Card()), getWidth() - 150, 100, WDCard, HGCard, null);
        }
            g.drawImage(getImage(heldCard), getWidth() - 150, 100, WDCard, HGCard, null);
        
        //next turn button && player text
        g.setColor(new Color(175, 240, 235));
        g.fillRect(getWidth()-120, getHeight()-70, 100, 50);
        g.fillRect(getWidth()/2 - 280, getHeight()/4 + 80, 100, 50);
        g.setColor(Color.black);
        g.setFont(f);
        g.drawString("Next Player", getWidth()-110, getHeight()-40);
        g.drawString("Player " + skyjo.getPlayerName(), getWidth()/2 - 270, getHeight()/4 + 110);

		int scoreHeight = getHeight()/4 + 150;
		for(int i = 0; i < skyjo.getPlayerNum(); i++){
			g.drawString("Player " + i + " -> " + skyjo.getPlayer(i).getScore(), 100, scoreHeight);
			scoreHeight += 20;
			System.out.println("drawing the scores");
		}//draws the scores

		if(playState.equals("stop")){
			g.setFont(new Font("dialog", 1, 25));
			g.drawString("Winner is " + skyjo.playerLowScore(), getWidth()-100, 50);
		}

    }//end of paint


    //gets the image of a card depending on if it is face up or down
    public Image getImage(Card c) {
    	if(c.getValue() == -3) {
    		return null;
    	} else if(!c.getFace()) {
            return images.get(15);
        } 
        return images.get(c.getValue()+2);
    }//end of get image

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {
    	
    	while(playState.equals("playing") || playState.equals("lastTurn") && playersLeft != 0) {
	    	Card[][] matrix = skyjo.getCurrentPlayer().getMatrix();
	        int x = (int)e.getX();
	        int y = (int)e.getY();        
	        
	        if(skyjo.gameState().equals("beginningRound")) {
	        	skyjo.startTurn();
	        }
	        
	        if(skyjo.gameState().equals("draw")) {
	        	 if (x >= getWidth()/2 - 140 && x <= getWidth()/2 - 20 && y >= getHeight()/6 - 50 && y <= getHeight()/6 + 100) {
	        		heldCard = skyjo.removeDiscard();
	            	heldCard.setFaceUp(true);
	            	skyjo.draw();
	              	repaint();
	              	return;

	             } else if (x >= getWidth()/2 + 20 && x <= getWidth()/2 + 140 && y >= getHeight()/6 -50 && y <= getHeight()/6 +100){
	            	 heldCard = skyjo.removeFromDeck();
	            	 heldCard.setFaceUp(true);
	             	skyjo.draw();
	             	repaint();
	             	return;
	             }else {
	            	 skyjo.startTurn();
	            	 return;
	             }
	        }
	        
	        if(skyjo.gameState().equals("replaceCard")) {
	        	if (x >= (getWidth()/2-300) && x <= getWidth()/2 - 150 + (160*matrix.length) && y >= (int)(getHeight()/4 + 150) && y <= getHeight()/4 + (150*4)) {
	                int x1 = (x-getWidth()/2+300) / 155;
	                int y1 = ((y-(getHeight()/4 - 150))/150) - 2;
	                skyjo.replaceCard(x1, y1, matrix[y1][x1], heldCard);
	                heldCard = new Card();
	                try { 
	                	matrix[y1][x1].setFaceUp(true);
	                } catch (Exception E) {System.out.println("Something went wrong in flipping the matrix cards upside!");}
	                skyjo.endingTurn();
	                repaint();
	             	return;

	            } else if(x >= getWidth()/2 - 140 && x <= getWidth()/2 - 20 && y >= getHeight()/6 - 50 && y <= getHeight()/6 + 100 ){
	                skyjo.discardDrawCard(heldCard);
	                heldCard = new Card();
	                skyjo.mustFlipCard();
	               repaint();
	             	return;
	            }else {
	            	System.out.println("something went wrong int replace card");
	            	skyjo.draw();
	            	repaint();
	            	return;
	            }
	        }
	        
	        if(skyjo.gameState().equals("mustFlipCard")) {
	        	if (x >= (getWidth()/2-300) && x <= getWidth()/2 - 150 + (160*matrix.length) && y >= (int)(getHeight()/4 + 150) && y <= getHeight()/4 + (150*4)) {
	                int x1 = (x-getWidth()/2+300) / 155;
	                int y1 = ((y-(getHeight()/4 - 150))/150) - 2;
	                try { 
	                	matrix[y1][x1].setFaceUp(true);
	                } catch (Exception E) {System.out.println("Something went wrong in flipping the matrix cards upside!");}
	                skyjo.endingTurn();
	                repaint();
	             	return;

	        	}
	        	skyjo.mustFlipCard();
	        	return;
	        }
	        
	        else if(skyjo.gameState().equals("endingTurn")) {
	        	System.out.println(playState);
				System.out.println("entered endingTurn");

	        	if(skyjo.getCurrentPlayer().allCardsUp() && !(playState.equals("lastTurn"))) {
					System.out.println("entered lastTurn");
	        		playState = "lastTurn";
	        		playersLeft--;
					pNumber = skyjo.getPlayerName();
	        	}else if(playState.equals("lastTurn")) {
					System.out.println("entered lastTurn round");
	        		playersLeft--;
					skyjo.getCurrentPlayer().allCardsUp();
					repaint();
	        	}
	        	
	        	if(playersLeft == 0) {
	        		playState = "endRound";
					System.out.println("enters the endRound");
					skyjo.endRound(pNumber);
	        	}

				if(playState.equals("endRound")){
					if(skyjo.gameEnds()){
						playState = "stop";
						repaint();
						return;
					}else{
						playState = "playing";
						repaint();
						return;
					}
				}
	        	
	        	if (x >= getWidth()-120 && x <= getWidth()-20 && y >= getHeight()-70 && y <= getHeight()-20) {
	            	skyjo.switchCurrentPlayer();
	            	skyjo.startTurn();
	            	heldCard = new Card();
	            	repaint();
	             	return;

	        	} 

				skyjo.endingTurn();
				return;
	        }
	        
    	}

		//insert final scoring

    }//end of mouseClicked

}//end of class