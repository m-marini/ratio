package org.mmarini.ratio;

/**
 * 
 * @author US00852
 * @version $Id: ArrayOperationStrategy.java,v 1.2 2007/01/09 22:11:33 marco Exp $
 */
public interface ArrayOperationStrategy {
	/**
	 * 
	 * @param array
	 * @return
	 */
	public abstract RationalArray inverse(RationalArray array);

	/**
	 * 
	 * @param array
	 * @return
	 */
	public abstract RationalNumber getDeterminer(RationalArray array);
}
