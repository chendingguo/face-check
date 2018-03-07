package com.mtime.demo.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class User  extends BaseModel{

	private int id;
    private String name;
    private Integer age;
    private String password;
    
}
