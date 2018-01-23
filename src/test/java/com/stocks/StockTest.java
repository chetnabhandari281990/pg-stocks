package com.stocks;

import java.lang.reflect.Type;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stocks.model.Stock;
import com.stocks.response.SuccessResponse;
import com.stocks.service.StockService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StocksApplication.class)
@WebAppConfiguration
public class StockTest {
	
	public static final String BASE_URI = "http://localhost:8080/v1/stocks";

	@Autowired
	private StockService stockService;
	
	@Autowired
	private RestTemplate restTemplate;

	@Test
	public void saveStock() throws Exception {
		Stock stock = new Stock("testStockSvc", 1d);
		Stock savedStock = stockService.saveOrUpdate(stock);
		Assert.assertTrue(savedStock != null && savedStock.getId() != null);
	}
	
	@Test
	public void getStockById() throws Exception {
		Stock savedStock = stockService.findById(1l);
		if(savedStock != null) {
			Assert.assertTrue(savedStock.getId().equals(1l));
		}
	}
	
	
	@Test
	public void testGetAllStocks() throws Exception {
		ResponseEntity<String> res = restTemplate.exchange(BASE_URI, HttpMethod.GET, null, String.class);
		Assert.assertTrue(res.getStatusCode().equals(HttpStatus.OK));
		
	}
	
	
	@Test
	public void testCreateStock() throws Exception {
		Stock stock = new Stock("testStock", 1d);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Stock> entity = new HttpEntity<>(stock, headers);
		ResponseEntity<String> res = restTemplate.exchange(BASE_URI, HttpMethod.POST, entity, String.class);
		Assert.assertTrue(res.getStatusCode().equals(HttpStatus.OK));
		
	}
	
	@Test
	public void testDeleteStockById() throws Exception {
		// CREATE NEW STOCK
		Stock stock = new Stock("testStockForDelete", 1d);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Stock> entity = new HttpEntity<>(stock, headers);
		ResponseEntity<String> res = restTemplate.exchange(BASE_URI, HttpMethod.POST, entity, String.class);
		
		if (res.getBody() != null) {
			Gson gson = new Gson();
			Type listType = new TypeToken<SuccessResponse<Stock>>(){}.getType();
			SuccessResponse<Stock> successResp = gson.fromJson(res.getBody(), listType);
			ResponseEntity<String> deleteResponse = restTemplate.exchange(BASE_URI + "/" + successResp.getBody().getId(),
					HttpMethod.DELETE, null, String.class);
			Assert.assertTrue(deleteResponse.getStatusCode().equals(HttpStatus.OK));
		}

	}
}
	
	
	
