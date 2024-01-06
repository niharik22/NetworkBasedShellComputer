/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iterator;

import informationHierarchyStructure.Materials;
import java.util.NoSuchElementException;

/**
 * The structure of this Iterator class is designed to loop within the materials
 * (absorbents) list.
 *
 * References SourceMaking (n.d.). Iterator in java: Before and after. Retrieved
 * from https://sourcemaking.com/design_patterns/iterator/java/1 
 *
 * SourceMaking (n.d.). Iterator in java. Retrieved from
 * https://sourcemaking.com/design_patterns/iterator/java/2
 *
 */
public class Iterator implements IteratorInterface {

    private Materials materialObject;
    private String materialElement;
    private int size = 0;
    private int iteratorPosition;

    public Iterator(Materials materials) {
        this.materialObject = materials;
    }

    @Override
    public String firstElement() {
        this.materialObject.counter = 0;
        this.iteratorPosition = this.materialObject.counter;
        this.materialElement = this.materialObject.arrList.get(this.materialObject.counter);
        return this.materialElement;
    }

    @Override
    public String nextElement() {

        String val = this.materialElement;
        try {
            if (val.equals(this.materialElement)) {
                this.materialObject.counter++;
                for (int i = 0; i < this.materialObject.counter; i++) {
                    this.materialElement = this.materialObject.arrList.get(this.materialObject.counter);
                }
                this.iteratorPosition = this.materialObject.counter;

            }
        } catch (NoSuchElementException e) {
            this.materialElement = "";
        }

        return this.materialElement;
    }

    @Override
    public String currentElement() {
        this.iteratorPosition = this.materialObject.counter;
        this.materialElement = this.materialObject.arrList.get(this.iteratorPosition);
        return this.materialElement;
    }

    @Override
    public int getSize() {
        for (int i = 0; i <= this.materialObject.arrList.size(); i++) {
            if (i == this.materialObject.arrList.size()) {
                this.size = i;
            }
        }
        return this.size;
    }

    @Override
    public int getIteratorPosition() {
        return this.iteratorPosition;
    }
}
