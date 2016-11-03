package jp.ne.egotaku.scalascraping

/**
  * Created by satoutakuya on 2016/10/16.
  */
import java.io.File

import scala.collection.JavaConversions._
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import scalax.io._

class ScrapeService {
  var sequence: Int = 1
  def request(url: String): Unit = {
    val driver = new HtmlUnitDriver()
    driver.get(url)
    scrape(driver)
  }

  private def scrape(driver: HtmlUnitDriver): Unit = {
    val res = driver.findElementsByTagName("img")

    res.map(e => {
      val imgUrl = e.getAttribute("src")
      if (imgUrl.startsWith("http://")) save(imgUrl)
    })
  }

  private def save(url: String) = {
    val data = Resource.fromURL(url).byteArray
    val file = new File(s"/Users/satoutakuya/testimage/$sequence")
    sequence += 1
    Resource.fromFile(file).write(data)
  }
}
