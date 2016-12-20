
import java.io.*;

/**
 *
 * @author Eleonora Macuglia
 */
public class Istogramma 
{
    public String[][] registroVoti; // matrice pubblica per eventuale utilizzo, attualmente non è necessario emetterla pubblica
    
    /**
    *
    * Costruttore
    */
    public Istogramma(String[][] matriceInput){
        registroVoti = matriceInput;
    }
    
    /**
    *
    * Sovrascrivo il metodo toString
    */
    @Override public String toString(){
        byte[] conteggioVoti = new byte[10];
        String output = "";
        byte piuAlto = 0;
        
        //conto la frequenza dei voti e la salvo nell'array "conteggioVoti"
        for(int i = 0; i < registroVoti.length; i++){
            int voto = java.lang.Integer.parseInt(registroVoti[i][1]);
            conteggioVoti[voto-1] ++;
        }
        
        //setto piuAlto in base al voto con frequenza maggiore
        for(int i = 0; i < conteggioVoti.length; i++){
            if(piuAlto < conteggioVoti[i]){
                piuAlto = conteggioVoti[i];
            }
        }
        
        for(int i=piuAlto; i>0; i--){
            String sub = "";
            for(int j=0; j < conteggioVoti.length; j++){
                if(conteggioVoti[j] >= i){
                    sub += "* ";
                }else{
                    sub += "  ";
                }
            }
            output += sub + "\r\n";
        }
        
        output += "1 2 3 4 5 6 7 8 9 10";
        
        return output;
    }
    
}
