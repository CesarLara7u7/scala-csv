package com.cesar.api.department

import jakarta.inject.{Inject, Singleton}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration
import slick.jdbc.PostgresProfile.api._
@Singleton
class DepartmentRepositoryImpl @Inject()(database: Database)() extends DepartmentRepository {

  val table: TableQuery[DepartmentTable] = TableQuery[DepartmentTable]

  override def saveOrUpdate(departmentEntity: DepartmentEntity): Int = Await.result({
    database.run(table.insertOrUpdate(departmentEntity))
  }, Duration.Inf)

  override def getAll: Seq[DepartmentEntity] = Await.result({
    database.run(table.result)
  }, Duration.Inf)

  override def saveDepartments(departments: Seq[DepartmentEntity]): Unit = Await.result({
    database.run(table.insertAll(departments).map(_.sum))
  }, Duration.Inf)

}
