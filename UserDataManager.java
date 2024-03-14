import java.io.BufferedReader ;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;





public class UserDataManager 
{
	private static final String FILE_PATH = "src/Player.txt";
	private static User player;

    public void saveDataToTXT( String name, String password, String point) 
    {
    	try 
    	{
            FileWriter writer = new FileWriter(FILE_PATH, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(name + "," + password + "," + point);
            bufferedWriter.newLine();

            bufferedWriter.close();

        } 
    	catch (IOException e) 
    	{
            e.printStackTrace();
        }
    }

    public User UserChecker(String name , String password) {
        try 
        {
            FileReader reader = new FileReader(FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            while ((line = bufferedReader.readLine()) != null) 
            {
                String[] fields = line.split(",");
                if (fields.length == 3) 
                {
                    String names = fields[0];
                    String passwords = fields[1];
                    int points = Integer.parseInt(fields[2]);
                    if(name.equals(names) && password.equals(passwords))
                    {
                    	player = new User(name, password, points);
                    	break;
                    }
                }
            }

            bufferedReader.close();
        } 
    	catch (IOException e) 
        {
            e.printStackTrace();
        }
    	
    	return player;
    }
    
    public void updateScore(String name, String password, int score) 
    {
    	try 
    	{
            FileReader reader = new FileReader(FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);

            ArrayList<String> lines = new ArrayList<>();

            String line;
            while ((line = bufferedReader.readLine()) != null) 
            {
                String[] fields = line.split(",");
                if (fields.length == 3) 
                {
                    String names = fields[0];
                    String passwords = fields[1];
                    int points = Integer.parseInt(fields[2]);
                    if (name.equals(names) && password.equals(passwords)) 
                    {
                        points = score;
                    }
                    lines.add(names + "," + passwords + "," + points);
                }
            }

            bufferedReader.close();

            // Write the updated scores back to the file
            
            FileWriter writer = new FileWriter(FILE_PATH);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            for (String updatedLine : lines) 
            {
                bufferedWriter.write(updatedLine);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
        } 
    	catch (IOException e) 
    	{
            e.printStackTrace();
        }
    }
    
    public String ScoreFromTXT() 
    {
        ArrayList<User> playerList = new ArrayList<>();
        String print ="";
        try 
        {
            FileReader reader = new FileReader(FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(reader);

            String line;
            while ((line = bufferedReader.readLine()) != null) 
            {
                String[] fields = line.split(",");
                if (fields.length == 3) 
                {
                    String name = fields[0];
                    String password = fields[1];
                    int points = Integer.parseInt(fields[2]); 
                    playerList.add(new User(name,password, points));
                }
            }

            bufferedReader.close();
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // Sort the list based on points in descending order
        Collections.sort(playerList, Comparator.comparingInt(User::getScore).reversed());

        // Print the sorted scores
        for (User player : playerList) 
        {
            print += (player.getName() + " => " + player.getScore());
            print += "\n";
        }
        return print;
    }
    
}
