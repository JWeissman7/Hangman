import java.util.Scanner;
import java.io.*;
import java.util.ArrayList;

public class HangmanGame {
	// 1) Needs to connect to database 2) Need to allow difficult input 3) Need to be able to choose random word

	// variables that all methods in this class can see!
	static int numLives = 7;
	static String secretWord = "special";
	static int secretWordLength = 0;
	static int numMatches = 0;
	static ArrayList<String> currentBoard = new ArrayList<String>();
	static ArrayList<String> unseenBoard = new ArrayList<String>();
	static String userGuessLetter;
	static ArrayList<String> wordList = new ArrayList<String>(); // initialise array to store all possible words
	static boolean wordOK = true; // reject word if it contains anything other than apostrophe or lower case letter
	static boolean continueGame = true;
	
	public static void main(String[] args) {
		
	    	generateWords();

		System.out.println("Welcome to Hangman! Enjoy your game today!");
		
		Scanner userInput = new Scanner(System.in); // See Java help: https://www.javatpoint.com/Scanner-class
		System.out.println("Please enter a difficulty rating: easy, medium or hard. Note: input is case-sensitive.");
		String userDifficulty = userInput.next();
		
		randomWord(userDifficulty);

		System.out.println("Today's Hangman word is of length " + secretWordLength);
		System.out.println("Number of lives = 7");

		String[] unseenBoardProto = secretWord.split("");
		for (int k = 0; k < secretWordLength; k++ ) {
			unseenBoard.add(unseenBoardProto[k]);
		//	System.out.print(unseenBoard.get(k));
		}

		while (numLives > 0 && continueGame == true) {	
			System.out.println("");
			System.out.println("");
			System.out.println("");
		        for (int k = 0; k < secretWordLength; k++ ) { // problem is first iteration here!!!! 
		       		System.out.print(currentBoard.get(k));
			}
			System.out.println("");
			System.out.println("");
			System.out.println("");
			printHangman(numLives);
			// System.out.println("Please enter your guess now!");
			String userGuess = userInput.next();
			checkGuess(userGuess); // note: checkGuess decrement numLives if no match
			checkWinner();
		}

		if (numLives == 0) {
			printHangman(numLives);
		}
	
	}
	
	// gets called each time the user makes a guess - updates the currentboard
	// also updates numLives - decrements by 1 if NO match

	public static void checkWinner() {
		continueGame = false;
		for (int k = 0; k < secretWordLength; k++) {
			if (!unseenBoard.get(k).equals("*")) {
				continueGame = true;
			}
		}
	}
	
	public static void checkGuess(String tryLetter) {
		boolean matchLetter = false; // set to true if match

			// need to: 1) update currentBoard, ii) update unseenBoard, iii) set matchLetter = true (can keep setting it true)
			//if (getUnseenLetter(unseenBoard, j).equals(tryLetter));
		//}
				
		for (int k = 0; k < secretWordLength; k++ ) {

			if (tryLetter.equals(unseenBoard.get(k))) { 
				currentBoard.set(k, tryLetter);  
				unseenBoard.set(k, "*");
				matchLetter = true;
				numMatches = numMatches + 1;
				}			
		}

		// check if game is over or we should continue 
		// if should continue: print out line asking user for another guess
		
	//	for (int k = 0; k < secretWordLength; k++ ) {
	//		System.out.print(currentBoard.get(k));
	//	}
	//	System.out.println("");
		
		if (matchLetter == false) {
			numLives = numLives - 1;
		}
		
		if (numLives > 0 & numMatches < secretWordLength) {
			System.out.println("Take another guess! Number of lives = " + numLives);
		}
		else if (numLives > 0 && numMatches == secretWordLength) {
			System.out.println("Congratulations! You beat the computer!");
		}
		else 
			{System.out.println("The Hangman word was: " + secretWord + ".");	
			System.out.println("The Hangman Game is over. You are now hanging! Goodbye!");}		
			
	}
	
	public static void generateWords() {
		try {
			File myWordList = new File("words.txt");
			FileReader fileReader = new FileReader(myWordList);
			
			BufferedReader reader = new BufferedReader(fileReader);
			
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				wordList.add(line);
			}
			reader.close();

		} catch(Exception ex) {
			ex.printStackTrace();			
		}
		
		
		// how to print out an element String from wordList String array
		// String wordFromList = wordList.get(1);
		// System.out.println(wordFromList);
	}
	
	public static void randomWord(String difficulty) {
		
		boolean stillSearching = true;
		String suggestedWord = null;
		int suggestedWordLength = 0;
		int randomIndex;
		int dictionarySize = wordList.size();
			
		while (stillSearching)	{
		
		// Don't think I need a break here - as we are checking the boolean variable which we update!
			randomIndex = (int)(Math.random() * (dictionarySize + 1));
			suggestedWord = wordList.get(randomIndex);
      			suggestedWordLength = suggestedWord.length();

			if (! suggestedWord.matches(".*[^a-z].*") ) { // copied: if ( ! s.matches(".*[^a-z].*") ). I have inverted the truth value for exceptions.
					
				if (difficulty.equals("easy")) {
					if (suggestedWordLength < 6) {
						stillSearching = false;
						System.out.println("Difficulty has been set to easy.");
						}
					}
		
				else if (difficulty.equals("medium")) {
					if (suggestedWordLength > 5 && suggestedWordLength < 9) {
						stillSearching = false;
						System.out.println("Difficulty has been set to medium.");
						}
					}
		
				else	{
					if (suggestedWordLength > 8) {
			      			stillSearching = false;
						System.out.println("Difficulty has been set to hard.");
					  	}
					}

			}
				
		 } 
		
	    	 secretWord = suggestedWord;
		 secretWordLength = suggestedWordLength;
		 
		 

		// generate and print initial currentBoard.

		for (int k = 0; k < secretWordLength; k++ ) {
		    currentBoard.add("_ ");
		}
		
	}

	public static void printHangman(int printInput) { // input will be numLives
			
		  switch (printInput) {
        	  case 0:  
		     System.out.println(" " + " _______");
		     System.out.println(" " + " | " + " " + " " + " " + " |");
		     System.out.println(" " + " | " + " " + " " + " " + " O");
		     System.out.println(" " + " | " + " " + " " + " " + "/|\\");
                     System.out.println(" " + " | " + " " + " " + " / \\");
 		     System.out.println(" " + " | ");
                     System.out.println("__|___");
		     System.out.println("| " + " " + " " + " |");  
                     break;
            	  case 1:
		     System.out.println(" " + " _______");
		     System.out.println(" " + " | " + " " + " " + " " + " |");
		     System.out.println(" " + " | " + " " + " " + " " + " O");
		     System.out.println(" " + " | " + " " + " " + " " + "/|\\");
                     System.out.println(" " + " | " + " " + " " + " /");
 		     System.out.println(" " + " | ");
                     System.out.println("__|___");
		     System.out.println("| " + " " + " " + " |");   
                     break;
            	  case 2: 
                     System.out.println(" " + " _______");
		     System.out.println(" " + " | " + " " + " " + " " + " |");
		     System.out.println(" " + " | " + " " + " " + " " + " O");
		     System.out.println(" " + " | " + " " + " " + " " + "/|\\"); 
                     System.out.println(" " + " | ");
 		     System.out.println(" " + " | ");
                     System.out.println("__|___");
		     System.out.println("| " + " " + " " + " |");
                     break;
            	  case 3: 
		     System.out.println(" " + " _______");
		     System.out.println(" " + " | " + " " + " " + " " + " |");
		     System.out.println(" " + " | " + " " + " " + " " + " O");
		     System.out.println(" " + " | " + " " + " " + " " + "/|"); 
                     System.out.println(" " + " | ");
 		     System.out.println(" " + " | ");
                     System.out.println("__|___");
		     System.out.println("| " + " " + " " + " |");
                     break;
             	  case 4:  
		     System.out.println(" " + " _______");
		     System.out.println(" " + " | " + " " + " " + " " + " |");
		     System.out.println(" " + " | " + " " + " " + " " + " O");
		     System.out.println(" " + " | " + " " + " " + " " + "/");
                     System.out.println(" " + " | ");
 		     System.out.println(" " + " | ");
                     System.out.println("__|___");
		     System.out.println("| " + " " + " " + " |");
                     break;
            	  case 5:  
		     System.out.println(" " + " _______");
		     System.out.println(" " + " | " + " " + " " + " " + " |");
		     System.out.println(" " + " | " + " " + " " + " " + " O");
		     System.out.println(" " + " | ");
		     System.out.println(" " + " | ");
                     System.out.println(" " + " | ");
 		     System.out.println(" " + " | ");
                     System.out.println("__|___");
		     System.out.println("| " + " " + " " + " |");
                     break;
            	  case 6:  
		     System.out.println(" " + " _______");
		     System.out.println(" " + " | " + " " + " " + " " + " |");
		     System.out.println(" " + " | ");
		     System.out.println(" " + " | ");
		     System.out.println(" " + " | ");
                     System.out.println(" " + " | ");
 		     System.out.println(" " + " | ");
                     System.out.println("__|___");
		     System.out.println("| " + " " + " " + " |");
                     break;
            	  case 7:  
		     System.out.println(" " + " _______");
		     System.out.println(" " + " | ");
		     System.out.println(" " + " | ");
		     System.out.println(" " + " | ");
		     System.out.println(" " + " | ");
                     System.out.println(" " + " | ");
 		     System.out.println(" " + " | ");
                     System.out.println("__|___");
		     System.out.println("| " + " " + " " + " |");
                     break;
        	}

	}
		
}
	
	// public static void randomSecretWord(String difficulty)
	// three cases - affects length 
	// do by trial and error until finds word it wants in array
	// i.e. find by reject incorrect
		
		

	
	/* public int get(int[] r)
	  {
	     return r[0];
	  } */
	 
	 // getter method to return a String element of array - need for unseenBoard	
	//public static String getUnseenLetter(String[] stringArray, int indexArray) {
//		return stringArray[indexArray];
//	}
	
	/* String[] myStringArray = new String[3];
String[] myStringArray = {"a","b","c"};
String[] myStringArray = new String[]{"a","b","c"};

*/
	
	
	// methods to be called from main method


