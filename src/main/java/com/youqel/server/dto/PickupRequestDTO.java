package com.youqel.server.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PickupRequestDTO {

    private String pickupLocation;

    private String dropoffLocation;

    private Date pickupDate;

    private String pickupTruckType;

    private String details;

    private ContactInformationDTO shipper;

    private ContactInformationDTO receipient;

    public Date getPickupDate() {
        return pickupDate == null ? null : new Date(pickupDate.getTime());
    }

    public void setPickupDate(final Date pickupDate) {
        this.pickupDate = pickupDate == null ? null : new Date(pickupDate.getTime());
    }
}
