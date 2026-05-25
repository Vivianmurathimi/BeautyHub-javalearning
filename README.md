# 💄 BeautyHub Backend
> Where Style Meets Technology

A Spring Boot 3.x backend application modelling a beauty products supply chain — from suppliers through shop owners to end customers — with a full REST API and Thymeleaf web interface.

---

## 🏪 Business Model
```
Company (supplier) → ShopOwner (reseller) → Person (customer)
Company → can also sell directly → Person
```

---

## 🛠 Tech Stack
| Technology | Purpose |
|---|---|
| Java 17 + Spring Boot 3.3.0 | Core framework |
| Spring Data JPA + H2 | Database ORM + in-memory DB |
| Spring Security | Authentication & authorization |
| Thymeleaf + Bootstrap 5 | Web interface |
| Docker | Containerization |
| Maven | Build tool |

---

## 🗄 Database Relations
| Relation | Where |
|---|---|
| `OneToOne` | Company → Country (one company HQ'd in one country) |
| `OneToMany` | Country → ShopOwner, Country → Person |
| `ManyToOne` | ShopOwner → Country, Product → Company |
| `ManyToMany` | ShopOwner ↔ Product (inventory) |

---

## 🔒 Security
| Role | Username | Password | Can Delete |
|---|---|---|---|
| ADMIN | `admin` | `admin123` | ✅ Yes |
| USER | `user` | `user123` | ❌ No |

---

## 🚀 Getting Started

```bash
# Run
./mvnw spring-boot:run

# Test
./mvnw test

# Docker
./mvnw clean package -DskipTests
docker build -t beauty-hub-backend .
docker run -p 8080:8080 beauty-hub-backend
```

| URL | Description |
|---|---|
| `http://localhost:8080` | Web interface |
| `http://localhost:8080/h2-console` | Database console |
| `http://localhost:8080/api/countries` | REST API |

---

## ✅ Requirements
- ✅ Spring Boot 3.x
- ✅ SQL Database with JPA (H2 + Hibernate)
- ✅ All 4 DB Relations
- ✅ Thymeleaf Web Interface with Security
- ✅ REST CRUD API for all entities
- ✅ GitLab: [beauty-hub-backend](https://gitlab.com/vivianmurathimi/beauty-hub-backend)
- ✅ 25 Automated Tests passing

---

**Vivian Murathimi** | [@vivianmurathimi](https://gitlab.com/vivianmurathimi)