package com.bank.model;

public class AbstractEntity {
    private Integer id;

    public AbstractEntity() {
    }


    public AbstractEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
