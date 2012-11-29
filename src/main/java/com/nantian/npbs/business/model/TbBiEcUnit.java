package com.nantian.npbs.business.model;



/**
 * TbBiEcUnit entity. @author MyEclipse Persistence Tools
 */

public class TbBiEcUnit  implements java.io.Serializable {


    // Fields    

     private String ecUnitCode;
     private String ecOperCode;
     private String ecAuthOper;
     private String col1;
     private String col2;
     private String col3;
     private String col4;


    // Constructors

    /** default constructor */
    public TbBiEcUnit() {
    }

	/** minimal constructor */
    public TbBiEcUnit(String ecUnitCode, String ecOperCode, String ecAuthOper) {
        this.ecUnitCode = ecUnitCode;
        this.ecOperCode = ecOperCode;
        this.ecAuthOper = ecAuthOper;
    }
    
    /** full constructor */
    public TbBiEcUnit(String ecUnitCode, String ecOperCode, String ecAuthOper, String col1, String col2, String col3, String col4) {
        this.ecUnitCode = ecUnitCode;
        this.ecOperCode = ecOperCode;
        this.ecAuthOper = ecAuthOper;
        this.col1 = col1;
        this.col2 = col2;
        this.col3 = col3;
        this.col4 = col4;
    }

   
    // Property accessors

    public String getEcUnitCode() {
        return this.ecUnitCode;
    }
    
    public void setEcUnitCode(String ecUnitCode) {
        this.ecUnitCode = ecUnitCode;
    }

    public String getEcOperCode() {
        return this.ecOperCode;
    }
    
    public void setEcOperCode(String ecOperCode) {
        this.ecOperCode = ecOperCode;
    }

    public String getEcAuthOper() {
        return this.ecAuthOper;
    }
    
    public void setEcAuthOper(String ecAuthOper) {
        this.ecAuthOper = ecAuthOper;
    }

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
   








}