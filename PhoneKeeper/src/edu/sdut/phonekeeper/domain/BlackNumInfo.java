package edu.sdut.phonekeeper.domain;

/**
 * ������
 * @author Administrator
 *
 */
public class BlackNumInfo {
	private int _id;		// ID
	private String num;		// ����
	
	// ���캯��
	public BlackNumInfo(int _id, String num) {
		super();
		this._id = _id;
		this.num = num;
	}
	
	// getter And setter
	public int get_id() {
		return _id;
	}
	public void set_id(int _id) {
		this._id = _id;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	
}
