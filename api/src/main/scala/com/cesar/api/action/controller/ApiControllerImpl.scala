package com.cesar.api.action.controller

import akka.http.scaladsl.common.StrictForm.FileData
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.cesar.api.csv.action.{CsvAction, Statistics}
import jakarta.inject.{Inject, Singleton}
import org.slf4j.LoggerFactory
import spray.json.{DefaultJsonProtocol, JsField, JsObject, JsString}

import scala.util.{Failure, Success}

@Singleton
class ApiControllerImpl @Inject()(csvAction: CsvAction) extends ApiController with SprayJsonSupport with DefaultJsonProtocol {
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
        log.info("Error => {}", ex)
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
        log.info("Error => {}", ex)
        complete(StatusCodes.BadRequest, "File cannot be read")
      case Success(_) =>
        complete(StatusCodes.OK, "File read")
    }
  }


  override def receiveJobs(fileData: FileData): Route = {
    log.info("Jobs received")
    csvAction.receiveJobs(fileData) match {
      case Failure(ex) =>
        log.info("Error => {}", ex)
        complete(StatusCodes.BadRequest, "File cannot be read")
      case Success(_) =>
        complete(StatusCodes.OK, "File read")
    }
  }

  override def getStatistics: Route = {
    log.info("Getting statistics")
    csvAction.getStatistics match {
      case Failure(ex) =>
        log.info("Error => {}", ex)
        complete(StatusCodes.BadRequest, "Something went wrong")
      case Success(res: Seq[Statistics]) =>
        log.info("Result => " + res)
        //        complete(StatusCodes.OK, (StatisticsJsonProtocol.statisticsFormat.write(res)))
        val a: Seq[JsField] = res.zipWithIndex.map(r => r._2.toString -> JsString(r._1.toString))
        complete(StatusCodes.OK, JsObject(a: _*).toString())
    }
  }


}
