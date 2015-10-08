package models

import org.w3.banana._

/**
 * Created by jorge on 6/10/15.
 */
class Resource[Rdf<:RDF](
  val uri: Rdf#URI,
  val labels: List[String],
  val shapes: List[String]//List[Rdf#URI],
  //  properties: List[Rdf#Node],
  //  inverseProperties: List[Rdf#Node]
  ) {

}
