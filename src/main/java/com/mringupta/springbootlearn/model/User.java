package com.mringupta.springbootlearn.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private String id;
    private int roleID;
    private String roleDesc;
    private String rolePermission;
}
