package br.com.specification.sample.specification;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import br.com.specification.sample.annotation.SearchParam;
import br.com.specification.sample.enums.Operacao;

@Component
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

			return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
		};
	}

	public <T> Specification<T> criarFiltro(Object objeto, Class<T> data) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();
			Field[] declaredFields = objeto.getClass().getDeclaredFields();
			for (Field field : declaredFields) {

				if (field.isAnnotationPresent(SearchParam.class)) {
					try {
						field.setAccessible(true);
						Object value = field.get(objeto);
						SearchParam parametros = field.getAnnotation(SearchParam.class);
						if (value instanceof Number) {
							predicates.add(buildPredicate(root, criteriaBuilder, parametros,Integer.valueOf(value.toString())));
						} else {

						}
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					}

				}
			}

			return criteriaBuilder.or(predicates.toArray(new Predicate[predicates.size()]));
		};
	}
	
	private Predicate buildPredicate(Root<?> root, CriteriaBuilder criteriaBuilder, SearchParam parametros, Integer value) {
		switch (parametros.operacao()) {
		case EQUALS:
			return criteriaBuilder.equal(root.get(parametros.propriedade()), value);
		case MAJOR_EQUALS:
            return criteriaBuilder.greaterThanOrEqualTo(root.get(parametros.propriedade()), value);
        case MINOR_EQUALS:
            return criteriaBuilder.lessThanOrEqualTo(root.get(parametros.propriedade()), value);
		default:
			return criteriaBuilder.equal(root.get(parametros.propriedade()), value);
		}
	}




}
