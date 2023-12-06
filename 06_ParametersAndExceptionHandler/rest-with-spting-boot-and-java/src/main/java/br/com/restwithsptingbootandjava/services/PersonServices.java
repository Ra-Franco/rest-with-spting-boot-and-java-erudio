package br.com.restwithsptingbootandjava.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.restwithsptingbootandjava.data.vo.v1.PersonVO;
import br.com.restwithsptingbootandjava.exceptions.ResourceNotFoundException;
import br.com.restwithsptingbootandjava.mapper.ModelMapperConfig;
import br.com.restwithsptingbootandjava.model.Person;
import br.com.restwithsptingbootandjava.repositories.PersonRepository;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	@Autowired
	public List<PersonVO> findAll() {
		
		return ModelMapperConfig.parseListObjects(repository.findAll(), PersonVO.class);
	}

	public PersonVO findById(Long id) {
		logger.info("Finding one person.");
		var entity =  repository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("No records found for this ID: " + id));
		return ModelMapperConfig.parseObject(entity, PersonVO.class);
	}
	
	public PersonVO update(PersonVO person) {
		logger.info("Update person id: " + person.getId());
		var entity = repository.findById(person.getId())
		.orElseThrow(()-> new ResourceNotFoundException("No records found for this Id: " + person.getId()));
		
		entity.setFirstName(person.getFirstName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo = repository.save(entity);
		return ModelMapperConfig.parseObject(vo, PersonVO.class);
	}
	
	public PersonVO create (PersonVO person) {
		logger.info("Create person");
		
		var entity  = ModelMapperConfig.parseObject(person, Person.class);
		var vo = repository.save(entity);
		return ModelMapperConfig.parseObject(vo, PersonVO.class);
	}

	
	public void delete(Long id) {
		var entity = repository.findById(id)
		.orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
		logger.info("Deleting one person id: " + entity.getId());
	}
	
}
