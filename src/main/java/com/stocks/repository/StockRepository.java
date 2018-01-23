package com.stocks.repository;

import java.io.Serializable;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.stocks.model.Stock;

public interface StockRepository extends MongoRepository<Stock, Serializable>{

}
