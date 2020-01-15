package br.com.specification.sample.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Person.class)
public abstract class Person_ {

	public static volatile SingularAttribute<Person, Integer> phone;
	public static volatile SingularAttribute<Person, String> name;
	public static volatile SingularAttribute<Person, Integer> cpf;
	public static volatile SingularAttribute<Person, Integer> id;

}

