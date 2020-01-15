package br.com.specification.sample.dto;

import java.io.Serializable;
import java.util.List;

import br.com.specification.sample.annotation.SearchParam;
import br.com.specification.sample.enums.Operacao;
import lombok.Data;

@Data
public class BuscaRequestTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Integer> ids;
	@SearchParam(propriedade = "id" , operacao = Operacao.MAJOR_EQUALS)
	private Integer id;
	private String name;
	private Integer cpf;
	private Integer phone;

}
