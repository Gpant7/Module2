package module4;

import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.Feature;
import de.fhpotsdam.unfolding.data.GeoJSONReader;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.AbstractShapeMarker;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.MultiMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;
import processing.core.PFont;

/** EarthquakeCityMap
 * An application with an interactive map displaying earthquake data.
 * Author: UC San Diego Intermediate Software Development MOOC team
 * @author Your name here
 * Date: July 17, 2015
 * */
public class EarthquakeCityMap extends PApplet {
	
	// We will use member variables, instead of local variables, to store the data
	// that the setUp and draw methods will need to access (as well as other methods)
	// You will use many of these variables, but the only one you should need to add
	// code to modify is countryQuakes, where you will store the number of earthquakes
	// per country.
	
	// You can ignore this.  It's to get rid of eclipse warnings
	private static final long serialVersionUID = 1L;

	// IF YOU ARE WORKING OFFILINE, change the value of this variable to true
	private static final boolean offline = true;
	
	/** This is where to find the local tiles, for working without an Internet connection */
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	

	//feed with magnitude 2.5+ Earthquakes
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	
	// The files containing city names and info and country names and info
	private String cityFile = "city-data.json";
	private String countryFile = "countries.geo.json";
	
	// The map
	private UnfoldingMap map;
	
	// Markers for each city
	private List<Marker> cityMarkers;
	// Markers for each earthquake
	private List<Marker> quakeMarkers;

	// A List of country markers
	private List<Marker> countryMarkers;
	
	public void setup() {		
		// (1) Initializing canvas and map tiles
		size(900, 700, OPENGL);
		if (offline) {
		    map = new UnfoldingMap(this, 200, 50, 650, 600, new MBTilesMapProvider(mbTilesString));
		    earthquakesURL = "2.5_week.atom";  // The same feed, but saved August 7, 2015
		}
		else {
			map = new UnfoldingMap(this, 200, 50, 650, 600, new Google.GoogleMapProvider());
			// IF YOU WANT TO TEST WITH A LOCAL FILE, uncomment the next line
		    //earthquakesURL = "2.5_week.atom";
		}
		MapUtils.createDefaultEventDispatcher(this, map);
		
		// FOR TESTING: Set earthquakesURL to be one of the testing files by uncommenting
		// one of the lines below.  This will work whether you are online or offline
		//earthquakesURL = "test1.atom";
		//earthquakesURL = "test2.atom";
		
		// WHEN TAKING THIS QUIZ: Uncomment the next line
		earthquakesURL = "quiz1.atom";
		
		
		// (2) Reading in earthquake data and geometric properties
	    //     STEP 1: load country features and markers
		List<Feature> countries = GeoJSONReader.loadData(this, countryFile);
		countryMarkers = MapUtils.createSimpleMarkers(countries);
		/*
		for(Marker oups:countryMarkers)
		{
			System.out.println(oups.toString());
		}
		*/
		
		//     STEP 2: read in city data
		List<Feature> cities = GeoJSONReader.loadData(this, cityFile);
		cityMarkers = new ArrayList<Marker>();
		for(Feature city : cities)
		{
		  cityMarkers.add(new CityMarker(city));
		}
	    
		//     STEP 3: read in earthquake RSS feed
	    List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
	    quakeMarkers = new ArrayList<Marker>();
	    
	    for(PointFeature feature : earthquakes) {
		  //check if LandQuake
		  if(isLand(feature)) {
		    quakeMarkers.add(new LandQuakeMarker(feature));
		  }
		  // OceanQuakes
		  else {
		    quakeMarkers.add(new OceanQuakeMarker(feature));
		  }
	    }

	    //------------- DEBUGGING---------------------------------------------------//
	    // Use the following line in order to debug your code:
	    	
	    		printQuakes(earthquakes);
	    
	    //-------------------------------------------------------------------------//
	 		
	    // (3) Add markers to map
	    //     NOTE: Country markers are not added to the map.  They are used
	    //           for their geometric properties
	    map.addMarkers(quakeMarkers);
	    map.addMarkers(cityMarkers);
	    
	}  // End setup
	
	
	public void draw() {
		background(0);
		map.draw();
		addKey();
		
	}
	
	// helper method to draw key in GUI
	// TODO: Update this method as appropriate
	private void addKey() {	
		// Remember you can use Processing's graphics methods here
		PFont header = createFont("Arial Bold",15);
		PFont text = createFont("Arial",12);
		fill(255, 250, 240);
		rect(25, 50, 150, 300);
		
		fill(0);
		textFont(header);
		textAlign(LEFT,CENTER);
		text("Earthquake Key", 45, 75);
		
		fill(color(255, 0, 0));
		triangle(40,130,60,130,50,112);

		fill(color(255, 255, 255));
		ellipse(50,150,20,20);
		
		fill(color(255, 255, 255));
		rect(40,170,20,20);
				
		fill(color(255, 255, 0));
		ellipse(50,240,20,20);
		fill(color(0,0, 255));
		ellipse(50,270,20,20);
		fill(color(255, 0,0));
		ellipse(50,300,20,20);
		
		line(40,320,60,340);
		line(40,340,60,320);
		
		
		textFont(text);	
		fill(0, 0, 0);
		text("City Marker", 70, 120);
		text("Land Quake", 70, 150);
		text("Ocean Quake", 70, 180);
		text("Size ~ Magnitude", 40, 210);
		
		text("Shallowr", 70, 240);
		text("Intermediate", 70, 270);
		text("Deep", 70, 300);
		text("Past Day", 70, 330);
	}

	
	
	// Checks whether this quake occurred on land.  If it did, it sets the 
	// "country" property of its PointFeature to the country where it occurred
	// and returns true.  Notice that the helper method isInCountry will
	// set this "country" property already.  Otherwise it returns false.
	private boolean isLand(PointFeature earthquake)
	{
		// IMPLEMENT THIS: loop over all countries to check if location is in any of them
		// TODO: Implement this method using the helper method isInCountry
		
		for (Marker mark:countryMarkers)
		{
			if (isInCountry(earthquake, mark))
				return true;
		}
		return false;
	}
	
	// prints countries with number of earthquakes
	// You will want to loop through the country markers or country features
	// (either will work) and then for each country, loop through
	// the quakes to count how many occurred in that country.
	// Recall that the country markers have a "name" property, 
	// And LandQuakeMarkers have a "country" property set.
	private void printQuakes(List<PointFeature> earthquakes) 
	{
		// TODO: Implement this method
		
		float x; 
		float restNumber=0; //total number of earthquakes into the sea
		
		// creation of a new list of countries that have earthquakes with two properties: country name and number of earthquakes
		List<PointFeature> countriesWithEarthquakes = new ArrayList<PointFeature>();
		
		/* If you want to add in the list the element of sea - element indicating the earthquakes in the sea
		   initialize the list with this element using the next 4 lines and one line in the else statement below:
		   
				PointFeature neo = new PointFeature();
				neo.addProperty("country", "Deap Blue Sea");
				neo.addProperty("number", 1.0);
				countriesWithEarthquakes.add(neo);
		*/
			
		for (PointFeature earthquake:earthquakes)
		{
			boolean bool=false;

			//If the earthquake happened in a country,
			if (earthquake.getProperty("country") != null)
			{
				// search through the list,
				for (PointFeature list: countriesWithEarthquakes)
				{
					//until you find an already existing country - that had a previous earthquake.
					if ((earthquake.getProperty("country")) == list.getProperty("country"))
					{
						// Then increase the number of earthquakes in that country and replace it
						x = Float.parseFloat(list.getProperty("number").toString());
						x++; 
						list.putProperty("number", x);
						bool=true;
						break;
					}	  
				}
				// If there was no match - there is no country in the list with the same 
				//location with this earthquake -, create a new element in the list with
				// two properties : name of the country, the number of earthquakes in that
				// country with initialization in 0.
				if (!bool)
				{
					PointFeature neotato = new PointFeature();
					countriesWithEarthquakes.add(neotato);
					neotato.addProperty("country", earthquake.getProperty("country"));
					neotato.addProperty("number", 1.0);
				}
			}
			else
			{	
				//When the earthquake is at the sea
				restNumber++;
				/* If you want to add in the list the element of sea - element indicating the earthquakes in the sea
					countriesWithEarthquakes.get(0).putProperty("number", restNumber); 
				*/
			}
		}
		
		
		/* If you want to see the new list that contains countries being hit by earthquakes
		 and the number of them run the next two lines */
		for(PointFeature list: countriesWithEarthquakes)
			System.out.println(list.getProperties());
		
		System.out.println(" Total Number of Earthquakes : " + earthquakes.size());
		System.out.println(" Total Number of Countries with earthquakes :" +countriesWithEarthquakes.size());
		System.out.println(" Total Number of earthquakes in the sea :" +restNumber);
		
	}
	
	
	
	// helper method to test whether a given earthquake is in a given country
	// This will also add the country property to the properties of the earthquake 
	// feature if it's in one of the countries.
	// You should not have to modify this code
	private boolean isInCountry(PointFeature earthquake, Marker country) {
		// getting location of feature
		Location checkLoc = earthquake.getLocation();

		// some countries represented it as MultiMarker
		// looping over SimplePolygonMarkers which make them up to use isInsideByLoc
		if(country.getClass() == MultiMarker.class) {
				
			// looping over markers making up MultiMarker
			for(Marker marker : ((MultiMarker)country).getMarkers()) {
					
				// checking if inside
				if(((AbstractShapeMarker)marker).isInsideByLocation(checkLoc)) {
					earthquake.addProperty("country", country.getProperty("name"));
						
					// return if is inside one
					return true;
				}
			}
		}
			
		// check if inside country represented by SimplePolygonMarker
		else if(((AbstractShapeMarker)country).isInsideByLocation(checkLoc)) {
			earthquake.addProperty("country", country.getProperty("name"));
			
			return true;
		}
		return false;
	}

	public void mousePressed()
	{
		if (mouseButton == RIGHT)
		{
			map.zoomLevelOut();
		}
	}
}
