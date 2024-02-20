package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "username")
    @NotEmpty(message = "Name is not be Empty")
    @Size(min = 2, max = 20, message = "Name should be between 2 and 20 characters ")
    private String userName;


    @Column(name = "password")
    @NotEmpty(message = "password shoudl not be Empty")
    @Size(min = 2, max = 20, message = "password shoudl be between 2 and 20 characters")
    private String password;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))

    private List<Role> roles;
    public User() {
    }


    public User(String userName, String password, List<Role> roles) {
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override  // возвращаем права пользователя
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(r->new SimpleGrantedAuthority(r.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @Override  //аккаунт не просрочен. можно проверять время жизни аккаунта для повторной авторизации
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override  // аккаунт не заблокирован
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override  // пароль не просрочен
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override  // аккаунт включен
    public boolean isEnabled() {
        return true;
    }
}
