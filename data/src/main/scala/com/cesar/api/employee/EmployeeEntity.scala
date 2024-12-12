package com.cesar.api.employee

case class EmployeeEntity(
                           id: Int,
                           name: String,
                           datetime: String,
                           departmentId: Int,
                           jobId: Int
                         )
