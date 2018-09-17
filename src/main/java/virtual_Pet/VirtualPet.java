package virtual_Pet;

import java.util.Random;

import java.util.Scanner;

@SuppressWarnings("unused")
public class VirtualPet {
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
	int currentTurn = 0;	// ranges from 1 to 8
	int currentDay = 0;		// no limit
	int waterTrough = 0;	// out of 40
	boolean dayTime = true;	// Is it day or night
	boolean killSwitch = false;	// Ends game loop
	int randBoredom = rand.nextInt((8 - 3) + 1) + 3; 	// reduce boredom
	int randSales = rand.nextInt((35 - 15) + 1) + 15;	// Sales of Milk
	
	public VirtualPet(String inputCowName) {
		cowName = inputCowName;
	}
	
	// Make time go forward
	public boolean tick() {
		if (currentTurn >= 0 && currentTurn < 6) {
			++currentTurn;
			dayTime = true;
			return true;
		} else if (currentTurn >= 6 && currentTurn < 8) {
			++currentTurn;
			dayTime = false;
			cowTired = 0;
			cowBoredom -= 10;
			return true;
		} else if (currentTurn == 8) {
			currentTurn = 0;
			++currentDay;
			return true;
		} else {
			killSwitch = true;
			return false;
		}
	}
	
	// Calculate virtual cow's mood
	public boolean calculateMood() {
		cowMood = (((cowThirst + cowHunger + cowBoredom)/2) + (cowMilk/3));
		return true;
	}
	
	// Display ASCII art based on cow's mood
	public boolean moodGraphic() {
		affectionateGraphic();
		happyGraphic();
		normalGraphic();
		sadGraphic();
		angryGraphic();
		asleepGraphic();
		return true;
	}
	
	// As time goes forward, virtual cow's emotions wear down
	public boolean drain() {
		int moreHunger = rand.nextInt((7 - 2) + 1) + 2;
		int moreThirst = rand.nextInt((4 - 2) + 1) + 1;
		int moreBored = rand.nextInt((4 - 2) + 1) + 2;
		int moreMilk = rand.nextInt((3 - 1) + 1) + 1;
		cowHunger += moreHunger;
		cowThirst += moreThirst;
		cowBoredom += moreBored;
		cowMilk += moreMilk;
		return true;
	}
	
	// Feed your cow so it stays full, and therefore happy
 	public boolean feedCow() {
		String feedChoice1 = input.nextLine();
		String feedChoice2 = feedChoice1.toLowerCase();	
		if (feedChoice2 == "regular" ||  feedChoice2 == "regular feed" || feedChoice2 == "1") {
			if (totalFeed >= 1) {
				--totalFeed;
				int randFeed = (rand.nextInt((15 - 5) + 1) + 5);
				cowHunger -= randFeed;
				System.out.println("You fed " + cowName + "!");
				tick();
				return true;
			} else {
				System.out.println("You don't have any regular feed!");
				return false;
			}
		} else if (feedChoice2 == "fancy" || feedChoice2 == "fancy feed" || feedChoice2 == "2") {
			if (totalFancyFeed >= 1) {
				--totalFancyFeed;
				int randFancyFeed = (rand.nextInt((60 - 30) + 1) + 30);
				cowHunger -= randFancyFeed;
				System.out.println("You fed " + cowName + "!");
				tick();
				return true;
			} else {
				System.out.println("You don't have any fancy feed!");
				return false;
			}
		} else {
			System.out.println("Sorry, I didn't understand that");
			return false;
		}
	}
	
 	// Water your cow so it stays hydrated, and therefore happy
	public boolean waterCow() {
		int randThirst = (rand.nextInt((7 - 3) + 1) + 3);
		if (waterTrough >= 4) {
			int randTrough = (rand.nextInt((4 - 2) + 1) + 2);
			waterTrough -= randTrough;
			cowThirst -= randThirst;
			System.out.println("You gave " + cowName + " some water!");
			return true;
		} else if (waterTrough > 0 && waterTrough < 4) {
			waterTrough = 0;
			cowThirst -= randThirst;
			System.out.println("You gave " + cowName + " some water!");
			return true;
		} else if (waterTrough <= 0) {
			System.out.println("You don't have any water!");
			System.out.println(cowName + "is upset!");
			++cowThirst;
			return true;
		} else {
			System.out.println("How the fuck did you get this result?");
			return false;
		}
	}
	
	// Play with your cow so it stays happy, but it can only play up to three times a day
	public boolean playCow() {
		if (cowTired >= 3) {
			System.out.println(cowName +  " is tired as hell, and does NOT want to play");
			return true;
		} else if (cowBoredom <= 0) {
			System.out.println(cowName + "is content");
			return true;
		} else {
			++cowTired;
			cowBoredom -= randBoredom;
			return true;
		}
	}
	
	// Pet your cow when she's happy so she knows you love her
	public boolean petCow() {
		if (cowBoredom > 20) {
			System.out.println(cowName + " doesn't want to be pet right now");
			return false;
		} else if (cowBoredom <= 14 && cowThirst < 15 && cowHunger < 30 && cowMilk >= 15) {
			System.out.println(cowName + " really liked that");
			cowBoredom -= (randBoredom * 2);
			return true;
		} else {
			System.out.println("She liked that!");
			cowBoredom -= randBoredom;
			return true;
		}
		
	}

	// Milk your cow so you can go to the market and buy more food and water
	public boolean milkCow() {
		if (cowMilk > 3) {
			unitsMilk += (cowMilk/3);
			cowMilk = 0;
			System.out.println(cowName + " was milked");
			return true;
		} else {
			System.out.println(cowName + " was milked, but didn't have any milk to give");
			return false;
		}
	}
	
	// Go to the market so that you can buy more food and water for your cod
	public boolean goToMarket() {
		System.out.println("Welcome to the market!");
		marketGraphic();
		int cashMade = (unitsMilk * randSales);
		System.out.println("You sold your " + unitsMilk + " units of milk for $" + cashMade);
		currentMoney += cashMade;
		unitsMilk = 0;
		System.out.println("You have $" + currentMoney);
		return true;
	}
	
	// Buy food or water at the market
	public boolean buyAtMarket() {
		System.out.print("What would you like to do?\n1) Buy Water ($5 each)\n2) Buy Regular Feed ($20 each)\n3) Buy Fancy Feed ($50 each)\n4) Leave Market\n> ");
		String marketInput1 = input.nextLine();
		String marketInput2 = marketInput1.toLowerCase();
		while (marketInput2 != "4" || marketInput2 != "leave" || marketInput2 != "leave market") {
			if (marketInput2 == "1" || marketInput2 == "buy water" || marketInput2 == "water") {
				System.out.print("How many units of water would you like to buy?\n> ");
				int waterWanted = input.nextInt();
				if ((waterWanted * 5) > currentMoney) {
					System.out.println("You don't have enough money!");
				} else {
					currentMoney -= (waterWanted * 5);
					waterTrough += waterWanted;
					limit();
					System.out.println("You now have " + waterTrough + " units of water\nRemember, your trough can hold up to 40 units at a time");
				}
				System.out.print("What would you like to do?\n1) Buy Water ($5 each)\n2) Buy Regular Feed ($20 each)\n3) Buy Fancy Feed ($50 each)\n4) Leave Market\n> ");
				marketInput1 = input.nextLine();
				marketInput2 = marketInput1.toLowerCase();
			} else if (marketInput2 == "2" || marketInput2 == "buy regular feed" || marketInput2 == "regular feed" || marketInput2 == "regular") {
				System.out.print("How many bags of regular feed would you like to buy?\n> ");
				int feedWanted = input.nextInt();
				if ((feedWanted * 20) > currentMoney) {
					System.out.println("You don't have enough money!");
				} else {
					currentMoney -= (feedWanted * 20);
					totalFeed += feedWanted;
					limit();
					System.out.println("You now have " + totalFeed + " bags of fancy feed\nRemember, your barn can hold up to 15 regular bags at a time");
				}
				System.out.print("What would you like to do?\n1) Buy Water ($5 each)\n2) Buy Regular Feed ($20 each)\n3) Buy Fancy Feed ($50 each)\n4) Leave Market\n> ");
				marketInput1 = input.nextLine();
				marketInput2 = marketInput1.toLowerCase();
			} else if (marketInput2 == "3" || marketInput2 == "buy fancy feed" || marketInput2 == "fancy feed" || marketInput2 == "fancy") {
				System.out.print("How many bags of fancy feed would you like to buy?\n> ");
				int fancyFeedWanted = input.nextInt();
				if ((fancyFeedWanted * 50) > currentMoney) {
					System.out.println("You don't have enough money!");
				} else {
					currentMoney -= (fancyFeedWanted * 50);
					totalFancyFeed += fancyFeedWanted;
					limit();
					System.out.println("You now have " + totalFancyFeed + " bags of fancy feed\nRemember, your barn can hold up to 5 fancy bags at a time");
				}
				System.out.print("What would you like to do?\n1) Buy Water ($5 each)\n2) Buy Regular Feed ($20 each)\n3) Buy Fancy Feed ($50 each)\n4) Leave Market\n> ");
				marketInput1 = input.nextLine();
				marketInput2 = marketInput1.toLowerCase();
			} else if (marketInput2 == "feed") {
				System.out.println("What type of feed?");
				System.out.print("What would you like to do?\n1) Buy Water ($5 each)\n2) Buy Regular Feed ($20 each)\n3) Buy Fancy Feed ($50 each)\n4) Leave Market\n> ");
				marketInput1 = input.nextLine();
				marketInput2 = marketInput1.toLowerCase();
			} else if (marketInput2 == "4" || marketInput2 == "leave market" || marketInput2 == "leave") {
				System.out.println("Goodbye!");
			} else {
				System.out.println("Sorry, I didn't understand that");
				System.out.print("What would you like to do?\n1) Buy Water ($5 each)\n2) Buy Regular Feed ($20 each)\n3) Buy Fancy Feed ($50 each)\n4) Leave Market\n> ");
				marketInput1 = input.nextLine();
				marketInput2 = marketInput1.toLowerCase();
			}
		}
		if (dayTime) {
			cowMood += 20;
			System.out.println(cowName + " is happy that you came back!");
		}
		return true;
	}

	// graphic display
	public boolean affectionateGraphic() {
		if (cowMood <= 20 && dayTime) {
			System.out.println(cowName + " is very happy!");
			System.out.println("					           \\|/\n" + "   (___)	  %		     =+=\n"
					+ "   (^#^)_____/		   /|\\\n" + "    @@ `     \\         \n"
					+ "     \\ ____, /		\"moooo~\"\n" + "     //    //  \n" + "    ^^    ^^");
			return true;
		} else {
			return false;
		}
	}

	// graphic display
	public boolean happyGraphic() {
		if (cowMood > 20 && cowMood <= 40 && dayTime) {
			System.out.println(cowName + " is happy");
			System.out.println("					          \\|/\n" + "   (___)			      =+=\n"
					+ "   (^ ^)______%     /|\\\n" + "    @@ `     \\         \n"
					+ "     \\ ____, /		\"Moooo!\"\n" + "     //    //  \n" + "    ^^    ^^");
			return true;
		} else {
			return false;
		}
	}

	// graphic display
	public boolean normalGraphic() {
		if (cowMood > 40 && cowMood <= 60 && dayTime) {
			System.out.println(cowName + " seems melancholy");
			System.out.println("					          \\|/\n" + "   (___)			      =+=\n"
					+ "   (o o)_____/      /|\\\n" + "    @@ `     \\  \n" + "     \\ ____, /		\"moo\"\n"
					+ "     //    //  \n" + "    ^^    ^^");
			return true;
		} else {
			return false;
		}
	}

	// graphic display
	public boolean sadGraphic() {
		if (cowMood > 60 && cowMood <= 80 && dayTime) {
			System.out.println(cowName + " is sad\nMaybe you should check up on her.");
			System.out.println("					          \\|/\n" + "   (___)	          =+=\n"
					+ "   (- -)______      /|\\\n" + "    @@ `     \\\\        \n" + "     \\ ____, /		\"...\"\n"
					+ "     //    //  \n" + "    ^^    ^^    ");
			return true;
		} else {
			return false;
		}
	}

	// graphic display
	public boolean angryGraphic() {
		if (cowMood > 80 && cowMood <= 100 && dayTime) {
			System.out.println(cowName + " is upset\nYou probably need to do something or let her sleep");
			System.out.println("                    \\|/\n" + "   (___)            =+=\n"
					+ "   (\\ /)_____/      /|\\\n" + "    @@ `     \\  \n" + "     \\ __~~, /    \"MRRRRRRR\"\n"
					+ "     //    //  \n" + "    ^^    ^^");
			return true;
		} else {
			return false;
		}
	}

	// graphic display
	public boolean asleepGraphic() {
		if (!dayTime) {
			System.out.println(cowName + " is asleep in the field");
			System.out.println("					           ,-,\n" + 
					"   (___)			      / {   \n" + 
					"   (_ _)______		  \\ {    \n" + 
					"    @@ `     \\\\  	   `-`\n" + 
					"     \\ ____, / \\	 \n" + 
					"     //    //  		\"zzzz\"\n" + 
					"     ``    ``");
			return true;
		} else {
			return false;
		}
	}

	// graphic display
	public boolean marketGraphic() {
		System.out.println("   _        ,\n" + "  (_\\______/________\n" + "     \\-|-|/|-|-|-|-|/\n"
				+ "      \\==/-|-|-|-|-/\n" + "       \\/|-|-|-|,-'\n" + "        \\--|-'''\n"
				+ "         \\_j________\n" + "         (_)      (_)");
		return true;
	}

	// check to make sure that no stats exceed upper or lower limits
  	public boolean limit() {
		if (waterTrough > 40) {
			waterTrough = 40;
		} else if (waterTrough < 0) {
			waterTrough = 0;
		}
		if (cowHunger > 100) {
			cowHunger = 100;
		} else if (cowHunger < 0) {
			cowHunger = 0;
		}
		if (cowThirst > 50) {
			cowThirst = 50;
		} else if (cowThirst < 0) {
			cowThirst = 0;
		}
		if (totalFeed > 15) {
			totalFeed = 15;
		}
		if (totalFancyFeed > 5) {
			totalFancyFeed = 5;
		}
		if (cowBoredom < 0) {
			cowBoredom = 0;
		} else if (cowBoredom > 50) {
			cowBoredom = 50;
		}
		if (cowMilk > 30) {
			cowMilk = 30;
		} else if (cowMilk < 0) {
			cowMilk = 0;
		}
		if (cowTired >= 1 && dayTime == false) {
			cowTired = 0;
		}
		if (cowMood > 100) {
			cowMood = 100;
		} else if (cowMood < 0) {
			cowMood = 0;
		}
		return true;
	}
}
