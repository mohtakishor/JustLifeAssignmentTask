Feature: Booking a home cleaning service

  Scenario Outline: Create a booking with given details
    Given I have launched the application Successfully
    When I create a booking with "<Booking Type>", "<Location>", "<Material>", "<Cleaners>", "<Hours>", "<Cleaners Selection>", "<Date Selection>"
    And I logged in the Application
    And I have successfully did the payment with as "<Payment Method>"
    Then The booking should be created successfully

    Examples:
      | Booking Type | Location     | Material | Cleaners | Hours | Payment Method | Cleaners Selection | Date Selection |
      | One Time     | Dubai Marina | Yes      | 1        | 2     | Google Pay     | Yes                | Yes            |
      | Weekly       | Dubai Marina | No       | 2        | 2     | Google Pay     | No                 | No             |