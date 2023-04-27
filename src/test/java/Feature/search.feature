Feature: Search Func

  Scenario: add a product
    Given user on homepage
    Given user search for "mac"
    Given nagita to "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index"
    And should be succest login
