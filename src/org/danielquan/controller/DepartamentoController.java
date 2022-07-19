package org.danielquan.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashSet;
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
import org.danielquan.bean.Departamento;
import org.danielquan.db.Conexion;
import org.danielquan.system.Principal;


public class DepartamentoController implements Initializable{
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Departamento> listaDepartamento;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoDepartamento;
    @FXML private TextField txtNombreDepartamento;
    @FXML private TableView tblDepartamentos;
    @FXML private TableColumn colCodigoDepartamento;
    @FXML private TableColumn colNombreDepartamento;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    
    public void cargarDatos(){
        tblDepartamentos.setItems(getDepartamento());
        colCodigoDepartamento.setCellValueFactory(new PropertyValueFactory<Departamento,Integer>("codigoDepartamento"));
        colNombreDepartamento.setCellValueFactory(new PropertyValueFactory<Departamento,String>("nombreDepartamento"));
    }
    
    public ObservableList<Departamento> getDepartamento(){
        ArrayList<Departamento> lista = new ArrayList<Departamento>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarDepartamentos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Departamento(resultado.getInt("codigoDepartamento"),
                                            resultado.getString("nombreDepartamento")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaDepartamento = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarElemento(){
        if(tblDepartamentos.getSelectionModel().getSelectedItem() != null){
            txtCodigoDepartamento.setText(String.valueOf(((Departamento)tblDepartamentos.getSelectionModel().getSelectedItem()).getCodigoDepartamento()));
            txtNombreDepartamento.setText(((Departamento)tblDepartamentos.getSelectionModel().getSelectedItem()).getNombreDepartamento());
        }else{
            txtCodigoDepartamento.clear();
            txtNombreDepartamento.clear();
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
                cargarDatos();
                break;
                
            default:
                if(tblDepartamentos.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar el registro?", "Elimiar Departamento", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_EliminarDepartamento(?)}");
                            procedimiento.setInt(1, ((Departamento)tblDepartamentos.getSelectionModel().getSelectedItem()).getCodigoDepartamento());
                            procedimiento.execute();
                            listaDepartamento.remove(tblDepartamentos.getSelectionModel().getSelectedIndex());
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
                    if(tblDepartamentos.getSelectionModel().getSelectedItem() != null){
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
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ModificarDepartamento(?,?)}");
                Departamento registro = (Departamento)tblDepartamentos.getSelectionModel().getSelectedItem();
                registro.setNombreDepartamento(txtNombreDepartamento.getText());
                procedimiento.setInt(1, registro.getCodigoDepartamento());
                procedimiento.setString(2, registro.getNombreDepartamento());
                procedimiento.execute();
            }catch(Exception e){
                e.printStackTrace();
            }    
    }
    
    public void guardar(){
        
            Departamento registro = new Departamento();
            registro.setNombreDepartamento(txtNombreDepartamento.getText());
        if(txtNombreDepartamento.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Por favor no deje ningun campo vacío.");
        }else{
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_AgregarDepartamento(?)}");
                procedimiento.setString(1, registro.getNombreDepartamento());
                procedimiento.execute();
                listaDepartamento.add(registro);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void desactivarControles(){
        txtCodigoDepartamento.setEditable(false);
        txtNombreDepartamento.setEditable(false);
    }
    
    public void activarControles(){
        txtCodigoDepartamento.setEditable(false);
        txtNombreDepartamento.setEditable(true);
    }
    
    public void limpiarControles(){
        txtCodigoDepartamento.clear();
        txtNombreDepartamento.clear();
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
    
}
