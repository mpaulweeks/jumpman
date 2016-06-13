import java.awt.*;
import javax.imageio.ImageIO;
import java.util.*;

public class Body
{
	Rectangle rect;
	Point center;
	Image body;
	int frame, xrad, yrad, xdir, ydir;
	Color color;
	Image[] stand, left, right;
		
	public Image getBody()
	{
		return body;
	}
	
	public Point getCenter()
	{
		return center;
	}
	
	public Point getCorner()
	{
		return new Point(center.x-xrad,center.y-yrad);
	}
	
	public int getBottom()
	{
		return center.y+yrad;
	}
	
	public int getXrad()
	{
		return xrad;
	}
	
	public int getYrad()
	{
		return yrad;
	}
	
	public void translate(int i)
	{
		center.x += i;
	}
	
	public Color getColor()
	{
		return color;
	}
	
	public int getArea()
	{
		return 4*xrad*yrad;
	}
}