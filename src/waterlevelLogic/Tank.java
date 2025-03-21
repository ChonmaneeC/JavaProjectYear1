package waterlevelLogic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;


public class Tank extends BackGround 
{
    double volume = 0;
    int MAX_VOLUME = 100;
    Color waterColor = Color.CYAN;
    
    public Tank() 
    {
        setPreferredSize(new Dimension(540, 500));
    }
    
    
    public void setVolume(double volume) 
    { 
	    if (this.volume<=100)
	     {
	    	this.volume = volume;
	     }
	     else
	     {
	    	this.volume = 100;
	     }
     }
     
    public double getVolume() 
    {
    	return volume;
    }
     
    public void setWaterColor(Color color) 
    {
    	this.waterColor = color;
    	repaint();
    }
    
    public void addVolume(double volume){}
    
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.drawOval(70,10,400,20);
        
        g.setColor(Color.BLACK);
        int[] a = {70,70,470,470};
        int[] b = {20,500,500,20};
        g.drawPolyline(a, b, 4);
        
        int height = (int) (470 * ((double) volume / 100));
        g.setColor(waterColor);
        g.fillRect(70, 500 - height, 400, height);
    }
}