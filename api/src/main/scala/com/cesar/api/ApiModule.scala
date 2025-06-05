package com.cesar.api

import com.cesar.api.action.controller.{ApiController, ApiControllerImpl}
import com.google.inject.{AbstractModule, Singleton}

class ApiModule extends  AbstractModule{

  override def configure(): Unit = {

    bind(classOf[ApiController]).to(classOf[ApiControllerImpl]).in(classOf[Singleton])
    bind(classOf[Routes]).in(classOf[Singleton])
  }
}
