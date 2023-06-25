import java.util.Random;

public class ExemploAG {
    public static void main(String[] args) {

        int cargas[] = {5,10,15,3,10,5,2,16,9,7,5,10,15,3,10,5,2,16,9,7};
        int populacao[][] = new int[11][21];
        
        int populacaoIntermediaria[][] = new int[11][21];
        
        System.out.println("\f");
        Random aleatorio = new Random();
        
        // Gerando população inicial
        
        for(int i=0; i<11;i++){
            for(int j=0; j<20;j++){
                populacao[i][j] = aleatorio.nextInt(2);
            }
        }
        
        // G: Número de gerações 
        for(int g = 0; g < 30; g++){
        
            System.out.println("\n\nGeração: "+g);
            for(int i=0; i<11;i++){
                aptidao(populacao[i], cargas);
            }
            
            System.out.println("Populacao atual");
            for(int i=0; i<11;i++){
                System.out.print("(" + i + ") "); 
                for(int j=0; j<20;j++){
                    System.out.print(populacao[i][j]+" ");
                }
                System.out.println(" - função: " + populacao[i][20]);
            }
            populacaoIntermediaria[0]=elitismo(populacao);
            if(populacaoIntermediaria[0][20]==0) {
                System.out.println("Solução encontrada");
                System.out.println("Cargas - Pessoa 0");
                for(int i=0; i<20; i++){
                    if(populacaoIntermediaria[0][i]==0) System.out.print(cargas[i]+ " ");
                }
                System.out.println("\nCargas - Pessoa 1");
                for(int i=0; i<20; i++){
                    if(populacaoIntermediaria[0][i]==1) System.out.print(cargas[i]+ " ");
                }
                System.out.println();
                break;
            }
            crossOver(populacao, populacaoIntermediaria);
            populacao= populacaoIntermediaria;
            if(aleatorio.nextInt(2)==0) mutacao(populacao);
        }
        
        
    }
    
    public static void mutacao(int [][]populacao){
        Random gera = new Random();
        int quantidade = gera.nextInt(3)+1;
        
        while(quantidade>0){
            int linha = gera.nextInt(10)+1;
            int coluna = gera.nextInt(20);
        
            if(populacao[linha][coluna]==0) populacao[linha][coluna] = 1;
            else populacao[linha][coluna] = 0;
            System.out.println("Mutacao no cromossomo: " + linha + " na coluna: " + coluna);
            quantidade--;
        }
        
    }
    public static void aptidao(int[] linha, int[] cargas){
        int acum1 = 0;
        int acum2 = 0;
        
        for(int i=0; i<20; i++){
            if(linha[i] == 0){
                acum1 += cargas[i];
            }
            else{
                acum2 += cargas[i];
            }
        }
        linha[20] = Math.abs(acum1-acum2);
    }
    
    public static int[] elitismo(int[][] populacao){
        int aux[] = new int[21];
        int linha = 0;
        int menorVal = populacao[0][20];
        
        for(int i =1; i<11; i++){
            if(populacao[i][20] < menorVal){
                menorVal = populacao[i][20];
                linha = i;
            }
        }
        
        for(int j=0; j<21;j++){
            aux[j] = populacao[linha][j];
        }
        
        return aux;
    }
    
    public static void crossOver(int[][] populacao,int[][] populacaoIntermeidiaria){
        Random aleatorio = new Random();
        
        for(int i = 1; i<11; i+=2){
            int individuo1 = torneio(populacao);
            int individuo2 = torneio(populacao);
            
            //int padrasto = torneio(populacao);
            //int madrasta = torneio(populacao);
            
            for(int j= 0; j<10; j++){
                populacaoIntermeidiaria[i][j] = populacao[individuo1][j];
                populacaoIntermeidiaria[i+1][j] = populacao[individuo2][j];
            }
            for(int j= 10; j<20; j++){
                populacaoIntermeidiaria[i][j] = populacao[individuo2][j];
                populacaoIntermeidiaria[i+1][j] = populacao[individuo1][j];
            }
        }
    }
    
    public static int torneio(int[][] populacao){
        Random aleatorio = new Random();
        
        int linha1 = aleatorio.nextInt(11);
        int linha2 = aleatorio.nextInt(11);
        
        if(populacao[linha1][20]>populacao[linha2][20]){
            return linha2;
        }
        else return linha1;
    }
}