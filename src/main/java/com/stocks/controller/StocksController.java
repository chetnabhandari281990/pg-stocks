package com.stocks.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.stocks.exception.StockRunTimeException;
import com.stocks.model.Stock;
import com.stocks.response.SuccessResponse;
import com.stocks.service.StockService;
import com.stocks.util.AppConstants;
import com.stocks.util.CommonUtils;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/v1/stocks")
public class StocksController {
	
	private static final Logger logger = LoggerFactory.getLogger(StocksController.class);
	
	@Autowired
	private StockService stockSvc;
	
	@ApiOperation(value = "Create a new stock")
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> createStock(@Valid @RequestBody Stock stock,  Errors errors) throws Exception {
		logger.debug("createStock :: entering with request: " + CommonUtils.toJson(stock));
        //If error, just return a 400 bad request
        CommonUtils.validateRequestBody(errors);
        stock = stockSvc.saveOrUpdate(stock);
        SuccessResponse<Stock> result = new SuccessResponse<Stock>(stock);

        return ResponseEntity.ok(result);
        
	}


	
	@ApiOperation(value = "Get stock by stockId")
	@RequestMapping(method = RequestMethod.GET, value = "/{stockId}")
	public ResponseEntity<?> fetchStockById(@PathVariable Long stockId) throws Exception {
		logger.debug("fetchStockById :: entering with stockId: {}", stockId);
		Stock stock = stockSvc.findById(stockId);
		SuccessResponse<Stock> result = new SuccessResponse<Stock>(stock);
		if(stock != null) {
			return ResponseEntity.ok(result);
		} else {
			// Custom exception
			throw new StockRunTimeException(AppConstants.ERROR_CODE_STOCK_NOT_FOUND,"Invalid stock Id");
		}
		
	}
	@ApiOperation(value = "Modify stock by stockId")
	@RequestMapping(method = RequestMethod.PUT, value = "/{stockId}")
	public ResponseEntity<?> modifyStockById( @PathVariable Long stockId, @Valid @RequestBody Stock editStock, Errors errors) throws Exception {
		logger.debug("modifyStockById :: entering with editStock: {}", editStock);
		
		//If error, just return a 400 bad request
		CommonUtils.validateRequestBody(errors);
       
		editStock = stockSvc.findAndModifyStock(stockId, editStock);
        SuccessResponse<Stock> result = new SuccessResponse<Stock>(editStock);
        return ResponseEntity.ok(result);
		
	}
	

	
	@ApiOperation(value = "Fetch List Of Stocks")
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> getAllStocks() {
		logger.debug("getAllStocks :: entering getAllStocks:");
		SuccessResponse<Iterable<Stock>> result = new SuccessResponse<Iterable<Stock>>(stockSvc.findAll());
		return ResponseEntity.ok(result);
	}
		
	
	@ApiOperation(value = "Delete Stock by Id")
	@RequestMapping(method = RequestMethod.DELETE,  value = "/{stockId}")
	public ResponseEntity<?> deleteStock(@PathVariable Long stockId) {
		logger.debug("deleteStock ::stockId {} :", stockId);
		stockSvc.delete(stockId);
		SuccessResponse<String> result = new SuccessResponse<String>("Stock successfully deleted");
		return ResponseEntity.ok(result);
	}
		
	
	
	

}
