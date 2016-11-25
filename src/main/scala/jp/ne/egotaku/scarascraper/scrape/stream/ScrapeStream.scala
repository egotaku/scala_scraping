package jp.ne.egotaku.scarascraper.scrape.stream

/**
  * Created by satoutakuya on 2016/10/18.
  */

import scala.concurrent._
import akka.actor._
import akka.stream._
import akka.stream.scaladsl._
import java.io.{File, IOException}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.imageio.ImageIO

import scala.sys.process._
import jp.ne.egotaku.scarascraper.scrape._
import net.ruippeixotog.scalascraper.model.Element

class ScrapeStream extends Actor {
  implicit val system = ActorSystem("HogeSystem")
  implicit val materializer = ActorMaterializer()
  implicit val ec: ExecutionContext = ExecutionContext.Implicits.global

  override def receive: Receive = {
    case l: List[Element] => if (!l.isEmpty) run(l)
    case other => throw new RuntimeException("none image list")
  }

  private def run(l: List[Element]) = {
    checkAndMove(l).run()
  }

  private def checkAndMove(l: List[Element]) = {
    val imageSave = new ImageSave()
    val source = Source(l).mapAsyncUnordered(3) { e =>
      imageSave.download(e)
    }

    val step = Flow[File].mapAsyncUnordered(3) { file =>
      Future (if (imageSizeCheck(file)) Some(file) else None)
    }

    val sink = Sink.foreachParallel[Option[File]](3) { file =>
      file match {
        case Some(f) => moveImage(f)
        case None => _
      }
    }

    source via step to sink
  }


  val scraper = new ScrapeImpl(system.actorOf(Props(classOf[ImageSave])))

 /* val source = Source(targetUrls).mapAsyncUnordered(3) { url =>
    scraper.scrape(url, 1, 1)
  }*/



  /*
  val s = Source(1 to 10000).mapAsyncUnordered(3) { num =>
    Future(num)
  }

  val step = Flow[Int].mapAsyncUnordered(3) { num =>
    println(num + "です")
    Future(num * 2)
  }

  val sink = Sink.foreach(println)
  val graph = s via step to sink

  def run() = {
    graph.run()
  }*/
  def imageSizeCheck(file: File) = {
    try {
      val image = ImageIO.read(file)
      image.getHeight() >= 100 && image.getWidth() >= 100
    } catch {
      case e: IOException => false
    }
  }

  def moveImage(file: File) = {
    val path = file.getPath()
    Process("mv " + path + " /Users/takuya_st/moveimage/" + file.getName).lineStream
    println(path)

    val f = DateTimeFormatter.ofPattern("HH:mm:ss SSS")
    val end = LocalDateTime.now()
    println(end.format(f))
  }
}
