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

public class Campus implements InformationInterface{
    private final String campusName;
    public ArrayList includedBuildingsOfCampus = new ArrayList();
    
    public Campus(String campusName){
        this.campusName = campusName;
    }
    
    public void add(Object o){
        this.includedBuildingsOfCampus.add(o);
    }
    
    @Override
    public StringBuffer listInformation(){
        INDENTATION.append("\n");
        INDENTATION.append(this.campusName);
        INDENTATION.append("\n");
        INDENTATION.append("   ");
             
        for(Object includedBuildingOfCampus: this.includedBuildingsOfCampus){
            
            InformationInterface objectInfo = (InformationInterface)includedBuildingOfCampus;
            objectInfo.listInformation();
            
        }

        INDENTATION.setLength(INDENTATION.length() - 3);
     
        return INDENTATION;
     
    }
      
}
