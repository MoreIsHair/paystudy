package com.yy.system.model.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author YY
 * @date 2019/12/5
 * @description
 */
@Data
public class SystemUser implements Serializable {
    private static final long serialVersionUID = -7938512178301821761L;
    private Long userId;
    private String username;
    private String password;
}
