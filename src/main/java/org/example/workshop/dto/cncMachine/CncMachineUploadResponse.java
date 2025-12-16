package org.example.workshop.dto.cncMachine;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CncMachineUploadResponse {
    private int written;
    private int unwritten;
}
