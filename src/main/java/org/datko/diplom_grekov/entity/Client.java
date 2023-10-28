package org.datko.diplom_grekov.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@Data
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;                            //ФИО

    @Column(name = "gender", nullable = false)
    private String gender;                          //пол (муж./жен.)

    @Column(name = "registrated_date", nullable = false)
    private Date registrationDate;

    public String getFormattedRegistrationDate() {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        return formatter.format(registrationDate);
    }

    @ManyToMany
    private Set<Survey> completedSurveys;

}
