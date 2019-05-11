
public class Plane 
{
	int xO;
	int yO;
	private double rad;
	double mgt;
	double x;
	double y;

	int xEnd1;
	int yEnd1;
	int xEnd2;
	int yEnd2;
	
	public Plane (int xO, int yO, int l1, int l2, double rad, double mgt)
	{
		this.xO = xO;
		this.yO = yO;
		this.mgt = mgt;
		this.rad = rad;
		locate();
	}
	
	
	public Plane (int x1, int y1, int x2, int y2)
	{
		xO = (x1 + x2) / 2;
		yO = (y1 + y2) / 2;
		mgt = 0;
		if (x2 < x1)
			rad = Math.atan((double)-(y2 - y1) / (x2 - x1)) + Math.PI / 2;
		else if (x2 > x1)
			rad = Math.atan((double)-(y2 - y1) / (x2 - x1)) + Math.PI / 2 * 3;
		else if (y2 > y1)
			rad = Math.PI / 2 + Math.PI / 2;
		else
			rad = Math.PI / 2 + Math.PI / 2 * 3;
		locate();
	}
	
	public String toString ()
	{
		return "x:" + x + " y:" + y + " rad:" + rad;
	}
	

	
	public void locate () //frame de kalmasını sağlıyor
	{
		x = -Math.cos(rad);
		y = -Math.sin(rad);
		
}

}