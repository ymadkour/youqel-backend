package com.youqel.server.domain;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
public abstract class AbstractBaseEntity {

    @Getter
    @Setter
    @Id
    private String id;

    // optimistic locking currently not working - see CalvProductOpLockJobTest
    @Getter
    @Setter
    @Version
    private Long version;

    // note, that createdTimestamp gets updated from CalvProjectItemWriter via reflection
    @Column(name = "created_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdTimestamp;

    @Column(name = "updated_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedTimestamp;

    public AbstractBaseEntity() {
        this(UUID.randomUUID().toString());
    }

    public AbstractBaseEntity(final String id) {
        this.id = id;
    }


    @Override
    public int hashCode() {
        return id.hashCode();
    }


    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof AbstractBaseEntity)) {
            return false;
        }
        return getId().equals(((AbstractBaseEntity) obj).getId());
    }

    @PreUpdate
    @PrePersist
    public void updateTimeStamps() {
        updatedTimestamp = new Date();
        if (createdTimestamp == null) {
            createdTimestamp = updatedTimestamp;
        }
    }

    public Date getCreatedTimestamp() {
        return createdTimestamp == null ? null : new Date(createdTimestamp.getTime());
    }

    public Date getUpdatedTimestamp() {
        return updatedTimestamp == null ? null : new Date(updatedTimestamp.getTime());
    }

}
