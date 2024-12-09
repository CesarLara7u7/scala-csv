package com.cesar.api

import com.cesar.api.csv.{CsvController, CsvControllerImpl, Routes}
import com.google.inject.{AbstractModule, Singleton}

class ApiModule extends  AbstractModule{

  override def configure(): Unit = {
    bind(classOf[CsvController]).to(classOf[CsvControllerImpl]).in(classOf[Singleton])
    bind(classOf[Routes]).in(classOf[Singleton])
  }
}
