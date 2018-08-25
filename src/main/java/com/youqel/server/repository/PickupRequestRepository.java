package com.youqel.server.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.youqel.server.domain.PickupRequestEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class PickupRequestRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(final PickupRequestEntity request) {
        entityManager.merge(request);
        log.info("Saved pick up request with id " + request.getId());
    }

    public List<PickupRequestEntity> listAllPickupRequests() {
        log.info("Listing all pick up requests...");
        return entityManager
                .createQuery("select pir from PickupRequest pir order by createdTimestamp desc",
                        PickupRequestEntity.class)
                .getResultList();
    }
}
