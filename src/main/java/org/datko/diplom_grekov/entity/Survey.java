package org.datko.diplom_grekov.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;
import java.util.Set;

@Data
@Entity
@Table(name = "survey_t")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_f", nullable = false)
    private String name;                                    //название опроса

    @Column(name = "description_f")
    private String description;                             //описание

    @Column(name = "field_f", nullable = false)
    private String field;                                   //сфера для опроса

    @Column(name = "is_active_f", nullable = false)
    private Boolean isActive;                               //активен ли опрос

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;                                //компания, создавшая опрос

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Survey survey = (Survey) o;
        return Objects.equals(id, survey.id) && Objects.equals(name, survey.name) && Objects.equals(isActive, survey.isActive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, isActive);
    }

    @OneToMany(mappedBy = "survey")
    private Set<ObjectSurv> objectSurvs;

    //добавить зависимость с той компанией, которая создала опрос

    //Добавить список объектов (предметы для вопроса) из другой таблицы при создании этого объекта

    //Добавить кол-во прошедших опрос???
}
