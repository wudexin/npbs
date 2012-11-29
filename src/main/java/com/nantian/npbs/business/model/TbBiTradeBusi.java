package com.nantian.npbs.business.model;



/**
 * TbBiTradeBusi entity. @author MyEclipse Persistence Tools
 */

public class TbBiTradeBusi  implements java.io.Serializable {


    // Fields    

     private TbBiTradeBusiId id;
     private String companyCode;
     private String companyName;
     private String contact;
     private String auid;
     private String agent;
     private String agentauid;
     private String remark;
     private String tradeTime;


    // Constructors

    /** default constructor */
    public TbBiTradeBusi() {
    }

	/** minimal constructor */
    public TbBiTradeBusi(TbBiTradeBusiId id) {
        this.id = id;
    }
    
    /** full constructor */
    public TbBiTradeBusi(TbBiTradeBusiId id, String companyCode, String companyName, String contact, String auid, String agent, String agentauid, String remark, String tradeTime) {
        this.id = id;
        this.companyCode = companyCode;
        this.companyName = companyName;
        this.contact = contact;
        this.auid = auid;
        this.agent = agent;
        this.agentauid = agentauid;
        this.remark = remark;
        this.tradeTime = tradeTime;
    }

   
    // Property accessors

    public TbBiTradeBusiId getId() {
        return this.id;
    }
    
    public void setId(TbBiTradeBusiId id) {
        this.id = id;
    }

    public String getCompanyCode() {
        return this.companyCode;
    }
    
    public void setCompanyCode(String companyCode) {
        this.companyCode = companyCode;
    }

    public String getCompanyName() {
        return this.companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContact() {
        return this.contact;
    }
    
    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAuid() {
        return this.auid;
    }
    
    public void setAuid(String auid) {
        this.auid = auid;
    }

    public String getAgent() {
        return this.agent;
    }
    
    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAgentauid() {
        return this.agentauid;
    }
    
    public void setAgentauid(String agentauid) {
        this.agentauid = agentauid;
    }

    public String getRemark() {
        return this.remark;
    }
    
    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTradeTime() {
        return this.tradeTime;
    }
    
    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }
   








}