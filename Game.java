import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;
import javax.imageio.ImageIO;

public class Game extends JFrame implements KeyListener
{
	Man man;
	ArrayList<Bound> Land;
	ArrayList<Enemy> Enemies;
	int xpane, ypane, xplace, xfinish, level, wait = 20, moving, backID;
	public static int score;
	
	public Game()
	{
		xpane = 1000;
		ypane = 800;
		makeGUI();
		System.out.println("gui made");
		man = new Man();
		System.out.println("man made");
		level = 1;
		while(!K_ESC)
		{
			xplace = 0;
			Land = new ArrayList<Bound>();
			Enemies = new ArrayList<Enemy>();
			moving = 0;
			backID = 0;
			
			if(level > 6)
				level = 1;
			if(level == 1)
			{
				Land.add(new Bound(-100,550,550,300,1));
				Land.add(new Bound(600,550,550,300,1));
				Land.add(new Bound(1300,550,350,300,1));
				Land.add(new Bound(1800,550,350,300,1));
				Land.add(new Bound(2300,550,750,300,1));
				Land.add(new Bound(2700,400,350,300,1));
				Land.add(new Bound(3300,550,2000,300,1));
				xfinish = 4000;
			}
			if(level == 2)
			{
				Land.add(new Bound(-100,700,500,300,1));
				for(int i = 0; i < 25; i++)
					Land.add(new Block(550 + i*40,600,0,0));
				for(int i = 0; i < 30; i++)
					Land.add(new Block(850 + i*40,400,0,0));
				for(int i = 0; i < 30; i++)
					Land.add(new Block(1300 + i*40,200,0,0));
				for(int i = 0; i < 20; i++)
					Land.add(new Block(1600 + i*40,0,0,0));
				for(int i = 0; i < 7; i++)
					Land.add(new Block(2200,i*40,0,0));
				for(int i = 0; i < 50; i++)
					Land.add(new Block(2000 + i*40,800,0,0));
				
				Land.add(new Block(2000,600,1,10));
				Land.add(new Block(2200,210,1,10));
				xfinish = 3000;
				moving = 2;
			}
			if(level == 3)
			{
				Land.add(new Bound(-100,700,2000,300,1));
				Land.add(new Bound(4000,450,1500,100,1));
				Land.add(new Bound(-100,-20,4000,20,2));
				Land.add(new Bound(200,550,200,20,2));
				Land.add(new Bound(300,150,100,20,2));
				Land.add(new Bound(500,350,200,20,2));
				Land.add(new Bound(800,550,200,20,2));
				Land.add(new Bound(1100,450,200,20,2));
				Land.add(new Bound(1400,550,650,20,2));
				Land.add(new Bound(2150,400,150,20,2));
				Land.add(new Bound(2400,400,100,20,2));
				Land.add(new Bound(2600,400,75,20,2));
				Land.add(new Bound(2800,400,50,20,2));
				Land.add(new Bound(2900,250,50,20,2));
				Land.add(new Bound(3100,250,50,20,2));
				Land.add(new Bound(3500,750,50,20,2));
				Land.add(new Bound(3700,650,50,20,2));
				Land.add(new Bound(3900,550,50,20,2));
				Land.add(new Bound(3950,800,1550,100,2));
				Land.add(new Bound(4400,550,200,300,3));
				Land.add(new Block(4200,550,1,10));
				xfinish = 4500;
				moving = 2;
			}
			if(level == 4)
			{
				backID = 1;
				Land.add(new Bound(-100,700,1000,300,1));
				Land.add(new Block(100,500,1,11));
				xfinish = 500;
				moving = 0;
			}
			if(level == 5)
			{
				Land.add(new Bound(-100,700,600,300,1));
				Land.add(new Bound(500,500,500,500,1));
				Land.add(new Bound(1000,300,500,500,1));
				Land.add(new Bound(1500,700,2850,300,1));
				Land.add(new Bound(1700,500,100,300,1));
				Land.add(new Bound(1900,500,800,20,2));
				Land.add(new Bound(2400,0,100,500,1));
				Land.add(new Bound(2700,300,100,500,1));
				Land.add(new Bound(2800,300,500,20,2));
				Land.add(new Bound(2900,500,500,20,2));
				Land.add(new Bound(3400,0,20,520,3));
				Land.add(new Bound(3700,550,20,150,3));
				Land.add(new Bound(3900,550,20,150,3));
				Land.add(new Bound(4100,550,20,150,3));
				Land.add(new Bound(4350,700,1000,300,3));
				xfinish = 4500;
				moving = 3;
			}
			if(level == 6)
			{
				Land.add(new Bound(0,700,2000,100,1));
				Land.add(new Bound(200,690,500,100,1));
				Land.add(new Bound(400,670,500,100,1));
				Land.add(new Bound(600,640,500,100,1));
				Land.add(new Bound(800,500,500,100,1));
				Land.add(new Bound(2000,550,100,500,1));
				xfinish = 2000;
				moving = 0;
				Enemies.add(new Enemy(900,1));
				Enemies.add(new Enemy(1100,1));
				Enemies.add(new Enemy(1500,1));
				Enemies.add(new Enemy(1600,1));
				Enemies.add(new Enemy(1700,1));
				Enemies.add(new Enemy(1800,1));
				Enemies.add(new Enemy(1900,1));
			}
			
			K_LEFT = false;
			K_RIGHT = false;
			K_SPACE = false;
			K_GAMEPAUSED = false;
			
			man.restart();
			while(!K_ESC)
			{
				if(K_F1)
				{
					man.dead = true;
					K_F1 = false;
				}
				if(K_LEFT)
				{
					if(K_SHIFT)
						man.walk(-8);
					else
						man.walk(-5);
						
					while(man.getCorner().x < 0)
						man.translate(1);
				}
				
				if(K_RIGHT)
				{
					if(K_SHIFT)
						man.walk(8);
					else
						man.walk(5);
						
					while(man.getCenter().x + man.getXrad() > xpane)
						man.translate(-1);
				}
				
				if(K_FLY && man.getFlying())
				{
					man.fly();
					K_SPACE = false;
				}
				else if(K_SPACE)
				{
					man.jump();
					K_SPACE = false;
				}
				
				if(K_GAMEPAUSED)
				{
					K_GAMEPAUSED = false;
					System.out.println("PAUSE");
					while(!K_GAMEPAUSED && !K_ESC){}
					K_GAMEPAUSED = false;
				}
				
				man.refresh();
				
				for(int e = 0; e < Enemies.size(); e++) //enemies stuff
				{
					Enemies.get(e).refresh();
					if(man.getRect().intersects(Enemies.get(e).getRect()))
					{
						man.attack(Enemies.get(e));
					}
					if(!Enemies.get(e).getAlive())
					{
						Enemies.remove(e);
						e--;
						System.out.println("enemy killed");
					}	
				}		
				
				for(int l = 0; l < Land.size(); l++) //fixPosition of man + enemies
				{				
					if(man.getRect().intersects(Land.get(l).getRect()))
						man.fixPosition(Land.get(l));
					for(int e = 0; e < Enemies.size(); e++)
						if(Enemies.get(e).getRect().intersects(Land.get(l).getRect()))
							Enemies.get(e).fixPosition(Land.get(l));
					if(Land.get(l).getCrumble())
					{
						Land.remove(l);
						l--;
					}
				}
				
				if(moving > 0)
				{
					xplace += moving;
					man.translate(-moving);
					for(int l = 0; l < Land.size(); l++)
						Land.get(l).translate(-moving);
					if(man.getCorner().x < 0)
						man.translate(moving);
					for(Enemy e : Enemies)
						e.translate(-moving);
				}				
				else if(man.getCenter().x > xpane-(xpane/3) && xplace < xfinish) //move terrain right
				{
					xplace += 5;
					man.translate(-5);
					for(int l = 0; l < Land.size(); l++)
						Land.get(l).translate(-5);
					for(Enemy e : Enemies)
						e.translate(-5);
				}
				/*
				else if(man.getCenter().x < 150 && xplace > 0) //move terrain left
				{
					xplace -= 5;
					man.translate(5);
					for(int l = 0; l < Land.size(); l++)
						Land.get(l).translate(5);
					for(Enemy e : Enemies)
						e.translate(-5);
				}*/
				
				if(man.getDead())
				{
					purge();
					draw();
					repaint();
					try{Thread.sleep(3000);}
					catch(Exception e){}
					man.kill();
				}
				if(man.getCorner().x < 0 || man.getCorner().y > ypane) //resets your man
				{
					man.kill();
					while(!K_ESC && xplace > 0)
					{
						xplace -= 5;
						for(int l = 0; l < Land.size(); l++)
							Land.get(l).translate(5);
						for(Enemy e : Enemies)
							e.translate(5);
							
						purge();
						draw();
						repaint();
						
						try{Thread.sleep(wait/10);}
						catch(Exception e){}
					}
					man.restart();
					break;
				}
				
				if(man.getCorner().x + man.getXrad() + xplace >= xfinish)
				{
					level++;
					score += ypane - man.getCenter().y;
					break;
				}
				
				purge();
				draw();
				repaint();
				
				try{Thread.sleep(wait);}
				catch(Exception e){System.out.println("sleeping falied");}
			}
		}
		System.out.println(score);
		this.dispose();
	}
	
	private Panel pane;
	private Graphics2D graphics;
	private Image image;
	private Point center;
	private Toolkit toolkit;
	private BufferedReader in;
	
	private String message;
	private int count;
	Image startup, help, info, back;
	Image[] backs;
	
	public void makeGUI()
	{
		setTitle("Jumpman");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pane = new Panel();
		pane.setPreferredSize(new Dimension(xpane, ypane));
		setContentPane(pane);
		pack();
		setVisible(true);
		this.addKeyListener(this);
		backs = new Image[2];
		try
		{
			for(int i = 0; i < backs.length; i++)
				backs[i] = ImageIO.read(new File("sprite/back" + i + ".jpg"));
		}
		catch (IOException e)
		{
			System.err.println("background image failed to load");
		}
	}
	
	public void setVisible(boolean visible)
	{
		if(graphics == null)
		{
			image = pane.createImage(pane.getSize().width, pane.getSize().height);
			graphics = (Graphics2D)image.getGraphics();
			purge();
		}
		super.setVisible(visible);
	}
	
	public void setVisible(boolean visible, int x, int y)
	{
		if(graphics == null)
		{
			image = pane.createImage(x, y);
			graphics = (Graphics2D)image.getGraphics();
			purge();
		}
		super.setVisible(visible);
	}
	
	public void setSize(int x, int y)
	{
		pane.setPreferredSize(new Dimension(x, y));
	}
	
	public void draw()
	{
		graphics.drawImage(backs[backID],0,0,null);	
		
		graphics.setColor(Color.magenta);
		graphics.fillRect(xfinish-xplace-5,0,10,ypane);
		graphics.setColor(Color.white);
		graphics.drawLine(xfinish-xplace,0,xfinish-xplace,ypane);
		
		for(int l = 0; l < Land.size(); l++)
		{
			graphics.setColor(Land.get(l).getColor());
			graphics.fillRect(Land.get(l).getCorner().x,Land.get(l).getCorner().y,Land.get(l).getXrad()*2,Land.get(l).getYrad()*2);
			if(Land.get(l).getArea() <= 900)
			{
				graphics.setColor(Color.black);
				graphics.drawRect(Land.get(l).getCorner().x,Land.get(l).getCorner().y,Land.get(l).getXrad()*2,Land.get(l).getYrad()*2);
			}
		}		
		
		graphics.setColor(Color.black);
		graphics.drawString("SCORE: " + Game.score, 5, ypane-15);
		
		for(Enemy e : Enemies)
		{			
			graphics.drawImage(e.getBody(),e.getCorner().x,e.getCorner().y,null);
		}
		
		if(K_Q)
			graphics.drawImage(man.getPenis(),man.getCorner().x,man.getCorner().y,null);
		else if(man.getRight())
			graphics.drawImage(man.getBody(),man.getCorner().x,man.getCorner().y,man.getCorner().x+2*man.getXrad(),man.getCorner().y+2*man.getYrad(),41*man.getFrame(),61*man.getRow(),41*man.getFrame()+40,61*man.getRow()+60,null);
		else
			graphics.drawImage(man.getBody(),man.getCorner().x+2*man.getXrad(),man.getCorner().y,man.getCorner().x,man.getCorner().y+2*man.getYrad(),41*man.getFrame(),61*man.getRow(),41*man.getFrame()+40,61*man.getRow()+60,null);
	}
	
	public void purge()
	{
		
	}
	
	private class Panel extends JPanel
	{
		public void paint(Graphics g)
	    {
	        g.drawImage(image, 0, 0, null);
	    }
	}
	
	boolean K_ESC;
	boolean K_INGAME;
	boolean K_GAMEPAUSED;
	boolean K_UP;
	boolean K_DOWN;
	boolean K_LEFT;
	boolean K_RIGHT;
	boolean K_SPACE;
	boolean K_FLY;
	boolean K_ALT;
	boolean K_ANYKEY;
	boolean K_ENTER;
	boolean K_CONTROL;
	boolean K_SHIFT;
	boolean K_BACKSPACE;
	boolean K_S;
	boolean K_Q;
	boolean K_F1;
	boolean K_F2;
	boolean K_F3;
	boolean K_F4;
	boolean K_F5;
	boolean K_F6;
	boolean K_F7;
	boolean K_F8;
	boolean K_F11;
	boolean K_F12;
	String K_string = "";
	
	public void keyPressed(KeyEvent event)
    {  
//    	System.out.println("KeyPressed");
    	int keyCode = event.getKeyCode();
    	int keyLocation = event.getKeyLocation();
        
        if(keyCode == KeyEvent.VK_Q)
        	K_Q = true;
        
        if(keyCode == KeyEvent.VK_F1)
        	K_F1 = true;
        
        if(keyCode == KeyEvent.VK_F2)
        	K_F2 = true;
        
        if(keyCode == KeyEvent.VK_F3)
        	K_F3 = true;
        
        if(keyCode == KeyEvent.VK_F4)
        	K_F4 = true;
        
        if(keyCode == KeyEvent.VK_F5)
        	K_F5 = true;
        
        if(keyCode == KeyEvent.VK_F6)
        	K_F6 = true;
        
        if(keyCode == KeyEvent.VK_F7)
        	K_F7 = true;
        
        if(keyCode == KeyEvent.VK_F8)
        	K_F8 = true;
        
        if(keyCode == KeyEvent.VK_F11)
        	K_GAMEPAUSED = true;
        
        if(keyCode == KeyEvent.VK_F12)
        	K_GAMEPAUSED = false;
        
        if(keyCode == KeyEvent.VK_P)
        	K_GAMEPAUSED = true;
        
        if(keyCode == KeyEvent.VK_S)
        	K_S = !K_S;
        
        if(keyCode == KeyEvent.VK_CONTROL)
        	K_CONTROL = true;
        
        if(keyCode == KeyEvent.VK_SPACE)
        {
        	K_SPACE = true;
        	K_FLY = true;
        }
        
        if(keyCode == KeyEvent.VK_ALT)
        	K_ALT = true;
        
        if(keyCode == KeyEvent.VK_SHIFT)
        	K_SHIFT = true;
                   
        if(keyCode == KeyEvent.VK_UP)
        	K_UP = true;
        	
        if(keyCode == KeyEvent.VK_DOWN)
        	K_DOWN = true;
        	
        if(keyCode == KeyEvent.VK_LEFT)
        	K_LEFT = true;
        	
        if(keyCode == KeyEvent.VK_RIGHT)
        	K_RIGHT = true;
        	
        if(keyCode == KeyEvent.VK_ENTER)
        	K_ENTER = true;
        
        if(keyCode == KeyEvent.VK_BACK_SPACE)
        {
        	K_string = new String("");
        	K_BACKSPACE = true;
        }        
        if(keyCode == KeyEvent.VK_ESCAPE)
        {
        	K_ESC = true;
        	K_ENTER = true;
        }
    }
    
    public void keyReleased(KeyEvent event)
    {
//    	System.out.println("KeyReleased");
    	int keyCode = event.getKeyCode();
    	int keyLocation = event.getKeyLocation();
    	    	
        if(keyCode == KeyEvent.VK_Q)
        	K_Q = false;
        	
        if(keyCode == KeyEvent.VK_UP)
        	K_UP = false;
        	
        if(keyCode == KeyEvent.VK_DOWN)
        	K_DOWN = false;
        	
        if(keyCode == KeyEvent.VK_LEFT)
        	K_LEFT = false;
        	
        if(keyCode == KeyEvent.VK_RIGHT)
        	K_RIGHT = false;
        	        
        if(keyCode == KeyEvent.VK_SHIFT)
        	K_SHIFT = false;
        	
        if(keyCode == KeyEvent.VK_SPACE)
        	K_FLY = false;
    }
    
    public void keyTyped(KeyEvent event)
    {
    	/*
	   	int keyCode = event.getKeyCode();
    	int keyLocation = event.getKeyLocation();

		if(!INGAME)
		{
	    	char c = event.getKeyChar();
	    	if(keyCode == KeyEvent.VK_ENTER)
	    	{}
	      	else if(c != KeyEvent.CHAR_UNDEFINED)
	      	{
		  		s = s + c;
		  		s = s.toUpperCase();
		  		if(BACKSPACE)
		  		{
		  			s = "";
		  			BACKSPACE = false;
		  		}
	      	}
	      	ANYKEY = true;
	      	event.consume();
		}
		*/
    }
}