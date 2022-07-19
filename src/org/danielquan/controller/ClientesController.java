package org.danielquan.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
import javax.swing.JOptionPane;
import org.danielquan.bean.Administracion;
import org.danielquan.bean.Clientes;
import org.danielquan.bean.Locales;
import org.danielquan.bean.TipoCliente;
import org.danielquan.db.Conexion;
import org.danielquan.report.GenerarReporte;
import org.danielquan.system.Principal;


public class ClientesController implements Initializable{
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Clientes> listaClientes;
    private ObservableList<Locales> listaLocales;
    private ObservableList<Administracion> listaAdministracion;
    private ObservableList<TipoCliente> listaTipoCliente;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoClientes;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtEmail;
    @FXML private ComboBox cmbLocales;
    @FXML private ComboBox cmbAdministracion;
    @FXML private ComboBox cmbTipoCliente;
    @FXML private TableView tblClientes;
    @FXML private TableColumn colCodigoCliente;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colApellido;
    @FXML private TableColumn colTelefono;
    @FXML private TableColumn colDireccion;
    @FXML private TableColumn colEmail;
    @FXML private TableColumn colLocal;
    @FXML private TableColumn colAdministracion;
    @FXML private TableColumn colTipoCliente;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblClientes.setItems(getClientes());
        colCodigoCliente.setCellValueFactory(new PropertyValueFactory<Clientes,Integer>("codigoCliente"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Clientes,String>("nombreCliente"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Clientes,String>("apellidoCliente"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Clientes,String>("telefonoCliente"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Clientes,String>("direccionCliente"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Clientes,String>("email"));
        colLocal.setCellValueFactory(new PropertyValueFactory<Locales,Integer>("codigoLocales"));
        colAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion,Integer>("codigoAdministracion"));
        colTipoCliente.setCellValueFactory(new PropertyValueFactory<TipoCliente,Integer>("codigoTipoCliente"));
        cmbLocales.setItems(getLocales());
        cmbAdministracion.setItems(getAdministracion());
        cmbTipoCliente.setItems(getTipoCliente());
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
    
    
    public ObservableList<TipoCliente> getTipoCliente(){
        ArrayList<TipoCliente> lista = new ArrayList<TipoCliente>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarTipoClientes()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new TipoCliente(resultado.getInt("codigoTipoCliente"),
                                            resultado.getString("descripcion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaTipoCliente = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarElemento(){
        if(tblClientes.getSelectionModel().getSelectedItem() != null){
            txtCodigoClientes.setText(String.valueOf(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente()));
            txtNombre.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getNombreCliente());
            txtApellido.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getApellidoCliente());
            txtTelefono.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getTelefonoCliente());
            txtEmail.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getEmail());
            txtDireccion.setText(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getDireccionCliente());
            cmbLocales.getSelectionModel().select(buscarLocal(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoLocales()));
            cmbAdministracion.getSelectionModel().select(buscarAdministracion(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
            cmbTipoCliente.getSelectionModel().select(buscarTipoCliente(((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoTipoCliente()));
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

    public TipoCliente buscarTipoCliente(int codigoTipoCliente){
        TipoCliente resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarTipoCliente(?)}");
            procedimiento.setInt(1, codigoTipoCliente);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new TipoCliente(registro.getInt("codigoTipoCliente"),
                                            registro.getString("descripcion"));
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
                if(tblClientes.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar el registro?", "Eliminar Cliente", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_EliminarCliente(?)}");
                            procedimiento.setInt(1, ((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
                            procedimiento.execute();
                            listaClientes.remove(tblClientes.getSelectionModel().getSelectedIndex());
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
                    if(tblClientes.getSelectionModel().getSelectedItem() != null){
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
            case NINGUNO:
                imprimirReporte();
                break;
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
        if(txtNombre.getText().equals("")|txtApellido.getText().equals("")|txtDireccion.getText().equals("")|
                txtTelefono.getText().equals("")|txtEmail.getText().equals("")|cmbLocales.getSelectionModel().getSelectedItem() == null|
                cmbAdministracion.getSelectionModel().getSelectedItem() == null| cmbTipoCliente.getSelectionModel().getSelectedItem() == null){
            JOptionPane.showMessageDialog(null, "Por favor no deje ningun campo vacío.");
        }else{
            Clientes registro = new Clientes();
            registro.setNombreCliente(txtNombre.getText());
            registro.setApellidoCliente(txtApellido.getText());
            registro.setTelefonoCliente(txtTelefono.getText());
            registro.setDireccionCliente(txtDireccion.getText());
            registro.setEmail(txtEmail.getText());
            registro.setCodigoLocales(((Locales)cmbLocales.getSelectionModel().getSelectedItem()).getCodigoLocal());
            registro.setCodigoAdministracion(((Administracion)cmbAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
            registro.setCodigoTipoCliente(((TipoCliente)cmbTipoCliente.getSelectionModel().getSelectedItem()).getCodigoTipoCliente());
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_AgregarCliente(?,?,?,?,?,?,?,?)}");
                procedimiento.setString(1, registro.getNombreCliente());
                procedimiento.setString(2, registro.getApellidoCliente());
                procedimiento.setString(3, registro.getTelefonoCliente());
                procedimiento.setString(4, registro.getDireccionCliente());
                procedimiento.setString(5, registro.getEmail());
                procedimiento.setInt(6, registro.getCodigoLocales());
                procedimiento.setInt(7, registro.getCodigoAdministracion());
                procedimiento.setInt(8, registro.getCodigoTipoCliente());
                procedimiento.execute();
                listaClientes.add(registro);
            }catch(Exception e){
                e.printStackTrace();
            }
        }    
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ModificarCliente(?,?,?,?,?,?)}");
            Clientes registro = (Clientes)tblClientes.getSelectionModel().getSelectedItem();
            registro.setNombreCliente(txtNombre.getText());
            registro.setApellidoCliente(txtApellido.getText());
            registro.setTelefonoCliente(txtTelefono.getText());
            registro.setDireccionCliente(txtDireccion.getText());
            registro.setEmail(txtEmail.getText());
            procedimiento.setInt(1, ((Clientes)tblClientes.getSelectionModel().getSelectedItem()).getCodigoCliente());
            procedimiento.setString(2, registro.getNombreCliente());
            procedimiento.setString(3, registro.getApellidoCliente());
            procedimiento.setString(4, registro.getTelefonoCliente());
            procedimiento.setString(5, registro.getDireccionCliente());
            procedimiento.setString(6, registro.getEmail());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        parametros.put("cliente", Integer.parseInt(JOptionPane.showInputDialog("Ingrese el codigo del cliente:")));
        GenerarReporte.mostrarReporte("ReporteCliente.jasper", "Reporte Cliente", parametros);
    }
    
    public void desactivarControles(){
        txtCodigoClientes.setEditable(false);
        txtNombre.setEditable(false);
        txtApellido.setEditable(false);
        txtTelefono.setEditable(false);
        txtDireccion.setEditable(false);
        txtEmail.setEditable(false);
        cmbLocales.setDisable(true);
        cmbAdministracion.setDisable(true);
        cmbTipoCliente.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigoClientes.setEditable(false);
        txtNombre.setEditable(true);
        txtApellido.setEditable(true);
        txtTelefono.setEditable(true);
        txtDireccion.setEditable(true);
        txtEmail.setEditable(true);
        cmbLocales.setDisable(false);
        cmbAdministracion.setDisable(false);
        cmbTipoCliente.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoClientes.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtDireccion.clear();
        txtEmail.clear();
        cmbLocales.getSelectionModel().clearSelection();
        cmbAdministracion.getSelectionModel().clearSelection();
        cmbTipoCliente.getSelectionModel().clearSelection();
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaTipoCliente(){
        escenarioPrincipal.ventanaTipoCliente();
    }
    
    @FXML
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
    
    
}
