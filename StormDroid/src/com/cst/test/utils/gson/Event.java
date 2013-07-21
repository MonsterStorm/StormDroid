package com.cst.test.utils.gson;

import com.cst.test.utils.gson.Field.Field_Address;
import com.cst.test.utils.gson.Field.Field_Contact;
import com.cst.test.utils.gson.Field.Field_End;
import com.cst.test.utils.gson.Field.Field_Photos;
import com.cst.test.utils.gson.Field.Field_Poster;
import com.cst.test.utils.gson.Field.Field_Start;
import com.cst.test.utils.gson.Field.Field_Type;

/**
 * activity created by user
 * @author MonsterStorm
 * 
 */
public class Event {
	private Node node;// ���ݣ�Node�� ���ݣ�Node����
	private String field_desc;// ����飨field_desc�� ����顣
	private Field_Type field_type;// ����ͣ�field_type�� ����͡�ֵΪ��1������2������3����
	private Field_Poster field_poster;// �������field_poster�� �������
	private Field_Photos field_photos;// ���Ƭ��field_photos�� �������Ƭ��
	private Field_Start field_start;// ��ʼʱ�䣨field_start�� ���ʼʱ�䡣
	private Field_End field_end;// ����ʱ�䣨field_end�� �����ʱ�䡣
	private Field_Address field_address;// ���ַ��field_ address�� �����ʱ�䡣
	private Field_Contact field_contact;// ������ʽ��field_contact�� �������ʽ��

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public String getField_desc() {
		return field_desc;
	}

	public void setField_desc(String field_desc) {
		this.field_desc = field_desc;
	}

	public Field_Type getField_type() {
		return field_type;
	}

	public void setField_type(Field_Type field_type) {
		this.field_type = field_type;
	}

	public Field_Poster getField_poster() {
		return field_poster;
	}

	public void setField_poster(Field_Poster field_poster) {
		this.field_poster = field_poster;
	}

	public Field_Photos getField_photos() {
		return field_photos;
	}

	public void setField_photos(Field_Photos field_photos) {
		this.field_photos = field_photos;
	}

	public Field_Start getField_start() {
		return field_start;
	}

	public void setField_start(Field_Start field_start) {
		this.field_start = field_start;
	}

	public Field_End getField_end() {
		return field_end;
	}

	public void setField_end(Field_End field_end) {
		this.field_end = field_end;
	}

	public Field_Address getField_address() {
		return field_address;
	}

	public void setField_address(Field_Address field_address) {
		this.field_address = field_address;
	}

	public Field_Contact getField_contact() {
		return field_contact;
	}

	public void setField_contact(Field_Contact field_contact) {
		this.field_contact = field_contact;
	}

}