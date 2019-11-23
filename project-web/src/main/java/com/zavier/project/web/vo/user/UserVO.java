package com.zavier.project.web.vo.user;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class UserVO {
    @NotNull
    @Size(min = 3, max = 12)
    private String userName;

    @NotNull
    @Size(min = 6, max = 20)
    private String password;
}
