package br.com.specification.sample.service;

import static br.com.specification.sample.specification.PersonSpecification.filterWithOptions;
import static br.com.specification.sample.specification.PersonSpecification.filtro;
import static org.springframework.data.jpa.domain.Specifications.where;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.specification.sample.domain.Person;
import br.com.specification.sample.repository.PersonRepository;
import br.com.specification.sample.specification.PersonSpecification;

@Service
public class PersonServiceImpl implements PersonService {

	@Autowired
	private PersonRepository personRepository;

	@Override
	public Page<Person> listar(Map<String, String> filters, Pageable pageable) {
		Specification<Person> filterWithOptions = filterWithOptions(filters);
		return personRepository.findAll(filterWithOptions, pageable);
	}

	@Override
	public Page<Person> listar(Specification<Person> criterios, Pageable pageable) {
		return personRepository.findAll(criterios, pageable);
	}

	public Page<Person> listagem1(String name, Integer cpf, Integer phone, Pageable pageable) {
		return personRepository.findAll(where(PersonSpecification.name(name)).or(PersonSpecification.cpf(cpf))
				.and(PersonSpecification.phone(phone)), pageable);
	}

	public Page<Person> listagem2(Map<String, String> filters, Pageable pageable) {
		Specification<Person> filterWithOptions = filtro(filters, Person.class);
		return personRepository.findAll(filterWithOptions, pageable);
	}
}