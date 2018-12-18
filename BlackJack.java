import java.util.*;
import java.util.concurrent.*;
public class BlackJack{
	public static void main(String args[]){

	System.out.println("Welcome To Ultimate Black Jack!!\n\n");
	System.out.println("If you need to see the rules, enter \"r\"");
	System.out.println("To start the game, just enter \"go\".");

	Scanner scan = new Scanner(System.in);
	String choice1 = scan.next();
	int stillPlay = 0;
	int playerStart = 0;
	int roundStart = 0;
	int choice2 = 0;


	if (choice1.equals("r")){
		System.out.println("The Rules to this game are simple.\nThe dealer hands two cards to the player, and two cards to himself (one facing down). \nThe objective of the game is to get higher than the dealer, while not going over 21. If you go over 21, you bust. \nAfter being dealt your cards, you can\nstand: End your turn\nhit: recieve another card\ndouble: Double your bet\ngive up: lose half your money and end the round.");
		while(stillPlay == 0){
		System.out.println("Would you like to start a game now: yes(1) or no(2)?");
			try{
				choice2 = scan.nextInt();
				if (choice2 == 1){
					stillPlay = 1;
				}
		
				else if (choice2 == 2){
					System.out.println("Okay, thank you for joining us for a bit! Have a great day!");
					stillPlay = 2;
					playerStart = 1;
					roundStart = 1;
				}
				else
					System.out.println("Please enter a correct response.");

			}
			catch(InputMismatchException e){
				String catcher = scan.next();
				System.out.println("Please enter a correct response");
				stillPlay = 1;
				choice2 = scan.nextInt();
				roundStart = 1;
				System.out.println("Thanks for checking us out! Have a good day!");

			}
	
		}
	}

	else 
		stillPlay = 1;

	Game game = new Game();

	while(playerStart == 0 && choice2 != 2){
		System.out.println("Do you have a profile with cash already (1), or would you like to set up a new one? (2)");
		int choice3 = scan.nextInt();
			if (choice3 == 1){
				game.loadPlayer();
				playerStart = 1;
			}
			else if (choice3 == 2){
				game.createPlayer();
				playerStart = 1;
			}
			else
				System.out.println("Please select a correct response.");
	}
	

	String stand;
	while(roundStart == 0){
		int turn = 1;
		game.placeBet();
		game.firstDeal();
		
		if (game.isBlackjack() == true)
			System.out.println();
		
		else{
			

			//Player's turn
		int skip = 0;
		while (game.getrSum(game.whichPlayer("p")) < 21){
			String menuChoice = scan.next();
			if (menuChoice.equals("h"))
				System.out.println("\nYou drew a " + game.hit(game.whoseHand("p"), game.whichPlayer("p")));

			else if (menuChoice.equals("d") && turn ==1)
				game.doubleBet();
			else if (menuChoice.equals("s")){
				game.stand();
				break;
			}
			else if (menuChoice.equals("g") && (turn == 1)){
				game.giveUp();
				if (game.getgiveUp() == 2){
					System.out.println("Thanks for playing! See you again!");
					roundStart = 1;
					break;
				}
			}
			else 
				System.out.println("Please enter a correct response");
			turn++;
			if (game.getrSum(game.whichPlayer("p")) < 21)
				System.out.println("Do you hit (h) or stand (s)");
			else if (game.getrSum(game.whichPlayer("p")) == 21)
				skip = 0;
			else
				skip = 1;
		}





		//Dealer's Turn 

		while (game.getrSum(game.whichPlayer("d")) <= 21 && skip !=1){
			try{
				TimeUnit.SECONDS.sleep(1);
				}
				catch(InterruptedException e){
					System.out.println("Interrupted");
				}
				System.out.println("The computer flips his second card and reveals a " + game.revealDealersCard());
			while (game.getrSum(game.whichPlayer("d")) <= 16){
				try{
				TimeUnit.SECONDS.sleep(2);
				}
				catch(InterruptedException e){
					System.out.println("Interrupted");
				}
				System.out.println("The dealer drew a " + game.hit(game.whoseHand("d"), game.whichPlayer("d")));
				if (game.getrSum(game.whichPlayer("d")) > 16)
					break;
			}
			break;

		}

		if (skip!= 1)
			System.out.println("\nYour hand adds up to: " + game.getrSum(game.whichPlayer("p")) + " and the dealer's hand adds up to " + " " + game.getrSum(game.whichPlayer("d")) + ".");
		}
		
		//
		//checking winner
		if (game.isBlackjack() == true)
			System.out.println("Congratulations. You got a blackjack!");
		if ((game.getrSum(game.whichPlayer("p")) > game.getrSum(game.whichPlayer("d")) && game.getgiveUp() != 2 && game.getrSum(game.whichPlayer("p")) <= 21) || game.getrSum(game.whichPlayer("d")) > 21 ){
			if ((game.isBlackjack() == false))
			System.out.println("Congratulations! You won the round and doubled your bet");
			game.updateBalance();
			System.out.println("Would you like to play another round? yes(0), or no (1)(Balance is: $" + game.getBalance() + ")" );
			roundStart = scan.nextInt();
			if (roundStart == 0)
				game.reset();
		}

		else if ((game.getrSum(game.whichPlayer("p")) < game.getrSum(game.whichPlayer("d")) && game.getgiveUp() != 2 && game.getrSum(game.whichPlayer("p")) <= 21) || game.getrSum(game.whichPlayer("p")) > 21){
			if (game.getrSum(game.whichPlayer("p")) > 21)
				System.out.println("You bust. Would you like to play another round? yes(0), or no (1)(Balance is: $" + game.getBalance() + ")" );
			else 
				System.out.println("You lost this round. Would you like to play another round? yes(0), or no (1)(Balance is: $" + game.getBalance() + ")" );
			roundStart = scan.nextInt();
			if (roundStart == 0)
				game.reset();

		}
		else if (game.getrSum(game.whichPlayer("p")) == game.getrSum(game.whichPlayer("d")) && game.getgiveUp() != 2 && game.getrSum(game.whichPlayer("p")) <= 21){
			game.updateBalanceTie();
			System.out.println("Tie. Would you like to play another round? yes(0), or no (1)(Balance is: $" + game.getBalance() + ")" ); //******************** use suits to rank
			roundStart = scan.nextInt(); 
			if (roundStart == 0){
				game.reset();

			}
			else if (roundStart == 1)
				System.out.println("Thanks for playing! Have a great day!");
		}


	}









	






	}
}