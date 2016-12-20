
import java.lang.*;
import java.io.*;

/**
 *
 * @author Eleonora Macuglia
 */
public class RegistroVoti 
{
    public String[][] registroVoti; // matrice pubblica per eventuale utilizzo, attualmente non è necessario emetterla pubblica
    public String[][] registroVotiMedie;
    
    /**
    *
    * Costruttore
    */
    public RegistroVoti(String[][] matriceInput){
        
        registroVoti = new String[matriceInput.length][2]; // Mi copio l'array perche' andro' a modificarlo
        for(int i = 0; i < matriceInput.length; i++){
            registroVoti[i][0] = matriceInput[i][0];
            registroVoti[i][1] = matriceInput[i][1];
        }

        String[][] tmpMatrice = new String[registroVoti.length][2];
        
        String tmpNome = "";
        float sommaVoti = 0;
        float numeroVoti = 0;
        int totaleStudenti = 0;
        
        /*
        Algoritmo che fa le medie di ogni studente mantenendo l'ordine con cui sono elencati i nomi (no ordine alfabetico)
        complessita' O(n^2)
        */
        for(int i = 0; i < registroVoti.length; i++)
        {
            if(registroVoti[i][0].length() == 0) //Il nome e' vuoto, e' già stato "usato"! continuo il ciclo
                continue;
            
            tmpNome = registroVoti[i][0];
            
            // Ciclo che va' a trovare tutti i voti dell'attuale studente (variabile tmpNome)
            for(int j = 0; j < registroVoti.length; j++){
                if(registroVoti[j][0].length() == 0)
                  continue;
                
                if(tmpNome.compareTo(registroVoti[j][0]) == 0){
                    int voto  = java.lang.Integer.parseInt(registroVoti[j][1]); // In fase di caricamento dei dati avevo gia' controllato che il contenuto della cella fosse effettivamente un numero! lo salto e lo do' per buono!
                    sommaVoti += voto;
                    numeroVoti++;
                    registroVoti[j][0] = "";
                }
            }
            
            // Salvo lo studente nell'array temporaneo "grande"
            float tmpMediaVoto = (sommaVoti/numeroVoti);
            tmpMatrice[totaleStudenti][0] = tmpNome;
            tmpMatrice[totaleStudenti][1] = Math.rint(tmpMediaVoto*100.0)/100.0 +""; // Math.rint arrotonda al numero pari piu' vicino (se equidistante)
            
            sommaVoti = 0;
            numeroVoti = 0;
            
            totaleStudenti++; // e' un nuovo studente!
        }
        
        //Non e' strettamente necessario ma cosi' ho sempre a disposizione un registroVoti "pulito"
        for(int i = 0; i < matriceInput.length; i++){
            registroVoti[i][0] = matriceInput[i][0];
            registroVoti[i][1] = matriceInput[i][1];
        }
        
        // Copio gli studenti dall'array temporaneo in quello "definitivo"
        registroVotiMedie = new String[totaleStudenti][2];
        for(int i= 0; i <totaleStudenti; i++){
            registroVotiMedie[i][0]= tmpMatrice[i][0];
            registroVotiMedie[i][1]= tmpMatrice[i][1];
        }
    }
    
    /**
    *
    * Sovrascrivo il metodo toString
    */
    @Override public String toString(){
        
        String output = "";
        
        for(int i= 0; i <registroVotiMedie.length; i++){
                    output += registroVotiMedie[i][0] + " " + registroVotiMedie[i][1] +"\r\n";
        }
        
        return output;
    }
}
