package Locators;

public interface MyLocators {
String searhBox="//input[@placeholder='Search']";
String searchButton="//i[@class='fa fa-search']";
String searchPruductList="//a//ancestor::div[@class=\"product-thumb\"]";


String usernameInput="//input[contains(@placeholder,'Username')]";
String passwordInput="//input[contains(@placeholder,'Password')]";
String loginButton="//button[contains(.,'Login')]";

String adminButton="//a[contains(.,'Admin')]";
String adminPageUsernameInput="//div[@class=\"oxd-form-row\"]//input[contains(@class,'oxd-input oxd-')]";





}
