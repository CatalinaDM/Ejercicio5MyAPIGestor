# Terraform – IaC para Ejercicio5 DevOps

Aprovisiona el entorno CI local con **Jenkins**, **SonarQube** y **MariaDB** usando el provider Docker de Terraform.

## Requisitos previos

| Herramienta | Versión mínima |
|---|---|
| [Terraform](https://developer.hashicorp.com/terraform/install) | 1.6+ |
| Docker Desktop (Windows) | 4.x |

## Estructura

```
terraform/
├── main.tf        # Recursos Docker (contenedores, volúmenes, red calidad-red)
├── variables.tf   # Variables con valores por defecto sincronizados
├── outputs.tf     # URLs de acceso tras el apply
└── README.md      # Guía de aprovisionamiento
```

## Uso rápido

```bash
# 1. Ir a la carpeta terraform
cd terraform

# 2. Inicializar provider
terraform init
# (O si usas el ejecutable local: .\terraform.exe init)

# 3. Ver qué se va a crear
terraform plan
# (Verifica los 12 recursos: red calidad-red, volúmenes y contenedores)

# 4. Crear la infraestructura
terraform apply -auto-approve

# 5. Ver las URLs resultantes
terraform output
```

## Variables personalizables

Sobrescribe desde la CLI o con un archivo `terraform.tfvars`:

```hcl
# terraform.tfvars  (NO subir al repo – contiene secretos)
mariadb_root_password = "TuPasswordSegura"
jenkins_port          = 8081
sonarqube_port        = 9000
mariadb_port          = 3306
```

O usando variables de entorno de Terraform:

```bash
export TF_VAR_mariadb_root_password="TuPasswordSegura"
terraform apply -auto-approve
```

## Destruir la infraestructura

```bash
terraform destroy -auto-approve
```

> **Nota:** Los volúmenes Docker (`jenkins_home`, `dbgestor_data`, `sonarqube_*`) persisten los datos de las bases de datos y configuraciones entre comandos `destroy` y `apply`.

## Recursos aprovisionados en Docker

| Recurso | Tipo | Nombre en Docker | Descripción |
|---|---|---|---|
| `docker_network.calidad_net` | Network | `calidad-red` | Red interna puente (Bridge) para intercomunicación segura entre contenedores |
| `docker_volume.jenkins_home` | Volume | `jenkins_home` | Persistencia de jobs, pipelines, plugins y credenciales de Jenkins |
| `docker_volume.mariadb_data` | Volume | `dbgestor_data` | Persistencia de datos relacionales de MariaDB/MySQL |
| `docker_volume.sonarqube_*` | Volumes | `sonarqube_data`, `logs`, `extensions` | Persistencia de perfiles de calidad y análisis de SonarQube |
| `docker_container.jenkins` | Container | `jenkins` | Servidor CI/CD en puerto 8081 con acceso a `/var/run/docker.sock` |
| `docker_container.sonarqube` | Container | `sonarqube` | Servidor de inspección de código en puerto 9000 |
| `docker_container.mariadb` | Container | `dbgestor-mariadb` | Servidor de base de datos relacional en puerto 3306 |
