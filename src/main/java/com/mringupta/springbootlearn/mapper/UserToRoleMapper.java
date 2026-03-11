package com.mringupta.springbootlearn.mapper;

import com.mringupta.springbootlearn.model.Role;
import com.mringupta.springbootlearn.model.User;

public class UserToRoleMapper {
    public static Role mapToRole(User user) {
        return Role.builder()
                .rolePermission(user.getRolePermission())
                .roleDesc(user.getRoleDesc())
                .roleID(user.getRoleID())
                .build();
    }
}
