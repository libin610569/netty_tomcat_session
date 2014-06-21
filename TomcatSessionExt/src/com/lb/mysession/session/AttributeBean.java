package com.lb.mysession.session;

public class AttributeBean {
	private String id;
	private String key;
	private Object value;
	public AttributeBean(String id,String key  ){
		this.id= id;
		this.key= key;
	}
	public AttributeBean(String id,String key,Object value ){
		this.id= id;
		this.key= key;

		this.value= value;
	}

	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
}
