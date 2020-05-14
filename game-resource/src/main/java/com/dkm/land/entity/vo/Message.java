/**
 * 
 */
package com.dkm.land.entity.vo;

/**
 * @author liandyao
 * @date 2018年6月22日 下午8:18:05
 * @version 1.0
 */
public class Message {

	private Integer num;
	private String msg;
	
	
	
	
	public Message() {
		super();
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Message(int state, String msg) {
		super();
		this.num = state;
		this.msg = msg;
	}
	
}
