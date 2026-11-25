variable "jenkins_image" {
  description = "Imagen de Docker para Jenkins"
  type        = string
  default     = "jenkins-with-docker"
}

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
  description = "Contraseña root de MariaDB (inyectada de forma segura vía TF_VAR o terraform.tfvars)"
  type        = string
  sensitive   = true
  default     = ""
}

variable "mariadb_database" {
  description = "Nombre de la base de datos inicial"
  type        = string
  default     = "dbgestor"
}
