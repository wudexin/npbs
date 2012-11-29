package com.nantian.npbs.business.model;

/**
 * TbCheckTempId entity. @author MyEclipse Persistence Tools
 */

public class TbCheckTempId implements java.io.Serializable {

	// Fields

	private String col1;
	private String col2;
	private String col3;
	private String col4;
	private String col5;
	private String col6;
	private String col7;
	private String col8;

	// Constructors

	/** default constructor */
	public TbCheckTempId() {
	}

	/** full constructor */
	public TbCheckTempId(String col1, String col2, String col3, String col4,
			String col5, String col6, String col7, String col8) {
		this.col1 = col1;
		this.col2 = col2;
		this.col3 = col3;
		this.col4 = col4;
		this.col5 = col5;
		this.col6 = col6;
		this.col7 = col7;
		this.col8 = col8;
	}

	// Property accessors

	public String getCol1() {
		return this.col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	public String getCol2() {
		return this.col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2;
	}

	public String getCol3() {
		return this.col3;
	}

	public void setCol3(String col3) {
		this.col3 = col3;
	}

	public String getCol4() {
		return this.col4;
	}

	public void setCol4(String col4) {
		this.col4 = col4;
	}

	public String getCol5() {
		return this.col5;
	}

	public void setCol5(String col5) {
		this.col5 = col5;
	}

	public String getCol6() {
		return this.col6;
	}

	public void setCol6(String col6) {
		this.col6 = col6;
	}

	public String getCol7() {
		return this.col7;
	}

	public void setCol7(String col7) {
		this.col7 = col7;
	}

	public String getCol8() {
		return this.col8;
	}

	public void setCol8(String col8) {
		this.col8 = col8;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TbCheckTempId))
			return false;
		TbCheckTempId castOther = (TbCheckTempId) other;

		return ((this.getCol1() == castOther.getCol1()) || (this.getCol1() != null
				&& castOther.getCol1() != null && this.getCol1().equals(
				castOther.getCol1())))
				&& ((this.getCol2() == castOther.getCol2()) || (this.getCol2() != null
						&& castOther.getCol2() != null && this.getCol2()
						.equals(castOther.getCol2())))
				&& ((this.getCol3() == castOther.getCol3()) || (this.getCol3() != null
						&& castOther.getCol3() != null && this.getCol3()
						.equals(castOther.getCol3())))
				&& ((this.getCol4() == castOther.getCol4()) || (this.getCol4() != null
						&& castOther.getCol4() != null && this.getCol4()
						.equals(castOther.getCol4())))
				&& ((this.getCol5() == castOther.getCol5()) || (this.getCol5() != null
						&& castOther.getCol5() != null && this.getCol5()
						.equals(castOther.getCol5())))
				&& ((this.getCol6() == castOther.getCol6()) || (this.getCol6() != null
						&& castOther.getCol6() != null && this.getCol6()
						.equals(castOther.getCol6())))
				&& ((this.getCol7() == castOther.getCol7()) || (this.getCol7() != null
						&& castOther.getCol7() != null && this.getCol7()
						.equals(castOther.getCol7())))
				&& ((this.getCol8() == castOther.getCol8()) || (this.getCol8() != null
						&& castOther.getCol8() != null && this.getCol8()
						.equals(castOther.getCol8())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getCol1() == null ? 0 : this.getCol1().hashCode());
		result = 37 * result
				+ (getCol2() == null ? 0 : this.getCol2().hashCode());
		result = 37 * result
				+ (getCol3() == null ? 0 : this.getCol3().hashCode());
		result = 37 * result
				+ (getCol4() == null ? 0 : this.getCol4().hashCode());
		result = 37 * result
				+ (getCol5() == null ? 0 : this.getCol5().hashCode());
		result = 37 * result
				+ (getCol6() == null ? 0 : this.getCol6().hashCode());
		result = 37 * result
				+ (getCol7() == null ? 0 : this.getCol7().hashCode());
		result = 37 * result
				+ (getCol8() == null ? 0 : this.getCol8().hashCode());
		return result;
	}

}