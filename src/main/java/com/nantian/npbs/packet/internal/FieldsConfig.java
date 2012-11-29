/**
 * 
 */
package com.nantian.npbs.packet.internal;

import java.util.Map;

import com.nantian.npbs.packet.internal.Field;
import com.nantian.npbs.packet.internal.FieldType.AlignType;
import com.nantian.npbs.packet.internal.FieldType.LengthType;
import com.nantian.npbs.packet.internal.FieldType.VariableType;

/**
 * @author TsaiYee
 *
 */
public abstract class FieldsConfig {
	
	private  Field fields[] = null;
	private  Map<String, Field> fieldsMap = null;
	private boolean isInitional =  false;
	
	public void setFields(Field fields[]) {
		this.fields = fields;
		initFieldConfig();
		isInitional = true;
	}
	
	public void setFieldsMap(Map<String, Field> fieldsMap) {
		this.fieldsMap = fieldsMap;
		initFieldConfig();
		isInitional = true;
	}
	
	public FieldsConfig() {
	}
	
	private  void checkField(int fieldNo) throws Exception {
		if (isInitional == false) throw new Exception("fields[] don't set, please set fields first!");
		if (fields[fieldNo] == null) throw new IllegalArgumentException("field " + fieldNo + " not be used!");
	}
	

	
	public  VariableType getFieldVariableType(int fieldNo) throws Exception {
		checkField(fieldNo);
		return fields[fieldNo].getVarType();
	}
	
	public  LengthType getFieldLengthType(int fieldNo) throws Exception {
		checkField(fieldNo);
		return fields[fieldNo].getLengthType();
	}
	
	public  AlignType getFieldAlignType(int fieldNo) throws Exception {
		checkField(fieldNo);
		return fields[fieldNo].getAlignType();
	}
	
	public  int getFieldBcdLength(int fieldNo) throws Exception {
		checkField(fieldNo);
		return fields[fieldNo].getBcdLength();
	}
	public  int getFieldAsciiLength(int fieldNo) throws Exception {
		checkField(fieldNo);
		return fields[fieldNo].getAsciiLength();
	}
	
	public  int getFieldHeaderBcdLength(int fieldNo) throws Exception {
		checkField(fieldNo);
		return fields[fieldNo].getHeaderBcdLength();
	}
	public  int getFieldHeaderAsciiLength(int fieldNo) throws Exception {
		checkField(fieldNo);
		return fields[fieldNo].getHeaderAsciiLength();
	}
	
	public  int getFieldMaxBcdLength(int fieldNo) throws Exception {
		checkField(fieldNo);
		return fields[fieldNo].getMaxBcdLength();
	}
	public  int getFieldMaxAsciiLength(int fieldNo) throws Exception {
		checkField(fieldNo);
		return fields[fieldNo].getMaxAsciiLength();
	}

	public  String getDescription(int fieldNo) throws Exception {
		checkField(fieldNo);
		return fields[fieldNo].getDescription();
	}
	
	private  void checkField(String fieldName) throws Exception {
		if (isInitional == false) throw new Exception("fields[] don't set, please set fields first!");
		if (fieldsMap.get(fieldName) == null) throw new IllegalArgumentException("field " + fieldName + " not be used!");
	}
	public  VariableType getFieldVariableType(String fieldName) throws Exception {
		checkField(fieldName);
		return fieldsMap.get(fieldName).getVarType();
	}
	
	public  LengthType getFieldLengthType(String fieldName) throws Exception {
		checkField(fieldName);
		return fieldsMap.get(fieldName).getLengthType();
	}
	
	public  AlignType getFieldAlignType(String fieldName) throws Exception {
		checkField(fieldName);
		return fieldsMap.get(fieldName).getAlignType();
	}
	
	public  int getFieldBcdLength(String fieldName) throws Exception {
		checkField(fieldName);
		return fieldsMap.get(fieldName).getBcdLength();
	}
	public  int getFieldAsciiLength(String fieldName) throws Exception {
		checkField(fieldName);
		return fieldsMap.get(fieldName).getAsciiLength();
	}
	
	public  int getFieldHeaderBcdLength(String fieldName) throws Exception {
		checkField(fieldName);
		return fieldsMap.get(fieldName).getHeaderBcdLength();
	}
	public  int getFieldHeaderAsciiLength(String fieldName) throws Exception {
		checkField(fieldName);
		return fieldsMap.get(fieldName).getHeaderAsciiLength();
	}
	
	public  int getFieldMaxBcdLength(String fieldName) throws Exception {
		checkField(fieldName);
		return fieldsMap.get(fieldName).getMaxBcdLength();
	}
	public  int getFieldMaxAsciiLength(String fieldName) throws Exception {
		checkField(fieldName);
		return fieldsMap.get(fieldName).getMaxAsciiLength();
	}

	public  String getDescription(String fieldName) throws Exception {
		checkField(fieldName);
		return fieldsMap.get(fieldName).getDescription();
	}
	protected  abstract void initFieldConfig();
}
