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

public class Room implements InformationInterface {
    
    private final String roomNum;
    public ArrayList includedMaterialsOfRoom = new ArrayList();
    
    public Room(String roomNum){
        this.roomNum = roomNum;
    }
    
    public void add(Object o){
        this.includedMaterialsOfRoom.add(o);
    }
    
    @Override
    public StringBuffer listInformation(){
        //INDENTATION.append("\n");
        INDENTATION.append("   ");
        INDENTATION.append(this.roomNum);
        INDENTATION.append("\n");
      
        
        for(Object includedMaterialOfRoom: this.includedMaterialsOfRoom){
            InformationInterface objectInfo = (InformationInterface)includedMaterialOfRoom;
            objectInfo.listInformation();
        }
        INDENTATION.setLength(INDENTATION.length());
        
        return INDENTATION;
        
    }
    
    
}
