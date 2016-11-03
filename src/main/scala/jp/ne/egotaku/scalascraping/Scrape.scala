package jp.ne.egotaku.Scrape

import jp.ne.egotaku.scalascraping.ScrapeService

object Scrape {
  def main(args: Array[String]): Unit = {
    val targetUrl = "https://www.google.co.jp/search?q=%E3%81%B5%E3%81%A8%E3%82%82%E3%82%82&safe=off&espv=2&biw=2374&bih=1263&source=lnms&tbm=isch&sa=X&ved=0ahUKEwji9YrPtuTPAhXhilQKHVvrBHkQ_AUIBigB"
    val scraper = new ScrapeService()
    scraper.request(targetUrl)
  }
}