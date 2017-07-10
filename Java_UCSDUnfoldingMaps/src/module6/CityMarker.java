package module6;

import java.awt.Font;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PConstants;
import processing.core.PFont;
import processing.core.PGraphics;


/** Implements a visual marker for cities on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * 
 */
public class CityMarker extends CommonMarker {
	
	public static int TRI_SIZE = 5;  // The size of the triangle marker
	
	public CityMarker(Location location) {
		super(location);
	}
	
	
	public CityMarker(Feature city) {
		super(((PointFeature)city).getLocation(), city.getProperties());
		// Cities have properties: "name" (city name), "country" (country name)
		// and "population" (population, in millions)
	}
	
	
	// pg is the graphics object on which you call the graphics
	// methods.  e.g. pg.fill(255, 0, 0) will set the color to red
	// x and y are the center of the object to draw. 
	// They will be used to calculate the coordinates to pass
	// into any shape drawing methods.  
	// e.g. pg.rect(x, y, 10, 10) will draw a 10x10 square
	// whose upper left corner is at position x, y
	/**
	 * Implementation of method to draw marker on the map.
	 */
	public void drawMarker(PGraphics pg, float x, float y) {
		//System.out.println("Drawing a city");
		// Save previous drawing style
		//pg.pushStyle();
		
		//pg.pushStyle();
		//implementation of the population MENU for the cities
		if (clicked)
		{
			
			String total= "Total Number of Quakes : " + getProperty("Number of Quakes").toString();
			String average= "Average Magnitude of Quakes : " + getProperty("Average Magnitude").toString();
			String startRecent="Recent Quakes : ";
			int startWidth= (int)pg.textWidth("Recent Quakes : ");
			String recent= getProperty("Recent Quakes").toString();
			int length=recent.length();
			//delete the [] from the earthquakes
			recent=recent.substring(1, length-1);
			length=length-2;
			
			float width = pg.textWidth(recent)+2;
			float start=x;
			String str=null;
			//find the number of chars that fit in the screen
			float analogy = ("1234".substring(0, 2)).length() / (pg.textWidth("12"));
			float numOfchars= 600*analogy;
			
			float maxWidth=width+startWidth;
			//System.out.println(("1234".substring(0, 2)).length());
			//System.out.println(pg.textWidth("12"));

			if (maxWidth < pg.textWidth(average))
				maxWidth=pg.textWidth(average);
			if (maxWidth < pg.textWidth(recent))
				maxWidth=pg.textWidth(recent);
			
			float width1=maxWidth;
			//checking the borders of the menu
			if ( (x+width) > 600 )
			{
				if (width<600)
				{
					width1=width+startWidth;
					start = 600-width-startWidth;
				}
				else
				{
					width1=600;
					start=4;
				}
			}
			
			int i=(int) (length/numOfchars) ;
			pg.fill(255,255,255);
			pg.rect(start, y, width1+6, 70+(i+1)*20);
			
			i=0;
			if (width>600)
			{
				while ( (recent!=null) && (length>0))
				{
					i++;
					if (length>numOfchars)
					{
						str=recent.substring(0, (int)numOfchars);
						recent=recent.substring((int)numOfchars, length);
						length=length-(int)numOfchars;
						pg.fill(0,0,0);
						pg.text(str,start+4+startWidth,y+50+i*20);
						//pg.rect(0, 100, 100, 100);
						//System.out.println("AAAAAA");
					}
					else
					{	
						pg.text(recent,start+4+startWidth,y+50+i*20);
						break;
					}
					//System.out.println(i);
				}
			}
			else
			{
				pg.fill(0,0,0);
				pg.text(recent,start+4+startWidth,y+70);
			}
	
			pg.fill(0,0,0);
			pg.textAlign(PConstants.LEFT, PConstants.TOP);
			
			//PFont myFont=new PFont(); 
			//pg.textFont(font);
			//pg.colorMode(100,100f);
	
			
			pg.textSize(20);
			pg.text(getCity(),start+30,y+5);
			pg.textSize(12);
			pg.text(total,start+4,y+30);
			pg.text(average,start+4,y+50);
			pg.text(startRecent,start+4,y+70);	
			
			pg.beginDraw();
			if (selected)
				showTitle(pg,x,y);
			pg.fill(150, 30, 30);
			pg.triangle(x, y-TRI_SIZE, x-TRI_SIZE, y+TRI_SIZE, x+TRI_SIZE, y+TRI_SIZE);
			
			pg.endDraw();
		}
		else
		{
			pg.beginDraw();
			if (selected)
				showTitle(pg,x,y);
			// IMPLEMENT: drawing triangle for each city
			pg.fill(150, 30, 30);
			pg.triangle(x, y-TRI_SIZE, x-TRI_SIZE, y+TRI_SIZE, x+TRI_SIZE, y+TRI_SIZE);
			pg.endDraw();
		}	
		
			// Restore previous drawing style
		//pg.popStyle();
	}
	

	/** Show the title of the city if this marker is selected */
	public void showTitle(PGraphics pg, float x, float y)
	{
		String name = getCity() + " " + getCountry() + " ";
		String pop = "Pop: " + getPopulation() + " Million";
		
		pg.pushStyle();
		
		pg.fill(255, 255, 255);
		pg.textSize(12);
		pg.rectMode(PConstants.CORNER);
		pg.rect(x, y-TRI_SIZE-39, Math.max(pg.textWidth(name), pg.textWidth(pop)) + 6, 39);
		pg.fill(0, 0, 0);
		pg.textAlign(PConstants.LEFT, PConstants.TOP);
		pg.text(name, x+3, y-TRI_SIZE-33);
		pg.text(pop, x+3, y - TRI_SIZE -18);
		
		pg.popStyle();
	}
	
	private String getCity()
	{
		return getStringProperty("name");
	}
	
	private String getCountry()
	{
		return getStringProperty("country");
	}
	
	private float getPopulation()
	{
		return Float.parseFloat(getStringProperty("population"));
	}
	
}
