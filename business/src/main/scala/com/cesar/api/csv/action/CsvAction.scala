package com.cesar.api.csv.action

import akka.http.scaladsl.common.StrictForm.FileData

import scala.util.Try

trait CsvAction {

  def readCsv(fileData: FileData): Try[Unit]
}
