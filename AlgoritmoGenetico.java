import java.util.Random;

public class AlgoritmoGenetico {
    static int NUMERO_GERACOES = ;
    static int QTD_CROMOSSOMOS = 50;
    static int QTD_PESOS = 180;
    static double TAXA_MUTACAO = 0.6;
    static double AMPLITUDE_MUTACAO = 0.2;

    public static void main(String[] args) {

        double populacaoInicial[][] = gerarPopulacaoInicial();
        double populacaoIntermediaria[][] = new double[QTD_CROMOSSOMOS][QTD_PESOS + 1];

        for (int g = 0; g < NUMERO_GERACOES; g++) {

            // Joga o jogo da velha
            for (int i = 0; i < QTD_CROMOSSOMOS; i++) {

                double[] cromossomo = new double[QTD_PESOS];
                for (int j = 0; j < QTD_PESOS; j++) {
                    cromossomo[j] = populacaoInicial[i][j];
                }

                Resultado resultado = new TestaRede().joga(cromossomo);
                populacaoInicial[i][QTD_PESOS] = calcularAptidao(resultado);
            }

            populacaoIntermediaria[0] = elitismo(populacaoInicial);

            // Critério de Parada
            if (g == NUMERO_GERACOES - 1) {
                System.out.println("Melhor cromossomo: " + populacaoIntermediaria[0][QTD_PESOS]);
                break;
            }

            crossover(populacaoInicial, populacaoIntermediaria);
            populacaoInicial = populacaoIntermediaria;
            mutacao(populacaoInicial);
        }
    }

    public static void mutacao(double[][] populacao) {
        Random aleatorio = new Random();

        for (int i = 0; i < populacao.length; i++) {
            for (int j = 0; j < QTD_CROMOSSOMOS; j++) {
                if (aleatorio.nextDouble() <= TAXA_MUTACAO) {
                    populacao[i][j] = ((aleatorio.nextDouble() * 2) - 1) * AMPLITUDE_MUTACAO;
                }
            }
        }
    }

    private static void crossover(double[][] populacao, double[][] populacaoIntermediaria) {
        for (int i = 1; i < QTD_CROMOSSOMOS; i += 2) {

            int debugI = i;

            // TODO Verificar se os individuos não são iguais
            int individuo1 = torneio(populacao);
            int individuo2 = torneio(populacao);

            for (int j = 0; j < QTD_PESOS; j++) {

                int debugJ = j;

                populacaoIntermediaria[i][j] = calcularMediaPesos(populacao[individuo1][j], populacao[individuo2][j]);
            }
        }
    }

    private static double calcularMediaPesos(double pesoPai, double pesoMae) {
        double media = (pesoPai + pesoMae) / 2;
        return media;
    }

    private static int torneio(double[][] populacao) {
        Random aleatorio = new Random();

        int linha1 = aleatorio.nextInt(QTD_CROMOSSOMOS);
        int linha2 = aleatorio.nextInt(QTD_CROMOSSOMOS);

        if (populacao[linha1][QTD_PESOS] > populacao[linha2][QTD_PESOS]) {
            return linha2;
        }

        return linha1;
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

        int oculta = 9; // numero de neuronios da camada oculta
        int saida = 9; // numero de neuronios da camada de saida
        int pesosOculta = oculta + 1; // numero de pesos por neuronio da camada oculta
        int pesosSaida = saida + 1; // numero de pesos por neuronio da camada de saida
        int totalPesos = pesosOculta * oculta + pesosSaida * saida;

        double populacao[][] = new double[QTD_CROMOSSOMOS][totalPesos + 1];

        for (int i = 0; i < QTD_CROMOSSOMOS; i++) {
            for (int j = 0; j < QTD_PESOS; j++) {
                populacao[i][j] = gera.nextDouble();
                // if (gera.nextBoolean()) {
                //     populacao[i][j] = gera.nextDouble();
                // }
            }
        }

        return populacao;
    }
}
