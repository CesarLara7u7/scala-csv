package com.cesar.api.csv
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import jakarta.inject.{Inject, Singleton}

@Singleton
class CsvControllerImpl @Inject() extends  CsvController {

  override def receiveCsv: Route = complete(StatusCodes.OK, "All works")
}
