package com.example.warehouse.warehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.warehouse.warehouse.entity.Rack;

@Repository
public interface RackRepository extends JpaRepository<Rack,Long>,CustomRackRepository{
	
	public Rack getBySlotNo(Integer slotNo);

}
