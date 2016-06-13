import java.awt.*;

public class Block extends Bound
{
	int hits, id;
	boolean crumble;
	
	public Block(int x, int y, int h, int i)
	{
		crumble = false;
		solid = true;
		center = new Point(x,y);
		hits = h;
		if(hits == 0)
		{
			crumble = true;
			hits = 1;
		}
		id = i;
		xrad = 20;
		yrad = xrad;
		corner = new Point(center.x-xrad,center.y-yrad);
		rect = new Rectangle(corner.x,corner.y,2*xrad,2*yrad);
		color = Color.pink;
	}
	
	public void hit(Man m)	//override
	{
		Game.score += 10;
		hits--;
		if(hits <= 0)
			color = Color.red;
		if(id == 10)	//jump powerup
			m.setJumplimit(m.getJumplimit()+1);
		if(id == 11)	//flying
			m.setFlying(true);
	}
	
	public boolean getCrumble()	//override
	{
		return crumble && hits <= 0;
	}
}