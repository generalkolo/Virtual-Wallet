package com.semanienterprise.virtualwallet.local.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by edetebenezer on 05/12/2019
 **/

@Entity
@Getter
@Setter
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String userName;
    public Double accountBalance;
    public String password;
}
