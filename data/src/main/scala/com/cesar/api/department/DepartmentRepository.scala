package com.cesar.api.department

trait DepartmentRepository {

  def saveOrUpdate(departmentEntity: DepartmentEntity): Int

  def getAll: Seq[DepartmentEntity]

  def saveDepartments(departments:Seq[DepartmentEntity]):Unit

}
