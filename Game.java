import java.util.*;
import java.util.concurrent.*;
public class Game{
	FileUtility customers = new FileUtility("customers.txt");
	LinkedList customersLL = new LinkedList();
	Deck gameDeck = new Deck();
	int betAmount =0;
	int giveUpint = 0;

	LinkedList p_hand = new LinkedList();
    LinkedList d_hand = new LinkedList();
	LinkedList numbers = new LinkedList();
	LinkedList suits = new LinkedList();

	boolean aceInHand = false;
	boolean blackjack = false;

	private class TurnTaker{
		int rankSum, suitSum, p_balance, lineOnTXTfile, IDnumber;
		LinkedList hand;
		
		public TurnTaker(int ID, int l){
			
			this.IDnumber = ID; //use to match with player.txt
			this.hand = null; //empty in the begining (need to shuffle cards)
			this.rankSum = 0; //zero initially
			this.suitSum = 0; //zero initially
			this.p_balance = 100; //initial balance for player (Will override if there is an existing profile in player.txt)
			this.lineOnTXTfile = l;
		}

		public TurnTaker(int ID, int balance, int l){
			
			this.IDnumber = ID; //use to match with player.txt
			this.hand = null; //empty in the begining (need to shuffle cards)
			this.rankSum = 0; //zero initially
			this.suitSum = 0; //zero initially
			this.p_balance = balance; //initial balance for player (Will override if there is an existing profile in player.txt)
			this.lineOnTXTfile = l;
		}
		public TurnTaker(){
			this.hand = null;
			this.rankSum = 0;
			this.suitSum = 0;

		}
	}

	TurnTaker player1, player2;
	public Game(){
		player1 = null;
		player2 = null;

	}//constructor 
		public void createPlayer(){
			int randomIDnumber = (int) (Math.random() * (99999));
			//****************//add line where it makes sure it doesn't ever use the same ID twice by checking customers.txt file
			customers.write(randomIDnumber+ " 100");

			System.out.println("Don't lose your 5-digit ID number if you want to be able to load the game again with your money.");
			System.out.println("Your ID number is: "+ randomIDnumber+ " and we're giving you $100 to start with!! Yay!");

			TurnTaker new_player = new TurnTaker(randomIDnumber, customers.size()-1);

			player1 = new_player;

		}
		

		public void loadPlayer(){
			System.out.println("Please enter your 5-digit ID number to load your balance.");
			Scanner scan = new Scanner(System.in);
			int userID = scan.nextInt();

			int customersTxtLineSize = customers.size(); //gets the size (in lines) of the file
			customers.reset(); //sets it to line zero

			for (int i = 0; i < customersTxtLineSize; i++){ //parses through however many lines there are
				String currentLine = customers.read();
				String delims = "[ ]+";
				String[] tempArray = currentLine.split(delims);

				customersLL.add((tempArray[0]));
				customersLL.add((tempArray[1]));

				int linemarker = i;
				if (userID == (Integer.parseInt(customersLL.get(2*i)))){
					int balance =(Integer.parseInt(customersLL.get(2*i + 1)));
					System.out.println("Welcome back!!! You have: $" + balance + " in your account :-)!!");
					TurnTaker new_player = new TurnTaker(userID, balance, linemarker);
					player1 = new_player;

					break;
					}
			}
		}

		public void placeBet(){
			Scanner sc = new Scanner(System.in);
			int loop = 0;

			if (player1.p_balance < 10){
					System.out.println("Sorry, you don't have enough money to play.");
					loop = 1;
			}
			else
				System.out.println("\nTo start off the round, you'll need to place a minimum bet of $10. How much would you like to wager? (You have $" + player1.p_balance + ").");
			

			
			while (loop == 0){
				betAmount = sc.nextInt();
				if (betAmount < 10)
					System.out.println("Sorry, you need to enter at least $10 to be able to play. Please try again.");
				else if (betAmount > player1.p_balance)
					System.out.println("You don't have enough funds in your account to bet that amount. Please chose a different amount.");
				else {
					System.out.println("You are betting: " + betAmount + "\n");
					loop = 1;
				}
			}

			player1.p_balance = player1.p_balance - betAmount;
			customers.update((player1.lineOnTXTfile), player1.IDnumber + " " + (player1.p_balance));

			
		}

		public void updateBalance(){
				int winAmount = 2*betAmount;
				customers.update((player1.lineOnTXTfile), player1.IDnumber + " " + (player1.p_balance + winAmount));
				player1.p_balance = player1.p_balance + winAmount;
		}
		public void updateBalanceTie(){
				int winAmount = betAmount;
				customers.update((player1.lineOnTXTfile), player1.IDnumber + " " + (player1.p_balance + winAmount));
				player1.p_balance = player1.p_balance + winAmount;
		}
		
		public void firstDeal() {
			TurnTaker dealer = new TurnTaker();
			player2 = dealer;

			player1.hand = p_hand;
			player2.hand = d_hand;


			System.out.println("Taking out deck...");
			try{
				TimeUnit.SECONDS.sleep(2);
			}
			catch(InterruptedException e){
				System.out.println("Interrupted");
			}
			gameDeck.create();

			System.out.println("Shuffling deck\n");
			try{
				TimeUnit.SECONDS.sleep(2);
			}
			catch(InterruptedException e){
				System.out.println("Interrupted");
			}
			
			
			gameDeck.shuffle();	
			gameDeck.shuffle();
			gameDeck.shuffle();
												//making sure it's random
			gameDeck.reshuffle();
			gameDeck.reshuffle();
			gameDeck.reshuffle();



			
			System.out.println("You're given... ");
			gameDeck.addTo(p_hand);
			gameDeck.addTo(p_hand);

			try{
				TimeUnit.SECONDS.sleep(1);
			}
			catch(InterruptedException e){
				System.out.println("Interrupted");
			}
			System.out.println(p_hand.get(0) + " and ");	
			
			
			try{
				TimeUnit.SECONDS.sleep(1);
			}
			catch(InterruptedException e){
				System.out.println("Interrupted");
			}
			
			System.out.println(p_hand.get(1) + ".");

			
			System.out.println("\nThe dealer has... ");
			gameDeck.addTo(d_hand);
			gameDeck.addTo(d_hand);
			try{
				TimeUnit.SECONDS.sleep(2);
			}
			catch(InterruptedException e){
				System.out.println("Interrupted");
			}
			System.out.println(d_hand.get(0) + " and the other card is facing down.");
			

			

			takerSum(p_hand, player1);
			takerSum(d_hand, player2);

			
			System.out.println("Do you hit (h), double (d), or stand (s), or give up (g)");

		}
		public String revealDealersCard(){
			return d_hand.get(1);
		}
		
		public String hit(LinkedList ll, TurnTaker tt){
			gameDeck.addTo(ll);
			takerSum(ll, tt);
			
			return ll.get((ll.size()-1));
			

		}

		public void doubleBet(){
			if (betAmount > player1.p_balance){
				System.out.println("Sorry, you don't have enough money to double up");
			}
			else{
				int doubleAmount = betAmount*2;
				player1.p_balance = player1.p_balance - betAmount;
				betAmount = doubleAmount;

				System.out.println("You're now betting: " + betAmount);
				gameDeck.addTo(p_hand);

				System.out.println("You drew a/an: " + p_hand.get((p_hand.size() - 1)));
				takerSum(p_hand, player1);
			}

		}
		
		public void stand(){
			System.out.println("\nYou stand.");

		}
		
		public void giveUp(){
			System.out.println("Are you sure you want to give up? You will only get back 1/2 of the money you bet. \nEnter \"1\" to give up. \nEnter \"2\" to stay in the game.");
			Scanner	scan = new Scanner(System.in);
			int giveUp = scan.nextInt();

			if (giveUp == 1){
				int returnAmount = (betAmount/2);
				int updatedBalance = player1.p_balance + betAmount;

				customers.update(player1.lineOnTXTfile, player1.IDnumber + " " + updatedBalance);
				System.out.println("Would you like to play another round? yes(1) or no(2)");
				giveUpint = scan.nextInt();

			}
			if (giveUp == 2){
				System.out.println("We're glad you're staying.");
				giveUpint = 1;
			}
		}

		public void takerSum(LinkedList ll, TurnTaker tt){
			aceInHand = false;
			blackjack = false;
			int rSum = 0;
			String delims = " of ";
			for (int i = 0; i < ll.size(); i++){
				String spl = ll.get(i);
				String[] splitter = spl.split(delims);
				if (splitter[0].equals("Ace")){
					aceInHand = true;
					splitter[0] = "11";
					numbers.add(splitter[0]);
				}
				else if (splitter[0].equals("Jack")){
					splitter[0] = "10";
					numbers.add(splitter[0]);
				}
				else if (splitter[0].equals("King")){
					splitter[0] = "10";
					numbers.add(splitter[0]);
				}
				else if (splitter[0].equals("Queen")){
					splitter[0] = "10";
					numbers.add(splitter[0]);
				}
				else
					numbers.add(splitter[0]);
			}

			for (int i = 0; i < numbers.size(); i++){
				rSum = rSum + Integer.parseInt((numbers.get(i)));			
					
			}
			if (p_hand.size() == 2 && rSum == 21)
				blackjack = true;
			if (aceInHand == true && rSum > 21)
				rSum = rSum - 10;
			tt.rankSum = rSum;
			while (numbers.size() != 0)
				numbers.remove(0);

		}
		public boolean isBlackjack(){
			if (blackjack == true)
				return true;
			else 
				return false;
			

		}

		public void takesSum(LinkedList ll, TurnTaker tt){
			int sSum = 0;
			String delims = " of ";
			for (int i = 0; i < ll.size(); i++){
				String spl = ll.get(i);
				String[] splitter = spl.split(delims);
				if (splitter[1].equals("Clubs")){
					splitter[1] = "-1";
					suits.add(splitter[1]);
				}
				else if (splitter[1].equals("Diamonds")){
					splitter[1] = "1";
					suits.add(splitter[1]);
				}
				else if (splitter[1].equals("Hearts")){
					splitter[1] = "2";
					suits.add(splitter[0]);
				}
				else if (splitter[1].equals("Spades")){
					splitter[1] = "-2";
					suits.add(splitter[1]);
				}
				
			}

			for (int i = 0; i < numbers.size(); i++){
				sSum = sSum + Integer.parseInt((numbers.get(i)));			
					
			}
			tt.suitSum = sSum;
			while (numbers.size() != 0)
				suits.remove(0);

		}

		public void reset(){
			while (p_hand.size() != 0){
				p_hand.remove(0);
			}
			while (d_hand.size() != 0){
				d_hand.remove(0);
			}
			gameDeck.reshuffle();
		}

		public int getgiveUp(){
			return giveUpint;
		}

		public int getrSum(TurnTaker a){
			return a.rankSum;
		}

		public int getsSum(TurnTaker b){
			return b.suitSum;
		}

		public LinkedList whoseHand(String x){
			if (x.equals("p"))
				return p_hand;
			if (x.equals("d"))
				return d_hand;

			else 
				return null;
		}

		public TurnTaker whichPlayer(String x){
			if (x.equals("p"))
				return player1;
			if (x.equals("d"))
				return player2;
			else 
				return null;
		}

		public int getBetAmount(){
			return betAmount;
		}

		public int getBalance(){
			return player1.p_balance;
		}
		
	
}