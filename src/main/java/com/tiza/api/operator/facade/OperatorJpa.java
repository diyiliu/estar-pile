package com.tiza.api.operator.facade;

import com.tiza.api.operator.dto.Operator;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description: OperatorJpa
 * Author: DIYILIU
 * Update: 2018-05-03 11:05
 */
public interface OperatorJpa extends JpaRepository<Operator, Long> {

    Operator findByOperatorId(String operatorId);
}
