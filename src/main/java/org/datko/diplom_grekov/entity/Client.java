package org.datko.diplom_grekov.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;                            //ФИО

    @Column(name = "age", nullable = false)
    private Integer age;                            //возраст

    @Column(name = "gender", nullable = false)
    private String gender;                          //пол (муж./жен.)

    @Column(name = "email", nullable = false)
    private String email;                           //эл.почта

    /*@Column(name = "completedSurv_f", nullable = false)
    private ArrayList<CompletedSurv> completedSurv = new ArrayList<CompletedSurv>();*/

    @Column(name = "registrated_date", nullable = false)
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
    /*@ManyToMany(mappedBy = "client")
    @JsonIgnore
    private Set<Survey> survey;*/                   //список опросов, которые прошёл пользователь
}
