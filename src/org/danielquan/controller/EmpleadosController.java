package org.danielquan.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
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
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.danielquan.bean.Administracion;
import org.danielquan.bean.Cargos;
import org.danielquan.bean.Clientes;
import org.danielquan.bean.Departamento;
import org.danielquan.bean.Empleados;
import org.danielquan.bean.Horarios;
import org.danielquan.db.Conexion;
import org.danielquan.report.GenerarReporte;
import org.danielquan.system.Principal;


public class EmpleadosController implements Initializable{
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoDeOperacion = operaciones.NINGUNO;
    private Principal escenarioPrincipal;
    private ObservableList<Empleados> listaEmpleados;
    private ObservableList<Departamento> listaDepartamentos;
    private ObservableList<Cargos> listaCargos;
    private ObservableList<Horarios> listaHorarios;
    private ObservableList<Administracion> listaAdministracion;
    private DatePicker fechaContratacion;
    @FXML private Button btnNuevo;
    @FXML private Button btnEliminar;
    @FXML private Button btnEditar;
    @FXML private Button btnReporte;
    @FXML private TextField txtCodigoEmpleado;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtEmail;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtSueldo;
    @FXML private ComboBox cmbDepartamento;
    @FXML private ComboBox cmbCargo;
    @FXML private ComboBox cmbHorario;
    @FXML private ComboBox cmbAdministracion;
    @FXML private GridPane grpFechaContratación;
    @FXML private TableView tblEmpleados;
    @FXML private TableColumn colCodigoEmpleado;
    @FXML private TableColumn colNombre;
    @FXML private TableColumn colApellido;
    @FXML private TableColumn colEmail;
    @FXML private TableColumn colTelefono;
    @FXML private TableColumn colContratacion;
    @FXML private TableColumn colSueldo;
    @FXML private TableColumn colDepartamento;
    @FXML private TableColumn colCargo;
    @FXML private TableColumn colHorario;
    @FXML private TableColumn colAdministracion;
    @FXML private ImageView imgNuevo;
    @FXML private ImageView imgEliminar;
    @FXML private ImageView imgEditar;
    @FXML private ImageView imgReporte;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fechaContratacion = new DatePicker(Locale.ENGLISH);   
        fechaContratacion.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        fechaContratacion.getCalendarView().todayButtonTextProperty().set("Today");
        fechaContratacion.getCalendarView().setShowWeeks(false);
        grpFechaContratación.add(fechaContratacion, 5, 1);
        cargarDatos();
    }
    
    public void cargarDatos(){
        tblEmpleados.setItems(getEmpleados());
        colCodigoEmpleado.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("codigoEmpleado"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Empleados,String>("nombreEmpleado"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Empleados,String>("apellidoEmpleado"));
        colEmail.setCellValueFactory(new PropertyValueFactory<Empleados,String>("correoElectronico"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Empleados,String>("telefonoEmpleado"));
        colContratacion.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("fechaContratacion"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleados,Double>("sueldo"));
        colDepartamento.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("departamentos"));
        colCargo.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("cargos"));
        colHorario.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("horarios"));
        colAdministracion.setCellValueFactory(new PropertyValueFactory<Empleados,Integer>("administracion"));
        cmbDepartamento.setItems(getDepartamento());
        cmbCargo.setItems(getCargo());
        cmbHorario.setItems(getHorario());
        cmbAdministracion.setItems(getAdministracion());
    }
    
    public ObservableList<Empleados> getEmpleados(){
        ArrayList<Empleados> lista = new ArrayList<Empleados>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarEmpleados()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Empleados(resultado.getInt("codigoEmpleado"),
                                            resultado.getString("nombreEmpleado"),
                                            resultado.getString("apellidoEmpleado"),
                                            resultado.getString("correoElectronico"),
                                            resultado.getString("telefonoEmpleado"),
                                            resultado.getDate("fechaContratacion"),
                                            resultado.getDouble("sueldo"),
                                            resultado.getInt("departamentos"),
                                            resultado.getInt("cargos"),
                                            resultado.getInt("horarios"),
                                            resultado.getInt("administracion")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        
        return listaEmpleados = FXCollections.observableArrayList(lista);
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
        
        return listaDepartamentos = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Cargos> getCargo(){
        ArrayList<Cargos> lista = new ArrayList<Cargos>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ListarCargos()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                lista.add(new Cargos(resultado.getInt("codigoCargo"),
                                        resultado.getString("nombreCargo")));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaCargos = FXCollections.observableArrayList(lista);
    }
    
    public ObservableList<Horarios> getHorario(){
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
        if(tblEmpleados.getSelectionModel().getSelectedItem() != null){
            txtCodigoEmpleado.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado()));
            txtNombre.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getNombreEmpleado());
            txtApellido.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getApellidoEmpleado());
            txtTelefono.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getTelefonoEmpleado());
            txtEmail.setText(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCorreoElectronico());
            txtSueldo.setText(String.valueOf(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getSueldo()));
            fechaContratacion.selectedDateProperty().set(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getFechaContratacion());
            cmbDepartamento.getSelectionModel().select(buscarDepartamento(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getDepartamentos()));
            cmbCargo.getSelectionModel().select(buscarCargo(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCargos()));
            cmbHorario.getSelectionModel().select(buscarHorario(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getHorarios()));
            cmbAdministracion.getSelectionModel().select(buscarAdministracion(((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getAdministracion()));
        }else{
            limpiarControles();
        }
    }
    
    public Departamento buscarDepartamento(int codigDepartamento){
        Departamento resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarDepartamento(?)}");
            procedimiento.setInt(1, codigDepartamento);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Departamento(registro.getInt("codigoDepartamento"),
                                                registro.getString("nombreDepartamento"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Cargos buscarCargo(int codigoCargo){
        Cargos resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarCargo(?)}");
            procedimiento.setInt(1, codigoCargo);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Cargos(registro.getInt("codigoCargo"),
                                        registro.getString("nombreCargo"));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return resultado;
    }
    
    public Horarios buscarHorario(int codigoHorario){
        Horarios resultado = null;
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_BuscarHorario(?)}");
            procedimiento.setInt(1, codigoHorario);
            ResultSet registro = procedimiento.executeQuery();
            while(registro.next()){
                resultado = new Horarios(registro.getInt("codigoHorario"),
                                            registro.getString("horarioEntrada"),
                                            registro.getString("horarioSalida"),
                                            registro.getBoolean("lunes"),
                                            registro.getBoolean("martes"),
                                            registro.getBoolean("miercoles"),
                                            registro.getBoolean("jueves"),
                                            registro.getBoolean("viernes"));
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
            if(tblEmpleados.getSelectionModel().getSelectedItem() != null){
                int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de eliminar el registro?", "Eliminar Empleado", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(respuesta == JOptionPane.YES_OPTION){
                    try{
                        PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_EliminarEmpleado(?)}");
                        procedimiento.setInt(1, ((Empleados)tblEmpleados.getSelectionModel().getSelectedItem()).getCodigoEmpleado());
                        procedimiento.execute();
                        listaEmpleados.remove(tblEmpleados.getSelectionModel().getSelectedItem());
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
                    if(tblEmpleados.getSelectionModel().getSelectedItem() != null){
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
        if(txtNombre.getText().equals("")|txtApellido.getText().equals("")|txtEmail.getText().equals("")|
                txtTelefono.getText().equals("")|fechaContratacion.getSelectedDate()==null|txtSueldo.getText().equals("")|
                cmbDepartamento.getSelectionModel().getSelectedItem()==null|cmbCargo.getSelectionModel().getSelectedItem()==null|
                cmbHorario.getSelectionModel().getSelectedItem()==null|cmbAdministracion.getSelectionModel().getSelectedItem()==null){
            JOptionPane.showMessageDialog(null, "Por favor no deje vacío ningún espacio");
        }else{
            Empleados registro = new Empleados();
            registro.setNombreEmpleado(txtNombre.getText());
            registro.setApellidoEmpleado(txtApellido.getText());
            registro.setCorreoElectronico(txtEmail.getText());
            registro.setTelefonoEmpleado(txtTelefono.getText());
            registro.setFechaContratacion(fechaContratacion.getSelectedDate());
            registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
            registro.setDepartamentos(((Departamento)cmbDepartamento.getSelectionModel().getSelectedItem()).getCodigoDepartamento());
            registro.setCargos(((Cargos)cmbCargo.getSelectionModel().getSelectedItem()).getCodigoCargo());
            registro.setHorarios(((Horarios)cmbHorario.getSelectionModel().getSelectedItem()).getCodigoHorario());
            registro.setAdministracion(((Administracion)cmbAdministracion.getSelectionModel().getSelectedItem()).getCodigoAdministracion());  
            try{
                PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_AgregarEmpleado(?,?,?,?,?,?,?,?,?,?)}");
                procedimiento.setString(1, registro.getNombreEmpleado());
                procedimiento.setString(2, registro.getApellidoEmpleado());
                procedimiento.setString(3, registro.getCorreoElectronico());
                procedimiento.setString(4, registro.getTelefonoEmpleado());
                procedimiento.setDate(5, new java.sql.Date(registro.getFechaContratacion().getTime()));
                procedimiento.setDouble(6, registro.getSueldo());
                procedimiento.setInt(7, registro.getDepartamentos());
                procedimiento.setInt(8, registro.getCargos());
                procedimiento.setInt(9, registro.getHorarios());
                procedimiento.setInt(10, registro.getAdministracion());
                procedimiento.execute();
                listaEmpleados.add(registro);
            }catch(Exception e){
                e.printStackTrace();
            }
        }    
    }
    
    public void actualizar(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{CALL sp_ModificarEmpleado(?,?,?,?,?,?,?)}");
            Empleados registro = (Empleados)tblEmpleados.getSelectionModel().getSelectedItem();
            registro.setNombreEmpleado(txtNombre.getText());
            registro.setApellidoEmpleado(txtApellido.getText());
            registro.setTelefonoEmpleado(txtTelefono.getText());
            registro.setCorreoElectronico(txtEmail.getText());
            registro.setFechaContratacion(fechaContratacion.getSelectedDate());
            registro.setSueldo(Double.parseDouble(txtSueldo.getText()));
            procedimiento.setInt(1, registro.getCodigoEmpleado());
            procedimiento.setString(2, registro.getNombreEmpleado());
            procedimiento.setString(3, registro.getApellidoEmpleado());
            procedimiento.setString(4, registro.getCorreoElectronico());
            procedimiento.setString(5, registro.getTelefonoEmpleado());
            procedimiento.setDate(6, new java.sql.Date(registro.getFechaContratacion().getTime()));
            procedimiento.setDouble(7, registro.getSueldo());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void imprimirReporte(){
        Map parametros = new HashMap();
        parametros.put("empleado", Integer.parseInt(JOptionPane.showInputDialog("Ingrese el codigo del empleado")));
        GenerarReporte.mostrarReporte("ReporteEmpleado.jasper", "Reporte Empleado", parametros);
    }
    
    public void desactivarControles(){
        txtCodigoEmpleado.setEditable(false);
        txtNombre.setEditable(false);
        txtApellido.setEditable(false);
        txtTelefono.setEditable(false);
        txtEmail.setEditable(false);
        txtSueldo.setEditable(false);
        cmbDepartamento.setDisable(true);
        cmbCargo.setDisable(true);
        cmbHorario.setDisable(true);
        cmbAdministracion.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigoEmpleado.setEditable(false);
        txtNombre.setEditable(true);
        txtApellido.setEditable(true);
        txtTelefono.setEditable(true);
        txtEmail.setEditable(true);
        txtSueldo.setEditable(true);
        cmbDepartamento.setDisable(false);
        cmbCargo.setDisable(false);
        cmbHorario.setDisable(false);
        cmbAdministracion.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoEmpleado.clear();
        txtNombre.clear();
        txtApellido.clear();
        txtTelefono.clear();
        txtEmail.clear();
        txtSueldo.clear();
        cmbDepartamento.getSelectionModel().clearSelection();
        cmbCargo.getSelectionModel().clearSelection();
        cmbHorario.getSelectionModel().clearSelection();
        cmbAdministracion.getSelectionModel().clearSelection();
    }
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void ventanaCargos(){
        escenarioPrincipal.ventanaCargos();
    }
    
    public void menuPrincipal(){
        escenarioPrincipal.menuPrincipal();
    }
}
