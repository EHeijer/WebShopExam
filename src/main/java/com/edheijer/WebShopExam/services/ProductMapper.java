package com.edheijer.WebShopExam.services;

import org.mapstruct.Mapper;

import com.edheijer.WebShopExam.dto.ProductDTO;
import com.edheijer.WebShopExam.models.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper extends EntityMapper<ProductDTO, Product>{

}
