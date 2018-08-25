package com.youqel.server.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
@Entity(name = "ContactInformation")
@Table(name = "contact_information")
public class ContactInformationEntity extends AbstractBaseEntity {

    @Column
    private String name;

    @Column
    private String mobile;

    @Column
    private String company;

}
