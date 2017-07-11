package com.team1.webapp.task7.databean;

import org.genericdao.PrimaryKey;

public class ResearchFundBean {
 private int fundid;
 private String name;
 private String symbol;
 private FundPriceHistoryBean[] history;
 
 public int getFundid() {
     return fundid;
 }
 public void setFundid(int i) {
     fundid = i;
 }
 
 public String getName()        { return name; }
 public void setName(String s)  { name = s;    }

 public String getSymbol()        { return symbol; }
 public void setSymbol(String s)  {  symbol = s;    }
 
 public FundPriceHistoryBean[] getHistory() {
     return history;
 }
 public void setHistory(FundPriceHistoryBean[] h) {
     history = h;
 }
 
 
}
