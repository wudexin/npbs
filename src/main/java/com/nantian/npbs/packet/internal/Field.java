/**
 * 
 */
package com.nantian.npbs.packet.internal;

import com.nantian.npbs.packet.internal.FieldType.AlignType;
import com.nantian.npbs.packet.internal.FieldType.LengthType;
import com.nantian.npbs.packet.internal.FieldType.VariableType;

/**
 * @author TsaiYee
 *
 * 注意，此类中所有的长度(length)均指字节(byte)长度，而不是指某个字符集对应的字符串的长度
 */
public class Field {
	
	private int no;
	private String name;
	private VariableType varType;
	private LengthType  lengthType;
	private int headerBcdLength;  //for variable length
	private int headerAsciilength; //for variable length
	private int maxBcdLength;  //for variable length
	private int maxAsciiLength;  //for variable length
	private int bcdLength; //for fixed length
	private int asciiLength; //for fixed length
	private AlignType alignType; //for fixed length
	private String description;
	
	//for variable length
	public Field(int no, VariableType varType, LengthType lengthType, int headerBcdLength, int headerAsciiLength, int maxBcdLength, int maxAsciiLength, String description) {
		if (lengthType != LengthType.VARIABLE) 
			throw new IllegalArgumentException("field lengthtype " + lengthType +" is't fit for this method!");
		
		this.no = no;
		this.varType = varType;
		this.lengthType = LengthType.VARIABLE;
		this.headerBcdLength = headerBcdLength;
		this.headerAsciilength = headerAsciiLength;
		this.maxBcdLength = maxBcdLength;
		this.maxAsciiLength = maxAsciiLength;
		this.description = description;
	}
	
	public Field(int no, VariableType varType, LengthType lengthType,int maxAsciiLength, String description) {
		if (lengthType != LengthType.VARIABLE) 
			throw new IllegalArgumentException("field lengthtype " + lengthType +" is't fit for this method!");
		this.no = no;
		this.varType = varType;
		this.lengthType = LengthType.VARIABLE;
		this.maxAsciiLength = maxAsciiLength;
		this.description = description;
	}
	
	//for variable length
	public Field(String name, VariableType varType, LengthType lengthType, int headerBcdLength, int headerAsciiLength, int maxBcdLength, int maxAsciiLength, String description) {
		if (lengthType != LengthType.VARIABLE) 
			throw new IllegalArgumentException("field lengthtype " + lengthType +" is't fit for this method!");
		
		this.name = name;
		this.varType = varType;
		this.lengthType = LengthType.VARIABLE;
		this.headerBcdLength = headerBcdLength;
		this.headerAsciilength = headerAsciiLength;
		this.maxBcdLength = maxBcdLength;
		this.maxAsciiLength = maxAsciiLength;
		this.description = description;
	}
	
	//for variable length
	public Field(String name, VariableType varType, LengthType lengthType,int maxAsciiLength, String description) {
		if (lengthType != LengthType.VARIABLE) 
			throw new IllegalArgumentException("field lengthtype " + lengthType +" is't fit for this method!");
		this.name = name;
		this.varType = varType;
		this.lengthType = LengthType.VARIABLE;
		this.maxAsciiLength = maxAsciiLength;
		this.description = description;
	}
	
	//for fixed length
	public Field(int no, VariableType varType, LengthType lengthType, int bcdLength, int asciiLength, AlignType alignType, String description) {
		if (lengthType != LengthType.FIXED) 
			throw new IllegalArgumentException("field lengthtype " + lengthType +" is't fit for this method!");
		this.no = no;
		this.varType = varType;
		this.lengthType = LengthType.FIXED;
		this.bcdLength = bcdLength;
		this.asciiLength = asciiLength;
		this.alignType = alignType;
		this.description = description;
	}
	
	public Field(String name, VariableType varType, LengthType lengthType, int bcdLength, int asciiLength, AlignType alignType, String description) {
		if (lengthType != LengthType.FIXED) 
			throw new IllegalArgumentException("field lengthtype " + lengthType +" is't fit for this method!");
		this.name = name;
		this.varType = varType;
		this.lengthType = LengthType.FIXED;
		this.bcdLength = bcdLength;
		this.asciiLength = asciiLength;
		this.alignType = alignType;
		this.description = description;
	}
	
	public String getName() {
		return this.name;
	}
	
	public VariableType getVarType() {
		return this.varType;
	}
	
	public LengthType getLengthType() {
		return this.lengthType;
	}
	
	public AlignType getAlignType() {
		return this.alignType;
	}
	
	public int getBcdLength() {
		return this.bcdLength;
	}
	
	public int getMaxBcdLength() {
		return this.maxBcdLength;
	}
	
	public int getAsciiLength() {
		return this.asciiLength;
	}
	
	public int getMaxAsciiLength() {
		return this.maxAsciiLength;
	}
	
	public int getHeaderBcdLength() {
		return this.headerBcdLength;
	}
	
	public int getHeaderAsciiLength() {
		return this.headerAsciilength;
	}
	
	public String getDescription() {
		return this.description;
	}

}
