package org.danielquan.bean;


public class Proveedores {
    private int codigoProveedor;
    private String NITProveedor;
    private String servicioPrestado;
    private String telefonoProveedor;
    private String direccionProveedor;
    private Double saldoFavor ;
    private Double saldoContra;
    private int administracion;
    
    public Proveedores() {
    }

    public Proveedores(int codigoProveedor, String NITProveedor, String servicioPrestado, String telefonoProveedor, String direccionProveedor, Double saldoFavor, Double saldoContra, int administracion) {
        this.codigoProveedor = codigoProveedor;
        this.NITProveedor = NITProveedor;
        this.servicioPrestado = servicioPrestado;
        this.telefonoProveedor = telefonoProveedor;
        this.direccionProveedor = direccionProveedor;
        this.saldoFavor = saldoFavor;
        this.saldoContra = saldoContra;
        this.administracion = administracion;
    }

    public int getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(int codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }

    public String getNITProveedor() {
        return NITProveedor;
    }

    public void setNITProveedor(String NITProveedor) {
        this.NITProveedor = NITProveedor;
    }

    public String getServicioPrestado() {
        return servicioPrestado;
    }

    public void setServicioPrestado(String servicioPrestado) {
        this.servicioPrestado = servicioPrestado;
    }

    public String getTelefonoProveedor() {
        return telefonoProveedor;
    }

    public void setTelefonoProveedor(String telefonoProveedor) {
        this.telefonoProveedor = telefonoProveedor;
    }

    public String getDireccionProveedor() {
        return direccionProveedor;
    }

    public void setDireccionProveedor(String direccionProveedor) {
        this.direccionProveedor = direccionProveedor;
    }

    public Double getSaldoFavor() {
        return saldoFavor;
    }

    public void setSaldoFavor(Double saldoFavor) {
        this.saldoFavor = saldoFavor;
    }

    public Double getSaldoContra() {
        return saldoContra;
    }

    public void setSaldoContra(Double saldoContra) {
        this.saldoContra = saldoContra;
    }

    public int getAdministracion() {
        return administracion;
    }

    public void setAdministracion(int administracion) {
        this.administracion = administracion;
    }
    
    public String toString(){
        return getCodigoProveedor()+" | "+getServicioPrestado()+" | "+getTelefonoProveedor();
    }
    
}
