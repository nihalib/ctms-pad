Feature: CRUD operation for Tariff Service

  @Tariff
  Scenario: Get Tariff Details
    When User request for tariff details
    Then service should handle and return success status

  @TariffPerLocation
  Scenario Outline: Get Tariff Details for given location <location>
    When User request tariff details for given <location>
    Then service should handle and return response <status>
    Examples:
    | location | status |
    | DUBAI    | 404    |
    | INDIA    | 404    |

  @AddTariff
  Scenario Outline: Add Tariff Details for given location <location>
    Given User wants to add new tariff details for given <location> with <price>
    And With the <currencyCode>
    When User makes a request
    Then service should handle and validate response
    Examples:
    | location | price | currencyCode |
    | DUBAI    | 15.00 | AED          |
    | INDIA    | 93.33 | INR          |

    @AddNewStation
    Scenario Outline: Add new station details for given <stationCode>
      Given User wants to add new location for given <stationCode>
      And details such as <location> and <price> and <currencyCode>
      When User makes a request with above details
      Then service should persist data and send valid response
      Examples:
      | stationCode | location | price | currencyCode |
      | qwe123      | India    | 93.33 | INR          |
      | asd456      | DUBAI    | 15.03 | AED          |