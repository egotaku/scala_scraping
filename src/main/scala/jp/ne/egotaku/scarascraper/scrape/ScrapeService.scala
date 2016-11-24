package jp.ne.egotaku.scarascraper.scrape

/**
  * Created by satoutakuya on 2016/10/17.
  */

import java.io.File

import akka.actor.ActorRef
import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import scala.concurrent.{ExecutionContext, Future}

class ScrapeImpl(actRef: ActorRef) {
  val browser = JsoupBrowser()
  val pageLimit = 1000
  val depthLimit = 2

  implicit val ec: ExecutionContext = ExecutionContext.Implicits.global

  def scrape(seedUrl: String, pageCount: Int, currentDepth: Int):Future[Unit] = Future {
    if (currentDepth <= depthLimit && pageCount <= pageLimit) {
      val doc = browser.get(seedUrl)
      val images = doc >?> elementList("img")
      val links = doc >?> elementList("a")
      images match {
        case Some(l) => actRef ! l
        case None => List.empty[File]
      }
      links match {
        case Some(l) => l.foreach { lt =>
          scrape(lt.attr("href"), pageCount + 1, currentDepth + 1)
        }
      }
    }
  }
}
