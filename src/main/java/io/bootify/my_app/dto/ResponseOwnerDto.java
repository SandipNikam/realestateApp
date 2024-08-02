package io.bootify.my_app.dto;

import lombok.Data;

import java.util.List;
@Data
public class ResponseOwnerDto {
    private String message;
    private List<PropertyOwnerDto> list;
    private String exception;
    private int totalPages;
    public ResponseOwnerDto(String message){
        this.message=message;
    }

    public ResponseOwnerDto(String message, List<PropertyOwnerDto> list, int totalPages) {
        this.message =message;
        this.list = list;
        this.totalPages = totalPages;
    }
}
