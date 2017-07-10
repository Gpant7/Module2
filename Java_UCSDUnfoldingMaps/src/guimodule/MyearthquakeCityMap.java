package guimodule;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class MyearthquakeCityMap extends PApplet
{
	private UnfoldingMap map;
	
	public void setup()
	{
		size(950, 600, OPENGL);
		map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
		map.zoomToLevel(2);
		MapUtils.createDefaultEventDispatcher(this, map);
		
		Location valloc = new Location(38.14f,-73.03f);
		Feature valEq = new PointFeature(valloc);
		valEq.addProperty("title", "Valdivia, Chile");
		valEq.addProperty("magnitude", "9.5");
		
		Marker valMk = new SimplePointMarker(valloc, valEq.getProperties());
		map.addMarker(valMk);
		
		
	}
	
	public void draw()
	{
		background(10);
		map.draw();
		//addKey();
	}

}
