package com.cesar.api.department

import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

class DepartmentTable(tag: Tag) extends Table[DepartmentEntity](tag, "department") {
  def id = column[Int]("id", O.PrimaryKey)

  def department = column[String]("department")

  override def * : ProvenShape[DepartmentEntity] = (id,department).mapTo[DepartmentEntity]
}
