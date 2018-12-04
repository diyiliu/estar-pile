package com.tiza.api.charge.facade;

import com.tiza.api.charge.dto.ChargeOrder;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description: ChargeOrderJpa
 * Author: DIYILIU
 * Update: 2018-12-03 10:19
 */
public interface ChargeOrderJpa extends JpaRepository<ChargeOrder, Long> {


    ChargeOrder findByStartChargeSeqAndConnector(String orderSeq, long connector);
}
