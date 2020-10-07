package com.bank.model;

import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client{
    private Integer id;
    private String name;
    private String email;
    private Date registered = new Date();
    private List<Account> accounts;



}
