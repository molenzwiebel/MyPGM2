package nl.thijsmolendijk.MyPGM2.Maps;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import nl.thijsmolendijk.MyPGM2.Tools;
import nl.thijsmolendijk.MyPGM2.Maps.Kits.Kit;
import nl.thijsmolendijk.MyPGM2.Maps.Kits.KitUtils;
import nl.thijsmolendijk.MyPGM2.Maps.Regions.RegionUtils;
import nl.thijsmolendijk.MyPGM2.Teams.Team;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLLoader {
	
	public static MapData load(File f, String fileLocation) throws Exception {
		MapData data = new MapData();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(f);
		Element main = (Element) doc.getElementsByTagName("map").item(0);
		data.fileLocation = fileLocation;
		System.out.println(fileLocation);
		data.name = main.getElementsByTagName("name").item(0).getTextContent();
		data.objective = main.getElementsByTagName("objective").item(0).getTextContent();
		data.version = main.getElementsByTagName("version").item(0).getTextContent();
		data = loadAuthors(main, data);
		data = loadContributors(main, data);
		data = loadTeams(main, data);
		data = loadKits(main, data);
		data = loadSpawns(main, data);
		data = loadRegions(main, data);
		return data;
	}

	private static MapData loadAuthors(Element main, MapData d) {
		NodeList children = main.getElementsByTagName("author");
		d.authors.clear();
		for (int i = 0; i < children.getLength(); i++) {
			Node n = children.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE && n.getParentNode().getNodeName().equals("authors")) {
				Element e = (Element) n;
				d.authors.put(e.getTextContent(), e.getAttribute("contribution"));
			}
		}
		return d;
	}

	private static MapData loadContributors(Element main, MapData d) {
		NodeList children = main.getElementsByTagName("contributor");
		d.contributors.clear();
		for (int i = 0; i < children.getLength(); i++) {
			Node n = children.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE && n.getParentNode().getNodeName().equals("contributors")) {
				Element e = (Element) n;
				d.contributors.put(e.getTextContent(), e.getAttribute("contribution"));
			}
		}
		return d;
	}
	
	private static MapData loadTeams(Element main, MapData d) {
		Team obs = new Team();
		obs.color = ChatColor.AQUA;
		obs.maxPlayers = Integer.MAX_VALUE;
		obs.name = "Observers";
		obs.isSpectating = true;
		d.teamManager.addTeam(obs);
		NodeList children = ((Element) main.getElementsByTagName("teams").item(0)).getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node n = children.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals("team")) {
				Element e = (Element) n;
				ChatColor c = Tools.matchColor(e.getAttribute("color"));
				int maxPlayers = Integer.parseInt(e.getAttribute("max"));
				String name = n.getTextContent();
				Team t = new Team();
				t.color = c;
				t.maxPlayers = maxPlayers;
				t.name = name;
				t.isSpectating = false;
				d.teamManager.addTeam(t);
			}
		}
		return d;
	}
	
	private static MapData loadSpawns(Element main, MapData d) {
		NodeList children = ((Element) main.getElementsByTagName("spawns").item(0)).getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node n = children.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals("spawn")) {
				Element e = (Element) n;
				Team t = d.teamManager.teamForName(e.getAttribute("team"));
				//For now, just get a point location
				if (e.getElementsByTagName("point").getLength() < 1)
					return d;
				Element point = (Element) e.getElementsByTagName("point").item(0);
				int yaw = Integer.parseInt(e.getAttribute("yaw"));
				int x = Integer.parseInt(point.getTextContent().split(",")[0]);
				int y = Integer.parseInt(point.getTextContent().split(",")[1]);
				int z = Integer.parseInt(point.getTextContent().split(",")[2]);
				Location spawn = new Location(Bukkit.getWorld(d.name+"_COPY"), x, y, z);
				spawn.setYaw(yaw);
				t.spawn = spawn;
			}
		}
		return d;
	}
	
	private static MapData loadRegions(Element m, MapData d) {
		Element main = (Element) m.getElementsByTagName("regions").item(0);
		if (main == null) return d;
		for (int i = 0; i < main.getChildNodes().getLength(); i++) {
			Node n = main.getChildNodes().item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				if (!RegionUtils.isValidRegionTag(e))
					continue;
				d.regions.put(e.getAttribute("name"), RegionUtils.parseRegion(e, false));
			}
		}
		return d;
	}
	
	private static MapData loadKits(Element m, MapData d) {
		if (m.getElementsByTagName("kits").getLength() < 1)
			return d;
		NodeList children = ((Element) m.getElementsByTagName("kits").item(0)).getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			Node n = children.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE && n.getNodeName().equals("kit")) {
				Element e = (Element) n;
				Kit parent = null;
				if (e.hasAttribute("parents"))
					parent = d.kits.get(e.getAttribute("parents"));
				d.kits.put(e.getAttribute("name"), KitUtils.parseKit(e, parent));
			}
		}
		return d;
	}
}
