package models

import java.io.File

trait IOObject {
    val file: File
    val name = file.getName
}