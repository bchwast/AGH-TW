package webcrawler

import scala.concurrent.*
import concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.*
import scala.io.StdIn.readLine

object WebCrawler extends App {
  import scala.io.Source
  import org.htmlcleaner.HtmlCleaner
  import java.net.URL

  def crawlOne(url: String, lvl: Int): Future[Array[String]] = Future {
    val cleaner = new HtmlCleaner

    val rootNode = cleaner.clean(new URL(url))

    val elements = rootNode
      .getElementsByName("a", true)
      .map(_.getAttributeByName("href")).map {
      case ent if !(ent contains "http") => url + "/" + ent
      case ent => ent
    }

    if (elements.length > 0) println("lvl: " + lvl + " url: " + url + elements.mkString(" (", ", ", ")"))
    elements
  }
  
  def dive(url: String, lvl: Int, maxLvl: Int): Future[Unit] = Future {
    if (lvl < maxLvl) {
      crawlOne(url, lvl) flatMap {
        urls => Future.sequence(urls map {one => dive(one, lvl + 1, maxLvl)})
      }
    }
  }

  val url = "https://home.agh.edu.pl/~faliszew/"
  val crawler = dive(url, 0, 3)
  Await.result(crawler, 1.minute)
  readLine()
}