import jp.ne.egotaku.scarascraper.scrape.ScrapeImpl
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


object ScrapeMain {
  def main(args: Array[String]): Unit = {
    val scraper = new ScrapeImpl()
    //val targetUrl = "https://www.google.co.jp/search?q=%E3%81%B5%E3%81%A8%E3%82%82%E3%82%82&safe=off&espv=2&biw=2374&bih=1263&source=lnms&tbm=isch&sa=X&ved=0ahUKEwji9YrPtuTPAhXhilQKHVvrBHkQ_AUIBigB"
    //val targetUrl = "http://seiga.nicovideo.jp/illust/ranking/point/hourly/g_fanart"
    val targetUrls: List[String] = List (
      "http://seiga.nicovideo.jp/tag/%E3%83%9E%E3%82%B7%E3%83%A5%E3%83%BB%E3%82%AD%E3%83%AA%E3%82%A8%E3%83%A9%E3%82%A4%E3%83%88",
      "http://seiga.nicovideo.jp/tag/%E3%82%BB%E3%82%A4%E3%83%90%E3%83%BC",
      "http://seiga.nicovideo.jp/search/%E3%82%A4%E3%83%AA%E3%83%A4?target=illust"
    )

    val start = LocalDateTime.now()
    val f = DateTimeFormatter.ofPattern("HH:mm:ss SSS")
    println(start.format(f))
    //targetUrls.par.map()
    targetUrls.foreach(targetUrl => scraper.scrape(targetUrl))
    //scraper.scrape(targetUrl)
    val end = LocalDateTime.now()
    println(end.format(f))
  }
}