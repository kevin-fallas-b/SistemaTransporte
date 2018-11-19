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
import sistematransporte.model.Dijsktra;
import sistematransporte.model.Mapa;
import sistematransporte.model.Nodo;
import sistematransporte.model.Vehiculo;

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
    public static Integer matPeso[][] = new Integer[100][100];

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
            llenarMatPeso();
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
        System.out.println("Mouse X: "+event.getSceneX()+" Mouse Y: "+event.getSceneY());
        if (event.getSceneX() < 422) {
            if (agregarAccidente || agregarReparacion) {
                ubicarArista(event.getSceneX(), event.getSceneY());
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
            }
        }
    };

   static Nodo nodoAux = null;
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
                        if(nodoAux == null){
                            nodoAux = nodo;
                        }
                        else{
                            //Agregar metodo de dikstra
                           // System.out.println("dijkstra "+new Dijsktra().DijkstraPrincipal(nodoAux, nodo));
                           Dijsktra d = new Dijsktra(mapa);
                           d.ejecutar(nodoAux);
                           nodoAux = null;
                           d.marcarRutaCorta(nodo, Color.ORANGE);
                           Vehiculo v = new Vehiculo();
                           v.setLayoutX((d.getAux().get(0).getStartX())-((v.getFitWidth())/2));
                           v.setLayoutY((d.getAux().get(0).getStartY())-((v.getFitHeight())/2));
                           apCentro.getChildren().add(v);
                           mapa.getDestinos().stream().forEach((t) -> {
                               t.setMarca(false);
                               t.setLongitudCamino(0);
                               t.setNodoAntecesorDisjktra(null);
                           });
                            /*mapa.getDestinos().stream().forEach((t) -> {
                                t.setFill(Color.RED);
                            });
                            */
                            //System.out.println("DIJKSTRA");
                        }
                        
                       /* System.out.println("Numero Nodo " + nodo.getNumNodo());
                        System.out.println("Nodos Adyacentes :" + nodo.getNodosAdyacentes().size());
                        nodo.getNodosAdyacentes().forEach((x) -> {
                            System.out.println("Nodo " + x.getNumNodo());
                        });
                        */
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
    private void presionarBtnCargarNodos(ActionEvent event) {
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

    private void llenarMatPeso() {
        StringBuilder sb = new StringBuilder();
        //Se crea una matriz cuadrada del tamanno del tamano de los nodos totales
        matPeso = new Integer [mapa.getDestinos().size()][mapa.getDestinos().size()];
        
        for (int i = 0; i < mapa.getDestinos().size(); i++) {
            Nodo aux = mapa.getDestinos().get(i);//Ubicamos el nodo con el que vamos a comparar
            for (int j = 0; j < mapa.getDestinos().size(); j++) {
                if(i!=j){// Si no se esta ubicado en la diagonal
                    Nodo aux2 = mapa.getDestinos().get(j);
                    
                    for(Arista arista : mapa.getAristas()){
                        //Intentamos ubicar la arista que coincida con los nodos auxiliares para agregar el peso en la matriz
                        if( ( arista.getDestino().equals(aux) && arista.getOrigen().equals(aux2) ) || ( arista.getDestino().equals(aux2) && arista.getOrigen().equals(aux))){
                            matPeso[i][j] = arista.getPeso();
                        }
                    }
                    //Si no se encontro la arista con los nodos auxiliares se llena la matriz con un peso 0
                    if(matPeso[i][j] == null){
                        matPeso[i][j] = 0;
                    }
                }
                //Si es la diagonal se llena la matriz con peso 0
                else{
                    matPeso[i][j] = 0;
                }
                
                sb.append(matPeso[i][j]);
                sb.append("\t");
            }
            
            sb.append("\n");
        }
        
        //Imprime la matriz
       // System.out.println("Matriz: \n"+sb);
        
    }

    private void ubicarArista(Double xx, Double yy) {
        Integer x= xx.intValue();
        Integer y= yy.intValue();
        for (Arista arista : mapa.getAristas()) {
            if (arista.getStartY() >= arista.getEndY()) {
                //esta caso es una arista que va de abajo hacia arriba
                if ((x > arista.getStartX()-10 && x < arista.getEndX()+10) && (y < arista.getStartY() && y > arista.getEndY())) {
                    System.out.println("Arista Encontrada origen: "+arista.getOrigen().getNumNodo()+" destino: "+arista.getDestino().getNumNodo());
                }
            } else if ((arista.getStartY() <= arista.getEndY())) {
                //arista que va de arriba hacia abajo 
                if ((x < arista.getStartX()+10 && x > arista.getEndX()-10) && (y > arista.getStartY() && y < arista.getEndY())) {
                    System.out.println("Arista Encontrada origen: "+arista.getOrigen().getNumNodo()+" destino: "+arista.getDestino().getNumNodo());
                }
            }
        }
    }
}
