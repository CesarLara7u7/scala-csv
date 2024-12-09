package com.cesar.api.csv

import akka.http.scaladsl.server.Route

trait CsvController {

  def receiveCsv:Route
}
