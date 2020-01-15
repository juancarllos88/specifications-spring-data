package br.com.specification.sample.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface BaseService<T> {

	public Page<T> listar(Specification<T> criterios, Pageable pageable);

	public Page<T> listar(Map<String, String> filters, Pageable pageable);
}
