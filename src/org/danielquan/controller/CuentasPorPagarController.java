package org.danielquan.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.danielquan.bean.Administracion;
import org.danielquan.bean.CuentaPorPagar;
import org.danielquan.bean.Proveedores;
import org.danielquan.db.Conexion;
import org.danielquan.system.Principal;


public class CuentasPorPagarController implements Initializable{
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<CuentaPorPagar> listaCuentasPorPagar;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<Proveedores> listaProveedores;
    private DatePicker fechaLimite;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoCuentasPorPagar;
    @FXML private TextField txtFactura;
    @FXML private TextField txtEstadoDePago;
    @FXML private TextField txtValorNeto;
    @FXML private ComboBox cmbAdministracion;
    @FXML private ComboBox cmbProveedores;
    @FXML private GridPane grpFechaLimite;
    @FXML private TableView tblCuentasPorPagar;
    @FXML private TableColumn colCodigoCuentasPorPagar;
    @FXML private TableColumn colFactura;
    @FXML private TableColumn colFechaLimite;
    @FXML private TableColumn colEstadoDePago;
    @FXML private TableColumn colValorNeto;
    @FXML private TableColumn colAdministracion;
    @FXML private TableColumn colProveedores;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fechaLimite = new DatePicker(Locale.ENGLISH);   
        fechaLimite.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fechaLimite.getCalendarView().todayButtonTextProperty().set("Today");
        fechaLimite.getCalendarView().setShowWeeks(false);
        grpFechaLimite.add(fechaLimite, 5, 0);
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblCuentasPorPagar.setItems(getCuentasPorPagar());
        colCodigoCuentasPorPagar.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,Integer>("codigoCuentasPorPagar"));
        colFactura.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,String>("numeroFactura"));
        colFechaLimite.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,Date>("fechaLimitePago"));
        colEstadoDePago.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,String>("estadoPago"));
        colValorNeto.setCellValueFactory(new PropertyValueFactory<CuentaPorPagar,Double>("valorNetoPago"));
        colAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion,Integer>("administracion"));
        colProveedores.setCellValueFactory(new PropertyValueFactory<Proveedores,Integer>("proveedores"));
        cmbAdministracion.setItems(getAdministracion());
        cmbProveedores.setItems(getProveedores());
    }
    
    
    public ObservableList<CuentaPorPagar> getCuentasPorPagar(){
        ArrayList<CuentaPorPagar> lista = new ArrayList<CuentaPorPagar>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarCuentasPorPagar()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new CuentaPorPagar(resultado.getInt("codigoCuentasPorPagar"),
                                                resultado.getString("numeroFactura"),
                                                resultado.getDate("fechaLimitePago"),
                                                resultado.getString("estadoPago"),
                                                resultado.getDouble("valorNetoPago"),
                                                resultado.getInt("administracion"),
                                                resultado.getInt("proveedores")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return listaCuentasPorPagar= FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Administracion> getAdministracion(){
        ArrayList<Administracion> lista = new ArrayList<Administracion>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarAdministracion()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Administracion(resultado.getInt("codigoAdministracion"),
                                                resultado.getString("direccion"),
                                                resultado.getString("telefono")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaAdministracion = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Proveedores> getProveedores(){
        ArrayList<Proveedores> lista = new ArrayList<Proveedores>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarProveedores}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Proveedores(resultado.getInt("codigoProveedor"),
                                            resultado.getString("NITProveedor"),
                                            resultado.getString("servicioPrestado"),
                                            resultado.getString("telefonoProveedor"),
                                            resultado.getString("direccionProveedor"),
                                            resultado.getDouble("saldoFavor"),
                                            resultado.getDouble("saldoContra"),
                                            resultado.getInt("administracion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaProveedores = FXCollections.observableArrayList(lista);
    }
    public void seleccionarElemento(){
        if(tblCuentasPorPagar.getSelectionModel().getSelectedItem() != null){    
            txtCodigoCuentasPorPagar.setText(String.valueOf(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoCuentasPorPagar()));
            txtFactura.setText(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getNumeroFactura());
            fechaLimite.selectedDateProperty().set(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getFechaLimitePago());
            txtEstadoDePago.setText(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getEstadoPago());
            txtValorNeto.setText(String.valueOf(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getValorNetoPago()));
            cmbAdministracion.getSelectionModel().select(buscarAdministracion(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getAdministracion()));
            cmbProveedores.getSelectionModel().select(buscarProveedor(((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getProveedores()));
        }else{
            txtCodigoCuentasPorPagar.clear();
            txtFactura.clear();
            txtEstadoDePago.clear();
            txtValorNeto.clear();
        }
    }

    public Administracion buscarAdministracion(int codigoAdministracion){
        Administracion resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarAdministracion(?)}");
            procedimiento.setInt(1, codigoAdministracion);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Administracion(registro.getInt("codigoAdministracion"),
                                                registro.getString("direccion"),
                                                registro.getString("telefono"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Proveedores buscarProveedor(int codigoProveedor){
        Proveedores resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarProveedor(?)}");
            procedimiento.setInt(1, codigoProveedor);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Proveedores(registro.getInt("codigoProveedor"),
                                            registro.getString("NITProveedor"),
                                            registro.getString("servicioPrestado"),
                                            registro.getString("telefonoProveedor"),
                                            registro.getString("direccionProveedor"),
                                            registro.getDouble("saldoFavor"),
                                            registro.getDouble("saldoContra"),
                                            registro.getInt("administracion"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    @FXML
    public void nuevo(){
        switch(tipoDeOperacion){
            case NINGUNO:
                limpiarControles();
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
                limpiarControles();
                desactivarControles();
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
                limpiarControles();
                desactivarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                imgNuevo.setImage(new Image("/org/javierbarillas/images/Nuevo.png"));
                imgEliminar.setImage(new Image("/org/javierbarillas/images/Eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
        default:
            if(tblCuentasPorPagar.getSelectionModel().getSelectedItem() != null){
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar el registro?", "Eliminar Administracion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_OPTION){
                    try{
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_EliminarCuentasPorPagar(?)}");
                        procedimiento.setInt(1, ((CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem()).getCodigoCuentasPorPagar());
                        procedimiento.execute();
                        listaCuentasPorPagar.remove(tblCuentasPorPagar.getSelectionModel().getSelectedIndex());
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
                    if(tblCuentasPorPagar.getSelectionModel().getSelectedItem() != null){
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
    
    public void guardar(){
        if(txtFactura.getText().equals("")|txtValorNeto.getText().equals("")|txtEstadoDePago.getText().equals("")|
                cmbAdministracion.getSelectionModel().getSelectedItem()== null|cmbProveedores.getSelectionModel().getSelectedItem()== null|
                fechaLimite.getSelectedDate()==null){
            JOptionPane.showMessageDialog(null, "Por favor no deje vacío ningún espacio");
        }else{
            CuentaPorPagar registro = new CuentaPorPagar();
            registro.setNumeroFactura(txtFactura.getText());
            registro.setFechaLimitePago(fechaLimite.getSelectedDate());
            registro.setEstadoPago(txtEstadoDePago.getText());
            registro.setValorNetoPago(Double.parseDouble(txtValorNeto.getText()));
            registro.setAdministracion(((Administracion)cmbAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
            registro.setProveedores(((Proveedores)cmbProveedores.getSelectionModel().getSelectedItem()).getCodigoProveedor());
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_AgregarCuentasPorPagar(?,?,?,?,?,?)}");
                procedimiento.setString(1, registro.getNumeroFactura());
                procedimiento.setDate(2, new java.sql.Date(registro.getFechaLimitePago().getTime()));
                procedimiento.setString(3, registro.getEstadoPago());
                procedimiento.setDouble(4, registro.getValorNetoPago());
                procedimiento.setInt(5, registro.getAdministracion());
                procedimiento.setInt(6, registro.getProveedores());
                procedimiento.execute();
                listaCuentasPorPagar.add(registro);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ModificarCuentasPorPagar(?,?,?,?,?)}");
            CuentaPorPagar registro = (CuentaPorPagar)tblCuentasPorPagar.getSelectionModel().getSelectedItem();
            registro.setNumeroFactura(txtFactura.getText());
            registro.setFechaLimitePago(fechaLimite.getSelectedDate());
            registro.setEstadoPago(txtEstadoDePago.getText());
            registro.setValorNetoPago(Double.parseDouble(txtValorNeto.getText()));
            procedimiento.setInt(1, registro.getCodigoCuentasPorPagar());
            procedimiento.setString(2, registro.getNumeroFactura());
            procedimiento.setDate(3, new java.sql.Date(registro.getFechaLimitePago().getTime()));
            procedimiento.setString(4, registro.getEstadoPago());
            procedimiento.setDouble(5, registro.getValorNetoPago());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void activarControles(){
        txtCodigoCuentasPorPagar.setEditable(false);
        txtFactura.setEditable(true);
        txtEstadoDePago.setEditable(true);
        txtValorNeto.setEditable(true);
        fechaLimite.setDisable(false);
        cmbAdministracion.setDisable(false);
        cmbProveedores.setDisable(false);
    }
    
    public void desactivarControles(){
        txtCodigoCuentasPorPagar.setEditable(false);
        txtFactura.setEditable(false);
        txtEstadoDePago.setEditable(false);
        txtValorNeto.setEditable(false);
        fechaLimite.setDisable(true);
        cmbAdministracion.setDisable(true);
        cmbProveedores.setDisable(true);
    }
    
    public void limpiarControles(){
        txtCodigoCuentasPorPagar.clear();
        txtFactura.clear();
        txtEstadoDePago.clear();
        txtValorNeto.clear();
        cmbAdministracion.getSelectionModel().clearSelection();
        cmbProveedores.getSelectionModel().clearSelection();
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaProveedores(){
        escenarioPrincipal.ventanaProveedor();
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
}
