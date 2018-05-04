package com.tiza.api.operator.dto;

import lombok.Data;

import javax.persistence.*;

/**
 * Description: Operator
 * Author: DIYILIU
 * Update: 2017-12-06 10:25
 */

@Data
@Entity
@Table(name = "BS_OPERATOR")
public class Operator {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operator_seq")
    @SequenceGenerator(name="operator_seq", sequenceName = "sq_bs_operator", allocationSize = 1)
    private Long id;

    private String operatorId;

    private String name;

    private String operatorSecret;

    private String dataSecret;

    private String dataSecretIv;

    private String sigSecret;
}
