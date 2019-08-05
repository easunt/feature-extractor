package com.ovio.extractor.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name="url_feature")
@Getter
@Setter
public class UrlFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Long urlId;
    private String feature;
}
