package uz.pdp.task4.model;


import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Timestamp lastLoginTime;
    @Column(nullable = false)
    private Timestamp registrationTime;
    @Column(nullable = false)
    private boolean isEnabled;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public User(String name, String email, String password, Timestamp lastLoginTime, Timestamp registrationTime, boolean isEnabled) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.lastLoginTime = lastLoginTime;
        this.registrationTime = registrationTime;
        this.isEnabled = isEnabled;
    }
}
