package org.datko.diplom_grekov.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "survey_t")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_f", nullable = false)
    private String name;                                    //название опроса


    //добавить зависимость с той компанией, которая создала опрос

    //Добавить список объектов (предметы для вопроса) из другой таблицы при создании этого объекта

    //Добавить кол-во прошедших опрос???
}
