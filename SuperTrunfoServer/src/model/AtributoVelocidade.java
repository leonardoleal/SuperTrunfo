package model;

import java.io.Serializable;

public class AtributoVelocidade extends AtributoCarta implements Serializable {
	private static final long serialVersionUID = 1L;

	public AtributoVelocidade(double valor) {
		super("Velocidade", valor);
	}
}
