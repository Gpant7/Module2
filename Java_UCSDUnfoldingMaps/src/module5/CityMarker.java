package module5;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.geom.Rectangle2D;

import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PGraphics;

/** Implements a visual marker for cities on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 *
 */
// TODO: Change SimplePointMarker to CommonMarker as the very first thing you do 
// in module 5 (i.e. CityMarker extends CommonMarker).  It will cause an error.
// That's what's expected.
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

	
	/**
	 * Implementation of method to draw marker on the map.
	 */
	public void drawMarker(PGraphics pg, float x, float y) {
		// Save previous drawing style
		pg.pushStyle();
		
		// IMPLEMENT: drawing triangle for each city
		pg.fill(150, 30, 30);
		pg.triangle(x, y-TRI_SIZE, x-TRI_SIZE, y+TRI_SIZE, x+TRI_SIZE, y+TRI_SIZE);
		
		
		// Restore previous drawing style
		pg.popStyle();
	}
	
	/** Show the title of the city if this marker is selected */
	public void showTitle(PGraphics pg, float x, float y)
	{
		// TODO: Implement this method
		//System.out.println("333333333333333");
		pg.textSize(20);
		
		Font font = new Font("Verdana", Font.PLAIN, 20);  
		FontMetrics metrics = new FontMetrics(font)
		{
			private static final long serialVersionUID = 1L; 
		};  
	    Rectangle2D bounds1 = metrics.getStringBounds(getCity().toString(), null);
	    Rectangle2D bounds2= metrics.getStringBounds(getCountry().toString(), null);  
	    Rectangle2D bounds3= metrics.getStringBounds(String.valueOf(getPopulation()), null);  
	    int widthInPixels = (int) (bounds1.getWidth() + bounds2.getWidth() + bounds3.getWidth() + 4) ;
	    
	    if (widthInPixels+x>650)
		{
	    	pg.fill(255,255,255);
			pg.rect(650-widthInPixels, y-20, widthInPixels+30, 25);
			pg.fill(0,0,0);
			pg.text(getCity(),650-widthInPixels,y);
			pg.text(getCountry(), 650-widthInPixels + (int)bounds1.getWidth() + 2, y);
			pg.text(getPopulation(), 650-widthInPixels + (int)bounds1.getWidth() + (int)bounds2.getWidth() + 4, y);
		}
		else
		{
			pg.fill(255,255,255);
			pg.rect(x, y-20, widthInPixels+30, 25);
			pg.fill(0,0,0);
			pg.text(getCity(),x,y);
			pg.text(getCountry(), x + (int)bounds1.getWidth() + 2, y);
			pg.text(getPopulation(), x + (int)bounds1.getWidth() + (int)bounds2.getWidth() + 4, y);
		}
	    
		//System.out.println(getCity());
	}
	
	
	
	/* Local getters for some city properties.  
	 */
	public String getCity()
	{
		return getStringProperty("name");
	}
	
	public String getCountry()
	{
		return getStringProperty("country");
	}
	
	public float getPopulation()
	{
		return Float.parseFloat(getStringProperty("population"));
	}

}
