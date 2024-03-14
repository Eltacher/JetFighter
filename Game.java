import java.awt.Color; 
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;













public class Game extends JFrame implements Runnable, KeyListener{
	
	private static final long serialVersionUID = 1L;							
	private static Plane player;
	private Background background1, background2, background3;
	public ArrayList<Rocket> enemyRocketList;
	private ArrayList<Rocket> rocketList;
	private ArrayList<Enemy> enemyList;
	
	public LoginScreenUI LoginScreenUI;
	public Font fontUI;	
	private BufferedImage rocketImage,enemyBulletImage;
	Image explosionIcon;
	
	private static final String ENEMYPLANE_IMAGE_PATH = "src/uçak11.png";
	private static final String PLANE_IMAGE_PATH = "src/uçak8.png";
	private static final String BACKGROUND_PATH = "src/bg3.jpg";              // All image file's paths
	private static final String BULLET_PATH = "src/mermi4.png";
	private static final String ENEMY_BULLET_PATH = "src/mermi9.png";
	
	private boolean moveLeftPressed = false;
	private boolean moveRightPressed = false;
	
	private User user;
	private int WIDTH, HEIGHT,score = 0;	
	private Random randomnumber;
    static int numberOfEnemy =0;



     public Game(int w, int h, User p) 
     {
         WIDTH = w;
         HEIGHT = h;
         user = p;
         setLayout(null);
         addKeyListener(this);
         rocketList = new ArrayList<>();
         enemyRocketList = new ArrayList<>();
         enemyList = new ArrayList<>();
         randomnumber = new Random();
         fontUI = new Font("Arial Black", Font.ROMAN_BASELINE, 18);
         prepareObjects();
     }
	
     @Override
     public void run() { // Thread run
         while (player.alive) {
             updateGame();
             repaint();

             try 
             {
                 Thread.sleep(25);
             } 
             catch (InterruptedException e) 
             {
                 e.printStackTrace();
             }
         }

         UserDataManager dM = new UserDataManager();
         dM.updateScore(user.getName(), user.getPass(), user.getScore());
         JOptionPane.showConfirmDialog(null, "Your score is: " + score, "GAME OVER", JOptionPane.CLOSED_OPTION);
         this.setVisible(false);
     }
	
	public void prepareObjects() // Prepare game
	{
		prepareBackground();
		preparePlanes();
		prepareRockets();
		loadExplosionIcon();
	}
	
	public void prepareBackground() // Loading dynamic background
	{
		try 
		{
			BufferedImage bg = ImageIO.read(new File(BACKGROUND_PATH));
            background1 = new Background(0, 0, WIDTH, HEIGHT, bg);
            background2 = new Background(0, background1.getHeight(), WIDTH, HEIGHT, bg);
            background3 = new Background(0, -background1.getHeight(), WIDTH, HEIGHT, bg);
		}
	        
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void preparePlanes() //Loading user's plane 
	{
		try 
		{
			BufferedImage planeimage = ImageIO.read(new File(PLANE_IMAGE_PATH));
            int planeWidth = (WIDTH * 20) / 100;
            int planeHeight = (planeimage.getHeight() * planeWidth) / planeimage.getWidth();
            player = new Plane(WIDTH / 2 - planeWidth / 2, HEIGHT - planeHeight - HEIGHT / 50, planeWidth, planeHeight, planeimage);
            player.setScreenWidth(WIDTH);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
        
	}
	
	public void prepareRockets()  // Loading rocket images
	{
        try 
        {
			rocketImage = ImageIO.read(new File(BULLET_PATH));
		} 
        catch (IOException e) 
        {
        	e.printStackTrace();
		}
        try 
        {
        	enemyBulletImage = ImageIO.read(new File(ENEMY_BULLET_PATH));
		} 
        catch (IOException e) 
        {
        	e.printStackTrace();
		}
	}
	
	private void loadExplosionIcon() 
	{
		try 
		{
            explosionIcon = new ImageIcon(new URL("https://media.tenor.com/RCQgbxVWhYgAAAAi/discord-discordgifemoji.gif")).getImage();
        } 
		catch (Exception e) 
		{
            e.printStackTrace();
        }
    }
	
	private void updateGame() 
	{
		if (moveLeftPressed) 
		{
	        player.moveLeft();
	    }
	    if (moveRightPressed) 
	    {
	        player.moveRight();
	    }
		movePlayerRockets();
        EnemyActions();
        checkCollisions();

        for (int ed = 0; ed < enemyList.size(); ed++) 
        {
            if (enemyList.get(ed).alive==false) 
            {
                enemyList.remove(ed);
                score += 20;
                user.setScore(score);
            }
        }

        handleBackgroundMovement();
    }
	
	private void movePlayerRockets()   
	{
		 Iterator<Rocket> iterator = rocketList.iterator();
	     while (iterator.hasNext()) 
	     {
	    	 Rocket rocket = iterator.next();
	    	 rocket.move();
	         
	    	 if (rocket.getY()< 0)  // Remove bullets that go off the top of the panel
	    	 {
	    		 iterator.remove();
	    	 }
	     }
	}

	 
	public void createEnemy()  // Creating enemy
	{
        try 
        {
            BufferedImage enemyImage = ImageIO.read(new File(ENEMYPLANE_IMAGE_PATH ));
            int enemyWidth = (WIDTH * 17) / 100;
            int enemyHeight = enemyImage.getHeight() * enemyWidth / enemyImage.getWidth();
            Enemy enemyObject;

            int randomX = randomnumber.nextInt(WIDTH - enemyWidth);
            int newY;

            if (enemyList.isEmpty() || enemyList.get(enemyList.size() - 1).getY() <= 0) {
                newY = -enemyHeight;
            } 
            else 
            {
                newY = enemyList.get(enemyList.size() - 1).getY() - randomnumber.nextInt(enemyHeight) - enemyHeight *(3/2);
            }

            enemyObject = new Enemy(randomX, newY, enemyWidth, enemyHeight, enemyImage, WIDTH, 100, 50);

            enemyList.add(enemyObject);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
	
	
	private void EnemyActions()   //Enemy actions
	{
        for (int moveEnemy = 0; moveEnemy < enemyList.size(); moveEnemy++) {
            int moveState = randomnumber.nextInt(100);

            if (enemyList.get(moveEnemy).getMoveCount() == 0) {
                if (moveState < 50) {
                    enemyList.get(moveEnemy).setMoveState(1);
                    enemyList.get(moveEnemy).setMoveCount(randomnumber.nextInt(100));
                } 
                else 
                {
                    enemyList.get(moveEnemy).setMoveState(0);
                    enemyList.get(moveEnemy).setMoveCount(randomnumber.nextInt(100));
                }
            } 
            else 
            {
                enemyList.get(moveEnemy).moveLR();

                int enemyshootChance = randomnumber.nextInt(150);
                if (enemyshootChance == 50) 
                {
                    enemyShoot(enemyList.get(moveEnemy));
                }
            }
        }

        Iterator<Enemy> Enemyiterator = enemyList.iterator();
        while (Enemyiterator.hasNext()) 
        {
            Enemy enemy = Enemyiterator.next();
            enemy.moveToDown();

            if (enemy.getY() > HEIGHT) 
            {
                Enemyiterator.remove();
            }
        }

        Iterator<Rocket> Enemyrocketiterator = enemyRocketList.iterator();
        while (Enemyrocketiterator.hasNext()) 
        {
            Rocket eBull = Enemyrocketiterator.next();
            eBull.move();

            if (eBull.getY() > HEIGHT) 
            {
                Enemyrocketiterator.remove();
            }
        }

        if (numberOfEnemy < 1000 && randomnumber.nextInt(200) == 50) 
        {
            createEnemy();
            numberOfEnemy++;
        }
	}
	 
	private void checkCollisions() 
	{
        checkEnemyCollisions();
        checkEnemyAndBulletsCollisions();
        checkEnemyBulletAndPlayerCollision();
    } 
	
	private void checkEnemyCollisions() 
	{
        Iterator<Enemy> enemyCounter = enemyList.iterator();

        while (enemyCounter.hasNext()) {
            Enemy enemy = enemyCounter.next();
            Rectangle enemyPlane = new Rectangle(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());

            if (enemyPlane.intersects(player.getX(), player.getY(), player.getWidth(), player.getHeight())) 
            {
                player.decreaseHealth(20);
                enemyCounter.remove();
            }
        }
    }
	
	private void checkEnemyBulletAndPlayerCollision() {
        Iterator<Rocket> eBulletIterator = enemyRocketList.iterator();

        while (eBulletIterator.hasNext()) {
            Rocket eBullet = eBulletIterator.next();
            Rectangle plane = new Rectangle(player.getX(), player.getY(), player.getWidth(), player.getHeight());

            if (plane.intersects(eBullet.getX(), eBullet.getY(), eBullet.getWidth(), eBullet.getHeight())) 
            {
                player.decreaseHealth(eBullet.getDamage());
                eBulletIterator.remove();
            }
        }
    }
	
	private void checkEnemyAndBulletsCollisions() {
        Iterator<Enemy> enemyIterator = enemyList.iterator();

        while (enemyIterator.hasNext()) {
            Enemy enemy = enemyIterator.next();
            Rectangle enemyPlane = new Rectangle(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());

            Iterator<Rocket> bulletIterator = rocketList.iterator();

            while (bulletIterator.hasNext()) {
                Rocket rocket = bulletIterator.next();

                if (enemyPlane.intersects(rocket.getX(), rocket.getY(), rocket.getWidth(), rocket.getHeight())) 
                {
                    enemy.decreaseHealth(rocket.getDamage());
                    if(!enemy.isEnemyAlive())
                    {
                    	player.increaseHealth(1);
                        player.setRocketNumber(player.getRocketNumber()+4);
                    }
                    
                    bulletIterator.remove();
                }
            }
        }
    }
	
	/*private void checkCollisions() 
	 {
			
			///////////////// Check Enemy Collisions
			Iterator<Enemy> enemyCounter =  enemyList.iterator();
			while(enemyCounter.hasNext()) {
				Enemy enemyList = enemyCounter.next();
				Rectangle Enemyplane = new Rectangle(enemyList.getX(),enemyList.getY(), enemyList.getWidth(), enemyList.getHeight());
				if (Enemyplane.intersects(player.getX(), player.getY(), player.getWidth(), player.getHeight())) {
					player.decreaseHealth(30);
					///// decrease health
					enemyCounter.remove();
				}
			}
			////////////////////// checks enemy and bullets collisions
			Iterator<Enemy> en = enemyList.iterator();
			while(en.hasNext()) {
				Enemy enemyList = en.next();
				Rectangle Enemyplane = new Rectangle(enemyList.getX(),enemyList.getY(), enemyList.getWidth(), enemyList.getHeight());
				Iterator<Rocket> bullet = rocketList.iterator();
				while(bullet.hasNext()) {
					Rocket rc = bullet.next();
					if(Enemyplane.intersects(rc.getX(),rc.getY(),rc.getWidth(),rc.getHeight())){
						enemyList.decreaseHealth(rc.getDamage());
						bullet.remove();
					}
				}
			}
			/////////////////////////// check enemybullet and player collision
			Iterator<Rocket> eBullet = enemyRocketList.iterator();
			while(eBullet.hasNext()) {
				Rocket ebull= eBullet.next();
				Rectangle plane = new Rectangle(player.getX(),player.getY(),player.getWidth(),player.getHeight());
				if(plane.intersects(ebull.getX(), ebull.getY(), ebull.getWidth(), ebull.getHeight())) {
					player.decreaseHealth(ebull.getDamage());
					eBullet.remove();
				}
			}
	}*/
	 
	private void handleBackgroundMovement()  // dynamic background logic 
	{
        if (background1.getY() >= HEIGHT) 
        {
            background1.setY(background2.getY() - background1.getHeight());
        }
        background1.setY(background1.getY() + background1.speed);

        if (background2.getY() >= HEIGHT) 
        {
            background2.setY(background3.getY() - background2.getHeight());
        }
        background2.setY(background2.getY() + background2.speed);

        if (background3.getY() >= HEIGHT) 
        {
            background3.setY(background1.getY() - background3.getHeight());
        }
        background3.setY(background3.getY() + background3.speed);
    }

	 
	public Image drawScene()  // Drawing Scene
	{
	    BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	    Graphics g = bufferedImage.createGraphics();

	 
	    g.drawImage(background1.getImage(), background1.getX(), background1.getY(), background1.getWidth(), background1.getHeight(), this);
	    g.drawImage(background2.getImage(), background2.getX(), background2.getY(), background2.getWidth(), background2.getHeight(), this);    // dynamic backgrounds
	    g.drawImage(background3.getImage(), background3.getX(), background3.getY(), background3.getWidth(), background3.getHeight(), this);

	    
	    g.drawImage(player.getImage(), player.getX(), player.getY(), player.getWidth(), player.getHeight(), this); // player's plane

	    
	    for (int i = 0; i < rocketList.size(); i++) 
	    {
	        g.drawImage(rocketList.get(i).getImage(), rocketList.get(i).getX(), rocketList.get(i).getY(), rocketList.get(i).getWidth(), rocketList.get(i).getHeight(), this); // player's rockets
	    }

	    
	    for (int en = 0; en < enemyList.size(); en++)  // enemy plane
	    {
	        Enemy enemy = enemyList.get(en);

	        if (enemy.isEnemyAlive()) 
	        {
	            g.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight(), this);
	        } 
	        /*else {
                // Create a JLabel with the explosion GIF and add it to the JPanel
                JLabel explosionLabel = new JLabel(new ImageIcon(explosionIcon));
                explosionLabel.setBounds(enemy.getX(), enemy.getY(), enemy.getWidth(), enemy.getHeight());
                add(explosionLabel);
            }*/
	    }

	    
	    for (int i = 0; i < enemyRocketList.size(); i++)  // enemy rockets
	    {
	        g.drawImage(enemyRocketList.get(i).getImage(), enemyRocketList.get(i).getX(),enemyRocketList.get(i).getY(), enemyRocketList.get(i).getWidth(),enemyRocketList.get(i).getHeight(), this);
	    }

	    g.setFont(fontUI);
	    g.drawString("" + score, WIDTH / 2, HEIGHT / 10);
	    g.drawString("AMMO: "+player.getRocketNumber(), 15, 760);
	    g.drawString("HEALTH " + player.getHealth(), 450, 750);
	    g.setColor(Color.RED);
	    g.fillRect(450, 755, WIDTH / 5 * player.getHealth() / 100, HEIGHT / 80);

	    if (!player.alive) 
	    {
	        g.clearRect(0, 0, getWidth(), getHeight());
	    }

	    return bufferedImage;
	}
	
	
	/*private void drawExplosionAnimation(int x, int y,int w,int h) 
	{
		explosionLabel.setBounds(x, y, w, h);
	    explosionLabel.setVisible(true);
	    
	    Timer timer = new Timer(1000, e -> explosionLabel.setVisible(false));
	    timer.setRepeats(false); // Only fire the timer once
	    timer.start();
	}*/
	 
	@Override
    public void paint(Graphics g) 
	{
        g.drawImage(drawScene(), 0, 0, this);
    }
	 
	
	public void shootRocket(int x, int y) // player's rocket shooting
	{
		 int bulletWidth = player.getWidth()/5;
	     int bulletHeight = rocketImage.getHeight() * bulletWidth / rocketImage.getWidth(); 
			if(player.getRocketNumber()>0) {
				player.decreaseRocket(); 
				rocketList.add(new Rocket(x + player.getWidth()/3, y + player.getHeight()/10, HEIGHT, 25, 0, bulletWidth,bulletHeight, rocketImage));
				
		}
	 }
	 
	public void enemyShoot(Enemy enemy) // enemy's rocket shooting
	{
		int rocketWidth = enemy.getWidth() / 5;
		int rocketHeight = enemyBulletImage.getHeight() * rocketWidth / enemyBulletImage.getWidth();
		// Set the damage and direction for enemy rockets
		enemyRocketList.add(new Rocket(enemy.getX() + enemy.getWidth() / 3, enemy.getY() + enemy.getHeight() - 5, HEIGHT, 2, 1, rocketWidth, rocketHeight, enemyBulletImage));
	}
	
	 
	 @Override
	public void keyTyped(KeyEvent e) 
	{
		
		
	}

	 @Override
	 public void keyPressed(KeyEvent e) // controls
	 {
	     switch (e.getKeyCode()) 
	     {
	         case KeyEvent.VK_LEFT:
	             moveLeftPressed = true;
	             break;
	         case KeyEvent.VK_RIGHT:
	             moveRightPressed = true;
	             break;
	         case KeyEvent.VK_SPACE:
	             shootRocket(player.getX(), player.getY());
	             break;
	     }
	 }

	 @Override
	 public void keyReleased(KeyEvent e) // controls
	 {
	     switch (e.getKeyCode()) 
	     {
	         case KeyEvent.VK_LEFT:
	             moveLeftPressed = false;
	             break;
	         case KeyEvent.VK_RIGHT:
	             moveRightPressed = false;
	             break;
	     }
	 }


	

	
	
}