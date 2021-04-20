Feature: Tariff

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