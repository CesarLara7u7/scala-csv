package com.cesar.api.csv.action.controller

import akka.http.scaladsl.common.StrictForm.FileData
import akka.http.scaladsl.server.Route

trait CsvController {

  def receiveCsv(fileData: FileData):Route
}
