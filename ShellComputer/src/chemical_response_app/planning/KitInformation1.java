/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chemical_response_app.planning;

/**
 * This class provide the information for the Absorbents and Cautions class
 *
 * Reference SourceMaking. (2017). Abstract factory in java. Retrieved from 
 * https://sourcemaking.com/design_patterns/abstract_factory/java/1
 */
public class KitInformation1 extends AbstractFactoryMediator {

    Absorbents absorbents = new Absorbents();
    Cautions cautions = new Cautions();
    String[] aa = {"oil-dri", "zorb-al", "dry sand"};
    String[] ac = {"avoid water", "avoid sodium bicarbonate"};

    @Override
    public Absorbents presentAbsorbents() {
        absorbents.setAbsorbents(aa);
        return this.absorbents;
    }

    @Override
    public Cautions presentCautions() {
        cautions.setCautions(ac);
        return this.cautions;
    }

    @Override
    public String[] getAbsorbents() {
        return this.aa;
    }

}
