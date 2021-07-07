package com.jasche.notetoself.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Data structure for User.
 *
 * id - unique String for identifying a Note
 * name - String containing name of User
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    private String id;
    private String name;
}