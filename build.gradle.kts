import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
  alias(libs.plugins.spring.boot)
  alias(libs.plugins.spring.dependency.management)
  alias(libs.plugins.ktfmt)
  alias(libs.plugins.kotlin.jvm)
  alias(libs.plugins.kotlin.plugin.spring)
  alias(libs.plugins.kotlin.plugin.jpa)
}

group = "gosex-esia"
version = "0.0.1-SNAPSHOT"

java.sourceCompatibility = JavaVersion.VERSION_17

repositories { mavenCentral() }

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-oauth2-authorization-server")
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
  implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
  implementation("org.flywaydb:flyway-core")
  runtimeOnly("org.postgresql:postgresql")
  testImplementation("org.springframework.boot:spring-boot-starter-test") {
    exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
  }
  testImplementation("org.springframework.security:spring-security-test")
}

tasks.withType<KotlinCompile> {
  compilerOptions {
    freeCompilerArgs.set(listOf("-Xjsr305=strict"))
    jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
  }
}

tasks.withType<Test> { useJUnitPlatform() }

fun readEnvFile(envFile: File): Map<String, String> {
  val env = mutableMapOf<String, String>()
  if (envFile.exists()) {
    envFile
        .readLines()
        .filter { it.isNotBlank() && !it.startsWith("#") }
        .forEach { line ->
          val (key, value) = line.split("=", limit = 2)
          env[key.trim()] = value.trim()
        }
  }
  return env
}

tasks.named<BootRun>("bootRun") {
  val envFile = file(".env.dev")
  if (envFile.exists()) {
    environment.putAll(readEnvFile(envFile))
  }
}