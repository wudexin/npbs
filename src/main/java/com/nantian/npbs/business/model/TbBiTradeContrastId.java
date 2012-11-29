package com.nantian.npbs.business.model;

/**
 * TbBiTradeContrastId entity. @author MyEclipse Persistence Tools
 */

public class TbBiTradeContrastId implements java.io.Serializable {

	// Fields

	private String tradeDate;
	private String pbSerial;
	private String oriPbSerial;

	// Constructors

	/** default constructor */
	public TbBiTradeContrastId() {
	}

	/** full constructor */
	public TbBiTradeContrastId(String tradeDate, String pbSerial,
			String oriPbSerial) {
		this.tradeDate = tradeDate;
		this.pbSerial = pbSerial;
		this.oriPbSerial = oriPbSerial;
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

	public String getOriPbSerial() {
		return this.oriPbSerial;
	}

	public void setOriPbSerial(String oriPbSerial) {
		this.oriPbSerial = oriPbSerial;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbBiTradeContrastId))
			return false;
		TbBiTradeContrastId castOther = (TbBiTradeContrastId) other;

		return ((this.getTradeDate() == castOther.getTradeDate()) || (this
				.getTradeDate() != null
				&& castOther.getTradeDate() != null && this.getTradeDate()
				.equals(castOther.getTradeDate())))
				&& ((this.getPbSerial() == castOther.getPbSerial()) || (this
						.getPbSerial() != null
						&& castOther.getPbSerial() != null && this
						.getPbSerial().equals(castOther.getPbSerial())))
				&& ((this.getOriPbSerial() == castOther.getOriPbSerial()) || (this
						.getOriPbSerial() != null
						&& castOther.getOriPbSerial() != null && this
						.getOriPbSerial().equals(castOther.getOriPbSerial())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getTradeDate() == null ? 0 : this.getTradeDate().hashCode());
		result = 37 * result
				+ (getPbSerial() == null ? 0 : this.getPbSerial().hashCode());
		result = 37
				* result
				+ (getOriPbSerial() == null ? 0 : this.getOriPbSerial()
						.hashCode());
		return result;
	}

}