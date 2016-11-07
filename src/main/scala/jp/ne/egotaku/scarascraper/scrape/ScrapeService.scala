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
      case None => List.empty[File]
    }
  }
  def download(list: List[Element]) = {
    //println(list)
    //list.foreach(e => if (e.attr("src").startsWith("https://")) save(e.attr("src")))
    for {
      e <- list
      if (e.attr("src").startsWith("http://"))
    } yield {
      save(e.attr("src"))
    }
  }

  private def save(url: String) = {
    //println(url)
    val data = Resource.fromURL(url).byteArray
    val file = new File(s"/Users/takuya_st/testimage/$sequence")
    sequence += 1
    Resource.fromFile(file).write(data)
    file
  }
}
