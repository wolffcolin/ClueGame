package clueGame;
import java.awt.Color;

/*
Class: Card
Description: Class that represents the cards in the game, uses the CardType enum
Collaborators: N/A
Sources: N/A
Authors: Colin Wolff and Eoghan Cowley
*/

public class Card {
    private String cardName;
    private CardType type;
    private Color ownerColor;

    // constructor
    public Card(String name, CardType type) {
        cardName = name;
        this.type = type;
    }

    // returns card type
    public CardType getCardType() {
        return this.type;
    }

    public String toString() {
        return cardName;
    }
    
    public void setColor(Color color) {
    	this.ownerColor = color;
    }
    
    public Color getColor() {
    	return ownerColor;
    }
}
