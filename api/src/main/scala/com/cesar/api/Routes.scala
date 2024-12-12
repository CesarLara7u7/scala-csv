package com.cesar.api

import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import com.cesar.api.action.ApiRoutes
import jakarta.inject.{Inject, Singleton}
@Singleton
class Routes @Inject()(csvControllerRoutes: ApiRoutes) {

  def allRoutes: Route = cors() {
    concat(csvControllerRoutes.publicRoutes)
  }

}
