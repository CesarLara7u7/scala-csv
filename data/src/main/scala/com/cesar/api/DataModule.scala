package com.cesar.api

import com.cesar.api.department.{DepartmentGateway, DepartmentGatewayImpl, DepartmentRepository, DepartmentRepositoryImpl}
import com.cesar.api.employee.{EmployeeGateway, EmployeeGatewayImpl, EmployeeRepository, EmployeeRepositoryImpl}
import com.cesar.api.job.{JobGateway, JobGatewayImpl, JobRepository, JobRepositoryImpl}
import com.google.inject.{AbstractModule, Provides, Singleton}
import com.typesafe.config.Config
import slick.jdbc.JdbcBackend
import slick.jdbc.JdbcBackend.Database

class DataModule(config: Config) extends AbstractModule {

  override def configure(): Unit = {
    bind(classOf[DepartmentRepository]).to(classOf[DepartmentRepositoryImpl]).in(classOf[Singleton])
    bind(classOf[DepartmentGateway]).to(classOf[DepartmentGatewayImpl]).in(classOf[Singleton])
    bind(classOf[EmployeeGateway]).to(classOf[EmployeeGatewayImpl]).in(classOf[Singleton])
    bind(classOf[DepartmentGateway]).to(classOf[DepartmentGatewayImpl]).in(classOf[Singleton])
    bind(classOf[EmployeeRepository]).to(classOf[EmployeeRepositoryImpl]).in(classOf[Singleton])
    bind(classOf[JobGateway]).to(classOf[JobGatewayImpl]).in(classOf[Singleton])
    bind(classOf[JobRepository]).to(classOf[JobRepositoryImpl]).in(classOf[Singleton])
    db
  }

  @Provides
  def db: JdbcBackend.JdbcDatabaseDef = {
    val host: String = config.getString("db.host")
    val port: String = config.getString("db.port")
    val database: String = config.getString("db.name")
    val user: String = config.getString("db.user")
    val password: String = config.getString("db.password")
    val res = Database.forURL(
      url = s"jdbc:postgresql://${host}:${port}/$database",
      user = user,
      password = password,
      driver = "org.postgresql.Driver"
    )
    updateDb(host, port, database, user, password)
    res
  }

  def updateDb(host: String,
               port: String,
               database: String,
               user: String,
               password: String): Unit = {
    import io.github.liquibase4s.{Liquibase, LiquibaseConfig}

    val config: LiquibaseConfig = LiquibaseConfig(
      url = s"jdbc:postgresql://${host}:${port}/$database",
      user = user,
      password = password,
      driver = "org.postgresql.Driver",
      changelog = "liquibase/master.yaml",
    )

    Liquibase(config).migrate() // returns Unit in case of success or throws Exception
  }
}
