Feature: CRUD operation for Tariff Service

  @Tariff
  Scenario: Get Tariff Details
    When User request for tariff details
    Then service should handle and return notfound status

  @TariffPerLocation
  Scenario Outline: Get Tariff Details for given location <location>
    When User request tariff details for given <location>
    Then service should handle and return response <status>
    Examples:
    | location | status |
    | DUBAI    | 404    |
    | INDIA    | 404    |

  @AddTariff
  Scenario Outline: Add Tariff Details for given location <stationCode>
    Given User wants to add new tariff details for given <stationCode> with <price>
    And With the <currencyCode>
    When User makes a request
    Then service should handle and validate response
    Examples:
    | stationCode | price | currencyCode |
    | DUBAI    | 15.00 | AED          |
    | INDIA    | 93.33 | INR          |

    @AddNewStation
    Scenario Outline: Add new station details for given <location>
      Given User wants to add new location for given <location>
      And details such as <stationCode> and <price> and <currencyCode>
      When User makes a request with above details
      Then service should persist data and send valid response
      Examples:
      | location   | stationCode | price | currencyCode |
      | India      | qwert       | 93.33 | INR          |
      | DUBAI      | asdfg       | 15.03 | AED          |