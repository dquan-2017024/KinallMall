package org.danielquan.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.DepthTest;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.danielquan.bean.Administracion;
import org.danielquan.bean.Clientes;
import org.danielquan.bean.CuentasPorCobrar;
import org.danielquan.bean.Locales;
import org.danielquan.db.Conexion;
import org.danielquan.system.Principal;


public class CuentasPorCobrarController implements Initializable{
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<CuentasPorCobrar> listaCuentasPorCobrar;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<Locales> listaLocales;
    private ObservableList<Clientes> listaClientes;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigCuentasPorCobrar;
    @FXML private TextField txtNumeroFactura;
    @FXML private TextField txtAnio;
    @FXML private TextField txtMes;
    @FXML private TextField txtValorNeto;
    @FXML private TextField txtEstado;
    @FXML private ComboBox cmbAdministracion;
    @FXML private ComboBox cmbLocales;
    @FXML private ComboBox cmbClientes;
    @FXML private TableView tblCuentasPorCobrar;
    @FXML private TableColumn colCodigoCuentasPorCobrar;
    @FXML private TableColumn colNumeroFactura;
    @FXML private TableColumn colAnio;
    @FXML private TableColumn colMes;
    @FXML private TableColumn colValorNeto;
    @FXML private TableColumn colEstado;
    @FXML private TableColumn colLocales;
    @FXML private TableColumn colAdministracion;
    @FXML private TableColumn colClientes;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos(){
        tblCuentasPorCobrar.setItems(getCuentasPorCobrar());
        colCodigoCuentasPorCobrar.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,Integer>("codigoCuentasPorCobrar"));
        colNumeroFactura.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,String>("numeroFactura"));
        colAnio.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,String>("anio"));
        colMes.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,Integer>("mes"));
        colValorNeto.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,Double>("valorNetoPago"));
        colEstado.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,String>("estadoDePago"));
        colLocales.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,Integer>("locales"));
        colAdministracion.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,Integer>("administracion"));
        colClientes.setCellValueFactory(new PropertyValueFactory<CuentasPorCobrar,Integer>("clientes"));
        cmbLocales.setItems(getLocales());
        cmbAdministracion.setItems(getAdministracion());
        cmbClientes.setItems(getClientes());
    }
    
    public ObservableList<Clientes> getClientes(){
        ArrayList<Clientes> lista = new ArrayList<Clientes>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Clientes(resultado.getInt("codigoCliente"),
                                        resultado.getString("nombreCliente"),
                                        resultado.getString("apellidoCliente"),
                                        resultado.getString("telefonoCliente"),
                                        resultado.getString("direccionCliente"),
                                        resultado.getString("email"),
                                        resultado.getInt("codigoLocales"),
                                        resultado.getInt("codigoAdministracion"),
                                        resultado.getInt("codigoTipoCliente")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaClientes = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<CuentasPorCobrar> getCuentasPorCobrar(){
        ArrayList<CuentasPorCobrar> lista = new ArrayList<CuentasPorCobrar>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarCuentasPorCobrar()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new CuentasPorCobrar(resultado.getInt("codigoCuentasPorCobrar"),
                                                resultado.getString("numeroFactura"),
                                                resultado.getString("anio"),
                                                resultado.getInt("mes"),
                                                resultado.getDouble("valorNetoPago"),
                                                resultado.getString("estadoDePago"),
                                                resultado.getInt("locales"),
                                                resultado.getInt("administracion"),
                                                resultado.getInt("clientes")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaCuentasPorCobrar = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Locales> getLocales(){
        ArrayList<Locales> lista = new ArrayList<Locales>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarLocales()}");
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
    
    public void seleccionarElemento(){
        if(tblCuentasPorCobrar.getSelectionModel().getSelectedItem() != null){
            txtCodigCuentasPorCobrar.setText(String.valueOf(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCuentasPorCobrar()));
            txtNumeroFactura.setText(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getNumeroFactura());
            txtAnio.setText(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getAnio());
            txtMes.setText(String.valueOf(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getMes()));
            txtValorNeto.setText(String.valueOf(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getValorNetoPago()));
            txtEstado.setText(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getEstadoDePago());
            cmbLocales.getSelectionModel().select(buscarLocal(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getLocales()));
            cmbAdministracion.getSelectionModel().select(buscarAdministracion(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getAdministracion()));
            cmbClientes.getSelectionModel().select(buscarCliente(((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getClientes()));
        }else{
            limpiarControles();
        }
    }
    
    public Locales buscarLocal(int codigoLocal){
        Locales resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarLocal(?)}");
            procedimiento.setInt(1, codigoLocal);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Locales(registro.getInt("codigoLocal"),
                                        registro.getDouble("saldoFavor"),
                                        registro.getDouble("saldoContra"),
                                        registro.getInt("mesesPendientes"),
                                        registro.getBoolean("disponibilidad"),
                                        registro.getDouble("valorLocal"),
                                        registro.getDouble("valorAdministracion"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return resultado;
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
    
    public Clientes buscarCliente(int codigoCliente){
        Clientes resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarCliente(?)}");
            procedimiento.setInt(1, codigoCliente);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Clientes(registro.getInt("codigoCliente"),
                                            registro.getString("nombreCliente"),
                                            registro.getString("apellidoCliente"),
                                            registro.getString("telefonoCliente"),
                                            registro.getString("direccionCliente"),
                                            registro.getString("email"),
                                            registro.getInt("codigoLocales"),
                                            registro.getInt("codigoAdministracion"),
                                            registro.getInt("codigoTipoCliente"));
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
            if(tblCuentasPorCobrar.getSelectionModel().getSelectedItem() != null){
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar el registro?", "Eliminar Cuentas Por Cobrar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_OPTION){
                    try{
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_EliminarCuentasPorCobrar(?)}");
                        procedimiento.setInt(1, ((CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem()).getCodigoCuentasPorCobrar());
                        procedimiento.execute();
                        listaCuentasPorCobrar.remove(tblCuentasPorCobrar.getSelectionModel().getSelectedIndex());
                        limpiarControles();
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }else{
                JOptionPane.showMessageDialog(null, "Debe seleccionar un elemento.");
            }
        }
    }
    
    @FXML
    public void editar(){
        switch(tipoDeOperacion){
            case NINGUNO:
                    if(tblCuentasPorCobrar.getSelectionModel().getSelectedItem() != null){
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
        if(txtNumeroFactura.getText().equals("")|txtAnio.getText().equals("")|txtMes.getText().equals("")|
                txtValorNeto.getText().equals("")|txtEstado.getText().equals("")|cmbLocales.getSelectionModel().getSelectedItem()== null|
                cmbAdministracion.getSelectionModel().getSelectedItem()== null|cmbClientes.getSelectionModel().getSelectedItem()== null){
            JOptionPane.showMessageDialog(null, "Por favor no deje vacío ningún espacio");
        }else{
            CuentasPorCobrar registro = new CuentasPorCobrar();
            registro.setNumeroFactura(txtNumeroFactura.getText());
            registro.setAnio(txtAnio.getText());
            registro.setMes(Integer.parseInt(txtMes.getText()));
            registro.setValorNetoPago(Double.parseDouble(txtValorNeto.getText()));
            registro.setEstadoDePago(txtEstado.getText());
            registro.setLocales(((Locales)cmbLocales.getSelectionModel().getSelectedItem()).getCodigoLocal());
            registro.setAdministracion(((Administracion)cmbAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
            registro.setClientes(((Clientes)cmbClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_AgregarCuentasPorCobrar(?,?,?,?,?,?,?,?)}");
                procedimiento.setString(1, registro.getNumeroFactura());
                procedimiento.setString(2, registro.getAnio());
                procedimiento.setInt(3, registro.getMes());
                procedimiento.setDouble(4, registro.getValorNetoPago());
                procedimiento.setString(5, registro.getEstadoDePago());
                procedimiento.setInt(6, registro.getLocales());
                procedimiento.setInt(7, registro.getAdministracion());
                procedimiento.setInt(8, registro.getClientes());
                procedimiento.execute();
                listaCuentasPorCobrar.add(registro);
            }catch(Exception e){
                e.printStackTrace();
            }
            
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ModificarCuentasPorCobrar(?,?,?,?,?,?)}");
            CuentasPorCobrar registro = (CuentasPorCobrar)tblCuentasPorCobrar.getSelectionModel().getSelectedItem();
            registro.setNumeroFactura(txtNumeroFactura.getText());
            registro.setAnio(txtAnio.getText());
            registro.setMes(Integer.parseInt(txtMes.getText()));
            registro.setValorNetoPago(Double.parseDouble(txtValorNeto.getText()));
            registro.setEstadoDePago(txtEstado.getText());
            procedimiento.setInt(1, registro.getCodigoCuentasPorCobrar());
            procedimiento.setString(2, registro.getNumeroFactura());
            procedimiento.setString(3, registro.getAnio());
            procedimiento.setInt(4, registro.getMes());
            procedimiento.setDouble(5, registro.getValorNetoPago());
            procedimiento.setString(6, registro.getEstadoDePago());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void desactivarControles(){
        txtCodigCuentasPorCobrar.setEditable(false);
        txtNumeroFactura.setEditable(false);
        txtAnio.setEditable(false);
        txtMes.setEditable(false);
        txtValorNeto.setEditable(false);
        txtEstado.setEditable(false);
        cmbLocales.setDisable(true);
        cmbAdministracion.setDisable(true);
        cmbClientes.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigCuentasPorCobrar.setEditable(false);
        txtNumeroFactura.setEditable(true);
        txtAnio.setEditable(true);
        txtMes.setEditable(true);
        txtValorNeto.setEditable(true);
        txtEstado.setEditable(true);
        cmbLocales.setDisable(false);
        cmbAdministracion.setDisable(false);
        cmbClientes.setDisable(false);
    }

    public void limpiarControles(){
        txtCodigCuentasPorCobrar.clear();
        txtNumeroFactura.clear();
        txtAnio.clear();
        txtMes.clear();
        txtValorNeto.clear();
        txtEstado.clear();
        cmbLocales.getSelectionModel().clearSelection();
        cmbAdministracion.getSelectionModel().clearSelection();
        cmbClientes.getSelectionModel().clearSelection();
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaLocales(){
        escenarioPrincipal.ventanaLocales();
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
}
