/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistematransporte.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXToggleButton;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.LinkedList;
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
import javafx.scene.shape.Circle;
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
    private Mapa mapa;
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
    private Boolean desarrollador=false;
    @FXML
    private JFXButton btnOcultarNodos;
    private LinkedList<Circle> dibujosNodos = new LinkedList<Circle>();//lista de los circulos de nodos
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        tbMostrarArea.setSelected(false);
        apCentro.getChildren().remove(ivAreaDelimitada);
        iniciarMapa();
        apCentro.setOnMouseReleased(seleccionarDestino);
        apOpcionesDes.setVisible(false);
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
    
    private void iniciarMapa(){
        ivAreaDelimitada.setVisible(false);
        mapa = new Mapa();
        //ivAreaDelimitada.setOnMouseReleased(mapa.seleccionarDestino);    
        apCentro.getChildren().add(mapa);
        apCentro.getChildren().add(ivAreaDelimitada);
        
    }

    @FXML
    private void presionarToggleMostrar(ActionEvent event) {
        if(tgMostrarNodos.selectedProperty().getValue()){
            sistematransporte.SistemaTransporte.mostrarNodos=true;
        }else{
            sistematransporte.SistemaTransporte.mostrarNodos=false;
        }
    }
    
    public EventHandler<MouseEvent> seleccionarDestino = (MouseEvent event) -> {
        if(sistematransporte.SistemaTransporte.mostrarNodos && event.getSceneX()<422.00){
            Circle circle = new Circle(5.00);
            circle.setLayoutX(event.getSceneX());
            circle.setLayoutY(event.getSceneY());
            circle.setFill(javafx.scene.paint.Color.RED);
            apCentro.getChildren().add(circle);
            Nodo nod = new Nodo(event.getSceneX(),event.getSceneY());
            mapa.agregarDestino(nod);
        }
    };

    @FXML
    private void presionarBtnGuardarDestinos(ActionEvent event) {
        try {
            mapa.guardarDestinosAArchivo();
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(PantPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PantPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void PresionarBtnPintarNodos(ActionEvent event) {
        LinkedList<Nodo> destinos = mapa.getDestinos();
        while(!destinos.isEmpty()){
            Nodo nod = destinos.removeFirst();
            Circle circle = new Circle(5.00);
            circle.setLayoutX(nod.getPosX());
            circle.setLayoutY(nod.getPosY());
            circle.setFill(javafx.scene.paint.Color.RED);
            apCentro.getChildren().add(circle);
            dibujosNodos.add(circle);
        }
    }

    @FXML
    private void presionarBtnCargarNodos(ActionEvent event) throws IOException {
        mapa.cargarNodo();
    }

    @FXML
    private void presionarTbOpcionesDesarrollador(ActionEvent event) {
        if(tbOpcionesDesarrollador.selectedProperty().getValue()){
            apOpcionesDes.setVisible(true);
        }else{
            apOpcionesDes.setVisible(false);
        }
        
    }

    @FXML
    private void presionarBtnOcultarNodos(ActionEvent event) {
        apCentro.getChildren().removeAll(dibujosNodos);
        dibujosNodos = new LinkedList<Circle>();
    }
}
