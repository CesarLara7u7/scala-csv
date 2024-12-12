package com.cesar.api.department

trait DepartmentGateway {

  def saveOrUpdate(departmentEntity: Department): Int

  def getAll: Seq[Department]
  def saveDepartments(departments:Seq[Department]):Unit

}
