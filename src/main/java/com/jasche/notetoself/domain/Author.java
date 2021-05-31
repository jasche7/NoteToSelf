package com.jasche.notetoself.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name="author")
@Data
public class Author {

    @Id
    @GeneratedValue
    private long id;

    @NonNull
    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Set<Note> notes;
}