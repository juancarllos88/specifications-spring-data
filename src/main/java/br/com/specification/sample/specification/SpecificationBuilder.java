package br.com.specification.sample.specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class SpecificationBuilder {

	public <T> Specification<T> criarFiltro(Map<String, String> filtros, Class<T> data) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			filtros.forEach((k, v) -> {
				try {
					if (data.getDeclaredField(k) != null) {
						predicates.add(criteriaBuilder.equal(root.get(k), v));
					}
				} catch (NoSuchFieldException | SecurityException e) {
					e.printStackTrace();
				}
			});

			return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
		};
	}
	
	
}
