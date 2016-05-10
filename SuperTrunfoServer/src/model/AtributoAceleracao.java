package model;

import java.io.Serializable;

public class AtributoAceleracao extends AtributoCarta implements Serializable {
	private static final long serialVersionUID = 1L;

	public AtributoAceleracao(double valor) {
		super("Aceleracao", valor);
	}
}
