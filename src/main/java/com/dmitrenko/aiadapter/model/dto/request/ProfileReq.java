package com.dmitrenko.aiadapter.model.dto.request;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ProfileReq {
    private String firstName;
    private String lastName;
}
