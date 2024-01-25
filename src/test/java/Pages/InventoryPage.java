package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InventoryPage {
    private WebDriver driver;
    public InventoryPage(WebDriver driver){
        this.driver=driver;
        PageFactory.initElements(driver,this);
    }
//    @FindBy(css = ".inventory_item")
//    private WebElement invetoryList;
//
    public int returnInventorySize(){
        return driver.findElements(By.cssSelector(".inventory_item")).size();
    }

    public int returnaddToCartSize(){
        return driver.findElements(By.xpath("//button[text()='ADD TO CART']")).size();
    }
}
