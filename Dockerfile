# ===== Stage 1: Build the project using Maven =====
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# ===== Stage 2: Run the JAR using lightweight JRE image =====
FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/target/moneymanager-0.0.1-SNAPSHOT.jar moneymanager-v1.jar
EXPOSE 9090
ENTRYPOINT ["java", "-jar", "moneymanager-v1.jar"]



# Here’s the **detailed explanation** of your Dockerfile and a **commented version** so you can understand it easily:



# ### **🔹 Explanation Line by Line**

# 1️⃣ **`FROM eclipse-temurin:21-jre`**

# * This line chooses a **base image** for your Docker container.
# * `eclipse-temurin:21-jre` → A lightweight image containing Java Runtime Environment (JRE) version 21.
# * This image can run Java applications but cannot compile them (it doesn't have JDK).

# ---

# 2️⃣ **`WORKDIR /app`**

# * Sets the **working directory** inside the container to `/app`.
# * Any following commands (like `COPY`, `RUN`, etc.) will be executed inside this `/app` folder.

# ---

# 3️⃣ **`COPY target/moneymanager-0.0.1-SNAPSHOT.jar moneymanager-v1.jar`**

# * Copies your **built JAR file** from your local machine’s `target` folder into the container's `/app` folder.
# * It also **renames** the file to `moneymanager-v1.jar`.
# * This is the application you built using Maven (`mvn clean package`).

# ---

# 4️⃣ **`EXPOSE 9090`**

# * Informs Docker that your application will listen on **port 9090** inside the container.
# * This doesn’t actually open the port, but it’s a hint for others running your container to know which port to map.

# ---

# 5️⃣ **`ENTRYPOINT ["java","-jar","moneymanager-v1.jar"]`**

# * This defines the **main command** that runs when the container starts.
# * It runs your Spring Boot application by executing:

#   ```bash
#   java -jar moneymanager-v1.jar
#   ```
# * This keeps your application running inside the container.

# ---

# ---

# ### ✅ **Commented Dockerfile (Easy Language)**

# ```dockerfile
# # ✅ Use an official Java 21 runtime image (JRE only, lightweight)
# FROM eclipse-temurin:21-jre

# # ✅ Set the working directory inside the container to /app
# WORKDIR /app

# # ✅ Copy the jar file from the 'target' folder on your computer
# #    into the container and rename it to moneymanager-v1.jar
# COPY target/moneymanager-0.0.1-SNAPSHOT.jar moneymanager-v1.jar

# # ✅ Tell Docker that this application will use port 9090
# EXPOSE 9090

# # ✅ When the container starts, run the jar file using Java
# ENTRYPOINT ["java","-jar","moneymanager-v1.jar"]
# ```

# ---

# ### **🔹 How This Works in Practice**

# 1. Build the JAR file:

#    ```bash
#    mvn clean package
#    ```
# 2. Build the Docker image:

#    ```bash
#    docker build -t moneymanager:v1 .
#    ```
# 3. Run the container:

#    ```bash
#    docker run -p 9090:9090 moneymanager:v1
#    ```
# 4. Your Spring Boot app will be running on:

#    ```
#    http://localhost:9090
#    ```

# ---

# Would you like me to create a **visual diagram (image)** showing how this Dockerfile works step by step (local → Docker image → container → running app)?

