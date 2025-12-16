package org.example.workshop.dto.cncMachine;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginationCncMachineResponse {
    private List<CncMachineShortResponse> list;
    private int totalPages;
}
