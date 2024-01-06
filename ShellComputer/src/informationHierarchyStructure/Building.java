/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Using the composite design pattern to for a tree data structure
 * for the campus, building, room, and materials object
 * 
 * Reference
 * SourceMaking (n.d.). Composite in java: Before and after. Retrieved from 
        https://sourcemaking.com/design_patterns/composite/java/1
   
   SourceMaking (n.d.). Composite in java: User-configurable 'views' of a composite. Retrieved from 
        https://sourcemaking.com/design_patterns/composite/java/4
 */
package informationHierarchyStructure;
import java.util.ArrayList;

public class Building implements InformationInterface {
    private final String buildingNum;
    public ArrayList includedRoomsOfBuilding = new ArrayList();
    
    public Building(String buildingNum){
        this.buildingNum = buildingNum;
    }
    
    public void add(Object o){
        this.includedRoomsOfBuilding.add(o);
    }
    
    @Override
    public StringBuffer listInformation(){
        
        INDENTATION.append(this.buildingNum);
        INDENTATION.append("\n");
        INDENTATION.append("   ");
        
        for(Object includedRoomOfBuilding: this.includedRoomsOfBuilding){
            InformationInterface objectInfo = (InformationInterface)includedRoomOfBuilding;
            objectInfo.listInformation();
        }
        INDENTATION.setLength(INDENTATION.length());
        
        return INDENTATION;
        
    }
    
 
}
