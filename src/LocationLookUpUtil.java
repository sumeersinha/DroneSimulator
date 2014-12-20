import java.util.HashMap;
import java.util.Map;

/**
 * 
 */

/**
 * @author sumeersinha
 *
 */
public class LocationLookUpUtil {

	public static Map<String, DestinationCoordinates> map;
	
	static{
		map=new HashMap<String,DestinationCoordinates>();
		map.put("ORD", new DestinationCoordinates(620, 310));
		map.put("DEN", new DestinationCoordinates(370, 380));
		map.put("JFK", new DestinationCoordinates(800, 350));
		map.put("LAS", new DestinationCoordinates(210, 370));
		map.put("MIN", new DestinationCoordinates(570, 280));
		map.put("DFW", new DestinationCoordinates(500, 490));
		map.put("LAX", new DestinationCoordinates(150, 400));
		map.put("PHX", new DestinationCoordinates( 260, 420));
		map.put("ATL", new DestinationCoordinates(630, 400));
		map.put("HOU", new DestinationCoordinates(500, 540));
		map.put("MCO", new DestinationCoordinates( 740, 550));
		//map.put("HOME", new DestinationCoordinates(193,143));
	}

	public static String[] places(){
		String places[]=new String[map.size()];
		int counter=0;
		for(String place:map.keySet()){
			places[counter]=place;
			counter++;
		}
		return places;
	}
	
}
