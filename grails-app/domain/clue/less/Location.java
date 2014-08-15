package clue.less;

public enum Location {
		HALL, LIBRARY, KITCHEN, STUDY, LOUNGE, BILLARD, DINING, BALLROOM, CONSERVATORY, HALLWAY1, 
		HALLWAY2, HALLWAY3, HALLWAY4, HALLWAY5, HALLWAY6, HALLWAY7, HALLWAY8, HALLWAY9, HALLWAY10,
		HALLWAY11, HALLWAY12;
	
		/**
		 * The board matrix is used for determining if a selected location to move to is valid
		 */
	public static Location[][] boardMatrix = new Location[][]{
			{STUDY, HALLWAY1, HALL, HALLWAY2, LOUNGE},
			{HALLWAY3, null, HALLWAY4, null, HALLWAY5},
			{LIBRARY, HALLWAY6, BILLARD, HALLWAY7, DINING},
			{HALLWAY8, null, HALLWAY9, null, HALLWAY10},
			{CONSERVATORY, HALLWAY11, BALLROOM, HALLWAY12, KITCHEN}
			
	};
		
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

	/**
	 * Checks if the location is a corner office, which has a secret path.
	 * @param location - Location being checked if it is a corner room
	 * @return Indicates if the location is a corner room of not
	 */
	public static boolean isCornerRoom(Location location) {
		if(location == Location.KITCHEN ||
				location == Location.STUDY ||
				location == Location.CONSERVATORY ||
				location == Location.LOUNGE) {
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
	
	public static int getLocationIndex(Location location) {
		// traverse the horizontal (x) axis of the matrix
		for(int x = 0; x < 5; ++x) {
			// traverse the vertical  (y) axis of the matrix
			for(int y = 0; y < 5; ++y) {
				if(boardMatrix[x][y] != null && boardMatrix[x][y].equals(location)) {
					return (x * 5) + y;
				}
			}
		}
		// This line should never get hit
		return -1;
	}
	
	public static boolean isMoveValid(Location start, Location end) {
		int startIndex = Location.getLocationIndex(start);
		int endIndex = Location.getLocationIndex(end);
		// check if both locations are corners
		if(isCornerRoom(start) && isCornerRoom(end)) {
			// check if both corner rooms are the accessible ones, AKA they criss-cross
			if(startIndex == 0 && endIndex == 24 ||
					startIndex == 24 && endIndex == 0 ||
					startIndex == 4 && endIndex == 20 ||
					startIndex == 20 && endIndex == 4) {
				return true;
			}
		} else {
			// check if both rooms are orthogonal
			int startX = startIndex % 5;
			int startY = startIndex / 5;
			int endX = endIndex % 5;
			int endY = endIndex / 5;
			if(startX == endX) {
				// compare the y axis
				if(Math.abs(startY - endY) == 1) {
					return true;
				}
			} else if(startY == endY) {
				// compare the x axis
				if(Math.abs(startX- endX) == 1) {
					return true;
				}
			}
		}
		// default the move is incorrect
		return false;
	}
}
