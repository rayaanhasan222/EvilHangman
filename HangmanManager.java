import java.util.*;

public class HangmanManager
{
	private String patternDisplayed;
    private Set<String> wordsConsidered;
    private int guessCount;
    private SortedSet<Character> lettersGuessed; 
	public HangmanManager( List<String> dictionary, int length, int max )
	{
		if(length<1 || max<0) {
			throw new IllegalArgumentException();
		}
		guessCount = max;
		wordsConsidered = new TreeSet<String>();
		lettersGuessed = new TreeSet<Character>();
		for (String word : dictionary) {
			if(word.length()==length) {
				wordsConsidered.add(word);
			}
		}
		patternDisplayed = "";
		for (int i = 0; i < length; i++) {
			patternDisplayed += "-";
		}
	}
	
	public Set<String> words()
	{
		return wordsConsidered;
	}	
	
	public int guessesLeft()
	{
		return guessCount;
	}
		
	public Set<Character> guesses()
	{
		 return lettersGuessed;
	}
	
	public String pattern()
	{
		if (wordsConsidered.isEmpty()) {
            throw new IllegalStateException();
        }
        return patternDisplayed;
	}
	
	public int record( char guess )
	{
		 if (wordsConsidered.isEmpty() || guessCount < 1) {
            throw new IllegalStateException();
        } else if (!wordsConsidered.isEmpty() && lettersGuessed.contains(guess)) {
            throw new IllegalArgumentException();
        }
        lettersGuessed.add(guess);
        Map<String, Set<String>> findFamily = new TreeMap<String, Set<String>>();
        for (String word : wordsConsidered) {
            String pattern = patternMaker(guess, word);
            Set<String> setOfWords = new TreeSet<String>();
            if (!findFamily.containsKey(pattern)) {
                findFamily.put(pattern, setOfWords);
            }
            findFamily.get(pattern).add(word);
        }
        int maximumSize = 0;
        if (!findFamily.isEmpty()) {
            for (String currentPattern : findFamily.keySet()) {
                if (findFamily.get(currentPattern).size() > maximumSize) {
                    wordsConsidered.clear();
                    wordsConsidered.addAll(findFamily.get(currentPattern));
                    patternDisplayed = currentPattern;
                    maximumSize = findFamily.get(currentPattern).size();
                }
            }
        }
        return decision(guess);
    }

    // This method keeps updating the pattern with each input
    // dashes represent unidentified letter
    // returns the pattern going to be displayed
    private String patternMaker(char guess, String word) {
        String pattern = "";
        int index = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != guess) {
                pattern += patternDisplayed.substring(index, index + 1);
            } else {
                pattern += guess + " ";
            }
            index += 1;
        }
        return pattern;
    }

    // Tracks the occurences of the letters in the pool
    // If the occurence is zero it gets reduced by one
    // returns the maximum occurences of the letters
    private int decision(char guess) {
        int maxOccurence = 0;
        for (int i = 0; i < patternDisplayed.length(); i++) {
            if (patternDisplayed.charAt(i) == guess) {
                maxOccurence++;
            }
        }
        lettersGuessed.add(guess);
        if (maxOccurence == 0) {
            guessCount--;
        }
        return maxOccurence;

	}
}
