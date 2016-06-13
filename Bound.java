import java.awt.*;

public class Bound extends Body
{
	Point corner;
	boolean solid, hurt;
	
	public Bound()
	{
	}
	
	public Bound(int x, int y, int w, int h, int id)
	{
		corner = new Point(x,y);
		rect = new Rectangle(x,y,w,h);
		xrad = w/2;
		yrad = h/2;
		center = new Point(x+xrad,y+yrad);
		solid = true;
		hurt = false;
		if(id == 1)	//landscape
		{
			color = Color.green;
		}
		if(id == 2)	//platforms
		{
			solid = false;
			color = Color.blue;
		}
		if(id == 3)	//fire blocks
		{
			hurt = true;
			color = Color.red;
		}
	}
	
	public Rectangle getRect() //override
	{
		return rect;
	}
	
	public Point getCorner() //override
	{
		return corner;
	}
	
	public boolean getSolid()
	{
		return solid;
	}
	
	public boolean getHurt()
	{
		return hurt;
	}
	
	public void translate(int i) //override
	{
		super.translate(i);
		corner.x += i;
		rect = new Rectangle(corner.x,corner.y,2*xrad,2*yrad);
	}
	
	public void hit(Man m)
	{
		
	}
	
	public boolean getCrumble()
	{
		return false;
	}
}