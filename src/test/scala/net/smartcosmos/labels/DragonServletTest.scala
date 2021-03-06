package net.smartcosmos.labels

import org.junit.runner.RunWith
import org.scalatra.test.specs2.ScalatraSpec
import org.specs2.runner.JUnitRunner
import org.json4s.jackson.Serialization._
import org.json4s.DefaultFormats
import org.json4s.Formats
import net.smartcosmos.labels.models.Dragon
import org.specs2.specification.ForEach

import org.specs2.execute.AsResult
import org.specs2.execute.Result
import java.sql.Connection
import org.scalatra.test.specs2.MutableScalatraSpec
import net.smartcosmos.labels.utils.DB

@RunWith(classOf[JUnitRunner])
class DragonServletTest extends MutableScalatraSpec {
  implicit val jsonFormats: Formats = DefaultFormats
  
      
  addServlet(new DragonServlet, "/dragon")
  
  "GET / on HelloWorldServlet" should {
    "return status 200" in {
      get("/dragon"){
        val resp = read[List[Dragon]](response.body)
        resp must contain(Dragon(1, "BLUE", "Saphira"), Dragon(2, "GREEN", "Puff"))
      }
    }
  }
  
}



trait DatabaseContext extends ForEach[Connection] {
  // you need to define the "foreach" method
  def foreach[R: AsResult](f: Connection => R): Result = {
    DB.rbtx { c => AsResult(f(c)) }
  }
}