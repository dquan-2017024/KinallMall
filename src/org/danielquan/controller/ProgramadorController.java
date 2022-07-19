package org.danielquan.controller;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.danielquan.system.Principal;

public class ProgramadorController implements Initializable{
    private Principal escenarioPrincipal;
    @FXML private Button btnProgramador;
    @FXML private Button btnAdmin;
    @FXML private Label lblNombre;
    @FXML private Label lblApellido;
    @FXML private Label lblTitulo;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
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
    
    
    @FXML
    private void opciones(ActionEvent event){
        if(event.getSource() == btnProgramador){
            lblNombre.setText("Daniel Enrique");
            lblApellido.setText("Quán Cruz");
            lblTitulo.setText("Estudiante");
        }else if(event.getSource() == btnAdmin){
            lblNombre.setText("Fundación");
            lblApellido.setText("KINAL");
            lblTitulo.setText("2022");
        }
    }
}
