package minimax;
/**
 * Write a description of class Estado here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Sucessor
{
    private int valor;
    private char[][]estado;
    private int linha, coluna;
    
    /**
     * Define um sucessor
     * @param estado corresponde ao estado sucessor
     * @param valor corresponde ao seu valor de utilidade
     */
    public Sucessor(char [][]estado, int valor){
        this.estado = estado;
        this.valor = valor;
    }
    
    public Sucessor(char [][]estado, int valor, int linha, int coluna){
        this.estado = estado;
        this.valor = valor;
        this.linha = linha;
        this.coluna = coluna;
    }
    
    public void setLinha(int linha){
        this.linha = linha;
    }
    
    public void setColuna(int coluna){
        this.coluna = coluna;
    }
    
    public int getLinha(){
        return linha;
    }
    
    public int getColuna(){
        return coluna;
    }
    
    public void setEstado(char [][]estado){this.estado = estado;}
    
    public char[][] getEstado(){return estado;}
    
    public int getValor() {return valor;}
    
    public void setValor(int valor){ this.valor = valor;}
    
    public String toString(){
        String msg = "";
        for(int i=0; i<3; i++){
            for(int j=0; j<3; j++){
                msg = msg + estado[i][j] + " ";
            }
            msg = msg + "\n";
        }
        return msg;
    }
}