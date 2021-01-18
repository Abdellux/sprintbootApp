package com.example.osa.entity;
 
import javax.persistence.*;



@Entity(name = "UserEntity")
@Table(name="users")
public class UserEntity {
    
    @Id
    @Column(name = "Id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @GeneratedValue
    private long id;

    @Column(name = "USERNAME", nullable = false, unique = true)
    private String username;
    
    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    public UserEntity(){}
    public UserEntity(String username, String email, String password) {
        this.email = email;
        this.username = username;
        this.password = password; 
    }
    public void setId( long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setUsername( String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail( String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword( String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
