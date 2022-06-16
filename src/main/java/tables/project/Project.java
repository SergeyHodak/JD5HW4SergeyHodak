package tables.project;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Project {
    private long id;
    private String name;
    private long companyId;
    private long customerId;
    private double cost;
    private LocalDate creationDate;
}