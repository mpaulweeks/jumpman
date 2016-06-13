import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.util.*;

public class Enemy extends Man
{
	boolean walker, jumper;
	int ID, life;
	
	public Enemy(int x, int id)
	{
		Super = false;
		jumplimit = 1;
		center = new Point(x,0);
		xrad = 20;
		yrad = 20;
		life = 1;
		left = new Image[1];
		right = new Image[1];
		if(id == 1)
		{
			walker = true;
			xdir = 2;
			jumper = true;
		}
		try
		{
			for(int i = 0; i < left.length; i++)
			{
				left[i] = ImageIO.read(new File("sprite/enemy" + id + "l" + i + ".png"));
				right[i] = ImageIO.read(new File("sprite/enemy" + id + "r" + i + ".png"));
			}
		}
		catch (IOException e){System.err.println("enemy init failed");}
		body = left[0];
	}
	
	public void refresh() //override
	{
		center.y += ydir;
		if(ydir < 15) //max falling speed
			ydir++;
		if(ydir > 2 && jumps < 1)
			jumps = 1;
		if(jumper && jumps == 0)
			jump();
		if(walker)
		{
			walk(xdir);
			if(xdir > 0)
				body = right[0];
			else
				body = left[0];
		}
	}
	
	public void jump() //override
	{
		if(jumps < jumplimit)
		{
			jumps++;
			ydir = -10;
		}
	}
	
	public void damage()
	{
		life--;
	}
	
	public boolean getAlive()
	{
		return life > 0;
	}
	
	public void fixPosition(Bound b) //override
	{
		Rectangle overlap = this.getRect().intersection(b.getRect());
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
					xdir *= -1;
				}
				else
				{
					center.x = b.getCorner().x + xrad + 2*b.getXrad(); //push right
					xdir *= -1;
				}
			}
			else						
			{
				center.y = b.getCorner().y - yrad;	//push up
				ydir = 0;
				jumps = 0;
			}				
		}
		
		if(Math.abs(b.getCorner().x-this.getCenter().x) < Math.abs(xdir)+1 || Math.abs(b.getCorner().x+2*b.getXrad()-this.getCenter().x) < Math.abs(xdir)+1)
			xdir *= -1;			
	}
}