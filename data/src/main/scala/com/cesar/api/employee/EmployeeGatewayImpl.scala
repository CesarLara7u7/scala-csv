package com.cesar.api.employee

import io.scalaland.chimney.dsl._
import jakarta.inject.{Inject, Singleton}

@Singleton
class EmployeeGatewayImpl @Inject()(employeeRepository: EmployeeRepository) extends EmployeeGateway {

  override def saveOrUpdate(hiredEmployee: HiredEmployee): Int = employeeRepository.saveOrUpdate(hiredEmployee.transformInto[EmployeeEntity])

  override def getAll: Seq[HiredEmployee] = employeeRepository.getAll.map(_.transformInto[HiredEmployee])

  override def saveEmployees(employees: Seq[HiredEmployee]): Unit = employeeRepository.saveEmployees(employees.map(_.transformInto[EmployeeEntity]))

  override def getStatistics: Seq[(String, String, Int, Int,Int,Int)] = employeeRepository.getStatistics
}
