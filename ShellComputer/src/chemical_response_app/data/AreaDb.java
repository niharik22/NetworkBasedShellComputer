package chemical_response_app.data;

/**
 * This class stands in for and organizes the area database information. 
 * In production version it will be replaced by adapters or proxies or ...
 *    which will refer to the real database.
 */

public class AreaDb {

	private class AreaRecord {

		/**
		 *  Constructor for dummy 'internal' database record
		 *  matching planner required interface.
		 *  
		 * @param bldg
		 * @param rm
		 * @param responsible
		 * @param ph
		 * @param hasKit
		 */
		private AreaRecord(int bldg, int rm, String responsible, String ph,
				boolean hasKit) {
			building = bldg;
			room = rm;
			responsibleParty = responsible;
			phone = ph;
			hasSpillKit = hasKit;
		}

		/**
		 * 			
		 * @return the buikding and room as a string
		 */
		String getLoc() {
			return " bldg " + building + " room" + room;
		}

		int building;
		int room;
		String responsibleParty;
		String phone;
		boolean hasSpillKit;
	}

	private static AreaRecord[] db;

	/**
	 * this will be unique per installation
	 */
	final AreaRecord ehs = new AreaRecord(0, 0, "EHS", "111-1111", true);

	public Area ehsAreaInfo;

	private static AreaDb theDb;

	// just to dummy up a database
	AreaDb() {
		db = new AreaRecord[5];
		db[0] = ehs;
		db[1] = new AreaRecord(1, 101, "Joe Smith", "333-3333", true);
		db[2] = new AreaRecord(1, 102, "Bill Jones", "444-4444", false);
		db[3] = new AreaRecord(2, 301, "Shari Ford", "555-5555", true);
		db[4] = new AreaRecord(3, 002, "Joe Smith", "666-6666", true);

		this.ehsAreaInfo = this.find(ehs.building, ehs.room);

	}

	/**
	 * Remove (set to false) the spill kit in the given building and room numbers
	 * 
	 * @param bldg
	 * @param room
	 */
	public void consumeSpillKit(int bldg, int room) {
		int inx = dbFind(bldg, room);
		if (inx > 0) {
			db[inx].hasSpillKit = false;
			System.err.println("Used spill kit bldg: " + db[inx].building
					+ " rm: " + room + "kit status :" + db[inx].hasSpillKit);
		} else {
			System.err.println("cosume spill kit did not find room: " + db[inx].building+" rm: "+room);
		}

	}

	/**
	 * Simple linear search for a location by building and room numbers
	 * 
	 * @param bldg
	 * @param room
	 * @return the close room with a spill kit unused
	 */
	private int dbFind(int bldg, int room) {
		int index = 0;
		while (index < db.length) {
			if (db[index].room == room && db[index].building == bldg)
				return index;
			index++;
		}
		return -1;
	}

	/**
	 *  True distance would come from database calculation.
	 *  Here we just use a simple substitute calculated by a Manhattan distance.
	 * 
	 * @param rm1
	 * @param bldg1
	 * @param rm2
	 * @param bldg2
	 * @return an integer distance
	 */
	private int distance(int rm1, int bldg1, int rm2, int bldg2) {
		return Math.abs(rm1 - rm2) + 10 * Math.abs(bldg1 - bldg2);
	}

	/**
	 * Find the information about an area.
	 * After weeks 8 null return may be handled by throwing an exception
	 * @param bldg
	 * @param rm
	 * @return an Area record for the room found (or null)
	 */
	public Area find(int bldg, int rm) {
		// TODO: real database search must be more intelligent about names!
		System.err.println("Looking for bldg, rm: " + bldg + "," + rm);
		Area rslt = new Area();
		// find the room or assume EHS
		int dbInx = dbFind(bldg, rm);
		System.err.println("found location is " + (dbInx > 0));
		if (dbInx < 0) {
			// TODO: for now just default to EHS if room unknown
			rslt.responsibleParty = ehs.responsibleParty;
			rslt.phone = ehs.phone;
			rslt.nearSpillKit = nearKit(ehs.building, ehs.room);
		} else {
			// found the room
			rslt.responsibleParty = db[dbInx].responsibleParty;
			rslt.phone = db[dbInx].phone;
			if (db[dbInx].hasSpillKit) {
				rslt.nearSpillKit = " " + bldg + " " + rm + " ";
			}
			else
				// find the nearest spill kit
				System.err.println("Searching for kit");
				rslt.nearSpillKit = nearKit(bldg, rm);
		}

		return rslt;
	}

	/**
	 * Search for a nearby spill kit.
	 * For simplicity, use Manhattan distance on building and room.
	 * 
	 * @param bldg
	 * @param rm
	 * @return  a string location for the pill kit building and room
	 */
	private String nearKit(int bldg, int rm) {
		int distToSpillKit = distance(0, 0, bldg, rm);
		System.err.println("Searching for kit near building " + bldg + " room "
				+ rm);

		AreaRecord nearKit = ehs;
		for (int i = 0; i < db.length && distToSpillKit > 0; i++) {
			AreaRecord ar = db[i];
			if (ar.hasSpillKit) {
				int dist = distance(ar.building, ar.room, bldg, rm);
				if (dist < distToSpillKit) {
					nearKit = ar;
					distToSpillKit = dist;
				}
			}
		}
		System.err.println("Found kit at building " + nearKit.building
				+ " room " + nearKit.room);

		// note near spill kit location and note spill kit is used up
		nearKit.hasSpillKit = false;

		return " " + nearKit.building + " " + nearKit.room + " ";
	}

	class Area implements chemical_response_app.data.AreaInformation {

		String bldg;
		String room;
		String responsibleParty;
		String phone;
		String nearSpillKit;

		private Area() {
			super();
			bldg = "unknown";
			room = "unknown";
			responsibleParty = "unknown";
			phone = "unknown";
			nearSpillKit = ehs.getLoc();
		}

		@Override
		public String getBuiding() {
			return bldg;
		}

		@Override
		public String getRoom() {
			return room;
		}

		@Override
		public String getResponsibleParty() {
			return responsibleParty;
		}

		@Override
		public String getPhone() {
			return phone;
		}

		@Override
		public String nearSpillKit() {
			return nearSpillKit;
		}
	}
	
	public static AreaDb getDb(){
		if (theDb==null) {
			theDb = new AreaDb();
		}
		return theDb;
	}

}
