package com.tiza.api.station.facade;

import com.tiza.api.station.dto.Connector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Description: ConnectorJpa
 * Author: DIYILIU
 * Update: 2018-05-03 17:31
 */
public interface ConnectorJpa extends JpaRepository<Connector, Long> {

    @Query(value = "SELECT * FROM bs_chargeconnector c" +
            "  JOIN bs_chargepile p ON c.pileid = p.id" +
            "  JOIN bs_chargestation s ON p.stationid = s.id" +
            " WHERE connectorid = ?1 AND s.operatorid = ?2", nativeQuery = true)
    Connector findByConnectorIDAndOperatorID(String connectorID, String operatorID);
}
