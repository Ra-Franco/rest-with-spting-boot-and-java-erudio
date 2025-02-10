package br.com.restwithsptingbootandjava.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;

import br.com.restwithsptingbootandjava.exceptions.RequiredObjectsIsNullException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.restwithsptingbootandjava.controllers.PersonController;
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
	
	public List<PersonVO> findAll() {
		var persons = ModelMapperConfig.parseListObjects(repository.findAll(), PersonVO.class);
		persons.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		return persons;
	}

	public PersonVO findById(Long id) {
		logger.info("Finding one person.");
		var entity =  repository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("No records found for this ID: " + id));
		var vo =  ModelMapperConfig.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
	}
	
	public PersonVO update(PersonVO person) {
		if (person == null) throw new RequiredObjectsIsNullException();
		logger.info("Update person id: " + person.getKey());
		var entity = repository.findById(person.getKey())
		.orElseThrow(()-> new ResourceNotFoundException("No records found for this Id: " + person.getKey()));
		
		entity.setFirstName(person.getFirstName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var entityToVo = repository.save(entity);
		var vo = ModelMapperConfig.parseObject(entityToVo, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(person.getKey())).withSelfRel());
		return vo;
	}
	
	public PersonVO create (PersonVO person) {
		if (person == null) throw new RequiredObjectsIsNullException();
		logger.info("Create person");
		Person entity  = ModelMapperConfig.parseObject(person, Person.class);
		PersonVO vo = ModelMapperConfig.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}

	
	public void delete(Long id) {
		var entity = repository.findById(id)
		.orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
		logger.info("Deleting one person id: " + entity.getId());
	}
	
}
