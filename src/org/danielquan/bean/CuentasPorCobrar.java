package org.danielquan.bean;


public class CuentasPorCobrar {
    private int codigoCuentasPorCobrar;
    private String numeroFactura;
    private String anio;
    private int mes ;
    private double valorNetoPago;
    private String estadoDePago;
    private int locales;
    private int administracion;
    private int clientes;

    public CuentasPorCobrar() {
    }

    public CuentasPorCobrar(int codigoCuentasPorCobrar, String numeroFactura, String anio, int mes, double valorNetoPago, String estadoDePago, int locales, int administracion, int clientes) {
        this.codigoCuentasPorCobrar = codigoCuentasPorCobrar;
        this.numeroFactura = numeroFactura;
        this.anio = anio;
        this.mes = mes;
        this.valorNetoPago = valorNetoPago;
        this.estadoDePago = estadoDePago;
        this.locales = locales;
        this.administracion = administracion;
        this.clientes = clientes;
    }

    public int getCodigoCuentasPorCobrar() {
        return codigoCuentasPorCobrar;
    }

    public void setCodigoCuentasPorCobrar(int codigoCuentasPorCobrar) {
        this.codigoCuentasPorCobrar = codigoCuentasPorCobrar;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public double getValorNetoPago() {
        return valorNetoPago;
    }

    public void setValorNetoPago(double valorNetoPago) {
        this.valorNetoPago = valorNetoPago;
    }

    public String getEstadoDePago() {
        return estadoDePago;
    }

    public void setEstadoDePago(String estadoDePago) {
        this.estadoDePago = estadoDePago;
    }

    public int getLocales() {
        return locales;
    }

    public void setLocales(int locales) {
        this.locales = locales;
    }

    public int getAdministracion() {
        return administracion;
    }

    public void setAdministracion(int administracion) {
        this.administracion = administracion;
    }

    public int getClientes() {
        return clientes;
    }

    public void setClientes(int clientes) {
        this.clientes = clientes;
    }
    
    
}
