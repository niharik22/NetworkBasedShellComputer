/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iterator;

/**
 * References
 * SourceMaking (n.d.). Iterator in java: Before and after. Retrieved from
 *      https://sourcemaking.com/design_patterns/iterator/java/1 
 * 
 * SourceMaking (n.d.). Iterator in java. Retrieved from
 *      https://sourcemaking.com/design_patterns/iterator/java/2
 */
public interface IteratorInterface {
    public String firstElement();
    public String nextElement();
    public String currentElement();
    public int getSize();
    public int getIteratorPosition();
}
