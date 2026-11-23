# ============================================================
# Ejercicio5 DevOps – Infraestructura como Código (IaC)
# Proveedor: Docker (local)
# Aprovisiona: Jenkins, SonarQube y MariaDB locales
# ============================================================

terraform {
  required_version = ">= 1.6.0"
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0"
    }
  }
}

provider "docker" {}

# ---- Red interna compartida --------------------------------
resource "docker_network" "devops_net" {
  name = "devops-net"
}

# ---- Volúmenes persistentes --------------------------------
resource "docker_volume" "jenkins_home" {
  name = "jenkins-home"
}

resource "docker_volume" "sonarqube_data" {
  name = "sonarqube-data"
}

resource "docker_volume" "sonarqube_logs" {
  name = "sonarqube-logs"
}

resource "docker_volume" "sonarqube_extensions" {
  name = "sonarqube-extensions"
}

resource "docker_volume" "mariadb_data" {
  name = "mariadb-data"
}

# ---- Imágenes ----------------------------------------------
resource "docker_image" "jenkins" {
  name         = "jenkins/jenkins:lts-jdk21"
  keep_locally = true
}

resource "docker_image" "sonarqube" {
  name         = "sonarqube:community"
  keep_locally = true
}

resource "docker_image" "mariadb" {
  name         = "mariadb:11"
  keep_locally = true
}

# ---- Jenkins -----------------------------------------------
resource "docker_container" "jenkins" {
  name  = var.jenkins_container_name
  image = docker_image.jenkins.image_id

  restart = "unless-stopped"

  ports {
    internal = 8080
    external = var.jenkins_port
  }
  ports {
    internal = 50000
    external = 50000
  }

  volumes {
    volume_name    = docker_volume.jenkins_home.name
    container_path = "/var/jenkins_home"
  }

  # Acceso al socket de Docker para builds in-container
  volumes {
    host_path      = "/var/run/docker.sock"
    container_path = "/var/run/docker.sock"
  }

  networks_advanced {
    name = docker_network.devops_net.name
  }
}

# ---- SonarQube ---------------------------------------------
resource "docker_container" "sonarqube" {
  name  = var.sonarqube_container_name
  image = docker_image.sonarqube.image_id

  restart = "unless-stopped"

  ports {
    internal = 9000
    external = var.sonarqube_port
  }

  volumes {
    volume_name    = docker_volume.sonarqube_data.name
    container_path = "/opt/sonarqube/data"
  }
  volumes {
    volume_name    = docker_volume.sonarqube_logs.name
    container_path = "/opt/sonarqube/logs"
  }
  volumes {
    volume_name    = docker_volume.sonarqube_extensions.name
    container_path = "/opt/sonarqube/extensions"
  }

  env = [
    "SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true"
  ]

  networks_advanced {
    name = docker_network.devops_net.name
  }
}

# ---- MariaDB (entorno local de desarrollo) -----------------
resource "docker_container" "mariadb" {
  name  = var.mariadb_container_name
  image = docker_image.mariadb.image_id

  restart = "unless-stopped"

  ports {
    internal = 3306
    external = var.mariadb_port
  }

  volumes {
    volume_name    = docker_volume.mariadb_data.name
    container_path = "/var/lib/mysql"
  }

  env = [
    "MARIADB_ROOT_PASSWORD=${var.mariadb_root_password}",
    "MARIADB_DATABASE=${var.mariadb_database}"
  ]

  networks_advanced {
    name = docker_network.devops_net.name
  }
}
