package jp.ne.egotaku.scarascraper.scrape

/**
  * Created by satoutakuya on 2016/10/17.
  */

import java.io.File

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Element
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._

import scala.concurrent.{ExecutionContext, Future}
import scalax.io.Resource

class ScrapeImpl {
  val browser = JsoupBrowser()
  var sequence = 1
  implicit val ec: ExecutionContext = ExecutionContext.Implicits.global
  def scrape(url: String) = Future {
    val doc = browser.get(url)
    val images = doc >?> elementList("img")
    images match {
      case Some(l) => download(l)
      case None => println("nothing")
    }
  }
  def download(list: List[Element]) = {
    println(list.length)
    val result = list.map(e => {
      if (e.attr("src").startsWith("https://"))
        save(e.attr("src"))
    })

  }

  private def save(url: String) = {
    val data = Resource.fromURL(url).byteArray
    val file = new File(s"/Users/satoutakuya/testimage/$sequence")
    sequence += 1
    Resource.fromFile(file).write(data)
  }
}
