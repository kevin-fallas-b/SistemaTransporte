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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sistematransporte.model.Accidente;
import sistematransporte.model.Arista;
import sistematransporte.model.Dijsktra;
import sistematransporte.model.Mapa;
import sistematransporte.model.Nodo;
import sistematransporte.model.Vehiculo;
import sistematransporte.util.Mensaje;

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
    private JFXButton btnCargarNodos;
    @FXML
    private JFXToggleButton tbOpcionesDesarrollador;
    @FXML
    private AnchorPane apOpcionesDes;
    @FXML
    private JFXButton btnOcultarNodos;
    private Mapa mapa = new Mapa();
    public static Boolean agregarAccidente = false;//bool utilizado para solo agregar un accidente a la vez
    public static Boolean agregarReparacion = false;//igual que arriba
    private final Mensaje mensaje = new Mensaje();
    public static Integer matPeso[][];
    private ArrayList<Nodo> ruta = new ArrayList();
    private ArrayList<Arista> rutaConAristasAlrevez = new ArrayList<>();
    public static boolean animacionTermin = true;
    public static AnchorPane anchorPane;
    private JFXRadioButton rTrafico = null;
    @FXML
    private Label lbCostoFinal;
    @FXML
    private Label lbRecorridoEstimado;
    @FXML
    private Label lbRecorridoFinal;
    @FXML
    private Label lbTiempo;
    Boolean enEjecucion = false;
    @FXML
    private Label lbTiempoMin;
    public static ArrayList<Arista> accidentes = new ArrayList();
    public static ArrayList<Accidente> imagenesAccidentes = new ArrayList();
    public static ArrayList<Arista> auxAristas;
    public static Boolean timerEnEjecucion = false;
    public static Boolean rutaNueva = false;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            lbTiempoMin.setText("0");
            tbMostrarArea.setSelected(false);
            apCentro.getChildren().remove(ivAreaDelimitada);
            iniciarMapa();
            apCentro.setOnMouseReleased(seleccionarDestino);
            apOpcionesDes.setVisible(false);
            llenarMatPeso();
            anchorPane = apCentro;

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

    static Nodo nodoOrigen = null;

    public EventHandler<MouseEvent> seleccionarDestino = (MouseEvent event) -> {
        if (!agregarAccidente && !agregarReparacion && !enEjecucion) {
            //metodo que se encarga de selecionar destinos y llamar
            Double y1 = event.getSceneY() - 10;
            Double y2 = event.getSceneY() + 10;

            while (y1 <= y2) {
                Double x1 = event.getSceneX() - 10;
                Double x2 = event.getSceneX() + 10;
                while (x1 <= x2) {
                    for (Nodo nodo : mapa.getDestinos()) {
                        if (x1 == nodo.getCenterX() && y1 == nodo.getCenterY()) {
                            nodo.setFill(Color.AQUA);
                            if (nodoOrigen == null && !enEjecucion) {
                                nodoOrigen = nodo;
                                lbTiempoMin.setText("0");
                                lbTiempo.setText("");
                                lbCostoFinal.setText("");
                                lbRecorridoFinal.setText("");
                                lbRecorridoEstimado.setText("");
                            } else {
                                if (nodo != nodoOrigen && !enEjecucion) {
                                    animacionTermin = true;
                                    GenerarRuta(nodoOrigen, nodo, new Vehiculo());
                                    nodoOrigen = null;
                                    enEjecucion = true;
                                }
                            }
                            x1 = x2;
                            y1 = y2;
                        }
                    }
                    x1++;
                }
                y1++;
            }
        } else {
            //mensaje.show(Alert.AlertType.INFORMATION, "Informacion de mapa", "Debes agregar un accidente o un cierre a la ruta.");
        }

    };
    public static Timer timer;

    //private int costoDeViaje=0;
    private void calcularTarifa() {
        float valorTiempo = 50;
        float valorRecorrido = 4;
        float tem = Float.valueOf(lbTiempoMin.getText());
        float tem2 = Float.valueOf(lbTiempo.getText()) / 60;
        Float tiempoFin = (tem * valorTiempo) + (tem2 * valorTiempo);
        float valorR = Integer.valueOf(lbRecorridoFinal.getText()) * valorRecorrido;
        System.out.println("valor ruta " + valorR);
        lbCostoFinal.setText("" + (valorR + tiempoFin));
    }

    private void tiempo() {
        timerEnEjecucion = true;
        this.timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            int tic = 0;
            int min = 1;

            @Override
            public void run() {

                Platform.runLater(() -> {
                    lbTiempo.setText(String.valueOf(tic));
                    tic++;
                    if (tic > 60) {
                        lbTiempoMin.setText(String.valueOf(min) + ":");
                        min++;
                        tic = 0;
                    }

                });

            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    private void GenerarRuta(Nodo ini, Nodo fin, Vehiculo carro) {
        ruta.clear();
        rutaConAristasAlrevez.clear();
        Dijsktra d = new Dijsktra(mapa);
        d.ejecutar(ini);
        d.marcarRutaCorta(fin, Color.BLACK);
        int conTemp = 0;
        rutaConAristasAlrevez = d.getAux();

        if (ini.equals(nodoOrigen)) {
            tiempo();
            for (Arista t : d.getAux()) {
                conTemp += t.getPeso();
            }
            lbRecorridoEstimado.setText("" + conTemp);
        }
        ruta.add(ini);
        int cont = 0;

        for (int i = rutaConAristasAlrevez.size() - 1; i >= 0; i--) {
            Arista arista = rutaConAristasAlrevez.get(i);
            if (ruta.get(cont) == arista.getDestino()) {
                ruta.add(arista.getOrigen());
            } else {
                ruta.add(arista.getDestino());
            }
            cont++;
        }

        Arista aristaAux = null;
        if (!d.getAux().isEmpty()) {
            aristaAux = rutaConAristasAlrevez.get(rutaConAristasAlrevez.size() - 1);
        }

        trazarCarro(ini, fin, carro, aristaAux);
    }

    private int contDistanciaR = 0;
    private int tTrafico = 1500;

    public void modificarTiempo() {
        if (rbTraficoBajo.isSelected()) {
            tTrafico = 1500;
        } else if (rbTraficoMedio.isSelected()) {
            tTrafico = 3000;
        } else {
            tTrafico = 4500;
        }

    }

    private void trazarCarro(Nodo ini, Nodo fin, Vehiculo carro, Arista a) {
        //Agrega el carro solo si es el primer nodo ya que es un metodo recursivo
        if (ini.equals(nodoOrigen) && animacionTermin) {
            carro.setLayoutX((ruta.get(0).getCenterX()) - ((carro.getFitWidth()) / 2));
            carro.setLayoutY((ruta.get(0).getCenterY()) - ((carro.getFitHeight()) / 2));
            apCentro.getChildren().add(carro);
        }
        int i = ruta.indexOf(ini);
        modificarTiempo();
        //Crea la animacion
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(carro.layoutXProperty(), (ruta.get(i).getCenterX() - ((carro.getFitWidth()) / 2)));
        KeyValue kvy = new KeyValue(carro.layoutYProperty(), (ruta.get(i).getCenterY() - ((carro.getFitHeight()) / 2)));
        KeyFrame kf = new KeyFrame(Duration.millis(tTrafico), kv);
        KeyFrame kfy = new KeyFrame(Duration.millis(tTrafico), kvy);
        timeline.getKeyFrames().addAll(kf, kfy);
        timeline.play();

        //Evita que los hilos no afecten la vista y no se caiga el programa
        Platform.runLater(() -> {
            mapa.getDestinos().stream().forEach((t) -> {
                t.setMarca(false);
                t.setLongitudCamino(0);
                t.setNodoAntecesorDisjktra(null);
            });

            //Permite hacer recursividad cada vez que termina de situarse en un nodo
            timeline.setOnFinished((param) -> {
                //La animacion termina
                animacionTermin = true;
                //Genera la ruta hasta que llegue al destino
                if (ruta.indexOf(ruta.get(i)) + 1 < ruta.size() && !ini.equals(fin)) {
                    carro.setRotate(carro.rotarCarro(ruta.get(i).getCenterX(), ruta.get(i).getCenterY(), ruta.get(i + 1).getCenterX(), ruta.get(i + 1).getCenterY()) + 270);
                    //Aumenta el peso de la arista
                    List<Arista> auxList = rutaConAristasAlrevez;
                    if (rutaNueva) {
                        rutaNueva = false;
                        auxList.remove(auxList.size() - 1);
                        auxList.stream().forEach((t) -> {
                            t.setStroke(Color.YELLOW);
                            t.setStrokeWidth(3);
                        });
                    }
                    contDistanciaR += a.getPeso();
                    lbRecorridoFinal.setText("" + contDistanciaR);

                    modificarTrafico();

                    GenerarRuta(ruta.get(i + 1), fin, carro);

                } else {
                    rTrafico = null;
                    contDistanciaR = 0;
                    mapa.getDestinos().stream().forEach((t) -> {
                        t.setFill(Color.CORAL);
                    });
                    timer.cancel();
                    timerEnEjecucion = false;
                    calcularTarifa();
                    enEjecucion = false;
                    rbTraficoBajo.setSelected(true);
                    if (!accidentes.isEmpty()) {
                        accidentes.clear();
                        while (!imagenesAccidentes.isEmpty()) {
                            apCentro.getChildren().remove(imagenesAccidentes.get(0));
                            imagenesAccidentes.remove(0);
                        }
                    }
                    multiplicar = 1;
                    //lbRecorridoFinal.setText(""+contDistanciaR);
                    mapa.getAristas().stream().forEach((t) -> {
                        t.setAccidente(false);
                        t.setStroke(Color.TRANSPARENT);
                        t.setStrokeWidth(3);
                    });
                    apCentro.getChildren().remove(carro);
                    //System.out.println(timeline.getTotalDuration());
                }
            });
        });
    }

    private int multiplicar = 1;

    public void modificarTrafico() {
        if (rTrafico == null) {
            rTrafico = rbTraficoBajo;
            if (rbTraficoBajo.isSelected()) {
                multiplicar = 1;
                rTrafico = rbTraficoBajo;
            } else if (rbTraficoMedio.isSelected()) {
                multiplicar = 2;
                rTrafico = rbTraficoMedio;
            } else if (rbTraficoAlto.isSelected()) {
                multiplicar = 3;
                rTrafico = rbTraficoAlto;
            }

            if (multiplicar != 1) {
                rutaConAristasAlrevez.stream().forEach((t) -> {
                    Integer peso = t.getPeso();
                    peso = peso * multiplicar;
                    t.setPeso(peso);
                });

                rutaConAristasAlrevez.remove(rutaConAristasAlrevez.size() - 1);
                rutaConAristasAlrevez.stream().forEach((t) -> {
                    t.setStroke(Color.YELLOW);
                    t.setStrokeWidth(3);
                });

            }

        } else {
            JFXRadioButton radio = rTrafico;
            int numero = multiplicar;
            if (rbTraficoBajo.isSelected()) {
                multiplicar = 1;
                rTrafico = rbTraficoBajo;
            } else if (rbTraficoMedio.isSelected()) {
                multiplicar = 2;
                rTrafico = rbTraficoMedio;
            } else if (rbTraficoAlto.isSelected()) {
                multiplicar = 3;
                rTrafico = rbTraficoAlto;
            }

            if (!rTrafico.equals(radio)) {
                rutaConAristasAlrevez.stream().forEach((t) -> {
                    Integer peso = t.getPeso();
                    if (numero > multiplicar) {
                        peso = peso / multiplicar;
                    } else {
                        peso = peso * multiplicar;
                    }

                    //peso = peso + peso * multiplicar;
                    //System.out.println("PESO OR: " + t.getPeso() + " PESO Corregido " + peso);
                    t.setPeso(peso);
                });
                rbTraficoBajo.setSelected(true);
                modificarTiempo();
                rutaConAristasAlrevez.remove(rutaConAristasAlrevez.size() - 1);
                rutaConAristasAlrevez.stream().forEach((t) -> {
                    t.setStroke(Color.YELLOW);
                    t.setStrokeWidth(3);
                });
            }

        }

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
        matPeso = new Integer[mapa.getDestinos().size()][mapa.getDestinos().size()];
        for (int i = 0; i < mapa.getDestinos().size(); i++) {
            Nodo aux = mapa.getDestinos().get(i);//Ubicamos el nodo con el que vamos a comparar
            for (int j = 0; j < mapa.getDestinos().size(); j++) {
                if (i != j) {// Si no se esta ubicado en la diagonal
                    Nodo aux2 = mapa.getDestinos().get(j);

                    for (Arista arista : mapa.getAristas()) {
                        //Intentamos ubicar la arista que coincida con los nodos auxiliares para agregar el peso en la matriz
                        if ((arista.getDestino().equals(aux) && arista.getOrigen().equals(aux2)) || (arista.getDestino().equals(aux2) && arista.getOrigen().equals(aux))) {
                            matPeso[i][j] = arista.getPeso();
                        }
                    }
                    //Si no se encontro la arista con los nodos auxiliares se llena la matriz con un peso 0
                    if (matPeso[i][j] == null) {
                        matPeso[i][j] = 0;
                    }
                } //Si es la diagonal se llena la matriz con peso 0
                else {
                    matPeso[i][j] = 0;
                }
                sb.append(matPeso[i][j]);
                sb.append("\t");
            }
            sb.append("\n");
        }
    }
}
