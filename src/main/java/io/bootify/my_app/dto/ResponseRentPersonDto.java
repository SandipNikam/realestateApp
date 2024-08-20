package io.bootify.my_app.dto;

import lombok.Data;

import java.util.List;
@Data
public class ResponseRentPersonDto {

    private String message;
    private List<RentPersonDto> list;
    private String exception;
    private int totalPages;
    public ResponseRentPersonDto(String message){
        this.message=message;
    }

    public ResponseRentPersonDto(String message, List<RentPersonDto> list, int totalPages) {
        this.message =message;
        this.list = list;
        this.totalPages = totalPages;
    }
}
