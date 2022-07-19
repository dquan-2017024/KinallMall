
package org.danielquan.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
import org.danielquan.db.Conexion;
import org.danielquan.report.GenerarReporte;
import org.danielquan.system.Principal;

/**
 *
 * @author Daniel Quan
 */
public class AdministracionController implements Initializable{
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Administracion> listaAdministracion; 
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoAdmin;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtTelefono;
    @FXML private TableView tblAdministracion;
    @FXML private TableColumn colCodAdministracion;
    @FXML private TableColumn colDireccion;
    @FXML private TableColumn colTelefono;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
 @Override
    public void initialize(URL location, ResourceBundle resources) {
       cargarDatos();
    }
    
    public void cargarDatos(){
        tblAdministracion.setItems(getAdministracion());
        colCodAdministracion.setCellValueFactory(new PropertyValueFactory<Administracion,Integer>("codigoAdministracion"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Administracion,String>("direccion"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Administracion,String>("telefono"));
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
    
    @FXML
    public void seleccionarElemento(){
        if(tblAdministracion.getSelectionModel().getSelectedItem() != null){
            txtCodigoAdmin.setText(String.valueOf(((Administracion)tblAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion()));
            txtDireccion.setText(((Administracion)tblAdministracion.getSelectionModel().getSelectedItem()).getDireccion());
            txtTelefono.setText(((Administracion)tblAdministracion.getSelectionModel().getSelectedItem()).getTelefono());
        }else {
            txtCodigoAdmin.clear();
            txtDireccion.clear();
            txtTelefono.clear();
        }
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
                imgNuevo.setImage(new Image("/org/danielquan/images/Guardar.png"));
                imgEliminar.setImage(new Image("/org/danielquan/images/Cerrar.png"));
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
                imgNuevo.setImage(new Image("/org/danielquan/images/Nuevo.png"));
                imgEliminar.setImage(new Image("/org/danielquan/images/Eliminar.png"));
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
                imgNuevo.setImage(new Image("/org/danielquan/images/Nuevo.png"));
                imgEliminar.setImage(new Image("/org/danielquan/images/Eliminar.png"));
                tipoDeOperacion = operaciones.NINGUNO;
                break;
            
            default:
                if (tblAdministracion.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar el registro?", "Eliminar Administracion", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_EliminarAdministracion(?)}");
                            procedimiento.setInt(1, ((Administracion)tblAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());
                            procedimiento.execute();
                            listaAdministracion.remove(tblAdministracion.getSelectionModel().getSelectedIndex());
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
                    if(tblAdministracion.getSelectionModel().getSelectedItem() != null){
                        activarControles();
                        btnEditar.setText("Actualizar");
                        btnReporte.setText("Cancelar");
                        btnNuevo.setDisable(true);
                        btnEliminar.setDisable(true);
                        imgEditar.setImage(new Image("/org/danielquan/images/Guardar.png"));
                        imgReporte.setImage(new Image("/org/danielquan/images/Cerrar.png"));
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
                    imgEditar.setImage(new Image("/org/danielquan/images/Editar.png"));
                    imgReporte.setImage(new Image("/org/danielquan/images/Reporte.png"));
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
                    imgEditar.setImage(new Image("/org/danielquan/images/Editar.png"));
                    imgReporte.setImage(new Image("/org/danielquan/images/Reporte.png"));
                    tipoDeOperacion = operaciones.NINGUNO;
                break;
        }
    }
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        parametros.put("codigoAdministracion", null);
        GenerarReporte.mostrarReporte("ReporteAdministracion.jasper", "Reporte de Administración", parametros);
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ModificarAdministracion(?,?,?)}");
            Administracion registro = (Administracion)tblAdministracion.getSelectionModel().getSelectedItem();
            registro.setDireccion(txtDireccion.getText());
            registro.setTelefono(txtTelefono.getText());
            procedimiento.setInt(1, registro.getCodigoAdministracion());
            procedimiento.setString(2, registro.getDireccion());
            procedimiento.setString(3, registro.getTelefono());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void guardar(){
        if(txtDireccion.getText().equals("") | txtTelefono.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Por favor no deje ningun campo vacío.");
        }else{
        Administracion registro = new Administracion();
        registro.setDireccion(txtDireccion.getText());
        registro.setTelefono(txtTelefono.getText());
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_AgregarAdministracion(?,?)}");
                procedimiento.setString(1, registro.getDireccion());
                procedimiento.setString(2, registro.getTelefono());
                procedimiento.execute();
                listaAdministracion.add(registro);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void desactivarControles(){
        txtCodigoAdmin.setEditable(false);
        txtDireccion.setEditable(false);
        txtTelefono.setEditable(false);
    }

    public void activarControles(){
        txtCodigoAdmin.setEditable(false);
        txtDireccion.setEditable(true);
        txtTelefono.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoAdmin.clear();
        txtDireccion.clear();
        txtTelefono.clear();
    }
    
    
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    @FXML
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
}
