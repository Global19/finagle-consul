package com.twitter.finagle.consul

import scala.collection.mutable

object ConsulServiceFactory {

  private[this] var services: mutable.Map[String, ConsulService] = mutable.Map()

  def getService(hosts: String): ConsulService = {
    synchronized {
      var service = services.getOrElseUpdate(hosts, {
        val newClient = ConsulClientFactory.getClient(hosts)
        new ConsulService(newClient)
      })
      service
    }
  }
}