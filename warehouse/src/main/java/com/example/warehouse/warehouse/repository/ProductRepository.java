package com.example.warehouse.warehouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.warehouse.warehouse.entity.Product;
import com.example.warehouse.warehouse.entity.Rack;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer>,CustomProductRepository {

	public void deleteByRack(Rack rack);
	
	public Product getByProductCode(Long productCode);
	
	public List<Product> getByProductColor(String productColor);
	
	
}
