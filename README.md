# 🚀 Bulk Insertion Benchmark in Java

This project implements and benchmarks **six different strategies** for high-volume data insertion using Java and PostgreSQL. The goal is to identify the most efficient method to insert millions of records with varying levels of abstraction—from JPA to raw database access.

---

## 📦 Strategies Covered

- `saveAll()` using Spring Data JPA
- Hibernate batch insertion with `EntityManager`
- Hibernate batch insertion with concurrency
- Native SQL using `JdbcTemplate`
- PL/SQL stored procedure invocation
- PostgreSQL `COPY` via CSV (fastest)

---

## 🛠️ Local Setup

- **Java:** 8
- **Maven:** 3.8.8
- **Database:** PostgreSQL (tested using Docker container)

To spin up a Postgres container:
```bash
docker run --name my-postgres \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=testdb \
  -p 5432:5432 \
  -v pgdata:/var/lib/postgresql/data \
  -d postgres:15
```

---

## ▶️ Running the App

1. **Configure Batch Size**  
   Set the number of records to insert in `src/main/resources/application.properties`:
   ```properties
   benchmark.record.count=100000
   ```

2. **Build the Project**
   ```bash
   mvn clean install
   ```

3. **Run the Application**
   ```bash
   mvn spring-boot:run
   ```

The app will execute all strategies sequentially and log the time taken for each one.

---

## 📈 Benchmark Outputs

Each strategy prints:
- Time taken to insert records
- Number of records inserted
- Strategy name

This makes it easy to compare performance across different methods under identical load.
