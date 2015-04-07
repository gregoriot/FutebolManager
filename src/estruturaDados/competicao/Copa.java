package estruturaDados.competicao;

import java.util.ArrayList;

import jogo.ConteudoEstatico;
import estruturaDados.Confederacao;
import estruturaDados.ConfrontoIdaVolta;
import estruturaDados.Jogador;
import estruturaDados.Time;

/**
 * @author Gilvanei Greg�rio
 * @version 1.0
 */
public class Copa extends GenericoCompeticao{
	
	/**
	 * enum Turno, constantes que determinam a que turno a partida pertence
	 */
	public enum Turno {
		primeiraRodada(0), segundaRodada(1), oitavas(2), quartas(3), semi(4), finall(5);

		private int id;

		private Turno(int id) {
			this.id = id;
		}

		public int getId() {
			return this.id;
		}
	}
	
	/** Rodadas */
	public ArrayList<ArrayList<ConfrontoIdaVolta>> jogos;
	
	/** Referencia da confedera��o a qual esta copa pertence */
	private Confederacao confederacao;
	
	/** Rodada atual em rela��o da semana atual */
	private int rodadaAtual;
	
	/** Dias em que a partida de volta dos confrontos acontecem sendo 
	 * necess�rio marcar os confrontos da rodada seguinte*/
	private ArrayList<Integer> diasChaves = new ArrayList<Integer>();
	
	/**
	 * Construtor da classe define o numero de participates e as datas
	 * dos confrontos.
	 * 
	 * @param Confederacao confederacao
	 */
	public Copa(Confederacao confederacao){
		this.confederacao = confederacao;	
		
		/* A quantidade de participantes na copa pode ser de 64, 32 ou 16 times */
		if(confederacao.times.size() >= 64)
			NUMERO_DE_PARTICIPANTES = 64;
		else if(confederacao.times.size() >= 32)
			NUMERO_DE_PARTICIPANTES = 32;
		else if(confederacao.times.size() >= 16)
			NUMERO_DE_PARTICIPANTES = 16;
		
		int[]tempDEJ = {15, 17, 23, 25, 27, 29, 37, 39, 51, 53, 63, 65};
		DIAS_DE_JOGOS = tempDEJ;
		
		/** Dias chaves s�o apenas os dias onde as partidas de volta acontecem */
		for(int i=0; i<DIAS_DE_JOGOS.length; i++){
			if(i%2 != 0){
				diasChaves.add(DIAS_DE_JOGOS[i]);
			}
		}
	}
	
	/**
	 * Todo inicio de temporada os dados s�o limpos para que uma nova possa se iniciar.
	 */
	@Override
	public void comecarTemporada(){
		artilheiros = new ArrayList<Jogador>();
		cartoesAmarelos = new ArrayList<Jogador>();
		cartoesVermelhos = new ArrayList<Jogador>();

		jogos = new ArrayList<ArrayList<ConfrontoIdaVolta>>();
		jogos.add(new ArrayList<ConfrontoIdaVolta>());
		jogos.add(new ArrayList<ConfrontoIdaVolta>());
		jogos.add(new ArrayList<ConfrontoIdaVolta>());
		jogos.add(new ArrayList<ConfrontoIdaVolta>());
		jogos.add(new ArrayList<ConfrontoIdaVolta>());
		jogos.add(new ArrayList<ConfrontoIdaVolta>());

		ArrayList<Integer> jaEscolhidos = new ArrayList<Integer>();
		int t1;
		int t2;
		
		if(confederacao.times.size() >= 64)
			rodadaAtual = Turno.primeiraRodada.getId();
		else if(confederacao.times.size() >= 32)
			rodadaAtual = Turno.segundaRodada.getId();
		else if(confederacao.times.size() >= 16)
			rodadaAtual = Turno.oitavas.getId();
		
		for(int i=0; i<NUMERO_DE_PARTICIPANTES/2; i++){
			t1 = ConteudoEstatico.random.nextInt(NUMERO_DE_PARTICIPANTES/2);
			t2 = ConteudoEstatico.random.nextInt(NUMERO_DE_PARTICIPANTES/2)+NUMERO_DE_PARTICIPANTES/2;
			while(jaEscolhidos.contains(t1) || jaEscolhidos.contains(t2)){
				t1 = ConteudoEstatico.random.nextInt(NUMERO_DE_PARTICIPANTES/2);
				t2 = ConteudoEstatico.random.nextInt(NUMERO_DE_PARTICIPANTES/2)+NUMERO_DE_PARTICIPANTES/2;
			}
			
			confederacao.times.get(t1).dadosCompeticoes.put(getID(), new DadosCompeticao());
			confederacao.times.get(t2).dadosCompeticoes.put(getID(), new DadosCompeticao());
			jaEscolhidos.add(t1);
			jaEscolhidos.add(t2);
			
			jogos.get(rodadaAtual).add(new ConfrontoIdaVolta(confederacao.times.get(t1), confederacao.times.get(t2), rodadaAtual, this));
		}
		
		System.out.println(jogos.get(rodadaAtual).size()+" jogos da primeira fase");
	}
	
	/**
	 * Atualizar a situa��o da competi��o, no caso da copa, quando confronto de ida e volta terminam
	 * � necess�rio marcar a rodada seguinte at� que reste apenas o campe�o.
	 * 
	 * @param int semana
	 */
	public void atualizar(int semana){
		ArrayList<Time> proximafase = new ArrayList<Time>();
		int i = diasChaves.indexOf(semana);
		if(i < rodadaAtual)
			return;
		
		for(int j=0; j<jogos.get(i).size(); j++){
			ConfrontoIdaVolta confIV = jogos.get(i).get(j);
			if(confIV.turno < Turno.finall.getId()){
				proximafase.add(confIV.vencedor);

				confIV.encerrado = true;
			}
		}

		/* Confrontos da fase seguinte s�o marcados */
		for(int j=0; j<proximafase.size()/2; j++){
			jogos.get(rodadaAtual+1).add(new ConfrontoIdaVolta(proximafase.get(j*2), proximafase.get((j*2)+1), rodadaAtual+1, this));
		}
				
		rodadaAtual++;
		System.out.println("avan�aram: "+proximafase.size()+"\n");
	}
	
	@Override
	public void fimTemporada() {
		
	}

	@Override
	public int getID() {
		return 0;
	}
}
