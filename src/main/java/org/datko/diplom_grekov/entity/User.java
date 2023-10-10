package org.datko.diplom_grekov.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "user_t")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_f", nullable = false)
    private String name;                            //ФИО

    @Column(name = "age_f", nullable = false)
    private Integer age;                            //возраст

    @Column(name = "gender_f", nullable = false)
    private String gender;                          //пол (муж./жен.)

    @Column(name = "email_f", nullable = false)
    private String email;                           //эл.почта

    /*@Column(name = "completedSurv_f", nullable = false)
    private ArrayList<CompletedSurv> completedSurv = new ArrayList<CompletedSurv>();*/

    @Column(name = "registrated_date_f", nullable = false)
    private Date registrationDate;

    /*private String getFormattedGender() {
        String genderString;
        if (gender == true) {
            genderString = "мужской";
        }
        else {
            genderString = "женский";
        }
        return genderString;
    }*/

    //Добавить после Spring Security
    /*@Column(name = "password_f", nullable = false)
    private String password;                        //пароль*/

    ////изучить - не так написано
    /*@ManyToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Survey> survey;*/                   //список опросов, которые прошёл пользователь
}
