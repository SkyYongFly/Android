package com.example.daomain;

/**
 * 封装服务器返回的body中的element信息
 * @author yzas
 *
 */
public class LotteryMessage {

	private String lotteryid;
	private String lotteryname;
	private String issue;
	private String lasttime;
	
	
	
	public LotteryMessage() {
		super();
	}
	public LotteryMessage(String lotteryid, String lotteryname, String issue, String lasttime) {
		super();
		this.lotteryid = lotteryid;
		this.lotteryname = lotteryname;
		this.issue = issue;
		this.lasttime = lasttime;
	}
	public String getLotteryid() {
		return lotteryid;
	}
	public void setLotteryid(String lotteryid) {
		this.lotteryid = lotteryid;
	}
	public String getLotteryname() {
		return lotteryname;
	}
	public void setLotteryname(String lotteryname) {
		this.lotteryname = lotteryname;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getLasttime() {
		return lasttime;
	}
	public void setLasttime(String lasttime) {
		this.lasttime = lasttime;
	}
	@Override
	public String toString() {
		return "LotteryMessage [lotteryid=" + lotteryid + ", lotteryname=" + lotteryname + ", issue=" + issue
				+ ", lasttime=" + lasttime + "]";
	}
	
	
	

}
