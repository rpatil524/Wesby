package es.weso.wfLodPortal.sparql.custom

import es.weso.wfLodPortal.Configurable
import es.weso.wfLodPortal.models.RdfLiteral
import es.weso.wfLodPortal.models.OptionalResultQuery
import es.weso.wfLodPortal.models.LazyDataStore
import es.weso.wfLodPortal.models.Model
import es.weso.wfLodPortal.utils.CommonURIS._
import es.weso.wfLodPortal.sparql.ModelLoader
import es.weso.wfLodPortal.models.RdfResource
import scala.collection.mutable.ListBuffer

object YearsCustomQuery extends CustomQuery with Configurable {

  def loadYears(mode: String): List[Int] = {

    val param = mode match {
      case "webindex" => "http://data.webfoundation.org/webindex/v2013/"
      case "odb" => "http://data.webfoundation.org/odb/v2013/"
    }

    def inner(r: RdfResource, orq: OptionalResultQuery): Option[List[Int]] = {
      val uri = r.uri.absolute
      if (uri.contains(param)) {
        Some(loadIndicatorInterval(orq.s.get))
      } else None
    }

    val rs = ModelLoader.loadUri(cex, "Indicator")
    val foo = handleResource[Option[List[Int]]](rs.predicate, rdf, "type", inner _).flatten
    foo.foldLeft(new ListBuffer[Int]())((a, b) => a ++ b).toSet.toList

  }

  protected def loadIndicatorInterval(ls: LazyDataStore[Model]): List[Int] = {
	val dataStore = ls.data
    val starts = firstNodeAsLiteral(dataStore, time, "intervalStarts").toInt
    val intervalFinishes = firstNodeAsLiteral(dataStore, time, "intervalFinishes").toInt
    starts to intervalFinishes toList
  }

}