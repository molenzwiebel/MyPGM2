package nl.thijsmolendijk.MyPGM2.Maps.Regions;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RegionUtils {
	public static boolean isValidRegionTag(Node tag) {
		if (tag.getNodeType() != Node.ELEMENT_NODE)
			return false;
		Element e = (Element) tag;
		if (e.getNodeName().equals("cuboid")) {
			if (e.hasAttribute("min") && e.hasAttribute("max") && e.hasAttribute("name"))
				return true;
			return false;
		}
		if (e.getNodeName().equals("rectangle")) {
			if (e.hasAttribute("min") && e.hasAttribute("max") && e.hasAttribute("name"))
				return true;
			return false;
		}
		if (e.getNodeName().equals("cylinder")) {
			if (e.hasAttribute("base") && e.hasAttribute("radius") && e.hasAttribute("name") && e.hasAttribute("height"))
				return true;
			return false;
		}
		if (e.getNodeName().equals("circle")) {
			if (e.hasAttribute("center") && e.hasAttribute("radius") && e.hasAttribute("name"))
				return true;
			return false;
		}
		if (e.getNodeName().equals("sphere")) {
			if (e.hasAttribute("center") && e.hasAttribute("radius") && e.hasAttribute("name"))
				return true;
			return false;
		}
		if (e.getNodeName().equals("union")) {
			if (e.hasAttribute("name"))
				return true;
			return false;
		}
		if (e.getNodeName().equals("complement")) {
			if (e.hasAttribute("name"))
				return true;
			return false;
		}
		if (e.getNodeName().equals("negative")) {
			if (e.hasAttribute("name"))
				return true;
			return false;
		}
		return false;
	}
	
	public static IRegion parseRegion(Element tag, boolean inverted) {
		if (tag.getNodeName().equals("cylinder"))
			return RegionUtils.parseCylinder(tag, inverted);
		if (tag.getNodeName().equals("cuboid"))
			return RegionUtils.parseCuboid(tag, inverted);
		if (tag.getNodeName().equals("rectangle"))
			return RegionUtils.parseRectangle(tag, inverted);
		if (tag.getNodeName().equals("circle"))
			return RegionUtils.parseCircle(tag, inverted);
		if (tag.getNodeName().equals("sphere"))
			return RegionUtils.parseSphere(tag, inverted);
		if (tag.getNodeName().equals("union"))
			return RegionUtils.parseUnion(tag, inverted);
		if (tag.getNodeName().equals("complement"))
			return RegionUtils.parseComplement(tag, inverted);
		if (tag.getNodeName().equals("negative"))
			return RegionUtils.parseNegative(tag);
		return null;
	}
	
	public static IRegion parseCuboid(Element tag, boolean inverted) {
		Location min = new Location(Bukkit.getWorld("world"), 0, 0, 0);
		Location max = new Location(Bukkit.getWorld("world"), 0, 0, 0);
		String[] minData = tag.getAttribute("min").split(",");
		String[] maxData = tag.getAttribute("max").split(",");
		min.setX(Integer.parseInt(minData[0]));
		min.setY(Integer.parseInt(minData[1]));
		min.setZ(Integer.parseInt(minData[2]));
		max.setX(Integer.parseInt(maxData[0]));
		max.setY(Integer.parseInt(maxData[1]));
		max.setZ(Integer.parseInt(maxData[2]));
		return new RegionCuboid(min, max, inverted);
	}
	
	public static IRegion parseRectangle(Element tag, boolean inverted) {
		Location min = new Location(Bukkit.getWorld("world"), 0, 0, 0);
		Location max = new Location(Bukkit.getWorld("world"), 0, 0, 0);
		String[] minData = tag.getAttribute("min").split(",");
		String[] maxData = tag.getAttribute("max").split(",");
		min.setX(Integer.parseInt(minData[0]));
		min.setY(Integer.parseInt(minData[1]));
		min.setZ(0);
		max.setX(Integer.parseInt(maxData[0]));
		max.setY(Integer.parseInt(maxData[1]));
		max.setZ(0);
		return new RegionRectangle(min, max, inverted);
	}
	
	public static IRegion parseCylinder(Element tag, boolean inverted) {
		Location base = new Location(Bukkit.getWorld("world"), 0, 0, 0);
		String[] minData = tag.getAttribute("base").split(",");
		base.setX(Integer.parseInt(minData[0]));
		base.setY(Integer.parseInt(minData[1]));
		base.setZ(Integer.parseInt(minData[2]));
		int radius = Integer.parseInt(tag.getAttribute("radius"));
		int height = Integer.parseInt(tag.getAttribute("height"));
		return new RegionCilinder(base, radius, height, inverted);
	}
	
	public static IRegion parseCircle(Element tag, boolean inverted) {
		Location base = new Location(Bukkit.getWorld("world"), 0, 0, 0);
		String[] minData = tag.getAttribute("center").split(",");
		base.setX(Integer.parseInt(minData[0]));
		base.setY(Integer.parseInt(minData[1]));
		base.setZ(Integer.parseInt(minData[2]));
		int radius = Integer.parseInt(tag.getAttribute("radius"));
		return new RegionCircle(base, radius, inverted);
	}
	
	public static IRegion parseSphere(Element tag, boolean inverted) {
		Location base = new Location(Bukkit.getWorld("world"), 0, 0, 0);
		String[] minData = tag.getAttribute("center").split(",");
		base.setX(Integer.parseInt(minData[0]));
		base.setY(Integer.parseInt(minData[1]));
		base.setZ(Integer.parseInt(minData[2]));
		int radius = Integer.parseInt(tag.getAttribute("radius"));
		return new RegionSphere(base, radius, inverted);
	}
	
	public static IRegion parseUnion(Element tag, boolean inverted) {
		NodeList list = tag.getChildNodes();
		RegionUnion toReturn = new RegionUnion(inverted);
		for (int i = 0; i < list.getLength(); i++) {
			Node n = list.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				if (!RegionUtils.isValidRegionTag(e))
					continue;
				toReturn.addBlocksToRegion(RegionUtils.parseRegion(e, inverted).blocks());
			}
		}
		return toReturn;
	}
	public static IRegion parseComplement(Element tag, boolean inverted) {
		NodeList list = tag.getChildNodes();
		boolean firstElementFound = false;
		RegionComplement toReturn = null;
		for (int i = 0; i < list.getLength(); i++) {
			Node n = list.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				if (!RegionUtils.isValidRegionTag(e))
					continue;
				if (!firstElementFound) {
					toReturn = new RegionComplement(RegionUtils.parseRegion(e, inverted), inverted);
					firstElementFound = true;
					continue;
				}
				toReturn.removeBlocksFromRegion(RegionUtils.parseRegion(e, inverted).blocks());
			}
		}
		return toReturn;
	}
	public static IRegion parseNegative(Element tag) {
		NodeList list = tag.getChildNodes();
		IRegion toReturn = null;
		for (int i = 0; i < list.getLength(); i++) {
			Node n = list.item(i);
			if (n.getNodeType() == Node.ELEMENT_NODE) {
				Element e = (Element) n;
				toReturn = RegionUtils.parseRegion(e, true);
			}
		}
		return toReturn;
	}
}
