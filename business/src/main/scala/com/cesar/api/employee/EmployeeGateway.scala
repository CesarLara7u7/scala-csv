package com.cesar.api.employee

trait EmployeeGateway {

  def saveOrUpdate(hiredEmployee: HiredEmployee): Int

  def getAll: Seq[HiredEmployee]

  def saveEmployees(employees: Seq[HiredEmployee]): Unit

  def getStatistics: Seq[(String, String, Int, Int,Int,Int)]
}
