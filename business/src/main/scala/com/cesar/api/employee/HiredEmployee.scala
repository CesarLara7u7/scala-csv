package com.cesar.api.employee

case class HiredEmployee(
                     id: Int,
                     name: String,
                     datetime: String,
                     departmentId: Int,
                     jobId: Int
                   )
