package module3;

//Java utilities libraries
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.Collections;
//import java.util.Comparator;
import java.util.List;







//Processing library
import processing.core.PApplet;

//Unfolding libraries
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

//Parsing library
import parsing.ParseFeed;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {

	// You can ignore this.  It's to keep eclipse from generating a warning.
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFLINE, change the value of this variable to true
	private static final boolean offline = true;
	
	// Less than this threshold is a light earthquake
	public static final float THRESHOLD_MODERATE = 5;
	// Less than this threshold is a minor earthquake
	public static final float THRESHOLD_LIGHT = 4;

	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	// The map
	private UnfoldingMap map;
	
	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";

	
	public void setup() {
		size(950, 600, OPENGL);

		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 700, 500, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom"; 	// Same feed, saved Aug 7, 2015, for working offline
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 700, 500, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
			//earthquakesURL = "2.5_week.atom";
		}
		
	    map.zoomToLevel(2);
	    MapUtils.createDefaultEventDispatcher(this, map);	
			
	    // The List you will populate with new SimplePointMarkers
	    List<SimplePointMarker> markers = new ArrayList<SimplePointMarker>();

	    //Use provided parser to collect properties for each earthquake
	    //PointFeatures have a getLocation method
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    
	    // Here is an example of how to use Processing's color method to generate 
	    // an int that represents the color yellow.  
	    int yellow = color(255, 255, 0);
	    System.out.println(yellow);
	    int red = color(255, 0, 0);
	    System.out.println(red);
	    int blue = color(0, 0, 255);
	    System.out.println(blue);
	    float big = (float) 13;
	    float medium = (float) 8;
	    float small = (float) 5;
	    //TODO: Add code here as appropriate

	    for (int i=0; i < earthquakes.size() ; i++)
	    {
	    	//create SimplePointMarkers for each PointFeature 
	    	PointFeature feat = earthquakes.get(i);
	    	SimplePointMarker marks = createMarker(feat);
	    	
	    	//Correlation between the two lists
	    	//System.out.println(marks.getProperties());
	    	markers.add(marks);
	    	//System.out.println(markers.hashCode());
	    	
	    	//convert and check the value of magnitude 
	    	Object magObj = marks.getProperty("magnitude");
	    	float mag = Float.parseFloat(magObj.toString());
	    	if (mag<4.0)
	    	{
	    		marks.setColor(blue);
	    		marks.setRadius(small);
	    	}
	    	else if (mag>=4.0 && mag<=4.9)
	    	{
	    		marks.setColor(yellow);
	    		marks.setRadius(medium);
	    	}
	    	else
	    	{
	    		marks.setColor(red);
	    		marks.setRadius(big);
	    	}
	    	
	    	map.addMarker(marks);	    	
	    	//System.out.println(g.getLocation());
	    };
	
	    for (SimplePointMarker oups:markers)
	   {
	    	System.out.println(oups.getProperties());
	    	//System.out.println(markers.hashCode());
	   };
	  
	 
	}
		
	// A suggested helper method that takes in an earthquake feature and 
	// returns a SimplePointMarker for that earthquake
	// TODO: Implement this method and call it from setUp, if it helps
	private SimplePointMarker createMarker(PointFeature feature)
	{
		// finish implementing and use this method, if it helps.

		Location loc = feature.getLocation();
		HashMap<String,Object> prop = feature.getProperties();
		SimplePointMarker back = new SimplePointMarker(loc,prop);
		return back;
	}
	
	public void draw() {
	    background(10);
	    map.draw();
	    addKey();
	}


	// helper method to draw key in GUI
	// TODO: Implement this method to draw the key
	private void addKey() 
	{	
		//create the box and paint it grey
		fill(250,250,250);
		rect(20,50,160,250);
		
		//create the text : the circles first and the words after
		fill(0);
		textSize(20);
		text("Earthquake Key",27, 90);
		
		fill(250,0,0);
		ellipse(40,130,20,20);
		fill(0);
		textSize(10);
		text("5.0 + Magnitude ",70,130);
		
		fill(0,0,250);
		ellipse(40,150,15,15);
		fill(0);
		text("4.0 + Magnitude",70,150);
		
		fill(250,250,0);
		ellipse(40,170,10,10);
		fill(0);
		text("Below 4.0",70,170);
		// Remember you can use Processing's graphics methods here
	}
	
	//this method makes the zoom out when pressing the right mouse button
	public void mousePressed()
	{
		if (mouseButton == RIGHT)
		{
			map.zoomLevelOut();
		}
	}
}
