package com.yc.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
// 邮件实体类
public class Email {

    private Integer code;

    private String fromemail;

    private String to;

    private String subject;

    private String sendTime;

}
