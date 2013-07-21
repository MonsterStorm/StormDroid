package com.cst.test.utils.gson;
/**
 * base node 
 * @author MonsterStorm
 * @version 1.0
 */
public class Node {
	private Long nid;// int(10) 内容ID。
	private Long vid;// int(10) 内容版本ID。
	private String type;// varchar(32) 内容类型。内容是活动=event。
	private String language;// varchar(12) 内容语言。
	private String title;// varchar(255) 内容标题。内容是活动，此处为活动名称。
	private Long uid;// int(11) 内容的作者。内容是活动，此处为发起人用户ID。
	private Long status;// int(11) 内容状态。0表示内容没发布。1表示内容已发布可以被用户查看。
	private Long created;// int(11) 内容创建时间。
	private Long changed;// int(11) 内容最近修改时间。
	private Long comment;// int(11) 内容被评论的状态。0表示内容禁止评论。1表示评论只读，不能添加新评论。2表示评论可以被查看，用户可以创建新评论。
	private Long promote;// int(11) 表示内容是否在网站首页显示。1表示是，0表示否。
	private Long sticky;// int(11) 用户内容排序置顶。1表示是，0表示否。
	private Long tnid;// int(10) 内容的翻译版本的ID。
	private Long translate;// int(11) 翻译更新状态，1表示需要更新。0表示已经更新。

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
