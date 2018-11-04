package com.example.warehouse.warehouse.service;

import java.util.List;

import com.example.warehouse.warehouse.entity.Product;
import com.example.warehouse.warehouse.entity.WareHouse;

public interface WareHouseService {

	Integer createWareHouse(Integer wareHouseCapacity);

	String storeProduct(Product product);

	String sellProduct(Integer slotNo);

	List<WareHouse> getWareHouseStatus();

	List<Long> getProductCodesByColor(String productColor);

	List<Integer> getSlotNumbersByColor(String productColor);

	Integer getSlotNumberByProductCode(Long productCode);

}
