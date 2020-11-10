package com.edheijer.WebShopExam.models;

public enum ProductCategory {
	SUPPLEMENT("S"),CLOTHES("C"),TRAINING_SHOES("T");
	
	private String code;
	
	private ProductCategory(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}
	
}
