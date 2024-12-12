package com.cesar.api.csv.action

import akka.http.scaladsl.common.StrictForm.FileData
import com.cesar.api.department.Department
import com.cesar.api.employee.HiredEmployee
import com.cesar.api.job.Job

import scala.util.Try

trait CsvAction {

  def readCsv(fileData: FileData): Try[Unit]

  def receiveEmployees(fileData: FileData): Try[Unit]

  def receiveDepartments(fileData: FileData): Try[Unit]

  def receiveJobs(fileData: FileData): Try[Unit]

  def getJobs: Try[Seq[Job]]

  def getDepartments: Try[Seq[Department]]

  def getEmployees: Try[Seq[HiredEmployee]]

  def getStatistics: Try[Seq[Statistics]]
}
