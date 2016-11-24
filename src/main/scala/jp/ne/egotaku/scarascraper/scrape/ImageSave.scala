package jp.ne.egotaku.scarascraper.scrape

import java.io.File

import akka.actor.Actor
import net.ruippeixotog.scalascraper.model.Element

import scalax.io.Resource

/**
  * Created by takuya_st on 2016/11/24.
  */
class ImageSave extends Actor {

  var sequence = 1

  override def receive: Receive = {
    case l: List[Element] => if (!l.isEmpty) download(l)
    case other => throw new RuntimeException("none image list")
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
