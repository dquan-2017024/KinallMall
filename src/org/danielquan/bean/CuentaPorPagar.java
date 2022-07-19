package org.danielquan.bean;

import java.util.Date;


public class CuentaPorPagar {
    private int codigoCuentasPorPagar;
    private String numeroFactura;
    private Date fechaLimitePago;
    private String estadoPago;
    private Double valorNetoPago;
    private int administracion;
    private int proveedores;

    public CuentaPorPagar() {
    }

    public CuentaPorPagar(int codigoCuentasPorPagar, String numeroFactura, Date fechaLimitePago, String estadoPago, Double valorNetoPago, int administracion, int proveedores) {
        this.codigoCuentasPorPagar = codigoCuentasPorPagar;
        this.numeroFactura = numeroFactura;
        this.fechaLimitePago = fechaLimitePago;
        this.estadoPago = estadoPago;
        this.valorNetoPago = valorNetoPago;
        this.administracion = administracion;
        this.proveedores = proveedores;
    }

    public int getCodigoCuentasPorPagar() {
        return codigoCuentasPorPagar;
    }

    public void setCodigoCuentasPorPagar(int codigoCuentasPorPagar) {
        this.codigoCuentasPorPagar = codigoCuentasPorPagar;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Date getFechaLimitePago() {
        return fechaLimitePago;
    }

    public void setFechaLimitePago(Date fechaLimitePago) {
        this.fechaLimitePago = fechaLimitePago;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public Double getValorNetoPago() {
        return valorNetoPago;
    }

    public void setValorNetoPago(Double valorNetoPago) {
        this.valorNetoPago = valorNetoPago;
    }

    public int getAdministracion() {
        return administracion;
    }

    public void setAdministracion(int administracion) {
        this.administracion = administracion;
    }

    public int getProveedores() {
        return proveedores;
    }

    public void setProveedores(int proveedores) {
        this.proveedores = proveedores;
    }

    
    
    
    
}
