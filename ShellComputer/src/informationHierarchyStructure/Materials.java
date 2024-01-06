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
 * https://sourcemaking.com/design_patterns/composite/java/1
 *
 * SourceMaking (n.d.). Composite in java: User-configurable 'views' of a composite. Retrieved from 
 * https://sourcemaking.com/design_patterns/composite/java/4
 */
package informationHierarchyStructure;

import java.util.ArrayList;
import iterator.Iterator;

public class Materials implements InformationInterface {

    private final String[] materials;
    public ArrayList<String> arrList;
    public int counter = 0;

    public Materials(String[] materials) {
        this.materials = materials;
        this.arrList = new ArrayList<>();
    }

    public void addMaterials() {
        for (String material : this.materials) {
            this.arrList.add(material);
        }
    }

    @Override
    public StringBuffer listInformation() {

        for (String material : this.materials) {
            INDENTATION.append("         ");
            INDENTATION.append(material);
            INDENTATION.append("\n");
        }

        return INDENTATION;
    }

    public Iterator generateIterator() {
        return new Iterator(this);
    }

}
