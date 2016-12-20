
import java.awt.*;
import java.awt.event.*;

/**
*
*@author Eleonora Macuglia
*/
public class GUI extends Frame{
    private TextArea tVoti, tMedie, tIsto;
    private Checkbox checkOrdinaVoti, checkOrdinaMedie;
    
    /**
    *
    * Costruttore
    */
    public GUI(){
        super();
        setBounds(200, 200, 800, 400);
        setTitle("Progetto");
        
        addWindowListener(new GUIEvents());
        
        Panel panelNorth = new Panel();
        Panel panelSouth = new Panel();
        Panel panelCenter = new Panel();
        
        Button btnLeggiVoti = new Button("Leggi voti");
        btnLeggiVoti.addActionListener(new ClickEventLeggiVoti());
        
        Button btnCalcola = new Button("Calcola");
        btnCalcola.addActionListener(new ClickEventCalcola());
        
        Button btnEsci = new Button("Esci");
        btnEsci.addActionListener(new ClickEventEsci());
        
        tVoti = new TextArea();
        tMedie = new TextArea();
        tIsto = new TextArea();
        tIsto.setFont(new Font("monospaced", Font.PLAIN, 12)); //Setto il font dell'istogramma su un font monospace con dimensione 12 e font PLAIN
        
        MenuBar menuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        Menu menuModifica = new Menu("Modifica");
        
        MenuItem pulsOrdinaVoti = new MenuItem("Ordina voti");
        pulsOrdinaVoti.addActionListener(new ClickEventBtnOrdinaVoti());
        MenuItem pulsOrdinaMedie = new MenuItem("Ordina medie");
        pulsOrdinaMedie.addActionListener(new ClickEventBtnOrdinaMedie());
        
        menuModifica.add(pulsOrdinaVoti);
        menuModifica.add(pulsOrdinaMedie);
        
        MenuItem pulsSalva = new MenuItem("Salva");
        pulsSalva.addActionListener(new ClickEventSalva());
        MenuItem pulsEsci = new MenuItem("Esci");
        pulsEsci.addActionListener(new ClickEventEsci());;
        
        menuFile.add(pulsSalva);
        menuFile.add(pulsEsci);
        
        menuBar.add(menuFile);
        menuBar.add(menuModifica);
        
        setMenuBar(menuBar);
        
        checkOrdinaVoti = new Checkbox("Ordina voti", false);
        checkOrdinaVoti.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent ie){
                isVotiOrdinati = checkOrdinaVoti.getState();
                ChangeOrdinamentoVoti(isVotiOrdinati);
            }
        });

        checkOrdinaMedie = new Checkbox("Ordina medie", false);
        checkOrdinaMedie.addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent ie){
                isMedieOrdinate = checkOrdinaMedie.getState();
                ChangeOrdinamentoMedie(isMedieOrdinate);
            }
        }); 
        
        add(panelNorth, BorderLayout.NORTH);
        panelNorth.add(btnLeggiVoti);
        panelNorth.add(btnCalcola);
        panelNorth.add(checkOrdinaVoti);
        panelNorth.add(checkOrdinaMedie);
        
        add(panelCenter, BorderLayout.CENTER);
        panelCenter.setLayout(new GridLayout(1,3));  //creo layout a griglia: 1 riga e 3 colonne
        panelCenter.add(tVoti);
        panelCenter.add(tMedie);
        panelCenter.add(tIsto);
        
        add(panelSouth, BorderLayout.SOUTH);
        panelSouth.add(btnEsci);
                
        setVisible(true);   
    }
    
    private boolean isVotiOrdinati = false;
    private boolean isMedieOrdinate = false;
    private RegistroVoti registroVoti = null;
    private Istogramma nuovoIstogramma = null;
    private String[][] ContenutoInputFile; // Rimane NON ordinato
    
    /**
    *
    * Ascoltatore per la GUI
    */
    class GUIEvents extends WindowAdapter {
        public void windowClosing(WindowEvent e) {
            System.out.println("Window closing");
            System.exit(0);
        }
    }
    
    /**
    *
    * Ascoltatore per il pulsante "Leggi voti"
    */
    public class ClickEventLeggiVoti implements ActionListener{
        public void actionPerformed(ActionEvent e){
            
            ContenutoInputFile = Progetto.caricaDaFile(Progetto.percorsoInputFile);
            
            String out = "";
            for(int i = 0; i < ContenutoInputFile.length; i++){
                out += ContenutoInputFile[i][0] + " " + ContenutoInputFile[i][1] + "\n";
            }
            
            tVoti.setText(out);
        }
    }
    
    /**
    *
    * Ascoltatore per il pulsante "Calcola"
    */
    public class ClickEventCalcola implements ActionListener{
        public void actionPerformed(ActionEvent e){
            try{
                registroVoti = new RegistroVoti(ContenutoInputFile);
                tMedie.setText(registroVoti.toString());
                
                nuovoIstogramma = new Istogramma(ContenutoInputFile);
                tIsto.setText(nuovoIstogramma.toString());
            }catch(NullPointerException error){
                System.out.println("Devi prima leggere i voti!");
            }
        }
    }
    
    /**
    *
    * Ascoltatore per il pulsante "Esci"
    */
    public class ClickEventEsci implements ActionListener{
        public void actionPerformed(ActionEvent e){
            System.exit(0);
        }
    }
    
    /**
    *
    * Ascoltatore per il pulsante "Salva"
    */
    public class ClickEventSalva implements ActionListener{
        public void actionPerformed(ActionEvent e){
            Progetto.scriviSuFile(Progetto.percorsoOutMediaFile, tMedie.getText()); // Potrei usare come argomento registroVoti.toString(), ma voglio salvare quello effettivamente mostrato
            Progetto.scriviSuFile(Progetto.percorsoOutIstoFile, tIsto.getText());
        }
    }
    
    /**
    *
    * Ascoltatore per il pulsante "Ordina voti"
    */
    public class ClickEventBtnOrdinaVoti implements ActionListener{
        public void actionPerformed(ActionEvent e){
            
                ChangeOrdinamentoVoti(!isVotiOrdinati);
        }
    }
    
    /**
    *
    * Metodo per passare da un ordinamento all'altro dei voti
    * 
    * @param stato indica se e' ordinata(true) o non ordinata(false)
    */
    private void ChangeOrdinamentoVoti(boolean stato){
        try{
            isVotiOrdinati = stato;
            checkOrdinaVoti.setState(isVotiOrdinati);
            String[][] daVisualizzare = null;

            if(isVotiOrdinati){
                daVisualizzare = Progetto.ordinaMatrice(ContenutoInputFile);
                //visualizzione ordinata
            }else{
                daVisualizzare = ContenutoInputFile;
                //visualizzazione NON ordinata
            }

            // VISUALIZZAZIONE del contenuto di daVisualizzare
            String out = "";
            for(int i = 0; i < daVisualizzare.length; i++){
                out += daVisualizzare[i][0] + " " + daVisualizzare[i][1] + "\n";
            }

            tVoti.setText(out);
            
        }catch(NullPointerException error){
            System.out.println("Devi prima leggere i voti!");
        }
    }
    
    /**
    *
    * Ascoltatore per il pulsante "Odina medie"
    */
    public class ClickEventBtnOrdinaMedie implements ActionListener{
        public void actionPerformed(ActionEvent ae){
                ChangeOrdinamentoMedie(!isMedieOrdinate);
        }
    }
     
    /**
    *
    * Metodo per passare da un ordinamento all'altro delle medie
    * 
    * @param stato indica se e' ordinata(true) o non ordinata(false)
    */
    public void ChangeOrdinamentoMedie(boolean stato){
        try{
            isMedieOrdinate = stato;
            checkOrdinaMedie.setState(isMedieOrdinate);
            String[][] daVisualizzare = null;

            if(isMedieOrdinate){
                daVisualizzare = Progetto.ordinaMatrice(registroVoti.registroVotiMedie);
                //visualizzione ordinata
            }else{
                daVisualizzare = registroVoti.registroVotiMedie;
                //visualizzazione NON ordinata
            }

            // VISUALIZZAZIONE del contenuto di "daVisualizzare"
            String out = "";
            for(int i = 0; i < daVisualizzare.length; i++){
                out += daVisualizzare[i][0] + " " + daVisualizzare[i][1] + "\n";
            }

            tMedie.setText(out);
            
        }catch(NullPointerException e){
            System.out.println("Devi prima calcolare le medie!");
        }
    }
}
