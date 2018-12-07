package com.homebusiness.prototype.restapi.repository;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.homebusiness.prototype.restapi.model.Planets;

public interface PlanetsRepository extends MongoRepository<Planets, String>{
	
	@Query ("{name:'?0'}")
	Planets findCustomByName(String name);

}
