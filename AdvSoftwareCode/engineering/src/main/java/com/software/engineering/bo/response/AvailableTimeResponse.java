package com.software.engineering.bo.response;

import lombok.*;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AvailableTimeResponse {

    private List<LocalTime> availableDates;
}
