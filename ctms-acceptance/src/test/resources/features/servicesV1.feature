Feature: CRUD operation for Tariff Service

  @Tariff
  Scenario: Get Tariff Details
    When User request for tariff details
    Then service should handle and return notfound status

  @AddStation
  Scenario Outline: Add Station without tariff
    Given User wants to add new station with <stationId>
    And station contains <providerId> and <chargingModes>
    When user makes a request with station details
    Then service should return <status>
    Examples:
    | stationId | providerId | chargingModes | status |
    | 0011010   | DCS        | AC1           | 200    |
    | 1234567   | CPI        | DC            | 200    |

  @AddStation
  Scenario Outline: Add Station with tariff details
    Given User wants to add new station with <stationId>
    And station contains <providerId> and <chargingModes>
    And contains tariff details with <stationId>
    When user makes a request with station details
    Then service should return <status>
    Examples:
      | stationId | providerId | chargingModes | status |
      | 1987654   | DCS        | AC1           | 200    |
      | 3456789   | CPI        | DC            | 200    |

  @GetStation
  Scenario: Get Station details
    When User requests station details
    Then service should provide station details

  @AddTariff
  Scenario Outline: Add Tariff details to existing station
    Given Tariff details available with <stationId>
    And Tariff <price> for <cityCode>
    When User makes request with tariff details
    Then service should add tariff details
    Examples:
    | stationId | cityCode | price  |
    | 0011010   | BLR      | 110.00 |
    | 1234567   | HYD      | 95.10  |
    | 1987654   | BOM      | 98.15  |
    | 3456789   | COK      | 88.10  |

  @GetTariff
  Scenario: Get Tariff details
    When User requests for tariff details
    Then service should provide tariff details