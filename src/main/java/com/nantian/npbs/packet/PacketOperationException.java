/**
 * PacketOperationException.java
 *
 * Created by TsaiYee , Nov 22, 2010
 */
package com.nantian.npbs.packet;

/**
 * @author TsaiYee
 *
 */
public class PacketOperationException extends Exception {

	/**
	 * @param e
	 */
	public PacketOperationException(Exception e) {
		super(e);
	}

	/**
	 * 
	 */
	public PacketOperationException() {
		super();
	}

	/**
	 * @param string
	 */
	public PacketOperationException(String string) {
		super(string);
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4880009939148804121L;

}
