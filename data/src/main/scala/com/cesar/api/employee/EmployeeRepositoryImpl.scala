package com.cesar.api.employee

import jakarta.inject.{Inject, Singleton}
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.Await
import scala.concurrent.duration.Duration

@Singleton
class EmployeeRepositoryImpl @Inject()(database: Database) extends EmployeeRepository {

  val table: TableQuery[EmployeeTable] = TableQuery[EmployeeTable]

  override def saveOrUpdate(departmentEntity: EmployeeEntity): Int = Await.result({
    database.run(table.insertOrUpdate(departmentEntity))
  }, Duration.Inf)

  override def getAll: Seq[EmployeeEntity] = Await.result({
    database.run(table.result)
  }, Duration.Inf)

  override def saveEmployees(departments: Seq[EmployeeEntity]): Unit = Await.result({
    database.run(table.insertAll(departments))
  }, Duration.Inf)


  override def getStatistics: Seq[(String, String, Int, Int,Int,Int)] = Await.result({
    val action = sql"""#${"""SELECT
                            |    department,
                            |    job,
                            |    SUM(CASE WHEN cuarter in (1,2,3) THEN 1 ELSE 0 END) AS Q1,
                            |    SUM(CASE WHEN cuarter in (4,5,6) THEN 1 ELSE 0 END) AS Q2,
                            |    SUM(CASE WHEN cuarter in (7,8,9) THEN 1 ELSE 0 END) AS Q3,
                            |    SUM(CASE WHEN cuarter in (10,11,12) THEN 1 ELSE 0 END) AS Q4
                            |FROM (
                            |    SELECT
                            |        d.department,
                            |        j.job,
                            |        CAST(SPLIT_PART(he.datetime, '-', 2) AS INT) AS cuarter
                            |    FROM
                            |        hired_employees he
                            |    INNER JOIN
                            |        job j ON he.job_id = j.id
                            |    INNER JOIN
                            |        department d ON he.department_id = d.id
                            |) AS t
                            |GROUP BY
                            |    department, job
                            |ORDER BY
                            |    department, job
                            |    ;
                            |
                            |""".stripMargin}""".as[(String, String, Int, Int,Int,Int)]
    database.run(action)
  }, Duration.Inf)
}
