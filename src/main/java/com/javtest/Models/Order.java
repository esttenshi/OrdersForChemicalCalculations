package com.javtest.Models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer order_id;
    private Integer id_user;
    private Integer id_researcher; 
    private Integer type_order; 
    private String comment_order;
    private Date date_creation;
    private Date date_completion;
    private Integer id_folder;
    private Integer status_order; 

    public Order() {}

    public Order(Integer id_user, Integer id_researcher, Integer type_order, String comment_order, Date date_creation, Date date_completion, Integer id_folder, Integer status_order) {
        this.id_user = id_user;
        this.id_researcher = id_researcher;
        this.type_order = type_order;
        this.comment_order = comment_order;
        this.date_creation = date_creation;
        this.date_completion = date_completion;
        this.id_folder = id_folder;
        this.status_order = status_order;
    }

    public Integer getOrderId() {
        return order_id;
    }

    public void setOrderId(Integer order_id) {
        this.order_id = order_id;
    }

    public Integer getUserId() {
        return id_user;
    }

    public void setUserId(Integer id_user) {
        this.id_user = id_user;
    }

    public Integer getResearcherId() {
        return id_researcher;
    }

    public void setResearcherId(Integer id_researcher) {
        this.id_researcher = id_researcher;
    }

    public Integer getOrderType() {
        return type_order;
    }

    public void setOrderType(Integer type_order) {
        this.type_order = type_order;
    }

    public String getCommentOrder() {
        return comment_order;
    }

    public void setCommentOrder(String comment_order) {
        this.comment_order = comment_order;
    }

    public Date getCreationDate() {
        return date_creation;
    }

    public void setCreationDate(Date date_creation) {
        this.date_creation = date_creation;
    }

    public Date getCompletionDate() {
        return date_completion;
    }

    public void setCompletionDate(Date date_completion) {
        this.date_completion = date_completion;
    }

    public Integer getFolderId() {
        return id_folder;
    }

    public void setFolderId(Integer id_folder) {
        this.id_folder = id_folder;
    }

    public Integer getStatusOrder() {
        return status_order;
    }

    public void setStatusOrder(Integer status_order) {
        this.status_order = status_order;
    }
}
