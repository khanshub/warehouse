package com.example.warehouse.warehouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.warehouse.warehouse.entity.Product;
import com.example.warehouse.warehouse.entity.WareHouse;
import com.example.warehouse.warehouse.service.WareHouseService;

@RestController
public class WareHouseController {

	@Autowired
	private WareHouseService wareHouseService;
	

	@PostMapping("/warehouse")
	public String createWareHouse(@RequestParam("wareHouseCapacity") Integer wareHouseCapacity) {

		Integer createdSlotCount = wareHouseService.createWareHouse(wareHouseCapacity);
		return "Created a warehouse with " + createdSlotCount + " slots";

	}


	@PostMapping("/store")
	public String storeProduct(@RequestBody Product product) {
		String responseMessage = wareHouseService.storeProduct(product);
		return responseMessage;

	}


	@DeleteMapping("/sell")
	public String sellProduct(@RequestParam("slotNo") Integer slotNo) {
		String ResponseMessage = wareHouseService.sellProduct(slotNo);
		return ResponseMessage;
	}


	@GetMapping("/status")
	public List<WareHouse> getWareHouseStatus() {
		return wareHouseService.getWareHouseStatus();
	}


	@GetMapping("/product_codes_for_products_with_colour")
	public List<Long> getProductCodes(@RequestParam("productColor") String productColor) {
		return wareHouseService.getProductCodesByColor(productColor);
	}


	@GetMapping("/slot_numbers_for_products_with_colour")
	public List<Integer> getSlotNumbers(@RequestParam("productColor") String productColor) {
		return wareHouseService.getSlotNumbersByColor(productColor);
	}
	

	@GetMapping("/slot_number_for_product_code")
	public String getSlotNumber(@RequestParam("productCode") Long productCode) {
		Integer slotnumber = wareHouseService.getSlotNumberByProductCode(productCode);
		if (null != slotnumber) {
			return "" + slotnumber;
		}
		return "Not found";
	}

}
