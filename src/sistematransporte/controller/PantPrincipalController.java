/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistematransporte.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXToggleButton;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import sistematransporte.model.Arista;
import sistematransporte.model.Mapa;
import sistematransporte.model.Nodo;

/**
 * FXML Controller class
 *
 * @author Kevin F
 */
public class PantPrincipalController extends Controller implements Initializable {

    @FXML
    private AnchorPane apCentro;
    @FXML
    private JFXToggleButton tbMostrarArea;
    @FXML
    private ImageView ivAreaDelimitada;
    @FXML
    private RadioButton rbDirigido;
    @FXML
    private JFXRadioButton rbNoDirigido;
    @FXML
    private JFXRadioButton rbDijkstra;
    @FXML
    private JFXRadioButton rbFloyd;
    @FXML
    private ToggleGroup tgDirigido;
    @FXML
    private ToggleGroup tgAlogiritmo;
    @FXML
    private JFXButton btnAccidente;
    @FXML
    private JFXButton btnReparacion;
    @FXML
    private JFXRadioButton rbTraficoBajo;
    @FXML
    private ToggleGroup tgTrafico;
    @FXML
    private JFXRadioButton rbTraficoAlto;
    @FXML
    private JFXRadioButton rbTraficoMedio;
    @FXML
    private JFXToggleButton tgMostrarNodos;
    @FXML
    private JFXButton btnGuardarDesitnos;
    @FXML
    private JFXButton btnCargarNodos;
    @FXML
    private JFXToggleButton tbOpcionesDesarrollador;
    @FXML
    private AnchorPane apOpcionesDes;
    @FXML
    private JFXButton btnOcultarNodos;
    private Mapa mapa = new Mapa();
    private Boolean agregarAccidente = false;//bool utilizado para solo agregar un accidente a la vez
    private Boolean agregarReparacion = false;//igual que arriba
    private Integer matPeso[][]= new Integer[100][100];
    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            tbMostrarArea.setSelected(false);
            apCentro.getChildren().remove(ivAreaDelimitada);
            
            iniciarMapa();
            apCentro.setOnMouseClicked(onClick);
            apCentro.setOnMouseReleased(seleccionarDestino);
            apOpcionesDes.setVisible(false);
        } catch (IOException ex) {
            Logger.getLogger(PantPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize() {
    }

    @FXML
    private void presionarToggleArea(ActionEvent event) {
        if (tbMostrarArea.selectedProperty().getValue()) {
            ivAreaDelimitada.setVisible(true);
        } else {
            ivAreaDelimitada.setVisible(false);
        }
    }

    private void iniciarMapa() throws IOException {
        
        ivAreaDelimitada.setVisible(false);
        ivAreaDelimitada.setOnMouseReleased(seleccionarDestino);
        apCentro.getChildren().add(mapa);
        apCentro.getChildren().add(ivAreaDelimitada);
        cargarNodos();
        cargarAristas();
    }

    @FXML
    private void presionarToggleMostrar(ActionEvent event) {
        //sistematransporte.SistemaTransporte.mostrarNodos = tgMostrarNodos.selectedProperty().getValue();
    }

    public void cargarNodos() {
        try {
            mapa.cargarNodo();
            
        } catch (IOException ex) {
            Logger.getLogger(PantPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    public void cargarAristas() {
        try {
            mapa.cagarAristas();
            
            mapa.getAristas().stream().forEach((arista) -> {
                apCentro.getChildren().add(arista);
            });
            
            mapa.getDestinos().stream().forEach((nodo) -> {
                apCentro.getChildren().add(nodo);
            });
            
        } catch (IOException ex) {
            Logger.getLogger(PantPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    private EventHandler<MouseEvent> onClick = (MouseEvent event) -> {
        if (event.getSceneX() < 422) {
            if (agregarAccidente || agregarReparacion) {
                System.out.println("Agregando Detalles a carretera.");
                ImageView imagen;
                if (agregarAccidente) {
                    imagen = new ImageView(new Image("sistematransporte/resources/accidente.png"));
                    agregarAccidente = false;
                } else {
                    imagen = new ImageView(new Image("sistematransporte/resources/trabajoVia.png"));
                    agregarReparacion = false;
                }
                imagen.setFitWidth(30.00);
                imagen.setFitHeight(25.00);
                imagen.setLayoutX(event.getSceneX() - 15);
                imagen.setLayoutY(event.getSceneY() - 12);
                apCentro.getChildren().add(imagen);
                llenarMatPeso();
            }
        }
    };
    
    public EventHandler<MouseEvent> seleccionarDestino = (MouseEvent event) -> {

        Double y1 = event.getSceneY() - 10;
        Double y2 = event.getSceneY() + 10;

        while (y1 <= y2) {
            Double x1 = event.getSceneX() - 10;
            Double x2 = event.getSceneX() + 10;
            while (x1 <= x2) {
                for (Nodo nodo : mapa.getDestinos()) {
                    if (x1 == nodo.getCenterX() && y1 == nodo.getCenterY()) {
                        
                        nodo.setFill(Color.AQUA);
                        
                        System.out.println("Click sobre nodo numero: "+nodo.getNumNodo()+" ,contiene " + nodo.getAristasAdyacentes().size()+ " aristas adyacentes" );
                        
                        nodo.getAristasAdyacentes().stream().forEach((arista) -> {
                            if(arista.getDestino().equals(nodo)){
                                System.out.println("Destino nodo num "+arista.getOrigen().getNumNodo()+" origen: "+arista.getDestino().getNumNodo());
                            }
                            else{
                                System.out.println("Destino nodo num "+arista.getDestino().getNumNodo()+" origen: "+arista.getOrigen().getNumNodo());
                            }
                            
                            
                            //System.out.println("Peso: "+ arista.getPeso());
                        });
                        
                        x1 = x2;
                        y1 = y2;
                    }
                }
                x1++;
            }
            y1++;
        }
    };

    @FXML
    private void presionarBtnGuardarDestinos(ActionEvent event) {
        /*try {
            mapa.guardarDestinosAArchivo();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PantPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PantPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }*/
    }


    @FXML
    private void presionarBtnCargarNodos(ActionEvent event){
        mapa.getDestinos().stream().forEach((nodo) -> {
            nodo.setVisible(true);
        });
        
        mapa.getAristas().stream().forEach((arista) -> {
            arista.setVisible(true);
        });
    }

    @FXML
    private void presionarTbOpcionesDesarrollador(ActionEvent event) {
        if (tbOpcionesDesarrollador.selectedProperty().getValue()) {
            apOpcionesDes.setVisible(true);
        } else {
            apOpcionesDes.setVisible(false);
        }
    }

    @FXML
    private void presionarBtnOcultarNodos(ActionEvent event) {
        mapa.getDestinos().stream().forEach((nodo) -> {
            nodo.setVisible(false);
        });
        mapa.getAristas().stream().forEach((arista) -> {
            arista.setVisible(false);
        });
    }

    @FXML
    private void presionarBtnAccidente(ActionEvent event) {
        agregarReparacion = false;
        agregarAccidente = true;
    }

    @FXML
    private void presionarBtnReparacion(ActionEvent event) {
        agregarReparacion = true;
        agregarAccidente = false;
    }
    private void llenarMatPeso(){
        for(int i=0;i<100;i++){
            for(int j=0;j<100;j++){
                matPeso[i][j]=0;
            }
        }
        
        LinkedList<Nodo> nodos = mapa.getDestinos();
        Nodo aux;
        for(int k=0;k<nodos.size();k++){
            aux=nodos.get(k);
            List <Arista> nodosAdyacentes = aux.getAristasAdyacentes();
            for(int i=0;i<nodosAdyacentes.size();i++){
                Arista auxArista=nodosAdyacentes.get(i);
                matPeso[aux.getNumNodo()][auxArista.getDestino().getNumNodo()]=auxArista.getPeso();
            }
        }
    }
}
