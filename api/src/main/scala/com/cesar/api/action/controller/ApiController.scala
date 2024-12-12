package com.cesar.api.action.controller

import akka.http.scaladsl.common.StrictForm.FileData
import akka.http.scaladsl.server.Route

trait ApiController {

  def receiveCsv(fileData: FileData): Route

  def receiveEmployees(fileData: FileData): Route

  def receiveDepartments(fileData: FileData): Route

  def receiveJobs(fileData: FileData): Route

  def getStatistics: Route
}
