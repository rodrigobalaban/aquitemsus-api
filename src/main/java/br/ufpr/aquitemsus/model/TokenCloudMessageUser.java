package br.ufpr.aquitemsus.model;

import javax.persistence.*;

@Entity
public class TokenCloudMessageUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private UserSus userSus;

    @Column
    private String token;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserSus getUserSus() {
        return userSus;
    }

    public void setUserSus(UserSus userSus) {
        this.userSus = userSus;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
