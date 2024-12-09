package com.cesar.api.csv
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import jakarta.inject.{Inject, Singleton}
import org.slf4j.LoggerFactory

@Singleton
class CsvControllerImpl @Inject() extends  CsvController {
  private val log = LoggerFactory.getLogger(getClass)

  override def receiveCsv: Route = {
    log.info("Get received")
    complete(StatusCodes.OK, "All works")
  }
}
