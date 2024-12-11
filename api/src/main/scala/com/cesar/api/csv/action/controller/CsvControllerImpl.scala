package com.cesar.api.csv.action.controller

import akka.http.scaladsl.common.StrictForm.FileData
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.cesar.api.csv.action.CsvAction
import jakarta.inject.{Inject, Singleton}
import org.slf4j.LoggerFactory

import scala.util.{Failure, Success}

@Singleton
class CsvControllerImpl @Inject()(csvAction: CsvAction) extends  CsvController {
  private val log = LoggerFactory.getLogger(getClass)

  override def receiveCsv(fileData: FileData): Route = {
    log.info("File received")
    csvAction.readCsv(fileData) match {
      case Failure(_) =>
        complete(StatusCodes.BadRequest, "File cannot be read")
      case Success(_) =>
        complete(StatusCodes.OK, "File read")
    }
  }

  override def receiveEmployees(fileData: FileData): Route = {
    log.info("Employees hired received")
    csvAction.receiveEmployees(fileData) match {
      case Failure(ex) =>
        log.info("Error => {}", ex )
        complete(StatusCodes.BadRequest, "File cannot be read")
      case Success(_) =>
        log.info("All works Ok")
        complete(StatusCodes.OK, "File read")
    }
  }


  override def receiveDepartments(fileData: FileData): Route = {
    log.info("Departments received")
    csvAction.receiveDepartments(fileData) match {
      case Failure(ex) =>
        log.info("Error => {}", ex )
        complete(StatusCodes.BadRequest, "File cannot be read")
      case Success(_) =>
        complete(StatusCodes.OK, "File read")
    }
  }


  override def receiveJobs(fileData: FileData): Route = {
    log.info("Jobs received")
    csvAction.receiveJobs(fileData) match {
      case Failure(ex) =>
        log.info("Error => {}", ex )
        complete(StatusCodes.BadRequest, "File cannot be read")
      case Success(_) =>
        complete(StatusCodes.OK, "File read")
    }
  }

}
