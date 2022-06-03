package uz.pdp.task4.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private Long id;
    private String name;
    private String email;
    private Timestamp registrationDate;
    private Timestamp lastLoginDate;
    private boolean isEnabled;
}
