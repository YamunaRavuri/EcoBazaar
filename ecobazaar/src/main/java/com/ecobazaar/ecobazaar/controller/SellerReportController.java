package com.ecobazaar.ecobazaar.controller;


import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import com.ecobazaar.ecobazaar.dto.SellerReport;

import com.ecobazaar.ecobazaar.service.SellerReportService;


@RestController

@RequestMapping("/api/reports")

public class SellerReportController {


private final SellerReportService sellerReportService;


public SellerReportController(SellerReportService sellerReportService) {

this.sellerReportService = sellerReportService;

}


@PreAuthorize("hasRole('SELLER')")

@GetMapping("/seller")

public SellerReport getSellerReport(Authentication auth) {

String email = auth.getName();

return sellerReportService.getSellerReport(email);

}

}