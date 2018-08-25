package com.youqel.server.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.youqel.server.dto.PickupRequestDTO;
import com.youqel.server.service.PickupRequestService;

import io.swagger.annotations.Api;

@CrossOrigin(origins = "*")
@RestController
@Api(value = "Youqel API", tags = "Youqel Rest Layer API")
@RequestMapping(value = "/api/pickup-request")
public class PickupRequestController {
	private static final Logger LOG = LoggerFactory.getLogger(PickupRequestController.class);

	@Autowired
	private PickupRequestService pickupRequestService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public void createPickupRequest(@RequestBody final PickupRequestDTO pickupRequest) {

		LOG.info("Received new pickup request");

		pickupRequestService.save(pickupRequest);

	}
}
