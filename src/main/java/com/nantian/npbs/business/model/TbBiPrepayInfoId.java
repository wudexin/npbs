package com.nantian.npbs.business.model;

/**
 * TbBiPrepayInfoId entity. @author MyEclipse Persistence Tools
 */

public class TbBiPrepayInfoId implements java.io.Serializable {

	// Fields

     private String tradeDate;
     private String pbSerial;



	// Constructors

	/** default constructor */
	public TbBiPrepayInfoId() {
	}

	/** full constructor */
	public TbBiPrepayInfoId(String tradeDate, String pbSerial) {
		this.tradeDate = tradeDate;
		this.pbSerial = pbSerial;
	}

	// Property accessors

	public String getTradeDate() {
		return this.tradeDate;
	}

	public void setTradeDate(String tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getPbSerial() {
		return this.pbSerial;
	}

	public void setPbSerial(String pbSerial) {
		this.pbSerial = pbSerial;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbBiPrepayInfoId))
			return false;
		TbBiPrepayInfoId castOther = (TbBiPrepayInfoId) other;

		return ((this.getTradeDate() == castOther.getTradeDate()) || (this
				.getTradeDate() != null
				&& castOther.getTradeDate() != null && this.getTradeDate()
				.equals(castOther.getTradeDate())))
				&& ((this.getPbSerial() == castOther.getPbSerial()) || (this
						.getPbSerial() != null
						&& castOther.getPbSerial() != null && this
						.getPbSerial().equals(castOther.getPbSerial())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTradeDate() == null ? 0 : this.getTradeDate().hashCode());
		result = 37 * result
				+ (getPbSerial() == null ? 0 : this.getPbSerial().hashCode());
		return result;
	}

}