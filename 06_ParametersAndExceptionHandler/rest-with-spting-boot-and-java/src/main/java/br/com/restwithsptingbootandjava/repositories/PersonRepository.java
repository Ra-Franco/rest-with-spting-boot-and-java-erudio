package br.com.restwithsptingbootandjava.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.restwithsptingbootandjava.model.Person;

//@Repository - Não é mais necessário
public interface PersonRepository extends JpaRepository<Person, Long> {}
