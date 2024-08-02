package io.bootify.my_app.dto;

import lombok.Data;

import java.util.List;
@Data
public class ResponsePropertyDto {
    private String message;
    private List<PropertyDto> list;
    private String exception;
    private int totalPages;
    public ResponsePropertyDto(String message){
        this.message=message;
    }

    public ResponsePropertyDto(String message, List<PropertyDto> list, int totalPages) {
        this.message =message;
        this.list = list;
        this.totalPages = totalPages;
    }
}
