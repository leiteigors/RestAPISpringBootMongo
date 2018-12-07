package com.homebusiness.prototype.restapi.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.homebusiness.prototype.restapi.model.PlanetRec;
import com.homebusiness.prototype.restapi.model.Planets;
import com.homebusiness.prototype.restapi.repository.PlanetsRepository;

@RestController
@RequestMapping("/planets")
public class RestAPIController {
	
	List<Planets> planetsList = new ArrayList<>();
	
	@Autowired
	PlanetsRepository planetsRepository;
	
	//create
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> create(@RequestBody Planets planets) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(org.springframework.http.MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
        
		ResponseEntity<PlanetRec> response = restTemplate.exchange("https://swapi.co/api/planets/" + planets.getId() , HttpMethod.GET , entity, new ParameterizedTypeReference<PlanetRec>(){});
		PlanetRec myPlanetRec = new PlanetRec();
		myPlanetRec = response.getBody();
		

		System.out.println(myPlanetRec.getFilms().size());

		planets.setCountFilms(myPlanetRec.getFilms().size());
		planetsRepository.save(planets);
		
		return new ResponseEntity<>("New planet added with successfully",HttpStatus.CREATED);
		
	}
	  
	//all
	@RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Planets>> findAll(){
            return new ResponseEntity<>(planetsRepository.findAll(),HttpStatus.OK);
    }
	
	//byName
	@SuppressWarnings("null")
	@RequestMapping(value="/name/{name}", method = RequestMethod.GET)
	public ResponseEntity<Planets> getPlanetsByName(@PathVariable ("name") String name) {

		
		Planets planets = planetsRepository.findCustomByName(name);
		return new ResponseEntity<>(planets, HttpStatus.OK);
		
	}

	//byId
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public ResponseEntity<Planets> getPlanetsById(@PathVariable ("id") String id)  {
		return planetsRepository.findById(id)
				.map(planetsMap -> ResponseEntity.ok().body(planetsMap))
				.orElse(ResponseEntity.notFound().build());
	}
	


	
	//update
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Planets> update(@PathVariable ("id") String id,
										 @Valid @RequestBody Planets planets) {
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(org.springframework.http.MediaType.APPLICATION_JSON));
        headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
		
        
		ResponseEntity<PlanetRec> response = restTemplate.exchange("https://swapi.co/api/planets/" + planets.getId() , HttpMethod.GET , entity, new ParameterizedTypeReference<PlanetRec>(){});
		PlanetRec myPlanetRec = new PlanetRec();
		myPlanetRec = response.getBody();
		
		planets.setCountFilms(myPlanetRec.getFilms().size());
		int total = myPlanetRec.getFilms().size();

		return planetsRepository.findById(id)
                .map(planetData -> {
                    planetData.setName(planets.getName());
                    planetData.setClimate(planets.getClimate());
                    planetData.setLand(planets.getLand());
                    planetData.setCountFilms(total);
                    Planets updatedPlanets = planetsRepository.save(planetData);
                    return ResponseEntity.ok().body(updatedPlanets);
                }).orElse(ResponseEntity.notFound().build());
		
	}
	
	//delete
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<?> delete(@PathVariable("id")String id) {
		ResponseEntity<Planets> planets = getPlanetsById(id);
		
		planetsRepository.deleteById(planets.getBody().getId());		
		return new ResponseEntity<>("Planet deleted with successfully",HttpStatus.OK);
		}
}
