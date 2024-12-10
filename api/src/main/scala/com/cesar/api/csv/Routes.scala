package com.cesar.api.csv

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import com.cesar.api.csv.action.CsvControllerRoutes
import jakarta.inject.{Inject, Singleton}
@Singleton
class Routes @Inject()(csvControllerRoutes: CsvControllerRoutes) {

  def allRoutes: Route = cors() {
    concat(csvControllerRoutes.publicRoutes)
  }

}
