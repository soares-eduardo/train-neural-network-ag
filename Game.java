
import java.util.Random;
import java.util.Scanner;

import genetics.Resultado;
import minimax.Sucessor;
import minimax.TestaMinimax;
import neural_network.Rede;

public class Game {
    private double[] tabuleiro;
    private int[][] tabuleiroVelha;
    private Rede rn;

    public Game() {

    }

    public void joga(double[] cromossomo) {
        // ------------------------ EXEMPLO DE TABULEIRO
        // ------------------------------------------
        // tabuleiro do jogo da velha - Exemplo de teste
        tabuleiroVelha = new int[][] { { -1, -1, -1 }, // -1: celula livre 1: X 0: O
                { -1, -1, -1 },
                { -1, -1, -1 } };

        System.out.println("\f\nTabuleiro inicial: ");
        for (int i = 0; i < tabuleiroVelha.length; i++) {
            for (int j = 0; j < tabuleiroVelha.length; j++) {
                System.out.print(tabuleiroVelha[i][j] + " \t");
            }
            System.out.println();
        }

        // tabuleiro de teste - conversao de matriz para vetor
        tabuleiro = new double[tabuleiroVelha.length * tabuleiroVelha.length];
        int k = 0;
        for (int i = 0; i < tabuleiroVelha.length; i++) {
            for (int j = 0; j < tabuleiroVelha.length; j++) {
                tabuleiro[k] = tabuleiroVelha[i][j];
                k++;
            }
        }

        // ------------------------ EXEMPLO DE REDE
        // ------------------------------------------
        // Cria e configura a rede
        // Criando a rede
        int oculta = 9; // numero de neuronios da camada oculta
        int saida = 9; // numero de neuronios da camada de saida
        rn = new Rede(oculta, saida); // topologia da rede: 9 neurônios na camada oculta e 9 na de saída

        // Simulando um cromossomo da populacao do AG
        Random gera = new Random();
        int pesosOculta = oculta + 1; // numero de pesos por neuronio da camada oculta
        int pesosSaida = saida + 1; // numero de pesos por neuronio da camada de saida
        int totalPesos = pesosOculta * oculta + pesosSaida * saida;
        // double[] cromossomo = new double[totalPesos];

        System.out.println("TAMANHO DO CROMOSSOMO: " + cromossomo.length);

        // Setando os pesos na rede
        rn.setPesosNaRede(tabuleiro.length, cromossomo); //

        System.out.println();

        // Exibe rede neural
        System.out.println("Rede Neural - Pesos: ");
        System.out.println(rn);

        // --------------EXEMPLO DE EXECUCAO ----------------------------------------

        int n = 0;
        while (true) {
            System.out.println("\n\n>>>RODADA: " + n);
            // Exibe um exemplo de propagação : saida dos neurônios da camada de saída
            double[] saidaRede = rn.propagacao(tabuleiro);
            System.out.println("Rede Neural - Camada de Saida: Valor de Y");
            for (int i = 0; i < saidaRede.length; i++) {
                System.out.println("Neuronio " + i + " : " + saidaRede[i]);
            }

            // Define posicao a jogar de acordo com rede
            int indMaior = 0;
            double saidaMaior = saidaRede[0];
            for (int i = 1; i < saidaRede.length; i++) {
                if (saidaRede[i] > saidaMaior) {
                    saidaMaior = saidaRede[i];
                    indMaior = i;
                }
            }
            int linha = indMaior / 3;
            int coluna = indMaior % 3;
            System.out.println("Neuronio de maior valor: " + indMaior + " - " + saidaRede[indMaior]);
            System.out.println(">>> Rede escolheu - Linha: " + linha + " Coluna: " + coluna);

            if (tabuleiroVelha[linha][coluna] != -1) {
                // TODO Punir a rede
                System.out.println("Posicao ocupada");
            } else {
                tabuleiroVelha[linha][coluna] = 1;

                System.out.println("\nTabuleiro apos jogada: ");
                for (int i = 0; i < tabuleiroVelha.length; i++) {
                    for (int j = 0; j < tabuleiroVelha.length; j++) {
                        System.out.print(tabuleiroVelha[i][j] + "\t");
                    }
                    System.out.println();
                }
            }

            // Verifica se há vencedor
            if (verificaVitoria(tabuleiroVelha)) {
                System.out.println("Vitória da Rede Neural");
            }

            // Verifica se há empate
            if (verificaEmpate(tabuleiroVelha)) {
                System.out.println("Empate");
            }

            // -----------------------------------------JOGA MINIMAX

            Scanner ler = new Scanner(System.in);

            System.out.println("Informe um número de 1 a 9");
            int numero = ler.nextInt();

            int[] posicao = obterPosicaoTabuleiro(numero);

            System.out.println(
                    ">>> Jogador escolheu - Linha: " + posicao[0] + " Coluna: " + posicao[1]);

            if (tabuleiroVelha[posicao[0]][posicao[1]] != -1) {
                System.out.println("Posicao ocupada PELO MINIMAX");
            } else {
                tabuleiroVelha[posicao[0]][posicao[1]] = 0;

                System.out.println("\nTabuleiro apos jogada: ");
                for (int i = 0; i < tabuleiroVelha.length; i++) {
                    for (int j = 0; j < tabuleiroVelha.length; j++) {
                        System.out.print(tabuleiroVelha[i][j] + "\t");
                    }
                    System.out.println();
                }
            }

            // Verifica se há vencedor
            if (verificaVitoria(tabuleiroVelha)) {
                System.out.println("Vitória do jogador");
                break;
            }

            // Verifica se há empate
            if (verificaEmpate(tabuleiroVelha)) {
                System.out.println("Empate do jogador");
                break;
            }

            n++;

            // tabuleiro de teste - conversao de matriz para vetor
            k = 0;
            for (int i = 0; i < tabuleiroVelha.length; i++) {
                for (int j = 0; j < tabuleiroVelha.length; j++) {
                    tabuleiro[k] = tabuleiroVelha[i][j];
                    k++;
                }
            }
        }
    }

    public static int[] obterPosicaoTabuleiro(int numero) {
        int linha = (numero - 1) / 3;
        int coluna = (numero - 1) % 3;
        return new int[] { linha, coluna };
    }

    public static boolean verificaVitoria(int[][] tabuleiro) {
        // Verificação das linhas
        for (int i = 0; i < 3; i++) {
            if (tabuleiro[i][0] == tabuleiro[i][1] && tabuleiro[i][1] == tabuleiro[i][2] && tabuleiro[i][0] != -1) {
                return true;
            }
        }

        // Verificação das colunas
        for (int j = 0; j < 3; j++) {
            if (tabuleiro[0][j] == tabuleiro[1][j] && tabuleiro[1][j] == tabuleiro[2][j] && tabuleiro[0][j] != -1) {
                return true;
            }
        }

        // Verificação das diagonais
        if ((tabuleiro[0][0] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][2] && tabuleiro[0][0] != -1)
                || (tabuleiro[0][2] == tabuleiro[1][1] && tabuleiro[1][1] == tabuleiro[2][0]
                        && tabuleiro[0][2] != -1)) {
            return true;
        }

        // Nenhuma vitória encontrada
        return false;
    }

    public static boolean verificaEmpate(int[][] tabuleiro) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tabuleiro[i][j] == -1) {
                    return false; // Ainda há uma posição vazia, não é empate
                }
            }
        }
        return true; // Todas as posições estão preenchidas, é empate
    }

    public static void main(String args[]) {
        double[] cromossomo = {0.16141297991914938,  -0.14145651386257727,  -0.006919602556971905,  0.029779528148895642,  -0.03898506775394908,  0.15703847185689193,  -0.40887821207352926,  -0.09424610279807091,  0.3716356935380137,  0.12426636046512339,  0.14493615252346786,  -0.2692953864862324,  0.16837610513528023,  0.19792105886735492,  -0.03988996533621095,  0.04788890825186574,  -0.11723845125534893,  0.015054771276382706,  -0.8113072338632334,  -0.07745255917357588,  -0.11668156283992932,  -0.1901288032758759,  -0.447697808963533,  -0.18130674508986067,  0.24995794580253977,  0.15087309288253892,  0.16248443659782055,  0.5746531259751219,  -0.1956722788912324,  -0.6840857401360154,  -0.0017217266487746575,  -0.3651290559988522,  0.3838835495959155,  0.2326196279939448,  0.007564384170978622,  0.0547153330783901,  0.15181446553173383,  0.5730849031230483,  0.1804801522514839,  -0.1466279594859011,  0.08451965302871255,  -0.7320543175807845,  0.0912242445041775,  0.15297708364556148,  -0.2053337087227563,  0.1140217295101726,  -0.6430839042841983,  0.0042734952600341995,  0.12043610354845785,  0.1439583283687378,  0.15811316990353963,  0.1259070510542973,  0.17221395722230756,  0.04977957060750598,  -0.17351678954698335,  -0.0673912485505736,  -0.5328731039695973,  0.048388328868377295,  -0.13207524562736786,  0.06917977163995141,  0.1447376102478679,  -0.005191784100115582,  -0.21717902626757002,  0.1327474151195959,  -0.14574703791265958,  0.19819683002693606,  0.16362213885220817,  -0.39898573785024716,  0.18383360921201258,  -0.2512752219238575,  0.4614983059418672,  -0.09652471676675037,  0.17979155079145087,  -0.09463813012451282,  0.11651755099444579,  0.03274905770392627,  0.09769380843839288,  0.1043934598096326,  0.13925434173623394,  0.07932278267152282,  0.1560437190117956,  0.06093173851191164,  -0.1806243419887383,  -0.1338739745473541,  -0.09642224124420312,  0.06472370425176299,  0.20418621378927,  -0.11391729085893978,  -0.08252216091690814,  0.1767674261014067,  -0.09390492089358311,  0.8556231987185121,  0.10901129511121721,  
0.030311734752360486,  -0.0025970820116230087,  0.1223209303845843,  -0.14961432521942358,  -0.42757720847614245,  0.10744556105135211,  -0.10955879570950722,  0.7333743368563266,  0.13844840972383715,  0.3922027133993405,  0.3316455809574699,  -0.6462723662720455,  0.5458708893006015,  -0.14257685265471748,  0.2905246550906925,  -0.3864602806274489,  0.03227045501203918,  -0.6494898201423986,  0.10959424264704254,  0.5490239040613336,  0.1333986482202109,  -0.2629692016848076,  0.7077589903852012,  0.30785323248011337,  -0.3471230600152812,  -0.47302403936946413,  -0.15843413103484982,  -0.5801692245690652,  -0.08143091887592713,  -0.14546340875606778,  -0.4984333315193708,  0.1698468131772749,  -0.522948625252637,  -0.39104812252618,  -0.03835515297254233,  0.12194472282424851,  0.08410446676243954,  0.1774650559054365,  -0.19543173796054703,  0.20460050956603876,  0.054069030724477274,  -0.6090524111702298,  -0.1640579513064908,  -0.18085679197264842,  -0.3194860185246546,  -0.2524741331810303,  -0.5606844172397872,  0.2079530605395053,  -0.6564225012388737, 
 0.3499206994021068,  -0.5229503151006805,  -0.4454904652801687,  -0.1226244828649915,  -0.3330331234202637,  0.05638733618003605,  -0.15200349088958864,  -0.2471334338314825,  0.6422261945568446,  0.8703041425956123,  -0.21608689334073866,  0.18044532169611194,  -0.6909399178791943,  0.7793246794409074,  -0.14855612504340687,  -0.0954476251841494,  -0.15163843002750743,  0.019442618485879026,  -0.3061550873296365,  0.39336038230682646,  0.5114994748557663,  -0.5071647286941745,  -0.02847119077325433,  -0.08087830752832403,  0.8462886306855143,  0.07148764775236671,  0.35590271201220236,  -0.016318310699684924,  -0.5163271763023243,  0.07889606750823153,  -0.182019672351384,  0.39301711215423446,  -0.13941322971395698,  0.7332719999853681,  -0.2131870651590861,  0.22334065001141828,  0.6217065620743738,  -0.4624221734486938};
    new Game().joga(cromossomo);
}
}
