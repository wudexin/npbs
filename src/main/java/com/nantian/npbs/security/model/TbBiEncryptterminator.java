package com.nantian.npbs.security.model;

import java.io.Serializable;

public class TbBiEncryptterminator implements Serializable {
	private static final long serialVersionUID = 1L;
	private String terminatorid;
	private String keyid;
	
	public TbBiEncryptterminator(){
		
	}
	
	public TbBiEncryptterminator(String terminatorid){
		this.terminatorid = terminatorid;
	}
	
	public TbBiEncryptterminator(String terminatorid,String keyid){
		this.terminatorid = terminatorid;
		this.keyid = keyid;
	}

	public String getTerminatorid() {
		return terminatorid;
	}

	public void setTerminatorid(String terminatorid) {
		this.terminatorid = terminatorid;
	}

	public String getKeyid() {
		return keyid;
	}

	public void setKeyid(String keyid) {
		this.keyid = keyid;
	}

}
