package com.team1.webapp.task7.databean;

import java.sql.Date;

import org.genericdao.PrimaryKey;

//no space in between composite PK, otherwise program will be bugged
@PrimaryKey("fundid,pricedate")
public class FundPriceHistoryBean {
	private int fundid;
	private Date pricedate;
	private double price;
	
	 public int getFundid() {
	     return fundid;
	 }
	 public void setFundid(int i) {
	     fundid = i;
	 }
	 
	 public Date getPricedate()        { return pricedate; }
	 public void setPricedate(Date s)  { pricedate = s;    }
	 
	 public double getPrice() {
	     return price;
	 }
	 public void setPrice(double i) {
	     price = i;
	 }

}
