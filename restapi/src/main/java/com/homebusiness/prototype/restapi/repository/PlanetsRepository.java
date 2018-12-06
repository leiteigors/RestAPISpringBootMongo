package com.homebusiness.prototype.restapi.repository;


import org.springframework.data.mongodb.repository.MongoRepository;

import com.homebusiness.prototype.restapi.model.Planets;

public interface PlanetsRepository extends MongoRepository<Planets, String>{

}
