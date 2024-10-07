import com.google.protobuf.gradle.id
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.protobuf

plugins {
    id("com.google.protobuf")
    id("java-library")
}

dependencies {
    compileOnly("javax.annotation:javax.annotation-api:1.3.2")
    implementation(project(":api"))
    implementation("com.google.protobuf:protobuf-java:${libs.versions.protobuf.get()}")
    implementation(libs.grpc.core)
    implementation(libs.grpc.protobuf)
    implementation(libs.grpc.services)
    implementation(libs.grpc.stub)
    runtimeOnly(libs.grpc.netty)
    testImplementation(libs.assertj)
    testImplementation(libs.junit)
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:${libs.versions.protobuf.get()}"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:${libs.versions.grpc.get()}"
        }
    }
    generateProtoTasks {
        all().forEach {
            it.builtins {
                named("java") { }
            }
            it.plugins {
                id("grpc") { }
            }
        }
    }
}

tasks.test.configure {
    useJUnitPlatform()
}