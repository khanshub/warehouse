package com.example.warehouse.warehouse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.warehouse.warehouse.entity.Product;
import com.example.warehouse.warehouse.entity.WareHouse;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING )
public class WarehouseApplicationTests {
	
	/*
	 * mocks the complete mvc flow to execute the testcase 
	 */
	private MockMvc mockMvc;
	
	/*
	 * creates application context 
	 */
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	/*
	 * building mockmvc
	 */
	@Before
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	/*
	 * testing the warehouse creation service
	 */
	@Test
	public void AcreateWareHouse() throws Exception {
		int capacity = 2;
		String uri = "/warehouse?wareHouseCapacity=" + capacity;
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals("Created a warehouse with " + capacity + " slots", content);
	}

	/*
	 * tests store product service 
	 * tests whether products getting stored to the first available slot only
	 * tests if warehouse if full
	 */
	@Test
	public void BstoreProduct() throws Exception {
		String uri = "/store";
		int slotNo=0;
		Long productCode = 72527273070l;
		for(int i=1;i<=3;i++){
			slotNo=i;
			Product product = new Product();
			product.setProductCode(productCode++);
			product.setProductColor("green");

			ObjectMapper objectMapper = new ObjectMapper();
			String productJson = objectMapper.writeValueAsString(product);
			
			MvcResult mvcResult = mockMvc.perform(
					MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(productJson))
					.andReturn();
			int status = mvcResult.getResponse().getStatus();
			assertEquals(200, status);
			String content = mvcResult.getResponse().getContentAsString();
			if(i<3){
				assertEquals("Allocated slot number: " + slotNo, content);
				
			}else{
				assertEquals("Warehouse is full or No slots Available", content);
			}
		}
	}

	/*
	 * tests get status service
	 */
	@Test
	public void CgetWareHouseStatus() throws Exception {
		String uri = "/status";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		WareHouse[] productlist = objectMapper.readValue(content, WareHouse[].class);
		assertTrue(productlist.length > 0);
	}

	/*
	 * tests get product codes by color service
	 */
	@Test
	public void DgetProductCodes() throws Exception {
		String productColor = "green";
		String uri = "/product_codes_for_products_with_colour?productColor=" + productColor;
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		Long[] productCodeslist = objectMapper.readValue(content, Long[].class);
		assertTrue(productCodeslist.length > 0);
	}
	
	/*
	 * tests get slot numbers by color service
	 */
	@Test
	public void EgetSlotNumbers() throws Exception {
		String productColor = "green";
		String uri = "/slot_numbers_for_products_with_colour?productColor=" + productColor;
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		ObjectMapper objectMapper = new ObjectMapper();
		Integer[] slotNumbersList = objectMapper.readValue(content, Integer[].class);
		assertTrue(slotNumbersList.length > 0);
	}

	/*
	 * tests get slot number by productCode service
	 */
	@Test
	public void FgetSlotNumber() throws Exception {
		Long productCode = 72527273070l;
		String expectedSlotNo = "" + 1;
		String uri = "/slot_number_for_product_code?productCode=" + productCode;
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(expectedSlotNo, content);
	}

	/*
	 * tests sell product service 
	 * tests whether products getting stored to the first available slot only
	 * tests if requested product is not found or warehouse if empty
	 */
	@Test
	public void GsellProduct() throws Exception {
			int slotNo = 0;
			for(int i=1;i<=3;i++){
				slotNo=i;
				String uri = "/sell?slotNo=" + slotNo;
				MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
				int status = mvcResult.getResponse().getStatus();
				assertEquals(200, status);
				String content = mvcResult.getResponse().getContentAsString();
				if(i<3){
					assertEquals("Slot number " + slotNo + " is free", content);
				}else{
					assertEquals("Not found or Warehouse is Empty", content);
					
				}
		}
	}

	@Test
	public void contextLoads() {

	}

}
