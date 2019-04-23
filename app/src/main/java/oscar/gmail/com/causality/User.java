package oscar.gmail.com.causality;

import android.arch.persistence.room.Entity;

import java.util.List;

@Entity
public class User {

    private String firstName;
    private String sureName;
    private List<Question> questions;
    //etc
}
