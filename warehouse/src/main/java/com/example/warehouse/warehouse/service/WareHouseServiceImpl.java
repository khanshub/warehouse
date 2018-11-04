package com.example.warehouse.warehouse.service;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.warehouse.warehouse.entity.Product;
import com.example.warehouse.warehouse.entity.Rack;
import com.example.warehouse.warehouse.entity.WareHouse;
import com.example.warehouse.warehouse.repository.ProductRepository;
import com.example.warehouse.warehouse.repository.RackRepository;

@Service
public class WareHouseServiceImpl implements WareHouseService{

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private RackRepository rackRepository;

	@Override
	@Transactional
	public Integer createWareHouse(Integer wareHouseCapacity) {
		Integer createdSlotCount=0;
		List<Rack> resultedlist=rackRepository.createWareHouse(wareHouseCapacity);
		if(null!=resultedlist){
			createdSlotCount=resultedlist.size();
		}
		return createdSlotCount;
	}

	@Override
	@Transactional
	public String storeProduct(Product product) {
		Integer slotStatus=0;
		String responseMessage=null;
		
		Rack firstAvailableRack=rackRepository.getRacksByStatus(slotStatus);
		if(null!=firstAvailableRack) {
			product.setRack(firstAvailableRack);
			Product resultedProduct=productRepository.save(product);
			slotStatus=1;
			if(null!=resultedProduct) {
				responseMessage= "Allocated slot number: "+firstAvailableRack.getSlotNo();
			}else {
				responseMessage="something went wrong";
			}
			firstAvailableRack.setSlotStatus(slotStatus);
			rackRepository.save(firstAvailableRack);
		}else {
			responseMessage= "Warehouse is full";
		}
		return responseMessage;
	}

	@Override
	@Transactional
	public String sellProduct(Integer slotNo) {
		String respnseMessage=null;
		Rack rack=rackRepository.getBySlotNo(slotNo);
		if(null!=rack) {
			productRepository.deleteByRack(rack);
			rack.setSlotStatus(0);
			Rack updatedRack=rackRepository.save(rack);
			if(null!=updatedRack)
				respnseMessage="Slot number "+slotNo+" is free";
			else
				respnseMessage="something went wrong";
		}else {
			respnseMessage="not found";
		}
		return respnseMessage;
	}

	@Override
	@Transactional
	public List<WareHouse> getWareHouseStatus() {
		List<WareHouse> warehouseList=new ArrayList<>();
		List<Product> products= productRepository.findAll();
		if(!products.isEmpty()) {
			for(Product product:products) {
				WareHouse 	wareHouse=new WareHouse();
				wareHouse.setSlotNo(product.getRack().getSlotNo());
				wareHouse.setProductCode(product.getProductCode());
				wareHouse.setColor(product.getProductColor());
				warehouseList.add(wareHouse);
			}
		}
	return	warehouseList;
	}

	@Override
	@Transactional
	public List<Long> getProductCodesByColor(String productColor) {
		List<Long> productCodes=new ArrayList<>();
		List<Product> products= productRepository.getByProductColor(productColor);
		if(!products.isEmpty()) {
			for(Product product:products) {
				productCodes.add(product.getProductCode());
			}
		}
		return productCodes;
	}

	@Override
	@Transactional
	public List<Integer> getSlotNumbersByColor(String productColor) {
		List<Integer> productColors=new ArrayList<>();
		List<Product> products= productRepository.getByProductColor(productColor);
		if(!products.isEmpty()) {
			for(Product product:products) {
				productColors.add(product.getRack().getSlotNo());
			}
		}
		return productColors;
	}

	@Override
	@Transactional
	public Integer getSlotNumberByProductCode(Long productCode) {
		Integer resultedProductCode=null;
		Product product=productRepository.getByProductCode(productCode);
		if(null!=product) {
			resultedProductCode=product.getRack().getSlotNo();
		}
		return resultedProductCode;
	}
	
}
