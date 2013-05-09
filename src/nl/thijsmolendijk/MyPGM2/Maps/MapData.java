package nl.thijsmolendijk.MyPGM2.Maps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import nl.thijsmolendijk.MyPGM2.Maps.Kits.Kit;
import nl.thijsmolendijk.MyPGM2.Maps.Regions.IRegion;
import nl.thijsmolendijk.MyPGM2.Teams.TeamManager;

public class MapData {
	public String name;
	public String version;
	public String objective;
	public String fileLocation;
	public HashMap<String, IRegion> regions = new HashMap<String, IRegion>();
	public TeamManager teamManager = new TeamManager();
	public HashMap<String, String> authors = new HashMap<String, String>();
	public HashMap<String, String> contributors = new HashMap<String, String>();
	public HashMap<String, Kit> kits = new HashMap<String, Kit>();
	
	public List<String> getAuthors() {
		List<String> toReturn = new ArrayList<String>();
		for (int i = 0; i < authors.size(); i++)
			toReturn.add((String) authors.keySet().toArray()[i]);
		return toReturn;
	}
}
