package model;

import java.io.Serializable;

public class AtributoZeroCem extends AtributoCarta implements Serializable {
	private static final long serialVersionUID = 1L;

	public AtributoZeroCem(double valor) {
		super("0 a 100 Km/h", valor);
	}
}
