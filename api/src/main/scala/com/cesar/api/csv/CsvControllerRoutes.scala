package com.cesar.api.csv

import akka.http.scaladsl.common.StrictForm.FileData
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import jakarta.inject.{Inject, Singleton}

import java.io.ByteArrayOutputStream

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
                complete(StatusCodes.OK)
              }
            }
          }
        },
        get {
          csvController.receiveCsv
        },
      )
    },
  )
}
