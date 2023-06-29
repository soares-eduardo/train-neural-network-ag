import java.util.Scanner;

import genetics.AlgoritmoGenetico;

public class Main {

    public static void main(String[] args) {
        Scanner ler = new Scanner(System.in);
        System.out.println("Informe o nível do Minimax com um valor de 1 a 3");
        int nivel = ler.nextInt();

        double[] melhoresPesos = new AlgoritmoGenetico().genetico(nivel);
        new Game().joga(melhoresPesos);
    }
}
