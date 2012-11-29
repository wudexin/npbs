package com.nantian.npbs.business.model;



/**
 * TbBiTradeBusiId entity. @author MyEclipse Persistence Tools
 */

public class TbBiTradeBusiId  implements java.io.Serializable {


    // Fields    

     private String pbSerial;
     private String tradeDate;


    // Constructors

    /** default constructor */
    public TbBiTradeBusiId() {
    }

    
    /** full constructor */
    public TbBiTradeBusiId(String pbSerial, String tradeDate) {
        this.pbSerial = pbSerial;
        this.tradeDate = tradeDate;
    }

   
    // Property accessors

    public String getPbSerial() {
        return this.pbSerial;
    }
    
    public void setPbSerial(String pbSerial) {
        this.pbSerial = pbSerial;
    }

    public String getTradeDate() {
        return this.tradeDate;
    }
    
    public void setTradeDate(String tradeDate) {
        this.tradeDate = tradeDate;
    }
   



   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TbBiTradeBusiId) ) return false;
		 TbBiTradeBusiId castOther = ( TbBiTradeBusiId ) other; 
         
		 return ( (this.getPbSerial()==castOther.getPbSerial()) || ( this.getPbSerial()!=null && castOther.getPbSerial()!=null && this.getPbSerial().equals(castOther.getPbSerial()) ) )
 && ( (this.getTradeDate()==castOther.getTradeDate()) || ( this.getTradeDate()!=null && castOther.getTradeDate()!=null && this.getTradeDate().equals(castOther.getTradeDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + ( getPbSerial() == null ? 0 : this.getPbSerial().hashCode() );
         result = 37 * result + ( getTradeDate() == null ? 0 : this.getTradeDate().hashCode() );
         return result;
   }   





}