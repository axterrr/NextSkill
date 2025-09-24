# --- Stage 1: Build ---
FROM gradle:8.10.2-jdk21 AS builder
WORKDIR /NextSkill

COPY build.gradle.kts settings.gradle.kts ./
COPY gradle gradle
RUN gradle build -x test --no-daemon || return 0

COPY . .
RUN gradle clean bootJar --no-daemon

# --- Stage 2: Run ---
FROM eclipse-temurin:21-jdk AS runner
WORKDIR /NextSkill

COPY --from=builder /NextSkill/build/libs/*.jar NextSkill.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "NextSkill.jar"]
