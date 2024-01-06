/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chemical_response_app.planning;

/**
 * This class provide the information for the Absorbents
 * and Cautions class
 * 
 * Reference
 * SourceMaking. (2017). Abstract factory in java. Retrieved from 
 *      https://sourcemaking.com/design_patterns/abstract_factory/java/1
 */
public class KitInformation2 extends AbstractFactoryMediator {
    Absorbents absorbents = new Absorbents();
    Cautions cautions = new Cautions();
    String[] ga = {"1:1:1 mixture of Flor-Dri (or unscented kitty litter), sodium bicarbonate, and sand"};
    String[] gc = {""};
    
    @Override
    public Absorbents presentAbsorbents(){
        absorbents.setAbsorbents(ga);
        return absorbents;
    }
    
    @Override
    public Cautions presentCautions(){
        cautions.setCautions(gc);
        return cautions;
    }
    
    @Override
    public String[] getAbsorbents(){
        return this.ga;
    }
   
    
}
