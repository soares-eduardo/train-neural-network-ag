import java.util.Random;

public class AlgoritmoGenetico {
    static int NUMERO_GERACOES = 30;
    static int QTD_CROMOSSOMOS = 4;
    static int QTD_PESOS = 180;
    public static void main(String[] args) {
        
        double populacaoInicial[][] = gerarPopulacaoInicial();
        double populacaoIntermediaria[][] = new double[30][181];

        for (int g = 0; g < NUMERO_GERACOES; g++) {

            // Joga o jogo da velha
            for (int i = 0; i < QTD_CROMOSSOMOS; i++) {
                Resultado resultado = new TestaRede().joga(populacaoInicial[i]);
                populacaoInicial[i][QTD_PESOS] = calcularAptidao(resultado);
            }

            for (double[] ds : populacaoInicial) {
                System.out.println(ds[ds.length - 1]);
            }

            double[] elitismo = elitismo(populacaoInicial);
            System.out.println(elitismo[elitismo.length - 1]);

        }
    }

    private static double[] elitismo(double[][] populacao) {
        double aux[] = new double[QTD_PESOS + 1];
        double maiorValor = populacao[0][QTD_PESOS];
        int linha = 0;

        for (int i = 1; i < QTD_CROMOSSOMOS; i++) {
            if (populacao[i][QTD_PESOS] > maiorValor) {
                maiorValor = populacao[i][QTD_PESOS];
                linha = i;
            }
        }

        for (int i = 0; i <= QTD_PESOS; i++) {
            aux[i] = populacao[linha][i];
        }

        return aux;
    }

    private static int calcularAptidao(Resultado resultado) {
        return new Heuristic().evaluateMove(resultado.getTabuleiro(), 1, resultado.getIsEmpate());
    }

    private static double[][] gerarPopulacaoInicial() {
        Random gera = new Random();
        double populacao[][] = new double[QTD_CROMOSSOMOS][QTD_PESOS + 1];

        for (int i = 0; i < QTD_CROMOSSOMOS; i++) {
            for (int j = 0; j < QTD_PESOS; j++) {
                populacao[i][j] = gera.nextDouble();
                if (gera.nextBoolean()) {
                    populacao[i][j] = gera.nextDouble();
                }
            }
        }

        return populacao;
    }
}
