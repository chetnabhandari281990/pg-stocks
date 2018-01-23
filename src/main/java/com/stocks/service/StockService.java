package com.stocks.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stocks.exception.StockRunTimeException;
import com.stocks.model.Stock;
import com.stocks.repository.StockRepository;
import com.stocks.util.AppConstants;
import com.stocks.util.CommonUtils;

@Service
public class StockService {
	
	@Autowired
	private SequenceGenerator seqGen;
	
	public static final String seqKey = "stockId";
	
	@Autowired
	private StockRepository stockRepo;
	
	// In memory map
	private Map<Long, Stock> inMemoryStockMap = new HashMap<Long, Stock>();
	
	
	@PostConstruct
	public void init() {
		ArrayList<Stock> stocks = (ArrayList<Stock>) stockRepo.findAll();
		if(org.apache.commons.collections.CollectionUtils.isNotEmpty(stocks)) {
			for(Stock s : stocks) {
				// Populate inmemory map
				inMemoryStockMap.put(s.getId(), s);
			}
		}
	}

	
	

	public Stock findById(Long id) {
		return inMemoryStockMap.get(id);

	}
	
	public Stock findAndModifyStock(Long stockId, Stock editedStock) {
		Stock existingStock = inMemoryStockMap.get(stockId);
		if(existingStock == null) {
			throw new StockRunTimeException(AppConstants.ERROR_CODE_STOCK_NOT_FOUND,"Invalid Stock Id");
		}
		editedStock.setId(stockId);
		editedStock.setCreatedAt(existingStock.getCreatedAt());
		return saveOrUpdate(editedStock);
		
	}
	
	public Stock saveOrUpdate(Stock stock) {
		if (stock.getId() == null) {
			// generate auto-id for new save
			stock.setId(seqGen.generateSequence(seqKey));
			stock.setCreatedAt(CommonUtils.getCurrentTime());
		}
		stock.setUpdatedAt(CommonUtils.getCurrentTime());
		stock = stockRepo.save(stock);
		inMemoryStockMap.put(stock.getId(), stock);
		return stock;

	}
	
	public void delete(Long stockId) {
		if(inMemoryStockMap.get(stockId) != null) {
			Stock stock = inMemoryStockMap.get(stockId);
			stockRepo.delete(stock);
			inMemoryStockMap.remove(stockId);
		} else {
			throw new StockRunTimeException(AppConstants.ERROR_CODE_STOCK_NOT_FOUND,"Invalid Stock Id");
		}
		

	}

	public Iterable<Stock> findAll() {
		return inMemoryStockMap.values();

	}

}
