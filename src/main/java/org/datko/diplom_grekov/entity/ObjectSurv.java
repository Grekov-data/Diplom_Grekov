package org.datko.diplom_grekov.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Data
@Entity
@Table(name = "object_surv_t")
public class ObjectSurv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name_f", nullable = false)
    private String name;                                    //название объекта для опроса

    @Column(name = "rating_f", nullable = false)              //рейтинг объекта
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "survey_id", nullable = false)
    private Survey survey;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObjectSurv that = (ObjectSurv) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(rating, that.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, rating);
    }
}
