package com.youqel.server.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "PickupRequest")
@Table(name = "pickup_request")
public class PickupRequestEntity extends AbstractBaseEntity {

    @Column(name = "pick_up_location")
    private String pickupLocation;

    @Column(name = "drop_off_location")
    private String dropoffLocation;

    @Column(name = "pick_up_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pickupDate;

    @Column
    private String pickupTruckType;

    @Column
    private String details;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_shipper_id")
    private ContactInformationEntity shipper;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "fk_receipient_id")
    private ContactInformationEntity receipient;

    public Date getPickupDate() {
        return pickupDate == null ? null : new Date(pickupDate.getTime());
    }

    public void setPickupDate(final Date pickupDate) {
        this.pickupDate = pickupDate == null ? null : new Date(pickupDate.getTime());
    }

}
