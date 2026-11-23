variable "jenkins_container_name" {
  description = "Nombre del contenedor Jenkins"
  type        = string
  default     = "jenkins"
}

variable "jenkins_port" {
  description = "Puerto HOST expuesto para Jenkins"
  type        = number
  default     = 8081
}

variable "sonarqube_container_name" {
  description = "Nombre del contenedor SonarQube"
  type        = string
  default     = "sonarqube"
}

variable "sonarqube_port" {
  description = "Puerto HOST expuesto para SonarQube"
  type        = number
  default     = 9000
}

variable "mariadb_container_name" {
  description = "Nombre del contenedor MariaDB"
  type        = string
  default     = "dbgestor-mariadb"
}

variable "mariadb_port" {
  description = "Puerto HOST expuesto para MariaDB"
  type        = number
  default     = 3306
}

variable "mariadb_root_password" {
  description = "Contraseña root de MariaDB (inyectar por variable de entorno TF_VAR_mariadb_root_password)"
  type        = string
  sensitive   = true
  default     = "rootpassword"
}

variable "mariadb_database" {
  description = "Nombre de la base de datos inicial"
  type        = string
  default     = "dbgestor"
}
