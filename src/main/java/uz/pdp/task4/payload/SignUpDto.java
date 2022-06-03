package uz.pdp.task4.payload;


import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpDto {

    private String name;
    private String email;
    private String password;
    private String confirmPassword;
}
