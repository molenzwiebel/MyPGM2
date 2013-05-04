package nl.thijsmolendijk.MyPGM2.Maps;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MapManager {
	public List<MapData> loadedMaps = new ArrayList<MapData>();
	public MapData currentMap = null;
	private static MapManager instance;
	public static MapManager get() {
		if (instance == null) {
			instance = new MapManager();
			return instance;
		}
		return instance;
	}
	
	public void loadMaps() throws Exception {
		File mapFolder = new File("Maps/");
		if (!mapFolder.exists())
			throw new Exception("[MyPGM2] Failed to find the Maps folder!");
		for (File mapFile : mapFolder.listFiles()) {
			if (!mapFile.isDirectory()) continue;
			System.out.println("[MyPGM2] Found map at: "+mapFile.getAbsolutePath());
			loadedMaps.add(XMLLoader.load(new File(mapFile, "/map.xml"), mapFile.getName()));
		}
		for (MapData m : loadedMaps)
			System.out.println("[MyPGM2] Map loaded: " +m.name +" " + m.version);
	}
	
	public List<String> loadedMapNames() {
		List<String> names = new ArrayList<String>();
		for (MapData d : this.loadedMaps) {
			names.add(d.name+" "+d.version);
		}
		return names;
	}
	
	
	public MapData matchMap(String map) {
		MapData found = null;
        String lowerName = map.toLowerCase();
        int delta = Integer.MAX_VALUE;
        for (MapData player : this.loadedMaps) {
            if (player.name.toLowerCase().startsWith(lowerName)) {
                int curDelta = player.name.length() - lowerName.length();
                if (curDelta < delta) {
                    found = player;
                    delta = curDelta;
                }
                if (curDelta == 0) break;
            }
        }
        return found;
	}
}
