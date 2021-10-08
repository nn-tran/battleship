package data_container;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ScoreKeeper{
	private Data data = Data.getInstance();
	private String previousHighScore = "None";
	private String latestScore = "None";
	private int previousHighScoreTurns = 200;
	private int latestTurns = 200;

	/**method will read data from HighScores.txt if any
	*/
	public void readHighScore(){
		try{
			//BufferedReader which reads data from a file
			BufferedReader file = new BufferedReader(new FileReader("HighScores.txt"));
			String firstLine=file.readLine();
			String secondLine=file.readLine();
			//array containing the name and number of turns of the latest player
			String[] latestArray = firstLine.split(",");
			latestScore = latestArray[0];
			latestTurns= Integer.parseInt(latestArray[1]);
			//array containing the name and number of turns of the best player
			String[] highArray = secondLine.split(",");
			previousHighScore = highArray[0];
			previousHighScoreTurns = Integer.parseInt(highArray[1]);
			file.close();
		}
		catch(IOException e) {System.out.println("reading high score failed");}
		
	}
	/**method will write to HighScores.txt
	*/
	public void writeHighScore(){
		try{
			//create a writer file
			BufferedWriter newPlayer= new BufferedWriter(new FileWriter("HighScores.txt"));
			//checks to see if the latest player beat the high score
			if(previousHighScoreTurns > data.getCount()){
				System.out.println(previousHighScoreTurns+" "+data.getCount());
				System.out.println("replaced high score");
				previousHighScore = data.getTurnPlayer().getName();
				previousHighScoreTurns = data.getCount();			
			}
			//sets the latest player's name and score
			latestScore = data.getTurnPlayer().getName();
			latestTurns = data.getCount();	

			String firstLine = latestScore + "," + latestTurns;
			String secondLine = previousHighScore + "," + previousHighScoreTurns;
			//writes the high score and most recent score to the text file
			newPlayer.write(firstLine);
			newPlayer.newLine();
			newPlayer.write(secondLine);
			newPlayer.close();
		}	
		catch(IOException e){
			System.out.println("File not Found");
		}
	}
	
	public String getHighScore() {
		return previousHighScore + "," + previousHighScoreTurns;
	}
	
	public String getLatest() {
		return latestScore + "," + latestTurns;
	}

	public static void main(String[] args) {}
}