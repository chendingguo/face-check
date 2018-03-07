package com.mtime.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserInfo extends BaseModel{

	private String id;
    private String name;
    private String image;
    
}
