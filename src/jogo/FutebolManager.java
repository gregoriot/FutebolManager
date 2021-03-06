package jogo;

import java.util.ArrayList;

import estruturaDados.Confederacao;
import estruturaDados.Time;

public class FutebolManager {

	public static void main(String[] args) {
		ArrayList<Time> times = new ArrayList<Time>();

		times.add(ConteudoEstatico.carregarTime("/Brasil/AtleticoMineiro.csv"));
		times.add(ConteudoEstatico.carregarTime("/Brasil/AtleticoParanaense.csv"));
		times.add(ConteudoEstatico.carregarTime("/Brasil/Avai.csv"));
		times.add(ConteudoEstatico.carregarTime("/Brasil/Chapecoense.csv"));
		times.add(ConteudoEstatico.carregarTime("/Brasil/Corinthians.csv"));
		times.add(ConteudoEstatico.carregarTime("/Brasil/Coritiba.csv"));
		times.add(ConteudoEstatico.carregarTime("/Brasil/Cruzeiro.csv"));
		times.add(ConteudoEstatico.carregarTime("/Brasil/Figueirense.csv"));
		times.add(ConteudoEstatico.carregarTime("/Brasil/Flamengo.csv"));
		times.add(ConteudoEstatico.carregarTime("/Brasil/Fluminense.csv"));
		times.add(ConteudoEstatico.carregarTime("/Brasil/Goias.csv"));
		times.add(ConteudoEstatico.carregarTime("/Brasil/Gremio.csv"));
		times.add(ConteudoEstatico.carregarTime("/Brasil/Internacional.csv"));
		times.add(ConteudoEstatico.carregarTime("/Brasil/Joiville.csv"));
		times.add(ConteudoEstatico.carregarTime("/Brasil/Palmeiras.csv"));
		times.add(ConteudoEstatico.carregarTime("/Brasil/PontePreta.csv"));
		times.add(ConteudoEstatico.carregarTime("/Brasil/Santos.csv"));
		times.add(ConteudoEstatico.carregarTime("/Brasil/SaoPaulo.csv"));
		times.add(ConteudoEstatico.carregarTime("/Brasil/Sport.csv"));
		times.add(ConteudoEstatico.carregarTime("/Brasil/Vasco.csv"));
		
		Confederacao c = new Confederacao(times);
		// 52 semanas, mas com os jogos de meio de semana da 104
		for (Time t : times) {
			t.comecarTemporada();
		}
		
		c.comecarTemporada();
		for (int i = 0; i < 100; i++) {
			for (Time t : times) {
				if (t.calendario.semanas[i] != null) {
					if (!t.calendario.getConcluido(i)) {
						t.calendario.realizarJogo(i);
					}
				}
				
				t.atualiza();
			}
			c.atualizar(i);
		}
		
		c.fimTemporada();
		
	}
}
