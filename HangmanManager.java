import java.util.*;

public class HangmanManager
{
	private String patternDisplayed;
    private Set<String> wordsConsidered;
    private int guessCount;
    private SortedSet<Character> lettersGuessed; 
	public HangmanManager( List<String> dictionary, int length, int max )
	{
		
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
		return 0;
	}
}
