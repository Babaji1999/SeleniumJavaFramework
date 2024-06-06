
@tag
Feature: Purchase the order from Ecommerce Website
  I want to use this template for my feature file

Background:
Given I had landed on Ecommerce page
  
  @tag2
  Scenario Outline: Title of your scenario outline
    Given Logged in with username <name> and password <password>
    When I add product <productName> to Cart 
    And Checkout <productname> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on ConfirmationPage

    Examples: 
      | name								 | password 	| productName 		|
      | chintulala@gmail.com | Chintu@123 | ADIDAS ORIGINAL	|
