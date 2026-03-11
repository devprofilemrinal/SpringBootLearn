package com.mringupta.springbootlearn.model;

import lombok.Builder;

@Builder
public record Role(int roleID, String roleDesc, String rolePermission) {
}
