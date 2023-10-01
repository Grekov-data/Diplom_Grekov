package org.datko.diplom_grekov.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "object_surv_t")
public class ObjectSurv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_f", nullable = false)
    private String name;                                    //название объекта для опроса

    /*@ManyToOne
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;*/

    //добавить рейтинг
}
