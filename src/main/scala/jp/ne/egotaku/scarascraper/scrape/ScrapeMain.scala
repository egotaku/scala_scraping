import jp.ne.egotaku.scarascraper.scrape.ScrapeImpl

object ScrapeMain {
  def main(args: Array[String]): Unit = {
    val scraper = new ScrapeImpl()
    val targetUrl = "https://www.google.co.jp/search?q=%E3%81%B5%E3%81%A8%E3%82%82%E3%82%82&safe=off&espv=2&biw=2374&bih=1263&source=lnms&tbm=isch&sa=X&ved=0ahUKEwji9YrPtuTPAhXhilQKHVvrBHkQ_AUIBigB"
    //val targetUrl = "http://seiga.nicovideo.jp/illust/ranking/point/hourly/g_fanart"
    scraper.scrape(targetUrl)
  }
}