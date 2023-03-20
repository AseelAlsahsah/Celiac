package com.software.engineering.controllers;

import com.software.engineering.bo.request.CreateClinicRequest;
import com.software.engineering.bo.response.ClinicResponse;
import com.software.engineering.services.ClinicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clinics")
public class ClinicController {

    private final ClinicService clinicService;

    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @PostMapping
    public ResponseEntity<ClinicResponse> createClinic(@RequestBody CreateClinicRequest request) {
        return new ResponseEntity<>(clinicService.createClinic(request), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ClinicResponse>> viewClinics() {
        return new ResponseEntity<>(clinicService.viewClinics(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ClinicResponse>> searchClinic(@RequestParam String name) {
        return new ResponseEntity<>(clinicService.searchClinics(name), HttpStatus.OK);
    }
}
