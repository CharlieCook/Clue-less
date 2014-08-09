package clue.less;

public enum Location {
		HALL, LIBRARY, KITCHEN, STUDY, LOUNGE, BILLARD, DINING, BALLROOM, CONSERVATORY, HALLWAY1, 
		HALLWAY2, HALLWAY3, HALLWAY4, HALLWAY5, HALLWAY6, HALLWAY7, HALLWAY8, HALLWAY9, HALLWAY10,
		HALLWAY11, HALLWAY12;
		
	/**
	 * Simply checks if a location is a hallway or not.
	 * @param location - Location to be checked
	 * @return Indication if the location is a hallway
	 */
	public static boolean isHallway(Location location) {
		if(location.equals(Location.HALLWAY1) ||
				location.equals(Location.HALLWAY2) ||
				location.equals(Location.HALLWAY3) ||
				location.equals(Location.HALLWAY4) ||
				location.equals(Location.HALLWAY5) ||
				location.equals(Location.HALLWAY6) ||
				location.equals(Location.HALLWAY7) ||
				location.equals(Location.HALLWAY8) ||
				location.equals(Location.HALLWAY9) ||
				location.equals(Location.HALLWAY10) ||
				location.equals(Location.HALLWAY11) ||
				location.equals(Location.HALLWAY12)) {
				return true;
			}
			return false;
	}

	public static Location[] getNonHallways(){
		Location[] ret = new Location[]{
				Location.HALL, Location.LIBRARY, Location.KITCHEN, 
				Location.STUDY, Location.LOUNGE, Location.BILLARD, 
				Location.DINING, Location.BALLROOM, Location.CONSERVATORY};
		return ret;
	}
}
