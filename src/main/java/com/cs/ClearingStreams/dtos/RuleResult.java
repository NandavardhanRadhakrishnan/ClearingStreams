package com.cs.ClearingStreams.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;

@AllArgsConstructor
@NoArgsConstructor
public class RuleResult<T> {

    private boolean success;
    private T data;
    private String message;


    public static <T> RuleResult<T> pass(T data){
        return new RuleResult<>(true, data, null);
    }

    public static <T> RuleResult<T> pass(){
        return RuleResult.pass(null);
    }

    public static <T> RuleResult<T> fail(T data, String message){
        return new RuleResult<>(false, data, message);
    }

    public static <T> RuleResult<T> fail(T data){
        return RuleResult.fail(data, null);
    }

    public static <T> RuleResult<T> fail(String message){
        return RuleResult.fail(null ,message);
    }

}
