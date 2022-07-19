package org.danielquan.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.danielquan.bean.Usuario;
import org.danielquan.db.Conexion;
import org.danielquan.system.Principal;


public class UsuarioController implements Initializable{
    private Principal escenarioPrincipal;
    enum operaciones{NUEVO, GUARDAR, NINGUNO};
    private operaciones tipoDeOperaciones = operaciones.NINGUNO;
    @FXML TextField txtCodigoUsuario;
    @FXML TextField txtNombreUsuario;
    @FXML TextField txtApellidoUsuario;
    @FXML TextField txtUsuarioLogin;
    @FXML TextField txtcontrasena;
    @FXML Button btnNuevo;
    @FXML ImageView imgNuevo;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    @FXML
    public void nuevo(){
        switch(tipoDeOperaciones){
            case NINGUNO:
                activarControles();
                limpiarControles();
                btnNuevo.setText("Guardar");
                imgNuevo.setImage(new Image("/org/javierbarillas/images/Guardar.png"));
                tipoDeOperaciones = operaciones.GUARDAR;
                break;
            case GUARDAR:
                guardar();
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                imgNuevo.setImage(new Image("/org/javierbarillas/images/Nuevo.png"));
                ventanaLogin();
                tipoDeOperaciones = operaciones.NINGUNO;
                break;
        }
    }
    
    @FXML
    public void cancelar(){ 
            desactivarControles();
            limpiarControles();
            escenarioPrincipal.ventanaLogin();          
    }
    
    public void guardar(){
        Usuario registro = new Usuario();
        registro.setNombreUsuario(txtNombreUsuario.getText());
        registro.setApellidoUsuario(txtApellidoUsuario.getText());
        registro.setUsuarioLogin(txtUsuarioLogin.getText());
        registro.setContrasena(txtcontrasena.getText());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("CALL sp_AgregarUsuario(?,?,?,?)");
            procedimiento.setString(1, registro.getNombreUsuario());
            procedimiento.setString(2, registro.getApellidoUsuario());
            procedimiento.setString(3, registro.getUsuarioLogin());
            procedimiento.setString(4, registro.getContrasena());
            procedimiento.execute();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void ventanaLogin(){
        escenarioPrincipal.ventanaLogin();
    }
    
    public void desactivarControles(){
        txtNombreUsuario.setEditable(false);
        txtApellidoUsuario.setEditable(false);
        txtUsuarioLogin.setEditable(false);
        txtcontrasena.setEditable(false);
    }
    
    public void activarControles(){
        txtNombreUsuario.setEditable(true);
        txtApellidoUsuario.setEditable(true);
        txtUsuarioLogin.setEditable(true);
        txtcontrasena.setEditable(true);
    }
    
    public void limpiarControles(){
        txtNombreUsuario.clear();
        txtApellidoUsuario.clear();
        txtUsuarioLogin.clear();
        txtcontrasena.clear();
    }
    
    
    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
}