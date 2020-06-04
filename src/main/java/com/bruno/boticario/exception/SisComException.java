package com.bruno.boticario.exception;

public class SisComException extends Exception{
    
    private String productName;
    private Integer stock;
    private String messageError;

    public SisComException(String productName, Integer stock, String messageError) {
        this.productName = productName;
        this.stock = stock;
        this.messageError = messageError;
    }

    public SisComException(String messageError) {
        this.messageError = messageError;
    }

    public String getMessageError() {
        return messageError;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getStock() {
        return stock;
    }
}