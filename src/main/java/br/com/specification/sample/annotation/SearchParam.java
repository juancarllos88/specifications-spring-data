package br.com.specification.sample.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import br.com.specification.sample.enums.Operacao;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SearchParam {
	
	public String propriedade() default "";

	public Operacao operacao() default Operacao.EQUALS;

}
