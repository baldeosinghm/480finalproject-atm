import java.util.*;
public class Deck{
	 String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
     String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
     String[] deck = new String[52];

     int cardNumber = 0;

    public void create(){ //create deck
        for (int i = 0; i < ranks.length; i++) {
            for (int j = 0; j < suits.length; j++) {
                deck[suits.length*i + j] = (ranks[i] + " of " + suits[j]);
           
            }
        }
    }

    int n = (suits.length * ranks.length);
    public void shuffle(){ //shuffles deck
        for (int i = 0; i < n; i++) {
            int r = i + (int) (Math.random() * (n-i));
            String temp = deck[r];
            deck[r] = deck[i];
            deck[i] = temp;

        }
        int cardNumber = 0;
    }
   	
   	public void addTo(LinkedList a){
   		String string = deck[cardNumber];
   		a.add((string));
   		
   		cardNumber++;
   	}

   	public int getcardNumber(){
   		return cardNumber;
   	}

   	public void reshuffle(){
   		
        for (int i = 0; i < n; i++) {
            int r = i + (int) (Math.random() * (n-i));
            String temp = deck[r];
            deck[r] = deck[i];
            deck[i] = temp;

        }
        int cardNumber = 0;
   	}


   	public Deck(){
   		

   	}



}



