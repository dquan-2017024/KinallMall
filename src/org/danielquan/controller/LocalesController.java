package org.danielquan.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.danielquan.bean.Administracion;
import org.danielquan.bean.Locales;
import org.danielquan.db.Conexion;
import org.danielquan.system.Principal;


public class LocalesController implements Initializable{
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Locales> listaLocales; 
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoLocal;
    @FXML private TextField txtSaldoFavor;
    @FXML private TextField txtSaldoContra;
    @FXML private TextField txtMesesPendientes;
    @FXML private TextField txtDisponibilidad;
    @FXML private TextField txtValorLocal;
    @FXML private TextField txtValorAdmin;
    @FXML private TableView tblLocales;
    @FXML private TableColumn colCodigoLocal;
    @FXML private TableColumn colSaldoFavor;
    @FXML private TableColumn colSaldoContra;
    @FXML private TableColumn colMesesPendientes;
    @FXML private TableColumn colDisponibilidad;
    @FXML private TableColumn colValorLocal;
    @FXML private TableColumn colValorAdmin;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblLocales.setItems(getLocales());
        colCodigoLocal.setCellValueFactory(new PropertyValueFactory<Locales,Integer>("codigoLocal"));
        colSaldoFavor.setCellValueFactory(new PropertyValueFactory<Locales,Double>("saldoFavor"));
        colSaldoContra.setCellValueFactory(new PropertyValueFactory<Locales,Double>("saldoContra"));
        colMesesPendientes.setCellValueFactory(new PropertyValueFactory<Locales,Integer>("mesesPendientes"));
        colDisponibilidad.setCellValueFactory(new PropertyValueFactory<Locales,Integer>("disponibilidad"));
        colValorLocal.setCellValueFactory(new PropertyValueFactory<Locales,Double>("valorLocal"));
        colValorAdmin.setCellValueFactory(new PropertyValueFactory<Locales,Double>("valorAdministracion"));
    }
    
    public ObservableList<Locales> getLocales(){
        ArrayList<Locales> lista = new ArrayList<Locales>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarLocales}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Locales(resultado.getInt("codigoLocal"),
                                        resultado.getDouble("saldoFavor"),
                                        resultado.getDouble("saldoContra"),
                                        resultado.getInt("mesesPendientes"),
                                        resultado.getBoolean("disponibilidad"),
                                        resultado.getDouble("valorLocal"),
                                        resultado.getDouble("valorAdministracion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaLocales = FXCollections.observableArrayList(lista);
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void seleccionarElemento(){
        if(tblLocales.getSelectionModel().getSelectedItem() != null){
            txtCodigoLocal.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getCodigoLocal()));
            txtSaldoFavor.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getSaldoFavor()));
            txtSaldoContra.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getSaldoContra()));
            txtMesesPendientes.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getMesesPendientes()));
            txtDisponibilidad.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).isDisponibilidad()));
            txtValorLocal.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getValorLocal()));
            txtValorAdmin.setText(String.valueOf(((Locales)tblLocales.getSelectionModel().getSelectedItem()).getValorAdministracion()));
        }else{
            txtCodigoLocal.clear();
            txtSaldoFavor.clear();
            txtSaldoContra.clear();
            txtMesesPendientes.clear();
            txtDisponibilidad.clear();
            txtValorLocal.clear();
            txtValorAdmin.clear();
        }
    }
    
    
    
    
    @FXML
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                imgNuevo.setImage(new Image("/org/javierbarillas/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/javierbarillas/images/Cerrar.png"));
                tipoDeOperacion = operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/javierbarillas/images/Nuevo.png"));
                imgEliminar.setImage(new Image("/org/javierbarillas/images/Eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                cargarDatos();
                break;
        }
    }
    
    @FXML
    public void eliminar(){
        switch(tipoDeOperacion){
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/javierbarillas/images/Nuevo.png"));
                imgEliminar.setImage(new Image("/org/javierbarillas/images/Eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            
            default:
                if (tblLocales.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar el registro?", "Eliminar Local", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_EliminarLocal(?)}");
                            procedimiento.setInt(1, ((Locales)tblLocales.getSelectionModel().getSelectedItem()).getCodigoLocal());
                            procedimiento.execute();
                            listaLocales.remove(tblLocales.getSelectionModel().getSelectedIndex());
                            limpiarControles();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento.");
                }
                break;
        }
    }
    
    
    @FXML
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                    if(tblLocales.getSelectionModel().getSelectedItem() != null){
                        activarControles();
                        btnEditar.setText("Actualizar");
                        btnReporte.setText("Cancelar");
                        btnNuevo.setDisable(true);
                        btnEliminar.setDisable(true);
                        imgEditar.setImage(new Image("/org/javierbarillas/images/Guardar.png"));
                        imgReporte.setImage(new Image("/org/javierbarillas/images/Cerrar.png"));
                        tipoDeOperacion = operaciones.ACTUALIZAR;
                    }else{
                        JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento.");
                    }
                break;
            case ACTUALIZAR:
                    actualizar();
                    desactivarControles();
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    imgEditar.setImage(new Image("/org/javierbarillas/images/Editar.png"));
                    imgReporte.setImage(new Image("/org/javierbarillas/images/Reporte.png"));
                    tipoDeOperacion = operaciones.NINGUNO;
                    cargarDatos();
                break;
        }     
    }
    
    @FXML
    public void reporte(){
        switch(tipoDeOperacion){
            case ACTUALIZAR:
                    desactivarControles();
                    limpiarControles();
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    imgEditar.setImage(new Image("/org/javierbarillas/images/Editar.png"));
                    imgReporte.setImage(new Image("/org/javierbarillas/images/Reporte.png"));
                    tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ModificarLocal(?,?,?,?,?,?,?)}");
            Locales registro = (Locales) tblLocales.getSelectionModel().getSelectedItem();
            registro.setSaldoFavor(Double.parseDouble(txtSaldoFavor.getText()));
            registro.setSaldoContra(Double.parseDouble(txtSaldoContra.getText()));
            registro.setMesesPendientes(Integer.parseInt(txtMesesPendientes.getText()));
            registro.setDisponibilidad(Boolean.parseBoolean(txtDisponibilidad.getText()));
            registro.setValorLocal(Double.parseDouble(txtValorLocal.getText()));
            registro.setValorAdministracion(Double.parseDouble(txtValorAdmin.getText()));
            procedimiento.setInt(1, registro.getCodigoLocal());
            procedimiento.setDouble(2, registro.getSaldoFavor());
            procedimiento.setDouble(3, registro.getSaldoContra());
            procedimiento.setInt(4, registro.getMesesPendientes());
            procedimiento.setBoolean(5, registro.isDisponibilidad());
            procedimiento.setDouble(6, registro.getValorLocal());
            procedimiento.setDouble(7, registro.getValorAdministracion());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void guardar(){
        
        if(txtSaldoFavor.getText().equals("") | txtSaldoContra.getText().equals("") | txtMesesPendientes.getText().equals("")
                | txtDisponibilidad.getText().equals("")| txtValorLocal.getText().equals("") | txtValorAdmin.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Por favor no deje ningun campo vacío.");
        }else{
            Locales registro = new Locales();
            registro.setSaldoFavor(Double.parseDouble(txtSaldoFavor.getText()));
            registro.setSaldoContra(Double.parseDouble(txtSaldoContra.getText()));
            registro.setMesesPendientes(Integer.parseInt(txtMesesPendientes.getText()));
            registro.setDisponibilidad(Boolean.parseBoolean(txtDisponibilidad.getText()));
            registro.setValorLocal(Double.parseDouble(txtValorLocal.getText()));
            registro.setValorAdministracion(Double.parseDouble(txtValorAdmin.getText()));
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_AgregarLocal(?,?,?,?,?,?)}");
                procedimiento.setDouble(1, registro.getSaldoFavor());
                procedimiento.setDouble(2, registro.getSaldoContra());
                procedimiento.setInt(3, registro.getMesesPendientes());
                procedimiento.setBoolean(4, registro.isDisponibilidad());
                procedimiento.setDouble(5, registro.getValorLocal());
                procedimiento.setDouble(6, registro.getValorAdministracion());
                procedimiento.execute();
                listaLocales.add(registro);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    
    
    public void desactivarControles(){
        txtCodigoLocal.setEditable(false);
        txtSaldoFavor.setEditable(false);
        txtSaldoContra.setEditable(false);
        txtMesesPendientes.setEditable(false);
        txtDisponibilidad.setEditable(false);
        txtValorAdmin.setEditable(false);
        txtValorLocal.setEditable(false);
    }

    public void activarControles(){
        txtCodigoLocal.setEditable(false);
        txtSaldoFavor.setEditable(true);
        txtSaldoContra.setEditable(true);
        txtMesesPendientes.setEditable(true);
        txtDisponibilidad.setEditable(true);
        txtValorAdmin.setEditable(true);
        txtValorLocal.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoLocal.clear();
        txtSaldoFavor.clear();
        txtSaldoContra.clear();
        txtMesesPendientes.clear();
        txtDisponibilidad.clear();
        txtValorAdmin.clear();
        txtValorLocal.clear();
    }
     
    public void ventanaCuentasPorCobrar(){
        escenarioPrincipal.ventanaCuentasPorCobrar();
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
}
