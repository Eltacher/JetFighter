public class User {

	private String Username;	
	private String pass;	
	private int score;		

	
	
	public User(String Username, String pass,int score)
	{
		this.Username = Username;
		this.pass = pass;
		this.score = score;
	}

	public String getName()
	{
		return Username;
	}
	
	public void setName(String name)
	{
		this.Username = name;
	}
	
	public String getPass()
	{
		return pass;
	}
	public void setPass(String pass)
	{
		this.pass = pass;
	}
	
	public int getScore()
	{
		return score;
	}
	
	public void setScore(int score)
	{
		this.score = score;
	}

}
