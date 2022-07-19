package org.danielquan.bean;

import java.util.Date;


public class Empleados {
    private int codigoEmpleado;
    private String nombreEmpleado;
    private String apellidoEmpleado;
    private String correoElectronico;
    private String telefonoEmpleado;
    private Date fechaContratacion; 
    private double sueldo;
    private int departamentos;
    private int cargos;
    private int horarios;
    private int administracion;

    public Empleados() {
    }

    public Empleados(int codigoEmpleado, String nombreEmpleado, String apellidoEmpleado, String correoElectronico, String telefonoEmpleado, Date fechaContratacion, double sueldo, int departamentos, int cargos, int horarios, int administracion) {
        this.codigoEmpleado = codigoEmpleado;
        this.nombreEmpleado = nombreEmpleado;
        this.apellidoEmpleado = apellidoEmpleado;
        this.correoElectronico = correoElectronico;
        this.telefonoEmpleado = telefonoEmpleado;
        this.fechaContratacion = fechaContratacion;
        this.sueldo = sueldo;
        this.departamentos = departamentos;
        this.cargos = cargos;
        this.horarios = horarios;
        this.administracion = administracion;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getApellidoEmpleado() {
        return apellidoEmpleado;
    }

    public void setApellidoEmpleado(String apellidoEmpleado) {
        this.apellidoEmpleado = apellidoEmpleado;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public String getTelefonoEmpleado() {
        return telefonoEmpleado;
    }

    public void setTelefonoEmpleado(String telefonoEmpleado) {
        this.telefonoEmpleado = telefonoEmpleado;
    }

    public Date getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(Date fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public int getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(int departamentos) {
        this.departamentos = departamentos;
    }

    public int getCargos() {
        return cargos;
    }

    public void setCargos(int cargos) {
        this.cargos = cargos;
    }

    public int getHorarios() {
        return horarios;
    }

    public void setHorarios(int horarios) {
        this.horarios = horarios;
    }

    public int getAdministracion() {
        return administracion;
    }

    public void setAdministracion(int administracion) {
        this.administracion = administracion;
    }

    
    
    
}
