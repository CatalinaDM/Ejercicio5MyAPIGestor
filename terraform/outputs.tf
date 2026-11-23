output "jenkins_url" {
  description = "URL local de Jenkins"
  value       = "http://localhost:${var.jenkins_port}"
}

output "sonarqube_url" {
  description = "URL local de SonarQube"
  value       = "http://localhost:${var.sonarqube_port}"
}

output "mariadb_host" {
  description = "Host:Puerto local de MariaDB"
  value       = "localhost:${var.mariadb_port}"
}

output "jenkins_container_id" {
  description = "ID del contenedor Jenkins"
  value       = docker_container.jenkins.id
}

output "sonarqube_container_id" {
  description = "ID del contenedor SonarQube"
  value       = docker_container.sonarqube.id
}
