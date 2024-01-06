package chemical_response_app.data;

import java.util.Iterator;
import java.util.Vector;

/**
 *  stands in for the database of materials stored on site
 * 
 * Production systems must wrap adapters or proxies or ... around the 
 * enterprise database.
 */

public class MaterialDb {
	
	final static public class MaterialRecord {
		public int building;
		public int room;
		public String material;
		
		MaterialRecord(int bldg, int rm, String matrl){
			building = bldg;
			room = rm;
			material = matrl;
		}
	}
	

	
	private static Vector<MaterialRecord> materials = null;
	
	/**
	 * Fundamental access to the materials data
	 * @return sequential, historical iterator of Material records
	 */
    public final static Iterator<MaterialRecord> getMaterialsList(){
        if (materials==null) loadDb();
    	return materials.iterator();
    }
	
	/**
	 * Just dummy info for now 
	 * replace with database search later
	 */
	private static void loadDb() {
		materials = new Vector<MaterialRecord>();
		materials .add(new MaterialRecord(1, 101, "Antifreeze"));
		materials .add(new MaterialRecord(1, 101, "Mercury"));
		materials .add(new MaterialRecord(1, 101, "Alkalai Metal"));
		materials .add(new MaterialRecord(1, 101, "Pesticides"));
		materials .add(new MaterialRecord(1, 102, "Acid Chloride"));
		materials .add(new MaterialRecord(1, 102, "Hydroflouric Acid"));
		materials .add(new MaterialRecord(1, 102, "Phosphorous"));
		materials .add(new MaterialRecord(2, 301, "Motor Oil"));
		materials .add(new MaterialRecord(2, 301, "Antifreeze"));
		materials .add(new MaterialRecord(2, 301, "Hydraulic Oil"));
		materials .add(new MaterialRecord(3, 002, "Hydroflouric Acid"));
		materials .add(new MaterialRecord(3, 002, "Solvents"));
	
	}

	
	// a 'cheapo' test as a simple main
	public static void main(String[] args) {
		
		Iterator<MaterialRecord> it = getMaterialsList();
		while (it.hasNext()){
			MaterialRecord mr = it.next();
			System.out.println("Bldg: "+mr.building+"  rm: "+mr.room+" item: "+mr.material);
		}
	}


	
	/*
	 * Some Material possible from the documentation:
Antifreeze 
Degreaser 
Diesel Fuel 
Fertilizers 
Gasoline 
Herbicides 
Magnesium Chloride 
Motor Oil 
Hydraulic Oil 
Paints/Stains 
Pesticides 
Solvents 
Used Oil
Acid Chloride
Alkalai Metal
Bromine
Hydroflouric Acid
Mercury
Phosphorous

	locations
1, 101
1, 102
2, 301
3, 002
	 */
}
