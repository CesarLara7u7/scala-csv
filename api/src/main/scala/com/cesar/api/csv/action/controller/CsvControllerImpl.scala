package com.cesar.api.csv.action.controller

import akka.http.scaladsl.common.StrictForm.FileData
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import com.cesar.api.csv.action.CsvAction
import jakarta.inject.{Inject, Singleton}
import org.slf4j.LoggerFactory

@Singleton
class CsvControllerImpl @Inject()(csvAction: CsvAction) extends  CsvController {
  private val log = LoggerFactory.getLogger(getClass)

  override def receiveCsv(fileData: FileData): Route = {
    log.info("Get received")
    val result = csvAction.readCsv(fileData)
    complete(StatusCodes.OK, "All works")
  }
}
