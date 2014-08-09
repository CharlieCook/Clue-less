package clue.less;

public class Location {
	private Locations currentLocation;
	
	public enum Locations{
		HALL, LIBRARY, KITCHEN, STUDY, LOUNGE, BILLARD, DINING, BALLROOM, CONSERVATORY, HALLWAY1, 
		HALLWAY2, HALLWAY3, HALLWAY4, HALLWAY5, HALLWAY6, HALLWAY7, HALLWAY8, HALLWAY9, HALLWAY10,
		HALLWAY11, HALLWAY12
	}
	
	public Location() {
		this.currentLocation = Locations.HALL;
	}
	
	public Location(Locations location) {
		this.currentLocation = location;
	}
	
	public Locations getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Locations currentLocation) {
		this.currentLocation = currentLocation;
	}

	/**
	 * Simply checks if a location is a hallway or not.
	 * @param location - Location to be checked
	 * @return Indication if the location is a hallway
	 */
	public static boolean isHallway(Location location) {
		if(location.equals(Locations.HALLWAY1) ||
				location.equals(Locations.HALLWAY2) ||
				location.equals(Locations.HALLWAY3) ||
				location.equals(Locations.HALLWAY4) ||
				location.equals(Locations.HALLWAY5) ||
				location.equals(Locations.HALLWAY6) ||
				location.equals(Locations.HALLWAY7) ||
				location.equals(Locations.HALLWAY8) ||
				location.equals(Locations.HALLWAY9) ||
				location.equals(Locations.HALLWAY10) ||
				location.equals(Locations.HALLWAY11) ||
				location.equals(Locations.HALLWAY12)) {
				return true;
			}
			return false;
	}

	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Location)) {
			return false;
		}
		
		if(obj == this) {
			return true;
		}
		
		Location location = (Location)obj;
		return location.currentLocation == this.currentLocation;
	}
}
