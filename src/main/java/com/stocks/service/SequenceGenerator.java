package com.stocks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.stocks.model.Sequence;

@Service
public class SequenceGenerator {

	@Autowired
	private MongoOperations mongoOperations;

	public Long generateSequence(String key) {
		Sequence seq = mongoOperations.findAndModify(new Query(Criteria.where("_id").is(key)),
						new Update().inc("seq", 1),
						new FindAndModifyOptions().returnNew(true),
						Sequence.class);
		return seq.getSeq();
	}
	
}
