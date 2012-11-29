/**
 * 
 */
package com.nantian.npbs.packet.internal;

/**
 * @author TsaiYee
 *
 */
public class FieldType {
	public static enum VariableType {BCD, ASCII, BINARY, BCDASCII};
	public static enum LengthType {FIXED, VARIABLE};
	public static enum AlignType {NONE, LEFTZERO, LEFTSPACE, RIGHTZERO, RIGHTSPACE}; //for fixed length
}
