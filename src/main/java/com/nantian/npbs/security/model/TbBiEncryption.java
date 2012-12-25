package com.nantian.npbs.security.model;

import java.io.Serializable;

public class TbBiEncryption implements Serializable {
	private static final long serialVersionUID = 1L;
	private String keyID;
	private String kek;
	private String oldMacKey;
	private String oldPinKey;
	private String newMacKey;
	private String newPinKey;
	
	public TbBiEncryption(){
		
	}
	
	public TbBiEncryption(String keyID){
		this.keyID = keyID;
	}

	public String getKeyID() {
		return keyID;
	}

	public void setKeyID(String keyID) {
		this.keyID = keyID;
	}

	public String getKek() {
		return kek;
	}

	public void setKek(String kek) {
		this.kek = kek;
	}

	public String getOldMacKey() {
		return oldMacKey;
	}

	public void setOldMacKey(String oldMacKey) {
		this.oldMacKey = oldMacKey;
	}

	public String getOldPinKey() {
		return oldPinKey;
	}

	public void setOldPinKey(String oldPinKey) {
		this.oldPinKey = oldPinKey;
	}

	public String getNewMacKey() {
		return newMacKey;
	}

	public void setNewMacKey(String newMacKey) {
		this.newMacKey = newMacKey;
	}

	public String getNewPinKey() {
		return newPinKey;
	}

	public void setNewPinKey(String newPinKey) {
		this.newPinKey = newPinKey;
	}
}
