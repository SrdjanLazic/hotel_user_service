package com.raf.hoteluserservice.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ClientRankUpdateDto {

    @NotNull
    private Integer minNumberOfReservations;
    @NotNull
    private Integer maxNumberOfReservations;
    @NotNull
    private Double discount;

    public ClientRankUpdateDto() {
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
}
