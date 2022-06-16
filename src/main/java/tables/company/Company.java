package tables.company;

import lombok.Data;

@Data
public class Company {
    private long id;
    private String name;
    private String description;
}