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
	
	/*
	 * creates a warehouse with capacity passed as a query parameter
	 */
	@PostMapping("/warehouse")
	public String createWareHouse(@RequestParam("wareHouseCapacity") Integer wareHouseCapacity) {

		Integer createdSlotCount = wareHouseService.createWareHouse(wareHouseCapacity);
		return "Created a warehouse with " + createdSlotCount + " slots";

	}

	/*
	 * store a product to warehouse and returns the slotno where the product has been stored
	 */
	@PostMapping("/store")
	public String storeProduct(@RequestBody Product product) {
		String responseMessage = wareHouseService.storeProduct(product);
		return responseMessage;

	}

	/*
	 * sells a product from warehouse and sets the slot free
	 */
	@DeleteMapping("/sell")
	public String sellProduct(@RequestParam("slotNo") Integer slotNo) {
		String ResponseMessage = wareHouseService.sellProduct(slotNo);
		return ResponseMessage;
	}

	/*
	 * gets the status of warehouse which contains the product details with respective allocated slotno
	 */
	@GetMapping("/status")
	public List<WareHouse> getWareHouseStatus() {
		return wareHouseService.getWareHouseStatus();
	}

	/*
	 * get product codes for products with  a perticuer colour
	 */
	@GetMapping("/product_codes_for_products_with_colour")
	public List<Long> getProductCodes(@RequestParam("productColor") String productColor) {
		return wareHouseService.getProductCodesByColor(productColor);
	}

	/*
	 * get allocated slot numbers for products with  a perticuer colour
	 */
	@GetMapping("/slot_numbers_for_products_with_colour")
	public List<Integer> getSlotNumbers(@RequestParam("productColor") String productColor) {
		return wareHouseService.getSlotNumbersByColor(productColor);
	}
	
	/*
	 * get allocated slot number for  a product
	 */
	@GetMapping("/slot_number_for_product_code")
	public String getSlotNumber(@RequestParam("productCode") Long productCode) {
		Integer slotnumber = wareHouseService.getSlotNumberByProductCode(productCode);
		if (null != slotnumber) {
			return "" + slotnumber;
		}
		return "Not found";
	}

}
