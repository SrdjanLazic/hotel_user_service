package com.raf.hoteluserservice.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// Svaki od 3 rankova (Gold, Silver, Bronze) ima svoj ClientRank, i preko min i max definisemo granicu za dodeljivanje nekog ranka
@Entity
public class ClientRank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer minNumberOfReservations;
    private Integer maxNumberOfReservations;
    private Rank rank;
    private Double discount;

    public ClientRank(){

    }

    public ClientRank(String name, Rank rank, Integer minNumberOfReservations, Integer maxNumberOfReservations, Double discount) {
        this.name = name;
        this.rank = rank;
        this.minNumberOfReservations = minNumberOfReservations;
        this.maxNumberOfReservations = maxNumberOfReservations;
        this.discount = discount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMinNumberOfReservations() {
        return minNumberOfReservations;
    }

    public void setMinNumberOfReservations(Integer minNumberOfReservations) {
        this.minNumberOfReservations = minNumberOfReservations;
    }

    public Integer getMaxNumberOfReservations() {
        return maxNumberOfReservations;
    }

    public void setMaxNumberOfReservations(Integer maxNumberOfReservations) {
        this.maxNumberOfReservations = maxNumberOfReservations;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
