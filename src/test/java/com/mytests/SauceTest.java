package com.mytests;

import Pages.InventoryPage;
import Pages.LoginPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.swing.*;

public class SauceTest extends BrowserStackBaseTest {
	LoginPage loginPage= new LoginPage(driver);
	InventoryPage inventoryPage=new InventoryPage(driver);



	@Test(priority = 1)
	public void checkInventoryItemTest() {
		loginPage.doLogin();
		Assert.assertTrue(inventoryPage.returnInventorySize() == 6);
	}

	@Test(priority = 2)
	public void checkAddToCartButtonTest() {
		loginPage.doLogin();
		Assert.assertTrue(inventoryPage.returnaddToCartSize() == 6);
	}

}
