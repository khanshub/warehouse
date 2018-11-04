package com.example.warehouse.warehouse.repository;

import java.util.List;

import com.example.warehouse.warehouse.entity.Rack;

public interface CustomRackRepository {
	
	public List<Rack> createWareHouse(final Integer wareHouseCapacity);
	
	public Rack getRacksByStatus(Integer status);

}
