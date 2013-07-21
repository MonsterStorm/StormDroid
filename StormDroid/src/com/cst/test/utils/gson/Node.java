package com.cst.test.utils.gson;
/**
 * base node 
 * @author MonsterStorm
 * @version 1.0
 */
public class Node {
	private Long nid;// int(10) ����ID��
	private Long vid;// int(10) ���ݰ汾ID��
	private String type;// varchar(32) �������͡������ǻ=event��
	private String language;// varchar(12) �������ԡ�
	private String title;// varchar(255) ���ݱ��⡣�����ǻ���˴�Ϊ����ơ�
	private Long uid;// int(11) ���ݵ����ߡ������ǻ���˴�Ϊ�������û�ID��
	private Long status;// int(11) ����״̬��0��ʾ����û������1��ʾ�����ѷ������Ա��û��鿴��
	private Long created;// int(11) ���ݴ���ʱ�䡣
	private Long changed;// int(11) ��������޸�ʱ�䡣
	private Long comment;// int(11) ���ݱ����۵�״̬��0��ʾ���ݽ�ֹ���ۡ�1��ʾ����ֻ����������������ۡ�2��ʾ���ۿ��Ա��鿴���û����Դ��������ۡ�
	private Long promote;// int(11) ��ʾ�����Ƿ�����վ��ҳ��ʾ��1��ʾ�ǣ�0��ʾ��
	private Long sticky;// int(11) �û����������ö���1��ʾ�ǣ�0��ʾ��
	private Long tnid;// int(10) ���ݵķ���汾��ID��
	private Long translate;// int(11) �������״̬��1��ʾ��Ҫ���¡�0��ʾ�Ѿ����¡�

	public Long getNid() {
		return nid;
	}

	public void setNid(Long nid) {
		this.nid = nid;
	}

	public Long getVid() {
		return vid;
	}

	public void setVid(Long vid) {
		this.vid = vid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getUid() {
		return uid;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Long getCreated() {
		return created;
	}

	public void setCreated(Long created) {
		this.created = created;
	}

	public Long getChanged() {
		return changed;
	}

	public void setChanged(Long changed) {
		this.changed = changed;
	}

	public Long getComment() {
		return comment;
	}

	public void setComment(Long comment) {
		this.comment = comment;
	}

	public Long getPromote() {
		return promote;
	}

	public void setPromote(Long promote) {
		this.promote = promote;
	}

	public Long getSticky() {
		return sticky;
	}

	public void setSticky(Long sticky) {
		this.sticky = sticky;
	}

	public Long getTnid() {
		return tnid;
	}

	public void setTnid(Long tnid) {
		this.tnid = tnid;
	}

	public Long getTranslate() {
		return translate;
	}

	public void setTranslate(Long translate) {
		this.translate = translate;
	}

}
