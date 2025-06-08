package com.javtest.Models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer payment_id;
    private Integer id_order;
    private Date date_payment;
    private String path_chek;

    public Payment() {}

    public Payment(Integer id_order, String path_check, Date date_payment) {
        this.id_order = id_order;
        this.path_chek = path_chek;
        this.date_payment = date_payment;
    }

    public Integer getPaymentId() {
        return payment_id;
    }

    public void setPaymentId(Integer payment_id) {
        this.payment_id = payment_id;
    }

    public Integer getOrderId() {
        return id_order;
    }

    public void setOrderId(Integer id_order) {
        this.id_order = id_order;
    }

    public String getPathChek() {
        return path_chek;
    }

    public void setPathChek(String path_chek) {
        this.path_chek = path_chek;
    }

    public Date getPaymentDate() {
        return date_payment;
    }

    public void setPaymentDate(Date date_payment) {
        this.date_payment = date_payment;
    }
}

