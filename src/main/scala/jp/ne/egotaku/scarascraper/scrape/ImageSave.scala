package jp.ne.egotaku.scarascraper.scrape

import java.io.File

import akka.actor.Actor
import net.ruippeixotog.scalascraper.model.Element

import scala.concurrent.Future
import scalax.io.Resource

/**
  * Created by takuya_st on 2016/11/24.
  */
class ImageSave {

  var sequence = 1

  def download(e: Element) = Future[File] {
    if (e.attr("src").startsWith("http://")) save(e.attr("src"))
  }

  private def save(url: String): File = {
    //println(url)
    val data = Resource.fromURL(url).byteArray
    val file = new File(s"/Users/takuya_st/testimage/$sequence")
    sequence += 1
    Resource.fromFile(file).write(data)
    file
  }
}
