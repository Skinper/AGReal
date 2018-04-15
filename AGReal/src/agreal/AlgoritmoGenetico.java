package agreal;

import java.util.Collections;
import java.util.Random;

import agreal.Populacao;

public class AlgoritmoGenetico {

	// Tamanho da população
	Integer tamanho;
	// Taxa de crossover - operador principal
	Double pCrossover;
	// Taxa de mutação - operador secundário
	Double pMutacao;
	// Criterio de parada - numero de gerações
	Integer geracoes;
	// Dados do problema
	Problema problema;

	// Minimo
	Double minimo;
	// Maximo
	Double maximo;
	// Variáveis
	Integer nVariaveis;

	public AlgoritmoGenetico(Integer tamanho, Double pCrossover, Double pMutacao, Integer geracoes, Problema problema,
			Double minimo, Double maximo, Integer nVariaveis) {

		this.tamanho = tamanho;
		this.pCrossover = pCrossover;
		this.pMutacao = pMutacao;
		this.geracoes = geracoes;
		this.problema = problema;

		this.minimo = minimo;
		this.maximo = maximo;
		this.nVariaveis = nVariaveis;
	}

	Populacao populacao;
	Populacao novaPopulacao;
	Individuo melhorSolucao;

	public Individuo getMelhorSolucao() {
		return melhorSolucao;
	}

	public void executar() {
		populacao = new Populacao( minimo, maximo, nVariaveis, tamanho, problema);
		novaPopulacao = new Populacao( minimo, maximo, nVariaveis, tamanho, problema);

		// Criar a população
		populacao.criar();

		// avaliar
		populacao.avaliar();

		// Recuperar o melhor individuo

		Random rnd = new Random();
		int ind1, ind2;

		// Enquanto o criterio de parada nao for satisfeito - Gerações
		for (int g = 1; g <= geracoes; g++) {
			
			for (int i =0; i < this.tamanho; i++) {
				// Crossover

				if (rnd.nextDouble() <= this.pCrossover) {
					// Realizar a operaçao
					ind1 = rnd.nextInt(this.tamanho);
					do {
						ind2 = rnd.nextInt(this.tamanho);
					} while (ind1 == ind2);

					Individuo desc1 = new Individuo(minimo, maximo, nVariaveis);
					Individuo desc2 = new Individuo( minimo, maximo, nVariaveis);

					//Progenitores
					Individuo p1 = populacao.getIndividuos().get(ind1);
					Individuo p2 = populacao.getIndividuos().get(ind2);
					
					//Ponto de corte
					int corte = rnd.nextInt(p1.getVariaveis().size());
					
					
					// Descendente 1 -> Ind1_1 +Ind2_2
					crossoverUmPonto(p1, p2, desc1, corte);
					
					// Descendente 2 -> Ind2_1 + Ind1_2
					crossoverUmPonto(p2, p1, desc2, corte);
					
					
					// Mutação
					
					// Descendente 1 
					 mutacaoPorVariavel(desc1);
					// Descendente 2 
					 mutacaoPorVariavel(desc2);
					
					// Avaliar as novas soluções
					problema.calcularFuncaoObjetivo(desc1);
					problema.calcularFuncaoObjetivo(desc2);
					
					// Inserir na nova população
					novaPopulacao.getIndividuos().add(desc1);
					novaPopulacao.getIndividuos().add(desc2);
				}
			}
			
			// Definir sobreviventes -> populacao + descendentes
			populacao.getIndividuos().addAll(novaPopulacao.getIndividuos());
			
			// Ordenar populacao;
			Collections.sort(populacao.getIndividuos());
			//Eliminar os demais individuos - criterio: tamanho da população
			populacao.getIndividuos().subList(this.tamanho, populacao.getIndividuos().size()).clear();
			
			novaPopulacao.getIndividuos().clear();
			
			// Imprimir a situação atual
			System.out.println("Gen = " + g + "\tCusto = " + populacao.getIndividuos().get(0).getFuncaoObjetivo());
			


		}
		System.out.println("Melhor resultado: ");
		System.out.println(populacao.getIndividuos().get(0).getVariaveis());
		GenerateCSV.generateCsvFile("teste",populacao.getIndividuos().get(0).getVariaveis().toString());
	}

	
	
	
	private void crossoverUmPonto(Individuo ind1, Individuo ind2, Individuo descendente, int corte) {
		
		// Crossover aritmetico - 1 ponto de corte
		Random rnd = new Random();
		Double alpha = rnd.nextDouble();
		
		
		
		//Ind1_1
	 // alpha * P1 ( parte do progenitor 1)
		for (int i = 0 ; i <corte ; i++) {
			Double valor = alpha* ind1.getVariaveis().get(i);
			
			descendente.getVariaveis().add(valor);
			
		}
		
		//Ind2_2
		// (1 - alpha) * P2 
		for (int i = corte ; i < this.nVariaveis ; i++) {
			Double valor = ( 1.0 - alpha) * ind2.getVariaveis().get(i);
			
			descendente.getVariaveis().add(valor);
			
		}
		
		
 
		
	}
	
	private void mutacaoPorVariavel (Individuo individuo) {
		Random rnd = new Random();
		
		for (int i = 0 ; i < individuo.getVariaveis().size(); i++) {
			
			if (rnd.nextDouble() <= this.pMutacao) {
			//Mutação aritmética
				//Multiplicar rnd e inerter ou nao o sinal
				Double valor = individuo.getVariaveis().get(i);
				// Multiplica por rnd
				valor *= rnd.nextDouble();
				
				//Inverter o sinal
				if ( !rnd.nextBoolean()) {
					valor = -valor;
				}
				if (valor >= this.minimo && valor <= this.maximo) {
					individuo.getVariaveis().set(i, valor);	
				}
				
				
				
				
			}
			
			
		}
		
	}

}
