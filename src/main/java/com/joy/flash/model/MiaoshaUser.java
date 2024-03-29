package com.joy.flash.model;

import lombok.Data;

import java.util.Date;

/**
 * Created by SongLiang on 2019-09-10
 */
@Data
public class MiaoshaUser {

    private Long id;

    private String nickname;

    private String password;

    private String salt;

    private String head;

    private Date registerDate;

    private Date lastLoginDate;

    private Integer loginCount;

}
