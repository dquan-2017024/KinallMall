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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.swing.JOptionPane;
import org.danielquan.bean.Horarios;
import org.danielquan.db.Conexion;
import org.danielquan.system.Principal;


public class HorariosController implements Initializable{
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Horarios> listaHorarios;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoHorario;
    @FXML private TextField txtHorarioEntrada;
    @FXML private TextField txtHorarioSalida;
    @FXML private TextField txtLunes;
    @FXML private TextField txtMartes;
    @FXML private TextField txtMiercoles;
    @FXML private TextField txtJueves;
    @FXML private TextField txtViernes;
    @FXML private TableView tblHorarios;
    @FXML private TableColumn colCodigoHorario;
    @FXML private TableColumn colHorarioEntrada;
    @FXML private TableColumn colHorarioSalida;
    @FXML private TableColumn colLunes;
    @FXML private TableColumn colMartes;
    @FXML private TableColumn colMiercoles;
    @FXML private TableColumn colJueves;
    @FXML private TableColumn colViernes;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
        
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblHorarios.setItems(getHorarios());
        colCodigoHorario.setCellValueFactory(new PropertyValueFactory<Horarios,Integer>("codigoHorario"));
        colHorarioEntrada.setCellValueFactory(new PropertyValueFactory<Horarios,String>("horarioEntrada"));
        colHorarioSalida.setCellValueFactory(new PropertyValueFactory<Horarios,String>("horarioSalida"));
        colLunes.setCellValueFactory(new PropertyValueFactory<Horarios,Boolean>("lunes"));
        colMartes.setCellValueFactory(new PropertyValueFactory<Horarios,Boolean>("martes"));
        colMiercoles.setCellValueFactory(new PropertyValueFactory<Horarios,Boolean>("miercoles"));
        colJueves.setCellValueFactory(new PropertyValueFactory<Horarios,Boolean>("jueves"));
        colViernes.setCellValueFactory(new PropertyValueFactory<Horarios,Boolean>("viernes"));
    }
    
    public ObservableList<Horarios> getHorarios(){
        ArrayList<Horarios> lista = new ArrayList<Horarios>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarHorarios()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Horarios(resultado.getInt("codigoHorario"), 
                                        resultado.getString("horarioEntrada"), 
                                        resultado.getString("horarioSalida"), 
                                        resultado.getBoolean("lunes"),
                                        resultado.getBoolean("martes"),
                                        resultado.getBoolean("miercoles"),
                                        resultado.getBoolean("jueves"),
                                        resultado.getBoolean("viernes")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaHorarios = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarElemento(){
        if(tblHorarios.getSelectionModel().getSelectedItem() != null){
            txtCodigoHorario.setText(String.valueOf(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getCodigoHorario()));
            txtHorarioEntrada.setText(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getHorarioEntrada());
            txtHorarioSalida.setText(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getHorarioSalida());
            txtLunes.setText(String.valueOf(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isLunes()));
            txtMartes.setText(String.valueOf(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isMartes()));
            txtMiercoles.setText(String.valueOf(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isMiercoles()));
            txtJueves.setText(String.valueOf(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isJueves()));
            txtViernes.setText(String.valueOf(((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).isViernes()));
        }else{
            limpiarControles();
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
                if(tblHorarios.getSelectionModel().getSelectedItem() != null){
                    int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar el registro?", "Eliminar Horario", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if(respuesta == JOptionPane.YES_OPTION){
                        try{
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_EliminarHorario(?)}");
                        procedimiento.setInt(1, ((Horarios)tblHorarios.getSelectionModel().getSelectedItem()).getCodigoHorario());
                        procedimiento.execute();
                        listaHorarios.remove(tblHorarios.getSelectionModel().getSelectedIndex());
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
                    if(tblHorarios.getSelectionModel().getSelectedItem() != null){
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
        if(txtHorarioEntrada.getText().equals("")|txtHorarioSalida.getText().equals("")|txtLunes.getText().equals("")|
                txtMartes.getText().equals("")|txtMiercoles.getText().equals("")|txtJueves.getText().equals("")|txtViernes.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Por favor no deje ningun campo vacío.");
        }else{
            Horarios registro = new Horarios();
            registro.setHorarioEntrada(txtHorarioEntrada.getText());
            registro.setHorarioSalida(txtHorarioSalida.getText());
            registro.setLunes(Boolean.parseBoolean(txtLunes.getText()));
            registro.setMartes(Boolean.parseBoolean(txtMartes.getText()));
            registro.setMiercoles(Boolean.parseBoolean(txtMiercoles.getText()));
            registro.setJueves(Boolean.parseBoolean(txtJueves.getText()));
            registro.setViernes(Boolean.parseBoolean(txtViernes.getText()));
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_AgregarHorario(?,?,?,?,?,?,?)}");
                procedimiento.setString(1, registro.getHorarioEntrada());
                procedimiento.setString(2, registro.getHorarioSalida());
                procedimiento.setBoolean(3, registro.isLunes());
                procedimiento.setBoolean(4, registro.isMartes());
                procedimiento.setBoolean(5, registro.isMiercoles());
                procedimiento.setBoolean(6, registro.isJueves());
                procedimiento.setBoolean(7, registro.isViernes());
                procedimiento.execute();
                listaHorarios.add(registro);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("CALL sp_ModificarHorario(?,?,?,?,?,?,?,?)");
            Horarios registro = (Horarios)tblHorarios.getSelectionModel().getSelectedItem();
            registro.setHorarioEntrada(txtHorarioEntrada.getText());
            registro.setHorarioSalida(txtHorarioSalida.getText());
            registro.setLunes(Boolean.parseBoolean(txtLunes.getText()));
            registro.setMartes(Boolean.parseBoolean(txtMartes.getText()));
            registro.setMiercoles(Boolean.parseBoolean(txtMiercoles.getText()));
            registro.setJueves(Boolean.parseBoolean(txtJueves.getText()));
            registro.setViernes(Boolean.parseBoolean(txtViernes.getText()));
            procedimiento.setInt(1, registro.getCodigoHorario());
            procedimiento.setString(2, registro.getHorarioEntrada());
            procedimiento.setString(3, registro.getHorarioSalida());
            procedimiento.setBoolean(4, registro.isLunes());
            procedimiento.setBoolean(5, registro.isMartes());
            procedimiento.setBoolean(6, registro.isMiercoles());
            procedimiento.setBoolean(7, registro.isJueves());
            procedimiento.setBoolean(8, registro.isViernes());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void activarControles(){
        txtCodigoHorario.setEditable(false);
        txtHorarioEntrada.setEditable(true);
        txtHorarioSalida.setEditable(true);
        txtLunes.setEditable(true);
        txtMartes.setEditable(true);
        txtMiercoles.setEditable(true);
        txtJueves.setEditable(true);
        txtViernes.setEditable(true);
    }
    
    public void desactivarControles(){
        txtCodigoHorario.setEditable(false);
        txtHorarioEntrada.setEditable(false);
        txtHorarioSalida.setEditable(false);
        txtLunes.setEditable(false);
        txtMartes.setEditable(false);
        txtMiercoles.setEditable(false);
        txtJueves.setEditable(false);
        txtViernes.setEditable(false);
    }
    
    public void limpiarControles(){
        txtCodigoHorario.clear();
        txtHorarioEntrada.clear();
        txtHorarioSalida.clear();
        txtLunes.clear();
        txtMartes.clear();
        txtMiercoles.clear();
        txtJueves.clear();
        txtViernes.clear();
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
