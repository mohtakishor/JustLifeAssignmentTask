Feature: Booking a home cleaning service

  Scenario Outline: Create a booking with given details
    Given I have launched the application Successfully
    When I create a booking with "<Booking Type>", "<Location>", "<Material>", "<Cleaners>", "<Hours>", "<Cleaners Selection>"
    And I logged in the Application
    And I have successfully did the "<Payment>"
    Then The booking should be created successfully

    Examples:
      | Booking Type | Location     | Material | Cleaners | Hours | Payment | Cleaners Selection |
      | Weekly       | Dubai Marina | Yes      | 1        | 2     | Cash    | Yes                |
      | One Time     | Dubai Marina | No       | 2        | 2     | Cash    | No                 |