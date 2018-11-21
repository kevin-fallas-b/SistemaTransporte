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
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
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
import model.Floyd;
import sistematransporte.model.Accidente;
import sistematransporte.model.Arista;
import sistematransporte.model.CierreCosevi;
import sistematransporte.model.Dijsktra;
import sistematransporte.model.Mapa;
import static sistematransporte.model.Mapa.destinos;
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
    public static ArrayList<CierreCosevi> cierresCosevi = new ArrayList();
    public static ArrayList<CierreCosevi> imagenesCierres = new ArrayList();
    public static ArrayList<Arista> auxAristas;
    public static Boolean timerEnEjecucion = false;

    private Floyd f = new Floyd();

    public static Boolean rutaNueva = false;

    @FXML
    private Label lbCostoestimado;

    public static Timer timer;
    private LinkedList<Arista> aristasParaGrafoDirigido = new LinkedList<Arista>();
    @FXML
    private JFXButton btnGuardarAristas;
    @FXML
    private Label lblAlgoritmoActivo;
    private Color colorActivo = Color.CORAL;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            lblAlgoritmoActivo.setVisible(false);
            lbTiempoMin.setText("00");
            lbTiempo.setText("00");
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
        mapa.cargarAristasDirigidas();

    }

    public void cargarNodos() {
        try {
            mapa.cargarNodo();

        } catch (IOException ex) {
            Logger.getLogger(PantPrincipalController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void cargarAristas() throws IOException {
        mapa.cagarAristas();

        mapa.getAristas().stream().forEach((arista) -> {
            apCentro.getChildren().add(arista);
        });
        mapa.getDestinos().stream().forEach((nodo) -> {
            apCentro.getChildren().add(nodo);
        });

    }

    static Nodo nodoOrigen = null;

    public EventHandler<MouseEvent> seleccionarDestino = (MouseEvent event) -> {
        if (!agregarAccidente && !agregarReparacion && !enEjecucion) {
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
                                lbTiempoMin.setText("00");
                                lbTiempo.setText("00");
                                lbCostoFinal.setText("");
                                lbRecorridoFinal.setText("");
                                lbRecorridoEstimado.setText("");
                            } else {

                                if (nodoOrigen == null) {
                                    nodoOrigen = nodo;
                                }
                                if (nodo != nodoOrigen && !enEjecucion) {
                                    animacionTermin = true;
                                    if (rbNoDirigido.isSelected()) {
                                        if (rbDijkstra.isSelected()) {
                                            GenerarRuta(nodoOrigen, nodo, new Vehiculo());
                                        } else if (rbFloyd.isSelected()) {
                                            floydWarshal(nodoOrigen, nodo, new Vehiculo());
                                        }
                                        nodoOrigen = null;
                                        enEjecucion = true;
                                    } else {
                                        if (rbDijkstra.isSelected()) {
                                            GenerarRuta(nodoOrigen, nodo, new Vehiculo());
                                        } else if (rbFloyd.isSelected()) {
                                            floydWarshal(nodoOrigen, nodo, new Vehiculo());
                                        }
                                        nodoOrigen = null;
                                        enEjecucion = true;
                                    }
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
        }
    };

    private void calcularTarifa() {
        float valorTiempo = 50;
        float valorRecorrido = 4;
        float tem = Float.valueOf(lbTiempoMin.getText());
        float tem2 = Float.valueOf(lbTiempo.getText()) / 60;
        Float tiempoFin = (tem * valorTiempo) + (tem2 * valorTiempo);
        float valorR = Integer.valueOf(lbRecorridoFinal.getText()) * valorRecorrido;
        lbCostoFinal.setText("" + Math.round(valorR + tiempoFin));

    }

    private void floydWarshal(Nodo ini, Nodo fin, Vehiculo carro) {
        GenerarRuta(ini, fin, carro);
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
                        lbTiempoMin.setText(String.valueOf(min));
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
            float valTiempo = ((((d.getAux().size() * 1500) + 1500)) / 1000);
            float valTiem = (valTiempo / 60) * 50;
            float cosEstimado = ((conTemp * 4) + valTiem);
            lbCostoestimado.setText("" + Math.round(cosEstimado));
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
    Arista arPintar = new Arista();

    private void trazarCarro(Nodo ini, Nodo fin, Vehiculo carro, Arista a) {
        arPintar.setStroke(Color.RED);
        if (ini.equals(nodoOrigen) && animacionTermin) {
            carro.setLayoutX((ruta.get(0).getCenterX()) - ((carro.getFitWidth()) / 2));
            carro.setLayoutY((ruta.get(0).getCenterY()) - ((carro.getFitHeight()) / 2));
            apCentro.getChildren().add(carro);
        }
        int i = ruta.indexOf(ini);
        modificarTiempo();
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(carro.layoutXProperty(), (ruta.get(i).getCenterX() - ((carro.getFitWidth()) / 2)));
        KeyValue kvy = new KeyValue(carro.layoutYProperty(), (ruta.get(i).getCenterY() - ((carro.getFitHeight()) / 2)));
        KeyFrame kf = new KeyFrame(Duration.millis(tTrafico), kv);
        KeyFrame kfy = new KeyFrame(Duration.millis(tTrafico), kvy);
        timeline.getKeyFrames().addAll(kf, kfy);
        timeline.play();
        Platform.runLater(() -> {
            mapa.getDestinos().stream().forEach((t) -> {
                t.setMarca(false);
                t.setLongitudCamino(0);
                t.setNodoAntecesorDisjktra(null);
            });
            timeline.setOnFinished((param) -> {
                //aqui pinta lo recorrido

                animacionTermin = true;
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
                    arPintar = a;

                } else {
                    arPintar = new Arista();
                    rTrafico = null;
                    contDistanciaR = 0;
                    mapa.getDestinos().stream().forEach((t) -> {
                        t.setFill(colorActivo);
                    });
                    timer.cancel();
                    timerEnEjecucion = false;
                    calcularTarifa();
                    enEjecucion = false;
                    rbTraficoBajo.setSelected(true);
                    if (!accidentes.isEmpty() || !cierresCosevi.isEmpty()) {
                        accidentes.clear();
                        cierresCosevi.clear();
                        while (!imagenesAccidentes.isEmpty()) {
                            apCentro.getChildren().remove(imagenesAccidentes.get(0));
                            imagenesAccidentes.remove(0);
                        }
                        while (!imagenesCierres.isEmpty()) {
                            apCentro.getChildren().remove(imagenesCierres.get(0));
                            imagenesCierres.remove(0);

                        }
                    }
                    multiplicar = 1;
                    mapa.getAristas().stream().forEach((t) -> {
                        t.setAccidente(false);
                        t.setReparacion(false);
                        t.setStroke(Color.TRANSPARENT);
                        t.setStrokeWidth(10);
                        t.setPeso(t.getPesoOriginal());
                    });
                    apCentro.getChildren().remove(carro);
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

    private void floydWarshall(Integer[][] m, int nodoInicio, int nodoFin) {
        int vec[] = f.floyd_cam(matPeso, nodoInicio, nodoInicio);
    }

    private void llenarMatPeso() {
        StringBuilder sb = new StringBuilder();
        //Se crea una matriz cuadrada del tamanno del tamano de los nodos totales
        matPeso = new Integer[mapa.getDestinos().size()][mapa.getDestinos().size()];
        for (int i = 0; i < mapa.getDestinos().size(); i++) {
            Nodo aux = mapa.getDestinos().get(i);//Ubicamos el nodo con el que vamos a comparar
            for (int j = 0; j < mapa.getDestinos().size(); j++) {
                matPeso[i][j] = 10000;
                if (i != j) {// Si no se esta ubicado en la diagonal
                    Nodo aux2 = mapa.getDestinos().get(j);

                    for (Arista arista : mapa.getAristas()) {
                        //Intentamos ubicar la arista que coincida con los nodos auxiliares para agregar el peso en la matriz
                        if ((arista.getDestino().equals(aux) && arista.getOrigen().equals(aux2)) || (arista.getDestino().equals(aux2) && arista.getOrigen().equals(aux))) {
                            matPeso[i][j] = arista.getPeso();
                        }
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

    @FXML
    private void presionarBtnGuardarAristas(ActionEvent event) throws FileNotFoundException, UnsupportedEncodingException {
        
        //PELIGRO PUEDE BORRAR TODAS LAS ARISTAS, TENER CUIDADO
        
        /*PrintWriter writer = new PrintWriter("src/sistematransporte/util/AristasDirigidas.txt", "UTF-8");
        while (!aristasParaGrafoDirigido.isEmpty()) {
            Arista arista = aristasParaGrafoDirigido.get(0);
            aristasParaGrafoDirigido.remove(0);
            Double x1 = arista.getStartX();
            Double y1 = arista.getStartY();
            Double x2 = arista.getEndX();
            Double y2 = arista.getEndY();
            String cordenadas = String.valueOf(x1) + "$" + String.valueOf(y1) + "$" + String.valueOf(x2) + "$" + String.valueOf(y2);
            writer.println(cordenadas);
        }
        writer.close();*/
    }

    @FXML
    private void presionarRbDijkstra(ActionEvent event) {
        if (enEjecucion) {
            rbFloyd.setSelected(true);
            rbDijkstra.setSelected(false);
        } else {
            colorActivo = Color.TRANSPARENT;
            mapa.getDestinos().stream().forEach((t) -> {
                t.setFill(colorActivo);
            });
            lblAlgoritmoActivo.setText("Se selecciono el algoritdo de Dijkstra");
            lblAlgoritmoActivo.setVisible(true);
            FadeTransition ft = new FadeTransition(Duration.millis(750), lblAlgoritmoActivo);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            ft.setAutoReverse(true);
            ft.setCycleCount(4);
            ft.setOnFinished((event1) -> {
                lblAlgoritmoActivo.setVisible(false);
                colorActivo = Color.CORAL;
                mapa.getDestinos().stream().forEach((t) -> {
                    t.setFill(colorActivo);
                });
            });
            ft.play();

        }
    }

    @FXML
    private void presionarRbFloyd(ActionEvent event
    ) {
        if (enEjecucion) {
            rbFloyd.setSelected(false);
            rbDijkstra.setSelected(true);
        } else {
            colorActivo = Color.TRANSPARENT;
            mapa.getDestinos().stream().forEach((t) -> {
                t.setFill(colorActivo);
            });
            lblAlgoritmoActivo.setText("Se selecciono el algoritdo de Floyd");
            lblAlgoritmoActivo.setVisible(true);
            FadeTransition ft = new FadeTransition(Duration.millis(750), lblAlgoritmoActivo);
            ft.setFromValue(1.0);
            ft.setToValue(0.0);
            ft.setAutoReverse(true);
            ft.setCycleCount(4);
            ft.setOnFinished((event1) -> {
                lblAlgoritmoActivo.setVisible(false);
                colorActivo = Color.DARKMAGENTA;
                mapa.getDestinos().stream().forEach((t) -> {
                    t.setFill(colorActivo);
                });
            });
            ft.play();

        }
    }
}
