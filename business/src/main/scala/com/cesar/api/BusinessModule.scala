package com.cesar.api

import com.cesar.api.csv.action.{CsvAction, CsvActionImpl}
import com.google.inject.{AbstractModule, Singleton}

class BusinessModule extends AbstractModule {


  override def configure(): Unit = {
    bind(classOf[CsvAction]).to(classOf[CsvActionImpl]).in(classOf[Singleton])
  }
}
