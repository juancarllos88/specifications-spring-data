package br.com.specification.sample.specification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import br.com.specification.sample.domain.Person;
import br.com.specification.sample.domain.Person_;

public class PersonSpecification {

    private static final String FIELD_SEPARATOR = ".";
    private static final String REGEX_FIELD_SPLITTER = "\\.";

    public static Specification<Person> filterWithOptions(final Map<String, String> params) {
        return (root, query, criteriaBuilder) -> {
            try {
                List<Predicate> predicates = new ArrayList<>();
                for (String field : params.keySet()) {
                    if (field.contains(FIELD_SEPARATOR)) {
                        filterInDepth(params, root, criteriaBuilder, predicates, field);
                    } else {
                        if (Person.class.getDeclaredField(field) != null) {
                            predicates.add(criteriaBuilder.equal(root.get(field), params.get(field)));
                        }
                    }
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            return null;
        };
    }
    
    public static  <T> Specification<T> filtro(final Map<String, String> params, Class<T> data) {
        return (root, query, criteriaBuilder) -> {
            try {
                List<Predicate> predicates = new ArrayList<>();
                for (String field : params.keySet()) {
                    if (field.contains(FIELD_SEPARATOR)) {
                        filterInDepth(params, root, criteriaBuilder, predicates, field);
                    } else {
                        if (data.getDeclaredField(field) != null) {
                            predicates.add(criteriaBuilder.equal(root.get(field), params.get(field)));
                        }
                    }
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            return null;
        };
    }

    private static <T> void filterInDepth(Map<String, String> params, Root<T> root, CriteriaBuilder criteriaBuilder,
                                      List<Predicate> predicates, String field) throws NoSuchFieldException {
        String[] compositeField = field.split(REGEX_FIELD_SPLITTER);
        if (compositeField.length == 2) {
            if(Collection.class.isAssignableFrom(Person.class.getDeclaredField(compositeField[0]).getType())) {
                Join<Object, Object> join = root.join(compositeField[0]);
                predicates.add(criteriaBuilder.equal(join.get(compositeField[1]), params.get(field)));
            }
        } else if(Person.class.getDeclaredField(compositeField[0]).getType().getDeclaredField(compositeField[1]) != null) {
            predicates.add(criteriaBuilder.equal(root.get(compositeField[0]).get(compositeField[1]), params.get(field)));
        }
    }
    
    
    public static Specification<Person> name(String name) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Person_.name), name);
    }
 
    public static Specification<Person> phone(Integer phone) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Person_.phone), phone);
    }
 
    public static Specification<Person> cpf(Integer cpf) {
        return (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(Person_.cpf), cpf);
    }
    
   

}
