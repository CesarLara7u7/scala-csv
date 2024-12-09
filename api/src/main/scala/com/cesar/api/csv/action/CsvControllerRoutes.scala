package com.cesar.api.csv.action

import akka.http.scaladsl.common.StrictForm.FileData
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.cesar.api.csv.action.controller.CsvController
import jakarta.inject.{Inject, Singleton}

@Singleton
class CsvControllerRoutes @Inject()(csvController: CsvController) {

  def publicRoutes: Route = concat(
    pathPrefix("csv") {
      concat(
        post {
          pathPrefix("file") {
            extractRequestContext { _ =>
              formFields("csv".as[FileData]) { (fileData) =>
                println(fileData)
                csvController.receiveCsv(fileData)
              }
            }
          }
        }
      )
    },
  )
}
