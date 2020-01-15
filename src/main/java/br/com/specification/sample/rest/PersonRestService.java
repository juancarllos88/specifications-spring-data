package br.com.specification.sample.rest;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.specification.sample.domain.Person;
import br.com.specification.sample.dto.BuscaRequestTO;
import br.com.specification.sample.service.PersonService;
import br.com.specification.sample.specification.SpecificationBuilder;

/**
 * PersonRestService
 *
 * @author Juan Carlos
 */
@RestController
@RequestMapping("/persons")
public class PersonRestService {

	@Autowired
	private PersonService personService;

	@Autowired
	private SpecificationBuilder specificationBuilder;

	@GetMapping("/listagem1")
	public Page<Person> listagem1(@RequestParam(required = false) Map<String, String> filters,
			@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {
		return personService.listar(filters, new PageRequest(page, size));
	}

	@GetMapping("/listagem2")
	public Page<Person> listagem2(@RequestParam(required = false) Map<String, String> filters, Pageable page) {
		Specification<Person> criterios = specificationBuilder.criarFiltro(filters, Person.class);
		return personService.listar(criterios, page);
	}

	@GetMapping("/listagem3")
	public Page<Person> listagem3(BuscaRequestTO buscaRequestTO, Pageable page) {
		Specification<Person> criterios = specificationBuilder.criarFiltro(buscaRequestTO, Person.class);
		return personService.listar(criterios, page);
	}

}
