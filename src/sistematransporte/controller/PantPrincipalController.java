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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
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
    private Mapa mapa = new Mapa();
    @FXML
    private JFXToggleButton tgMostrarNodos;
    @FXML
    private JFXButton btnGuardarDesitnos;
    @FXML
    private JFXButton btnPintarNodos;
    @FXML
    private JFXButton btnCargarNodos;
    @FXML
    private JFXToggleButton tbOpcionesDesarrollador;
    @FXML
    private AnchorPane apOpcionesDes;
    @FXML
    private JFXButton btnOcultarNodos;

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
            apCentro.setOnMouseReleased(seleccionarDestino);
            apOpcionesDes.setVisible(false);
            
            Nodo point = mapa.getDestinos().get(0);
            Nodo point2 = mapa.getDestinos().get(1); 
            
            System.out.println(point.getPuntoMapa().distance(point2.getPuntoMapa()));
            
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
        if (tgMostrarNodos.selectedProperty().getValue()) {
            sistematransporte.SistemaTransporte.mostrarNodos = true;
        } else {
            sistematransporte.SistemaTransporte.mostrarNodos = false;
        }
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
                        System.out.println("Arista Adyacentes: "+ nodo.getAristasAdyacentes().size());
                        nodo.getAristasAdyacentes().stream().forEach((arista) -> {
                            System.out.println("Peso: "+ arista.getPeso());
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
    private void PresionarBtnPintarNodos(ActionEvent event) {
        
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
}
