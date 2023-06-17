
/**
 * Escreva a descrição da classe Rede aqui.
 * 
 * @author Silvia 
 * @version 12/11/2020
 */
public class Rede
{
    private Neuronio[]camadaOculta;     //rede neural 9x9x9 -> topologia sugerida em aula
    private Neuronio[]camadaSaida;
    private double []saida;             //valores de saída da propagacao
    
    public Rede(int numNeuroniosOculta, int numNeuroniosSaida){
        if(numNeuroniosOculta <=0 || numNeuroniosSaida<=0){ 
            numNeuroniosOculta = 9;
            numNeuroniosSaida = 9;
        }
        camadaOculta = new Neuronio[numNeuroniosOculta];
        camadaSaida = new Neuronio[numNeuroniosSaida];
    }
    
    public void setPesosNaRede(int numEntradas, double []pesos){
        int k=0;
        double []tmp ;
        //Setando os pesos da camada oculta
        for(int i=0; i<camadaOculta.length; i++){
            tmp = new double[numEntradas+1];  //quantidade de pesos dos neurônios da camada oculta
            for(int j=0; j<numEntradas+1; j++){
                tmp[j] = pesos[k];
                k++;
            }
            camadaOculta[i]=new Neuronio(tmp);
        }
        //Setando os pesos da camada de saida
        for(int i=0; i<camadaSaida.length; i++){
            tmp = new double[camadaOculta.length+1];  //quantidade de pesos dos neurônios da camada oculta
            for(int j=0; j<camadaOculta.length+1; j++){
                tmp[j] = pesos[k];
                k++;
            }
            camadaSaida[i]=new Neuronio(tmp);
        }
    }
    
    public double[] propagacao(double []x){
        if(x==null) return null;
        
        double [] saidaOculta = new double[camadaOculta.length];
        saida = new double[camadaSaida.length];
        for(int i=0; i<camadaOculta.length; i++){
            saidaOculta[i]= camadaOculta[i].calculaY(x);
        }
        for(int i=0; i<camadaSaida.length; i++){
            saida[i]= camadaSaida[i].calculaY(saidaOculta);
        }
        return saida;
    }
    
    public String toString(){
        String msg = "Pesos da rede\n";
        msg = msg + "Camada Oculta\n";
        for(int i=0;i<camadaOculta.length; i++){
            msg = msg + "Neuronio " + i + ": " + camadaOculta[i] + "\n";
        }
        msg = msg + "Camada Saida\n";
        for(int i=0;i<camadaSaida.length; i++){
            msg = msg + "Neuronio " + i + ": " + camadaSaida[i] + "\n";
        }
        return msg;
    }
}
