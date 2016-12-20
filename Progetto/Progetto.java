
import java.io.*;

/**
*
* @author Eleonora Macuglia
*/
public class Progetto {

    public static String percorsoInputFile = "";
    public static String percorsoOutMediaFile = "";
    public static String percorsoOutIstoFile = "";
    
    /**
    *
    * Classe iniziale
    * 
    * @param args gli argomenti della linea di comando
    */
    public static void main(String args[]) { 
        
        if (args.length < 3){
            System.out.println("Argomenti insufficienti");
            System.out.println("Utilizzo del programma: java NomeProgramma FileConVoti FileMedie FileIstogramma");
            System.exit(0);
        }else{
            percorsoInputFile = args[0];
            percorsoOutMediaFile = args[1];
            percorsoOutIstoFile = args[2];

            GUI GUIProgetto = new GUI();
        }
    }
    
    /**
     * Classe per ordinare una matrice bidimensionale
     *
     * @param input matrice da ordinare
     * @return la matrice ordinata
     */
    public static String[][] ordinaMatrice(String[][] input){
        
        String[][] output = new String[input.length][2];
        
        for(int i = 0; i < input.length; i++){
            output[i][0] = input[i][0];
            output[i][1] = input[i][1];
        }
        
        mergeSort(output);
        // Utilizzo mergesort, ordino la prima colonna della matrice    
        
        return output;
    }
    
    /**
     * Algoritmo di ordinamento MERGESORT
     * O(nlogn)
     * 
     * @param array da ordinare
     */
    private static void mergeSort(String[][] array){
        
        if(array.length>=2){ // Divide finche' non si ottengono array da "fondere" di dimensione 1
            
            String[][] sinistra = new String[array.length/2][2];  // Creo l'array a sinsitra con la meta' dei nomi
            for(int i = 0; i < sinistra.length; i++){  // Copio la prima parte
                sinistra[i][0] = array[i][0];
                sinistra[i][1] = array[i][1];
            }
            
            String[][] destra = new String[array.length - sinistra.length][2]; // Creo l'array a destra con l'altra meta' dei nomi
            for(int i = 0; i < destra.length; i++){  // Copio la seconda parte
                destra[i][0] = array[sinistra.length + i][0];
                destra[i][1] = array[sinistra.length + i][1];
            }
            
            mergeSort(sinistra); // Ricorsione fino a array di lunghezza 1
            mergeSort(destra); 
            merge(array, sinistra, destra);
        }
    }
    private static void merge(String[][] array, String[][] sinistra, String[][] destra){
        int i = 0;
        int j = 0;
        
        for(int indice=0; indice < array.length; indice++){
            // SE (j >= destra.length) e' VERO allora significa che ho "finito" tutti gli elementi a destra, quindi il resto e' per forza a sinistra
            // SE (i< sinistra.length) viceversa
            if((j >= destra.length) || ((i< sinistra.length) && sinistra[i][0].compareToIgnoreCase(destra[j][0]) <0)){ // compareToIgnoreCase per ignorare le maiuscole
               array[indice][0] = sinistra[i][0];
               array[indice][1] = sinistra[i][1];
               
               i++; // indice relativo a sinistra
            }else{
               array[indice][0] = destra[j][0];
               array[indice][1] = destra[j][1];
               
               j++; // indice relativo a destra
            }
        }
    }
    
    /**
     * Classe per riempire una matrice con i dati caricati da file
     *
     * @param percorso percorso del file da caricare
     * @return la matrice biedimensionale di chiavi-valore
     */
    public static String[][] caricaDaFile(String percorso){
        String contenuto = "";
        try{
            FileInputStream f = new FileInputStream(percorsoInputFile);
            
            int tmp = f.read();  //leggo il primo byte per riempire il buffer caratteri tmp
            while (tmp > 0){
                contenuto += (char)tmp;
                tmp = f.read();
            }
            f.close();
        }catch(IOException e){
            System.out.println("File di input inesistente! path: " + percorsoInputFile);
        }
        
        String[] righe = contenuto.split("\\r?\\n");  // Divido la stringa in base alle righe
        String[][] matriceFinale = new String[righe.length][2];
        
        for (int i=0; i<righe.length; i++){
            String[] valori = righe[i].split(" ");  //separo gli elementi della stringa in coppie chiave-valore (separati da uno spazio nel file)
            if (valori.length < 2){
                System.out.println("Dati in input non validi");
            }else{
                matriceFinale[i][0] = valori[0];
                matriceFinale[i][1] = valori[1];
                try{
                    int tmp = java.lang.Integer.parseInt(valori[1]);
                }catch(Exception e){
                    System.out.println("Voto non valido");
                }
            }
        }
        
        return matriceFinale;
    }
        
    /**
     * Classe per scrivere dei dati in un file
     *
     * @param percorso percorso del file
     * @param stringaDaScrivere stringa da scrivere sul file
     */
    public static void scriviSuFile(String percorso, String stringaDaScrivere){
        try{
            FileOutputStream file = new FileOutputStream(percorso, false);
            PrintStream output = new PrintStream(file);
            
            output.print(stringaDaScrivere);
            
            output.flush();
            output.close();

            System.out.println("Dati salvati in " + percorso);
           
        }catch(FileNotFoundException e){
            System.out.println("Il file " + percorso + " non puo' essere creato");
        }
    }
    
}
