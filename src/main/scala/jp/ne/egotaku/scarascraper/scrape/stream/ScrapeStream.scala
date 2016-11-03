package jp.ne.egotaku.scarascraper.scrape.stream

/**
  * Created by satoutakuya on 2016/10/18.
  */

import scala.concurrent._
import akka.actor._
import akka.stream._
import akka.stream.scaladsl._
import java.io.{File, IOException}
import javax.imageio.ImageIO

import jp.ne.egotaku.scarascraper.scrape._

class ScrapeStream {
  implicit val system = ActorSystem("HogeSystem")
  implicit val materializer = ActorMaterializer()
  implicit val ec: ExecutionContext = ExecutionContext.Implicits.global
  val targetUrls: List[String] = List (
    "http://seiga.nicovideo.jp/tag/%E3%83%9E%E3%82%B7%E3%83%A5%E3%83%BB%E3%82%AD%E3%83%AA%E3%82%A8%E3%83%A9%E3%82%A4%E3%83%88",
    "http://seiga.nicovideo.jp/tag/%E3%82%BB%E3%82%A4%E3%83%90%E3%83%BC",
    "http://seiga.nicovideo.jp/search/%E3%82%A4%E3%83%AA%E3%83%A4?target=illust"
  )
  val scraper = new ScrapeImpl()
  val source = Source(targetUrls).mapAsyncUnordered(3) {url =>
    scraper.scrape(url)
  }

  val step = Flow[List[File]].mapAsyncUnordered(3) {files =>
    files.map(file => if (imageSizeCheck(file)) file)
  }

  def imageSizeCheck(file: File) = {
    try {
      val image = ImageIO.read(file)
      if (image.getHeight() <= 50 || image.getWidth() <= 50) {
        false
      }
      true
    } catch {
      case e: IOException => false
    }
  }
}
