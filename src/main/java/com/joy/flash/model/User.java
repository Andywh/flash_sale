package com.joy.flash.model;

import lombok.Data;

/**
 * Created by SongLiang on 2019-09-09
 */
@Data
public class User {

    private int id;

    private String name;

    public User() {
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }

}
