package com.youqel.server.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youqel.server.domain.ContactInformationEntity;
import com.youqel.server.domain.PickupRequestEntity;
import com.youqel.server.dto.ContactInformationDTO;
import com.youqel.server.dto.PickupRequestDTO;
import com.youqel.server.repository.PickupRequestRepository;

@Service
public class PickupRequestService {

    @Autowired
    private PickupRequestRepository pickupRequestRepository;

    @Autowired
    private MailService mailService;

    public List<PickupRequestEntity> listAllPickupRequests() {
        return pickupRequestRepository.listAllPickupRequests();
    }

    private void notifyNewRequest(final PickupRequestEntity request) {
        mailService.sendNewPickupRequestNotificaiton(request);
    }

    public void save(final PickupRequestEntity request) {
        pickupRequestRepository.save(request);
    }


    public void save(final PickupRequestDTO pickupRequest) {
        final PickupRequestEntity request = buildPickupRequest(pickupRequest);
        save(request);
        notifyNewRequest(request);

    }

    private PickupRequestEntity buildPickupRequest(final PickupRequestDTO pickupRequest) {
        return PickupRequestEntity.builder()
                .details(pickupRequest.getDetails())
                .pickupLocation(pickupRequest.getPickupLocation())
                .dropoffLocation(pickupRequest.getDropoffLocation())
                .pickupDate(pickupRequest.getPickupDate())
                .shipper(buildContactInformation(pickupRequest.getShipper()))
                .pickupTruckType(pickupRequest.getPickupTruckType())
                .receipient(buildContactInformation(pickupRequest.getReceipient())).build();
    }

    private ContactInformationEntity buildContactInformation(
            final ContactInformationDTO infomration) {
        return ContactInformationEntity.builder().name(infomration.getName())
                .mobile(infomration.getMobile()).company(infomration.getCompany()).build();
    }
}
