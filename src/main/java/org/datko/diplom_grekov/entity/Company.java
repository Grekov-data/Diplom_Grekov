package org.datko.diplom_grekov.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "company")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;                                            //название

    @Column(name = "website")
    private String website;                                         //веб-сайт

    @Column(name = "email_f", nullable = false)
    private String email;                                           //эл.почта

    @OneToMany(mappedBy = "company")
    private Set<Survey> surveys;

}
