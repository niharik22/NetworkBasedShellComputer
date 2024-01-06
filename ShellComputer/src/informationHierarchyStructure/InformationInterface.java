/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * The interface used for implementing the composite Design pattern.
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

public interface InformationInterface {
    public static StringBuffer INDENTATION = new StringBuffer(); 
    public abstract StringBuffer listInformation();
}
