package org.example.workshop.dto.cncMachine;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvBindByPosition;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CncMachineCsvToWriteDto {
    @CsvBindByPosition(position = 0)
    @CsvBindByName(column = "Height")
    private Double height;
    @CsvBindByPosition(position = 1)
    @CsvBindByName(column = "Width")
    private Double width;
    @CsvBindByPosition(position = 2)
    @CsvBindByName(column = "Length")
    private Double length;
    @CsvBindByPosition(position = 3)
    @CsvBindByName(column = "Weight")
    private Double weight;
    @CsvBindByPosition(position = 4)
    @CsvBindByName(column = "Maximum Power Consumption")
    private Integer maxPowerConsumption;
    @CsvBindByPosition(position = 5)
    @CsvBindByName(column = "Voltage")
    private Integer voltage;
}
