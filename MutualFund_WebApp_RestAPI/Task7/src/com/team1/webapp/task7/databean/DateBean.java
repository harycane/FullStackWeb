package com.team1.webapp.task7.databean;

import java.sql.Date;

import org.genericdao.PrimaryKey;

@PrimaryKey("dateid")
public class DateBean {
	public int getDateid() {
		return dateid;
	}
	public Date getDate() {
		return date;
	}
	public void setDateid(int dateid) {
		this.dateid = dateid;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	private int dateid;
	private Date date;
}
