package jp.ne.egotaku.scarascraper.scrape

/**
  * Created by satoutakuya on 2016/10/17.
  */

import java.io.{File, IOException}

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.model.Element
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._

import scala.sys.process._
import javax.imageio.ImageIO

import scala.concurrent.{ExecutionContext, Future}
import scalax.io.Resource

class ScrapeImpl {
  val browser = JsoupBrowser()
  var sequence = 1
  implicit val ec: ExecutionContext = ExecutionContext.Implicits.global
  def scrape(url: String) = {
    val doc = browser.get(url)
    val images = doc >?> elementList("img")
    images match {
      case Some(l) => download(l)
      case None => println("nothing")
    }
  }

  def download(list: List[Element]) = {
    val result = list.map(e => {
      if (e.attr("src").startsWith("http://")) {
        val file = save(e.attr("src"))
        if (imageSizeCheck(file)) {
          moveImage(file)
        }
      }
    })

  }

  private def save(url: String) = {
    val data = Resource.fromURL(url).byteArray
    val file = new File(s"/Users/takuya_st/testimage/$sequence")
    sequence += 1
    Resource.fromFile(file).write(data)
    file
  }

  def moveImage(file: File) = {
    val path = file.getPath()
    Process("mv " + path + " /Users/takuya_st/moveimage/" + file.getName).lineStream
  }

  def imageSizeCheck(file: File) = {
    try {
      val image = ImageIO.read(file)
      image.getHeight() >= 100 && image.getWidth() >= 100
    } catch {
      case e: IOException => false
    }
  }

}
