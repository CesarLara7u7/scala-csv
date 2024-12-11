package com.cesar

import akka.actor.typed.ActorSystem
import com.cesar.api.{ApiModule, BusinessModule}
import com.google.inject.{AbstractModule, Provides}

class Module(ac: ActorSystem[Nothing]) extends AbstractModule{

  override def configure(): Unit = {

    install(new ApiModule())
    install(new BusinessModule())
  }
  @Provides
  implicit def actorSystem: ActorSystem[Nothing] = ac
}
