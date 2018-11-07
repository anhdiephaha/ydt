package com.ydt.entity;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "profile")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @NotBlank
    @Size(max = 125)
    private String name;

    @NotBlank
    private String arr_access;

    public Profile() {
    }

    public Profile(@NotBlank @Size(max = 125) String name, @NotBlank String arr_access) {
        this.name = name;
        this.arr_access = arr_access;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArr_access() {
        return arr_access;
    }

    public void setArr_access(String arr_access) {
        this.arr_access = arr_access;
    }
}
