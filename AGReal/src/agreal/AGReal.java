package agreal;

import agreal.AlgoritmoGenetico;
import agreal.Problema;

public class AGReal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Problema problema = new Problema();

		
		Integer tamanho = 100;  // real1 = 50, real2 = 100
		Double pCrossover = 0.8;
		Double pMutacao = 0.07;  //real1 = 0.05, real2=0.07
		
		Integer geracoes = 100;

		Double minimo = -100.0;  
		Double maximo = 100.0;  
		Integer nVariaveis = 120; // real1 = 100, real2 = 120
		
 
		AlgoritmoGenetico ag = new AlgoritmoGenetico(tamanho, pCrossover, pMutacao, geracoes, problema,  minimo, maximo, nVariaveis);
		ag.executar();

	}

}
