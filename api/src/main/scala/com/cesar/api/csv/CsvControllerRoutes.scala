package com.cesar.api.csv

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import jakarta.inject.{Inject, Singleton}

@Singleton
class CsvControllerRoutes @Inject()(csvController: CsvController) {

  def publicRoutes:Route = concat(
    pathPrefix("csv"){
      get {
        csvController.receiveCsv
      }
    }
  )
}
