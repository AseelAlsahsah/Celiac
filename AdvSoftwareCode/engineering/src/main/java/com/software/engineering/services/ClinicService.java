package com.software.engineering.services;

import com.software.engineering.bo.request.CreateClinicRequest;
import com.software.engineering.bo.response.ClinicResponse;
import java.util.List;

public interface ClinicService {

    ClinicResponse createClinic(CreateClinicRequest request);

    List<ClinicResponse> viewClinics();

    List<ClinicResponse> searchClinics(String name);
}
