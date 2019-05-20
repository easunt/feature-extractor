package com.ovio.extractor.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="test")
public class Test {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    public Long getId() {
        return id;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
