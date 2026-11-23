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
├── main.tf        # Recursos Docker (contenedores, volúmenes, red)
├── variables.tf   # Variables con valores por defecto
├── outputs.tf     # URLs de acceso tras el apply
└── README.md      # Este archivo
```

## Uso rápido

```bash
# 1. Ir a la carpeta terraform
cd terraform

# 2. Inicializar provider
terraform init

# 3. Ver qué se va a crear
terraform plan

# 4. Crear la infraestructura
terraform apply -auto-approve

# 5. Ver las URLs resultantes
terraform output
```

## Variables personalizables

Sobrescribe desde la CLI o con un archivo `terraform.tfvars`:

```hcl
# terraform.tfvars  (NO subir al repo – contiene secretos)
mariadb_root_password = "MiPasswordSegura"
jenkins_port          = 8081
sonarqube_port        = 9000
```

O usando variables de entorno:

```bash
export TF_VAR_mariadb_root_password="MiPasswordSegura"
terraform apply -auto-approve
```

## Destruir la infraestructura

```bash
terraform destroy -auto-approve
```

> **Nota:** Los volúmenes Docker persisten datos entre `destroy`/`apply`. Para borrarlos también: `docker volume prune`.

## Lo que crea

| Recurso | Descripción |
|---|---|
| `docker_network.devops_net` | Red interna `devops-net` |
| `docker_container.jenkins` | Jenkins LTS en puerto 8081 |
| `docker_container.sonarqube` | SonarQube Community en puerto 9000 |
| `docker_container.mariadb` | MariaDB 11 en puerto 3306 |
| Volúmenes | `jenkins-home`, `sonarqube-data/logs/extensions`, `mariadb-data` |
