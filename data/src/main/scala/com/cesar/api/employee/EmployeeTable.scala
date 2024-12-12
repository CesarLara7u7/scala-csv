package com.cesar.api.employee

import com.cesar.api.department.DepartmentTable
import com.cesar.api.job.JobTable
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape

class EmployeeTable(tag: Tag) extends Table[EmployeeEntity](tag, "hired_employees") {
  def id = column[Int]("id", O.PrimaryKey)

  def name = column[String]("name")

  def datetime = column[String]("datetime")

  def departmentId = column[Int]("department_id")

  def jobId = column[Int]("job_id")

  def departmentFK = foreignKey("hired_employees_department_id_fkey", departmentId, TableQuery[DepartmentTable])(_.id)
  def fk = foreignKey("hired_employees_job_id_fkey", jobId, TableQuery[JobTable])(_.id)

  override def * : ProvenShape[EmployeeEntity] = (id,name,datetime,departmentId,jobId).mapTo[EmployeeEntity]
}
