package repositorio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import model.AtributoCarta;
import model.AtributoVelocidade;
import model.AtributoZeroCem;
import model.Carta;

public class RepositorioCartas {

	private List<Carta> cartas;

	public RepositorioCartas() {
		cartas = new ArrayList<Carta>();

		cartas.add(
				new Carta("Abt-Beetle 2,0 i"
					, Arrays.asList(new AtributoCarta[]  {
							new AtributoVelocidade(205)
							, new AtributoZeroCem(-8.5)
						})
				)
		);

		cartas.add(
				new Carta("Abt-As 4"
					, Arrays.asList(new AtributoCarta[]  {
							new AtributoVelocidade(277)
							, new AtributoZeroCem(-5.4)
						})
				)
		);

		cartas.add(
				new Carta("Abt-Golf GTI 1.8 T"
					, Arrays.asList(new AtributoCarta[]  {
							new AtributoVelocidade(246)
							, new AtributoZeroCem(-6.5)
						})
				)
		);

		cartas.add(
				new Carta("Abt-TT limited"
					, Arrays.asList(new AtributoCarta[]  {
							new AtributoVelocidade(265)
							, new AtributoZeroCem(-5.6)
						})
				)
		);
	}

	public Carta getCartaAleatória() {
		Random rand = new Random();

		return cartas.remove(rand.nextInt(cartas.size()-1));
	}

	public void setCartas(List<Carta> cartas) {
		this.cartas = cartas;
	}

	public ArrayList<Carta> getTodasCartas() {
		return new ArrayList<Carta>(this.cartas);
	}
}
