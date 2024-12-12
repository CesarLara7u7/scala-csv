package com.cesar.api.action

import akka.http.scaladsl.common.StrictForm.FileData
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.cesar.api.action.controller.ApiController
import jakarta.inject.{Inject, Singleton}

@Singleton
class ApiRoutes @Inject()(apiController: ApiController) {

  def publicRoutes: Route = concat(
    pathPrefix("csv") {
      post {
        concat(
          pathPrefix("file") {
            extractRequestContext { _ =>
              formFields("csv".as[FileData]) { (fileData) =>
                println(fileData)
                apiController.receiveCsv(fileData)
              }
            }
          },
          pathPrefix("employees") {
            extractRequestContext { _ =>
              formFields("csv".as[FileData]) { (fileData) =>
                apiController.receiveEmployees(fileData)
              }
            }
          },
          pathPrefix("departments") {
            extractRequestContext { _ =>
              formFields("csv".as[FileData]) { (fileData) =>
                apiController.receiveDepartments(fileData)
              }
            }
          },
          pathPrefix("jobs") {
            extractRequestContext { _ =>
              formFields("csv".as[FileData]) { (fileData) =>
                apiController.receiveJobs(fileData)
              }
            }
          }
        )
      }
    },
    pathPrefix("api" / "statistics") {
      get {
        apiController.getStatistics
      }
    }

  )
}
