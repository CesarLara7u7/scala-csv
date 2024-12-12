package com.cesar.api.employee

trait EmployeeRepository {
  def saveOrUpdate(departmentEntity: EmployeeEntity): Int

  def getAll: Seq[EmployeeEntity]

  def saveEmployees(departments:Seq[EmployeeEntity]):Unit

  def getStatistics: Seq[(String, String, Int, Int,Int,Int)]
}
