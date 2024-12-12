package com.cesar.api.department

import io.scalaland.chimney.dsl._
import jakarta.inject.{Inject, Singleton}

@Singleton
class DepartmentGatewayImpl @Inject()(departmentRepository: DepartmentRepository) extends DepartmentGateway {

  override def saveOrUpdate(departmentEntity: Department): Int = departmentRepository.saveOrUpdate(departmentEntity.transformInto[DepartmentEntity])

  override def getAll: Seq[Department] = departmentRepository.getAll.map(_.transformInto[Department])

  override def saveDepartments(departments: Seq[Department]): Unit = departmentRepository.saveDepartments(departments.map(_.transformInto[DepartmentEntity]))
}
