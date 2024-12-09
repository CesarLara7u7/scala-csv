package com.cesar.api.csv

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import jakarta.inject.{Inject, Singleton}
@Singleton
class Routes @Inject()(csvControllerRoutes: CsvControllerRoutes) {

  def allRoutes: Route = cors() {
    concat(csvControllerRoutes.publicRoutes)
  }

}
