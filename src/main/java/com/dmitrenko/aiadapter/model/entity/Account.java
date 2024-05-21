package com.dmitrenko.aiadapter.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.LinkedList;
import java.util.List;

@Entity
@Data
@Accessors(chain = true)
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"stations", "profiles"})
@ToString(callSuper = true, exclude = {"stations", "profiles"})
@Table(name = "account")
public class Account extends BaseEntity {

    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private List<Station> stations = new LinkedList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "account")
    private List<Profile> profiles = new LinkedList<>();
}
