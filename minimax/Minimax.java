package minimax;

/**
 * Write a description of class Minimax here.
 * 
 * @author Silvia Moraes 
 * @version 10/04/2017
 */
public class Minimax
{
    private Sucessor melhor;
    private char[][] estado;
    
    /**
     * Minimax: algoritmo de busca adversária
     * @param estado corresponde ao estado atual do tabuleiro
     */
    public Minimax(char [][]estado){
        this.estado = estado;
    }
    
    /**
     * Encontra o melhor estado sucessor ao estado atual. Usa a versão clássica do Minimax.
     * @return devolve um objeto Sucessor. Esse objeto contém o estado sucessor e sua função de utilidade.
     */
    public Sucessor getMelhor(){
        melhor = algoritmo(estado,false, livres(estado));     
        return melhor;
    }
    
     /**
     * Encontra o melhor estado sucessor ao estado atual. Usa a versão Alfa Beta Pruning do Minimax.
     * @return devolve um objeto Sucessor. Esse objeto contém o estado sucessor e sua função de utilidade.
     */
    public Sucessor getMelhorAB(){
        melhor = algoritmoAB(estado,false, livres(estado),-999,999);   
        return melhor;
    }
    
    /**
     * Conta as células livres do tabuleiro. Corresponde a profundidade máxima da árvore de busca.
     * @param estado corresponde ao estado atual do tabuleiro
     * @return devolve a quantidade de células desocupadas.
     */
    public int livres(char [][]estado){
        int cont =0;
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                if(estado[i][j]=='#') cont++;
            }
        }
        return cont;
    }
    
    /**
     * Gera os sucessores de um estado.
     * @param vizinhos corresponde à matriz que conterá os sucessores
     * @param estado corresponde ao estado (tabuleiro) atual
     * @param caracter corresponde ao caracter (X ou O) com o qual os sucessores devem ser gerados
     */
    public void geraVizinhos(char[][][] vizinhos, char [][]estado, char caracter,int [][] posicoes){
        //Guarda as posições livres
        int k=0;
        for(int i=0; i<3; i++)
           for(int j=0; j<3; j++)
              if(estado[i][j]=='#') {
                    posicoes[k][0]=i;
                    posicoes[k][1]=j;
                    k++;
              }
        //Copia o estado atual aos sucessores
        for(int v=0; v<vizinhos.length; v++)
            for(int i=0; i<3; i++)
                for(int j=0; j<3; j++)
                    vizinhos[v][i][j] = estado[i][j];
            
        //Altera os sucessores incluindo o caracter dado como parâmetro nas posições livre
        for(int v=0; v<vizinhos.length; v++)
                  vizinhos[v][posicoes[v][0]][posicoes[v][1]] = caracter;
                  
    }
    
    /**
     * Função utilidade: verifica se alguém ganhou
     * @param atual corresponde ao estado (tabuleiro) atual
     * @param profundidade corresponde à profundidade atual 
     * @return devolve -1 se o X ganhou; +1 se o O ganhou; 0 se houve empate e 100 se ainda há jogo
     */  
    public int utilidade(char [][]atual, int profundidade){
      
        if(vencedorDiagonal(atual,'X')) return -1;
        if(vencedorDiagonal(atual,'O')) return 1; 
        if(vencedorLinha(atual,'X')) return -1;
        if(vencedorLinha(atual,'O')) return 1;
        if(vencedorColuna(atual,'X')) return -1;
        if(vencedorColuna(atual,'O')) return 1;
        if(profundidade==0) return 0;
        return 100;
    }
    
    /**
     * Verifica se há vencedor em linha
     * @param atual corresponde ao estado (tabuleiro) atual
     * @param caracter corresponde a X (usuário) ou O (computador)
     * @return true se houve vencedor e false em c.c.
     */
    public boolean vencedorLinha(char [][]atual, char caracter){  
        for(int i=0; i<3; i++){
            int cont=0;
            for(int j=0; j<3; j++){
                if(atual[i][j]!=caracter) break;
                cont++;
            }
            if(cont==3) return true;
        }
        return false;
    }
    
    /**
     * Verifica se há vencedor em coluna
     * @param atual corresponde ao estado (tabuleiro) atual
     * @param caracter corresponde a X (usuário) ou O (computador)
     * @return true se houve vencedor e false em c.c.
     */
    public boolean vencedorColuna(char [][]atual, char caracter){  
        for(int j=0; j<3; j++){
            int cont=0;
            for(int i=0; i<3; i++){
                if(atual[i][j]!=caracter) break;
                cont++;
            }
            if(cont==3) return true;
        }
        return false;
    }
    
    /**
     * Verifica se há vencedor em diagonal
     * @param atual corresponde ao estado (tabuleiro) atual
     * @param caracter corresponde a X (usuário) ou O (computador)
     * @return true se houve vencedor e false em c.c.
     */
    public boolean vencedorDiagonal(char [][]atual, char caracter){  
        int cont = 0;
        for(int i=0; i<3; i++){
             if(atual[i][i]!=caracter) break;
             cont++;
        }
        if(cont==3) return true;
        
        cont = 0;
        for(int i=0; i<3; i++){
             if(atual[i][2-i]!=caracter) break;
             cont++;
        }
        if(cont==3) return true;
        
        return false;
    }
    
    /**
     * Algoritmo Minimax - versão clássica
     * @param estado corresponde ao estado atual
     * @param jogador: true é usuário;false é o computador
     * @param profundidade também corresponde a quantidade de células livres
     * @return devolve o melhor estado sucessor para o estado atual
     */
    public Sucessor algoritmo(char estado[][], boolean jogador, int profundidade){
        
        int valor = utilidade(estado,profundidade);
        if(valor!=100) return new Sucessor(estado,valor);
        
        int indSuc = 0;
        char[][][]vizinhos = new char [profundidade][3][3];
        int [][] posicoes = new int[vizinhos.length][2];
        
        if(jogador){   //adversário
            int menor  = 999;
            geraVizinhos(vizinhos,estado,'X',posicoes);
            
            for(int v=0; v<vizinhos.length;v++){
                Sucessor atual = algoritmo(vizinhos[v],false,profundidade-1);
                valor = atual.getValor();
                if(valor < menor) {
                    menor  = valor;
                    indSuc = v;
                }
            }
            return new Sucessor(vizinhos[indSuc],menor,posicoes[indSuc][0],posicoes[indSuc][1]);
        }
        else{
            int maior = -999;
            geraVizinhos(vizinhos,estado,'O',posicoes);
            
            for(int v=0; v<vizinhos.length;v++){
                Sucessor atual = algoritmo(vizinhos[v],true,profundidade-1);
                valor = atual.getValor();
                if(valor>maior) {
                    maior = valor;
                    indSuc = v;
                }
            }
            return new Sucessor(vizinhos[indSuc],maior,posicoes[indSuc][0],posicoes[indSuc][1]);
        }
    }
    
    /**
     * Algoritmo Minimax - versão Alfa Beta Pruning
     * @param estado corresponde ao estado atual
     * @param jogador: true é usuário;false é o computador
     * @param profundidade também corresponde a quantidade de células livres
     * @param alfa corresponde ao melhor valor MAX
     * @param beta corresponde ao melhor valor MIN
     * @return devolve o melhor estado sucessor para o estado atual
     */    
    public Sucessor algoritmoAB(char estado[][], boolean jogador, int profundidade,int alfa,int beta){    
        int valor = utilidade(estado,profundidade);
        if(valor!=100) return new Sucessor(estado,valor);
        
        int indSuc = 0;
        char[][][]vizinhos = new char [profundidade][3][3];
        int [][] posicoes = new int[vizinhos.length][2];
        
        if(jogador){   //adversário
            int menor  = 999;
            geraVizinhos(vizinhos,estado,'X',posicoes);
            
            for(int v=0; v<vizinhos.length;v++){
                Sucessor atual = algoritmoAB(vizinhos[v],false,profundidade-1,alfa,beta);
                valor = atual.getValor();
                if(valor < menor) {
                    menor  = valor;
                    indSuc = v;
                }
                if(menor<alfa) return new Sucessor(vizinhos[indSuc],menor,posicoes[indSuc][0],posicoes[indSuc][1]);
                if(valor<beta) beta = valor;
            }
            return new Sucessor(vizinhos[indSuc],menor,posicoes[indSuc][0],posicoes[indSuc][1]);
        }
        else{
            int maior = -999;
            geraVizinhos(vizinhos,estado,'O',posicoes);
            for(int v=0; v<vizinhos.length;v++){
                Sucessor atual = algoritmoAB(vizinhos[v],true,profundidade-1,alfa,beta);
                valor = atual.getValor();
                if(valor>maior) {
                    maior = valor;
                    indSuc = v;
                }
                if(maior>beta) return new Sucessor(vizinhos[indSuc],maior,posicoes[indSuc][0],posicoes[indSuc][1]);
                if(valor>alfa) alfa = valor;
            }
            return new Sucessor(vizinhos[indSuc],maior,posicoes[indSuc][0],posicoes[indSuc][1]);
        }
    }

}
