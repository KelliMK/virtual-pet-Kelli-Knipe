package virtual_Pet;

import java.util.Random;
import java.util.Scanner;

@SuppressWarnings("unused")
public class VirtualPetApp {
	public static void main(String args[]) {
		String cowName = "Rose";
		Scanner input = new Scanner(System.in);
		Random rand = new Random();
		int cowHunger = 0;		// out of 100
		int cowThirst = 0; 		// out of 50
		int cowTired = 0; 		// out of 3
		int cowBoredom = 0;		// out of 50
		int cowMilk = 0;		// out of 30
		int cowMood = 0;		// out of 100, determined by +Hunger, +Thirst, +Boredom, -Tiredness, and -Milk
		int unitsMilk = 0;		// out of 10
		int currentMoney = 0; 	// no limit
		int totalFeed = 2;		// out of 15
		int totalFancyFeed = 1;	// out of 5
		int currentTurn = 1;	// ranges from 1 to 8
		int currentDay = 0;		// no limit
		int waterTrough = 0;	// out of 40
		boolean dayTime = true;	// Is it day or night
		boolean killSwitch = false;	// Ends game loop
		int randBoredom = rand.nextInt((8 - 3) + 1) + 3; 	// reduce boredom
		int randSales = rand.nextInt((35 - 15) + 1) + 15;	// Sales of Milk
		System.out.println("Welcome to Virtual Pet Rose!\nby Kelli Knipe\nASCII art by Christopher Johnson");
		String userInput = " ";
		VirtualPet Rose = new VirtualPet(cowName);
		
		
		
		while (userInput.toLowerCase() != "exit" || userInput != "9") {
			
			Rose.calculateMood();
			Rose.moodGraphic();
			System.out.println("Choose an option\n1) Display Stats (stats)\n2) Feed " + cowName + " (feed)\n3) Water " + cowName + " (water)\n4) Play with " + cowName + " (play)\n5) Pet " + cowName + " (pet)\n6) Milk " + cowName + " (milk)\n7) Go to the Market (market)\n8) Do Nothing (nope)\n9) Exit Game (exit)");
			System.out.print("> ");
			String userInput1 = input.nextLine();
			System.out.println(" ");
			
			
			if (userInput1.equals("stats") || userInput1.equals("1")) {
				System.out.println(Rose.cowName + "'s Hunger: " + Rose.cowHunger + "/100");
				System.out.println(Rose.cowName + "'s Thirst: " + Rose.cowThirst + "/50");
				System.out.println(Rose.cowName + "'s Boredom: " + Rose.cowBoredom + "/50");
				System.out.println(Rose.cowName + "'s Milk: " + Rose.cowMilk + "/30");
				System.out.println(Rose.cowName + "'s Energy: " + (3 - Rose.cowTired) + "/3");
			
			
			} else if (userInput1.equals("feed") || userInput1.equals("2")) {
				System.out.println("You currently have:\nFeed: " + totalFeed + "\nFancy Feed: " + totalFancyFeed);	// Start cut here
				System.out.println("Would you like to feed " + cowName + " regular (1) or fancy (2) feed?");
				System.out.print("> ");
				Rose.feedCow();
				Rose.tick();
				Rose.drain();
				Rose.limit();
			
			
			} else if (userInput1.equals("water") || userInput1.equals("3")) {
				Rose.waterCow();
				Rose.tick();
				Rose.drain();
				Rose.limit();
			
			
			} else if (userInput1.equals("play") || userInput1.equals("4")) {
				Rose.playCow();
				Rose.tick();
				Rose.drain();
				Rose.limit();
			
			
			} else if (userInput1.equals("pet") || userInput1.equals("5")) {
				Rose.petCow();
				Rose.tick();
				Rose.drain();
				Rose.limit();
			
			
			} else if (userInput1.equals("milk") || userInput1.equals("6")) {
				Rose.milkCow();
				Rose.tick();
				Rose.drain();
				Rose.limit();
			
			
			} else if (userInput1.equals("market") || userInput1.equals("7")) {
				Rose.goToMarket();
				Rose.buyAtMarket();
				Rose.tick();
				Rose.drain();
				Rose.limit();
			
			
			} else if (userInput1.equals("nope") || userInput1.equals("8")) {
				System.out.println("Okay, lazy");
				Rose.tick();
				Rose.drain();
				Rose.limit();
			
			
			} else if (userInput1.equals("exit") || userInput1.equals("9")) {
				System.out.println("Thanks for playing!");
				userInput = "exit";
			
			
			} else {
				System.out.println("...I didn't catch that. Enter a non-zero integer or one of the commands in parentheses");
			}
		}
		input.close();
	}
}
