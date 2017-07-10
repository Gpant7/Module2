package module5;

import java.util.List;

import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import processing.core.PGraphics;

/** Implements a visual marker for ocean earthquakes on an earthquake map
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 *
 */

public class OceanQuakeMarker extends EarthquakeMarker {
	
	public OceanQuakeMarker(PointFeature quake) {
		super(quake);
		
		// setting field in earthquake marker
		isOnLand = false;
	}
	

	/** Draw the earthquake as a square */
	@SuppressWarnings("unchecked")
	@Override
	public void drawEarthquake(PGraphics pg, float x, float y) 
	{
		float x1,y1;
		List<ScreenPosition> positions;
		positions= (List<ScreenPosition>)getProperty("positions");
		pg.rect(x-radius, y-radius, 2*radius, 2*radius);
		
		if ((clicked) && (positions != null) )
		{
			//System.out.println(positions);
			//System.out.println(x + " " + y );

			for(ScreenPosition thesis:positions)
			{
				//System.out.println(thesis);
				x1=thesis.x;
				y1=thesis.y;
				pg.line(x,y,x1-200,y1-50);	
			}
		}
		else
			pg.noStroke();		
	}
	

	

}
