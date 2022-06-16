package tables.customer;

import lombok.Data;

@Data
public class Customer {
    private long id;
    private String firstName;
    private String secondName;
    private int age;
}