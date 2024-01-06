/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chemical_response_app.planning;

import chemical_response_app.GeneralInterface;

/**
 * Implementing "Abstract Factory" (sourcemaking, 2017) design pattern to
 * provide a class that acts as a virtual class for providing the information
 * regarding Absorbents and Cautions (sourcemaking, 2017; Kulkarni, 2013).
 *
 * References Kulkarni, V. (2013). Understanding factory method and abstract
 * factory patterns. Codeproject. Retrieved from
 * https://www.codeproject.com/articles/35789/understanding-factory-method-and-abstract-factory
 *
 * SourceMaking. (2017). Abstract factory design pattern. Retrieved from 
 * http://sourcemaking.com/design_patterns/abstract_factory 
 *
 * SourceMaking. (2017). Abstract factory in java. Retrieved from 
 * https://sourcemaking.com/design_patterns/abstract_factory/java/1 
 *
 */
public abstract class AbstractFactoryMediator {

    private static final KitInformation1 KIT_INFO1 = new KitInformation1();
    private static final KitInformation2 KIT_INFO2 = new KitInformation2();

    public static AbstractFactoryMediator getRelatedAbsorbentCautionInformation(Interview interview,
            GeneralInterface material) {

        AbstractFactoryMediator mediator = null;
        if (interview.materialSpilled.equals(material.getAcidChloride())) {
            mediator = KIT_INFO1;
        } else {
            mediator = KIT_INFO2;
        }
        return mediator;
    }

    public abstract Absorbents presentAbsorbents();

    public abstract Cautions presentCautions();

    public abstract String[] getAbsorbents();
}
