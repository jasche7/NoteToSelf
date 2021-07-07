package com.jasche.notetoself.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.Instant;

/**
 * Data structure for Note.
 *
 * id - unique Long for identifying a Note; assigned automatically in incremental order
 * dateCreated - date of Note's creation in Instant format
 * title - String representing title of Note
 * text - String representing contents of Note
 * user - User who created this Note
 */
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
    @ManyToOne(cascade= CascadeType.PERSIST)
    private User user;

}