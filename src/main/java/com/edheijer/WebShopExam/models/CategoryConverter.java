package com.edheijer.WebShopExam.models;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import java.util.stream.*;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<ProductCategory, String>{
	
	@Override
    public String convertToDatabaseColumn(ProductCategory category) {
        if (category == null) {
            return null;
        }
        return category.getCode();
    }
 
    @Override
    public ProductCategory convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
 
        return Stream.of(ProductCategory.values())
          .filter(c -> c.getCode().equals(code))
          .findFirst()
          .orElseThrow(IllegalArgumentException::new);
    }
}
