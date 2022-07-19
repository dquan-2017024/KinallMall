package org.danielquan.system;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.danielquan.bean.Usuario;
import org.danielquan.controller.AdministracionController;
import org.danielquan.controller.CargoController;
import org.danielquan.controller.ClientesController;
import org.danielquan.controller.CuentasPorCobrarController;
import org.danielquan.controller.CuentasPorPagarController;
import org.danielquan.controller.DepartamentoController;
import org.danielquan.controller.EmpleadosController;
import org.danielquan.controller.HorariosController;
import org.danielquan.controller.LocalesController;
import org.danielquan.controller.LoginController;
import org.danielquan.controller.MenuPrincipalController;
import org.danielquan.controller.ProgramadorController;
import org.danielquan.controller.ProveedoresController;
import org.danielquan.controller.TipoClienteController;
import org.danielquan.controller.UsuarioController;


public class Principal extends Application {
    private final String paqueteVista = "/org/danielquan/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    
    @Override
    public void start(Stage escenarioPrincipal) throws IOException {
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Kinal Mall");
        this.escenarioPrincipal.getIcons().add(new Image("/org/danielquan/images/Icono.png"));
        this.escenarioPrincipal.setX(340);//1366x788
        this.escenarioPrincipal.setY(150);
        this.escenarioPrincipal.initStyle(StageStyle.TRANSPARENT);
        ventanaLogin();
        escenarioPrincipal.show();
    }
    
    
    public void menuPrincipal(){
        try{

            MenuPrincipalController menu = (MenuPrincipalController)cambiarEscena("MenuPrincipalView.fxml", 405,409);
            menu.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProgramador(){
        try{
            ProgramadorController programador = (ProgramadorController)cambiarEscena("ProgramadorView.fxml", 667, 415);
            programador.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaAdministracion(){
        try{
            AdministracionController admini = (AdministracionController) cambiarEscena("AdministracionView.fxml",720,400);
            admini.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaClientes(){
        try{
            ClientesController cliente = (ClientesController) cambiarEscena("ClientesView.fxml",939,420);
            cliente.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaEmpleados(){
        try{
            EmpleadosController empleado = (EmpleadosController) cambiarEscena("EmpleadosView.fxml",982,450);
            empleado.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaTipoCliente(){
        try{
            TipoClienteController tipoCliente = (TipoClienteController) cambiarEscena("TipoClienteView.fxml",645,420);
            tipoCliente.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaLocales(){
        try{
            LocalesController local = (LocalesController) cambiarEscena("LocalesView.fxml",900,420);
            local.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaDepartamento(){
        try{
            DepartamentoController departamento = (DepartamentoController)cambiarEscena("DepartamentosView.fxml", 687,420);
            departamento.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaProveedor(){
        try{
            ProveedoresController proveedor = (ProveedoresController)cambiarEscena("ProveedoresView.fxml", 900,420);
            proveedor.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaCuentasPorPagar(){
        try{
            CuentasPorPagarController pagar = (CuentasPorPagarController)cambiarEscena("CuentasPorPagarView.fxml", 900, 420);
            pagar.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaHorarios(){
        try{
            HorariosController horario = (HorariosController)cambiarEscena("HorariosView.fxml", 900, 420);
            horario.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaCargos(){
        try{
            CargoController cargo = (CargoController)cambiarEscena("CargosView.fxml", 732, 420);
            cargo.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaCuentasPorCobrar(){
        try{
            CuentasPorCobrarController cobrar = (CuentasPorCobrarController)cambiarEscena("CuentasPorCobrarView.fxml", 900, 420);
            cobrar.setEscenarioPrincipal(this);
        }catch(Exception e){
           e.printStackTrace();
        }
    }
    
    public void ventanaUsuario(){
        try{
            UsuarioController usuario = (UsuarioController)cambiarEscena("UsuariosView.fxml", 587, 420);
            usuario.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void ventanaLogin(){
        try{
            LoginController login = (LoginController)cambiarEscena("LoginView.fxml", 467, 473);
            login.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws IOException{
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        InputStream archivo = Principal.class.getResourceAsStream(paqueteVista+fxml);
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(paqueteVista+fxml));
        escena = new Scene((AnchorPane)cargadorFXML.load(archivo), ancho, alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFXML.getController();
        return resultado;
    }
    
    
}
