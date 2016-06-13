import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.util.*;

public class Man extends Body
{
	boolean jumping, dead, flying, Super, is_right;
	int jumps, jumplimit, lives, count, turn;
	int row;
	Image p, man;
	
	public Man()
	{
		dead = false;
		Super = false;
		lives = 3;
		frame = 0;
		xrad = 20;
		yrad = 30;
		jumplimit = 1;
		count = 0;
		turn = 0;
		try
		{
			body = ImageIO.read(new File("sprite/man.png"));
			p = ImageIO.read(new File("sprite/p.png"));
		}
		catch (IOException e){System.err.println("man init failed");}
	}
	
	public void restart()
	{
		ydir = 0;
		center = new Point(150,100);
		jumps = 1;
	}
	
	public void kill()
	{
		center = new Point(-100,900);
		lives--;
		jumplimit = 1;
		flying = false;
		dead = false;		
	}
	
	public void jump()
	{
		if(jumps < jumplimit)
		{
			jumps++;
			ydir = -20;
		//	if(Keyboard.UP)
		//		ydir-=2;
		}
	}
	
	public void walk(int x)
	{
		xdir = x;
		center.x += x;		
	}
	
	public void refresh()
	{
		if(turn == 0)
		{
			count++;
		}
		turn++;
		if(count > 5)
		{
			count = 0;
		}
		if(turn == 2)
		{
			turn = 0;
		}
		center.y += ydir;
		if(ydir < 15) //max falling speed
			ydir++;
		if(ydir > 2 && jumps < 1)
			jumps = 1;
		
		frame = 0;
		if(Math.abs(xdir)>5)
		{
			frame = 1;
			turn = 0;
		}
		if(jumps > 0)
		{
			frame = 2;
			if(xdir == 0 && ydir < 0)
				frame = 1;
		}
		
		if(xdir == 0)
			row = 0;
		else if(xdir > 0)
		{
			row = 1;
			frame = frame * 6 + count;
            is_right = true;
		}
		else
		{
			row = 1;
			frame = frame * 6 + count;
            is_right = false;
		}
		xdir = 0;
	}
	
	public boolean getRight()
	{
		return is_right;
	}
	
	public int getFrame()
	{
		return frame;
	}
	
	public int getRow()
	{
		return row;
	}
	
	public Image getPenis()
	{
		return p;
	}
	
	public void setYdir(int i)
	{
		ydir = i;
	}
	
	public void fly()
	{
		jumps = jumplimit;
		if(ydir > -20)
			ydir -= 2;
	}
	
	public void setFlying(boolean b)
	{
		flying = b;
	}
	
	public boolean getFlying()
	{
		return flying;
	}
	
	public void setJumplimit(int i)
	{
		jumplimit = i;
	}
	
	public int getJumplimit()
	{
		return jumplimit;
	}
	
	public boolean getDead()
	{
		return dead;
	}
	
	public boolean getSuper()
	{
		return Super;
	}
	
	public void setBottom(int y)
	{
		center.y = y-yrad;
		ydir = 0;
		jumping = false;
	}
	
	public Rectangle getRect()
	{
		return new Rectangle(getCorner().x,getCorner().y,xrad*2,yrad*2);
	}
	
	public void fixPosition(Bound b)
	{
		Rectangle overlap = this.getRect().intersection(b.getRect());
		if(b.getHurt())
			dead = true;
		else if(b.getSolid())
		{
			if(overlap.getWidth() > overlap.getHeight())
			{
				if(center.y + yrad - b.getCorner().y < b.getBottom() - center.y - yrad)
				{
					center.y = b.getCorner().y - yrad;	//push up
					ydir = 0;
					jumps = 0;
				}
				else if(ydir < 0)
				{
					center.y = b.getBottom() + yrad; //push down
					ydir = 0;
					b.hit(this);
				}
			}
			else
			{
				Rectangle temp = this.getRect();
				temp.translate(0,-ydir);
				if(b.getRect().intersects(temp))
				{
					if(b.getCorner().x > this.getCorner().x)
					{
						center.x = b.getCorner().x - xrad;	//push left
					}
					else
					{
						center.x = b.getCorner().x + xrad + 2*b.getXrad(); //push right
					}
				}
				else						
				{
					center.y = b.getCorner().y - yrad;	//push up
					ydir = 0;
					jumps = 0;
				}				
			}
		}
		else if(ydir > 0 && center.y + yrad < b.getBottom())
		{
			center.y = b.getCorner().y - yrad;	//push up
			ydir = 0;
			jumps = 0;
		}
	}
	
	public void attack(Enemy e)
	{
		if(this.getSuper())
			e.damage();
		else if(e.getSuper())
			dead = true;
		else
		{
			Rectangle overlap = this.getRect().intersection(e.getRect());
			if(overlap.getWidth() > overlap.getHeight())
			{
				if(center.y + yrad - e.getCorner().y < e.getBottom() - center.y - yrad)
				{
					center.y = e.getCorner().y - yrad;	//push up
					ydir = -5;
					jumps = 0;
					e.damage();
				}
				else if(ydir < 0)
				{
					center.y = e.getBottom() + yrad; //push down
					ydir = 0;
					dead = true;
				}
			}
			else
			{
				Rectangle temp = this.getRect();
				temp.translate(0,-ydir);
				if(e.getRect().intersects(temp))
				{
					if(e.getCorner().x > this.getCorner().x)
					{
						center.x = e.getCorner().x - xrad;	//push left
						dead = true;
					}
					else
					{
						center.x = e.getCorner().x + xrad + 2*e.getXrad(); //push right
						dead = true;
					}
				}
				else						
				{
					center.y = e.getCorner().y - yrad;	//push up
					ydir = -5;
					jumps = 0;
					e.damage();
				}				
			}
		}
		/*
		int head = e.getCorner().y;
		int foot = this.getCenter().y + yrad;
		if(foot > head && foot - ydir < head)
		{
			e.damage();
			ydir = -4;			
		}
		else
			dead = true;
		*/
	}
}