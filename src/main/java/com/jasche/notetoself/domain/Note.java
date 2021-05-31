package com.jasche.notetoself.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Note {

    @Id
    @GeneratedValue
    private Long id;
    private Instant dateCreated;
    private String title;
    private String text;

}
