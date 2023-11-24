package br.com.restwithsptingbootandjava.services;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.restwithsptingbootandjava.exceptions.ResourceNotFoundException;
import br.com.restwithsptingbootandjava.model.Person;
import br.com.restwithsptingbootandjava.repositories.PersonRepository;

@Service
public class PersonServices {

	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	@Autowired
	PersonRepository repository;
	
	public List<Person> findAll() {
		return repository.findAll();
	}

	public Person findById(Long id) {
		logger.info("Finding one person.");
		return repository.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("No records found for this ID: " + id));
	}
	
	public Person update(Person person) {
		logger.info("Update person id: " + person.getId());
		var entity = repository.findById(person.getId())
		.orElseThrow(()-> new ResourceNotFoundException("No records found for this Id: " + person.getId()));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		return repository.save(person);
	}
	
	public Person create (Person person) {
		logger.info("Create person");
		return repository.save(person);
	}
	
	public void delete(Long id) {
		var entity = repository.findById(id)
		.orElseThrow(()-> new ResourceNotFoundException("No records found for this ID"));
		repository.delete(entity);
		logger.info("Deleting one person id: " + entity.getId());
	}
	
}
