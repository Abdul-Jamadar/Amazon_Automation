Feature: Login to Amazon and check a smartphones cost after applying buy with exchange offer.

Background: 
Given I am on Amazon homepage "https://www.amazon.in/" and  I am already a registered user.

Scenario Outline: Login to Amazon page and get a phones final price after applying exchange offer.
When I login using my phone number and password
Then My username should be visible.
Then I search for smartphone "<smartphone>" and navigate to that products page.
When I apply exchange offer for my smartphone model and IMEI number
Then I should be able to see the final price.
Then I should be able get the most profitable purchase with highest exchange price

Examples:

|			  smartphone					|
|Redmi Note 11T 5G					|
|Oppo F21s Pro 5G						|
|Samsung Galaxy S22 Ultra   |
|OnePlus 11R 5G							|
|iQOO 11 5G									|
|OnePlus Nord 2T 5G					|
|realme Narzo 50 Pro 5G			|


