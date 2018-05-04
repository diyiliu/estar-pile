package com.tiza.api.station.facade;

import com.tiza.api.station.dto.ConnectorStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Description: ConnectorStatusJpa
 * Author: DIYILIU
 * Update: 2018-05-03 17:08
 */

public interface ConnectorStatusJpa extends JpaRepository<ConnectorStatus, Long> {


}
