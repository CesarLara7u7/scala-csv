package com.cesar.api.csv.action

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.common.StrictForm
import akka.http.scaladsl.common.StrictForm.FileData
import akka.stream.alpakka.csv.scaladsl._
import akka.stream.scaladsl.{Sink, Source}
import com.cesar.api.department.{Department, DepartmentGateway}
import com.cesar.api.employee.{EmployeeGateway, HiredEmployee}
import com.cesar.api.job.{Job, JobGateway}
import io.scalaland.chimney.dsl.TransformationOps
import io.scalaland.chimney.syntax.TransformerOps
import jakarta.inject.{Inject, Singleton}
import org.slf4j.LoggerFactory

import java.nio.charset.StandardCharsets
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.util.Try

@Singleton
class CsvActionImpl @Inject()(
                               departmentGateway: DepartmentGateway,
                               jobGateway: JobGateway,
                               employeeGateway: EmployeeGateway
                             )(implicit ac: ActorSystem[Nothing]) extends CsvAction {
  private val log = LoggerFactory.getLogger(getClass)

  private def fileDataIntoMap[T](fileData: FileData, clazz: Class[T]): Seq[Map[String, String]] = Await.result({
    val byteString = fileData.entity.data
    Source
      .single(byteString)
      .via(CsvParsing.lineScanner())
      .via(CsvToMap.withHeadersAsStrings(StandardCharsets.UTF_8, clazz.getDeclaredFields.map(_.getName): _*))
      .runWith(Sink.seq)
  }, Duration.Inf)

  override def readCsv(fileData: StrictForm.FileData): Try[Unit] = Try {
    val result: Seq[Map[String, String]] = fileDataIntoMap(fileData, classOf[Nothing])
    result.foreach(println)
  }

  override def receiveEmployees(fileData: StrictForm.FileData): Try[Unit] = Try {
    log.info("Transforming csv to Employee")
    val employees = fileDataIntoMap(fileData, classOf[HiredEmployee]) map { map =>
      HiredEmployee(
        id = map("id").toInt,
        name = map("name"),
        datetime = map("datetime"),
        departmentId = map("departmentId").toIntOption.getOrElse(0),
        jobId = map("jobId").toIntOption.getOrElse(0)
      )
    }
    val invalid = employees.filter(e => e.name.isEmpty || e.jobId == 0 || e.departmentId == 0 || e.id == 0 || e.datetime.isEmpty)
    log.info("invalid employees=> {}, {}", invalid.size, invalid)
    val valid = employees.filterNot(invalid.contains)
    log.info("valid employees => {}", valid)
    val database = employeeGateway.getAll
    if(database.isEmpty) employeeGateway.saveEmployees(valid)
    else valid.filterNot(database.contains).foreach(employeeGateway.saveOrUpdate)
  }

  override def receiveDepartments(fileData: StrictForm.FileData): Try[Unit] = Try {
    log.info("Transforming csv to Department")
    val departments = fileDataIntoMap(fileData, classOf[Department]) map { map =>
      Department(id = map("id").toInt, department = map("department"))
    }
    val invalid = departments.filter(e => e.department.isEmpty || e.id == 0)
    log.info("invalid departments=> {}, {}", invalid.size, invalid)
    val valid = departments.filterNot(invalid.contains)
    log.info("valid departments => {}", valid.size)
    val database = departmentGateway.getAll
    log.info("WaZAA " + database.size)
    if(database.isEmpty) departmentGateway.saveDepartments(valid)
    else valid.filterNot(database.contains).foreach(departmentGateway.saveOrUpdate)
  }

  override def receiveJobs(fileData: StrictForm.FileData): Try[Unit] = Try {
    log.info("Transforming csv to Job")
    val jobs = fileDataIntoMap(fileData, classOf[Job]).map(map => Job(map("id").toInt, map("job")))
    val invalid = jobs.filter(e => e.job.isEmpty || e.id == 0)
    log.info("invalid jobs=> {}, {}", invalid.size, invalid)
    val valid = jobs.filterNot(invalid.contains)
    log.info("valid jobs => {}", valid.size)
    val database = jobGateway.getAll
    if(database.isEmpty) jobGateway.saveJobs(valid)
    else valid.filterNot(database.contains).foreach(jobGateway.saveOrUpdate)
  }


  override def getJobs: Try[Seq[Job]] = Try {
    jobGateway.getAll
  }

  override def getDepartments: Try[Seq[Department]] = Try {
    departmentGateway.getAll
  }

  override def getEmployees: Try[Seq[HiredEmployee]] = Try {
    employeeGateway.getAll
  }

  override def getStatistics: Try[Seq[Statistics]] = Try{
    employeeGateway.getStatistics.map(_
      .into[Statistics]
      .withFieldComputed(_.department, _._1)
      .withFieldComputed(_.job, _._2)
      .withFieldComputed(_.Q1, _._3)
      .withFieldComputed(_.Q2, _._4)
      .withFieldComputed(_.Q3, _._5)
      .withFieldComputed(_.Q4, _._6)
      .transform
    )
  }

}
